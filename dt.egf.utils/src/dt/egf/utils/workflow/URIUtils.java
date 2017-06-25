package dt.egf.utils.workflow;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;

public class URIUtils {
	private URIUtils() {
	}

	public static URI getResourceURI(IResource resource) {
		return URI.createPlatformResourceURI(resource.getFullPath().toString(), true);
	}
	
	public static IResource getResource(URI resourceURI) {
		IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
		
		if(resourceURI.isPlatformResource()) {
			String wsRelativePath = resourceURI.toPlatformString(true);
			return wsRoot.findMember(new Path(wsRelativePath));			
		} else if(resourceURI.isFile() && !resourceURI.isRelative()) {
            IFile[] files;
			try {
				files = wsRoot.findFilesForLocationURI(new java.net.URI(resourceURI.toString()));
	            if (files.length > 0) {
	            	return files[0];
	            }
				
			} catch (URISyntaxException e) {
				// do nothing just indicate we could not resolve to a resource
				return null;
			}
		}

		return null;
	}
	
	public static IFile getFile(URI uri) {
		IResource resource = getResource(uri);
		return resource instanceof IFile ? (IFile) resource : null;
	}

	public static void refresh(URI uri) {
	    IFile file = URIUtils.getFile(uri);
	    if(file != null) {
	        try {
	            file.getParent().refreshLocal(IResource.DEPTH_ONE, null);
	        } 
	        catch (CoreException e) {
	        }
	    }
	}

	public static URI getWeakestResolvablePlatformURI(URI resourceURI) {
		if (resourceURI.isPlatform()) {
			URI platformURI = resourceURI;
			// try weakest first (can be local workspace)
			if (resourceURI.isPlatformPlugin()) {
				platformURI = togglePlatformURI(resourceURI);
			}
			IResource rs = getResource(platformURI);
			if (rs != null) {
				if (rs.getProject().isOpen()) {
					return platformURI;
				}
			}
			// else try plugin variant
			platformURI = resourceURI;
			if (resourceURI.isPlatformResource()) {
				platformURI = togglePlatformURI(resourceURI);
			}
			if (getResource(platformURI) != null) {
				return platformURI;
			} else {
				if (getFileURI(platformURI) != null) {
					return platformURI;
				}
			}
		}
		return resourceURI;
	}
	
	public static URI togglePlatformURI(URI uri) {
		if (uri.isPlatform()) {
			URI plugin = URI.createURI("platform:/plugin/");
			URI resource = URI.createURI("platform:/resource/");
			if (uri.isPlatformPlugin()) {
				return uri.replacePrefix(plugin, resource);
			} else {
				return uri.replacePrefix(resource, plugin);
			}
		}
		return uri;
	}
	
	public static URI getSchemeURI(URI uri) {
		if (uri != null) {
			if (uri.scheme() == null) {
				return URI.createPlatformResourceURI(uri.toString(), true);
			} else {
				if (uri.scheme().length() == 1) {
					return URI.createFileURI(uri.toString());
				}
			}
		}
		return uri;
	}
	
	public static URI getFileURI(URI uri) {
		URI suri = getSchemeURI(uri);
		if (suri.isPlatformResource()) {
			IResource rs = URIUtils.getResource(suri);
			if (rs == null) return null;
			return URI.createFileURI(rs.getLocation().toString());
		} else if (suri.isPlatformPlugin()) {
			String symbolicName = getProjectname(suri);
			String path = null;
			URI subURI = getProjectSubFilePathURI(suri);
			if (subURI != null) {
				path = subURI.toString();
			}
			return URIUtils.getBundlePathURI(symbolicName, path);
		} else if (suri.toFileString() != null) {
			return suri;
		}
		return null;
	}
	
	public static URI getProjectSubFilePathURI(URI uri) {
		URI trimmedURI = uri.trimFragment().trimQuery();
		if (uri.isPlatform() && trimmedURI.segmentCount() > 2) {
			List<String> s = uri.segmentsList().subList(2, uri.segmentCount());
			String[] segments = s.toArray(new String[s.size()]);
			return URI.createHierarchicalURI(segments, null, null);
		}
		return null;
	}

	public static String getProjectname(URI uri) {
		String projectName = "";
		if ((uri.toPlatformString(false) != null) && uri.segmentCount() >= 2) {
			projectName = uri.segment(1);
		}
		return projectName;
	}

	public static URI getBundlePathURI(String symbolicName, String path) {
		URI result = null;
		Bundle bundle = Platform.getBundle(symbolicName);
		String subpath = path == null ? "/" : path;
		URL furl = (FileLocator.find(bundle, new Path(subpath),
					null));
		if (furl != null) {
			try {
				furl = FileLocator.toFileURL(furl);
				File f = URIUtil.toFile(furl.toURI());
				result = URI.createFileURI(f.getPath());
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	
	public static String getWorkspaceRootPath() {
		try {
			return ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
		} catch (Exception e) {
			return "";
		}
	}

	public static String getPortableString(String path) {
		if (path == null) return null;
		return new Path(path).toPortableString();
	}
	public static String getOSString(String path) {
		if (path == null) return null;
		return new Path(path).toOSString();
	}
	
	/**
	 * HAMI: Copied from task resources portfolio for convenience (limiting dependencies)
	 * @param projectName
	 * @param folder
	 * @param fileName
	 * @return
	 * @throws CoreException
	 */
	  /*
	   * Get file where contents is to be written
	   * Not existing folders are created
	   */
	  public static IFile getNewFile(String projectName, String folder, String fileName) throws CoreException {
	    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	    IProject project = root.getProject(projectName);

	    // build the class resource path
	    IPath path = new Path(""); //$NON-NLS-1$

	    if (folder != null && folder.trim().length() > 0)
	      for (String member : folder.split("\\.|/|\\\\")) { //$NON-NLS-1$
	        path = path.append(member);
	        IFolder aFolder = project.getFolder(path);
	        if (!aFolder.exists())
	          aFolder.create(true, true, null);
	      }

	    path = path.append(fileName);

	    return project.getFile(path);
	  }
	  /*
	   * Get folder where contents is to be written
	   * Not existing folders are created
	   */
	  public static IPath getNewPath(String projectName, String folder) throws CoreException {
	    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	    IProject project = root.getProject(projectName);

	    // build the class resource path
	    IPath path = new Path(""); //$NON-NLS-1$

	    if (folder != null && folder.trim().length() > 0)
	      for (String member : folder.split("\\.|/|\\\\")) { //$NON-NLS-1$
	        path = path.append(member);
	        IFolder aFolder = project.getFolder(path);
	        if (!aFolder.exists())
	          aFolder.create(true, true, null);
	      }

	    return path;
	  }

}
