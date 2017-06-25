package dt.egf.utils.workflow;

import java.io.InputStream;
import java.net.URL;

import org.eclipse.emf.mwe.core.resources.AbstractResourceLoader;
import org.eclipse.emf.mwe.core.resources.ResourceLoader;
import org.eclipse.emf.mwe.core.resources.ResourceLoaderDefaultImpl;

public class MweWSOverridingResourceLoader extends AbstractResourceLoader {
	public static final String OVERRIDING_BUNDLE_NAME = "nspyre.egf.portfolio.task.general.workflow.MweWSOverridingResourceLoader.OVERRIDING_BUNDLE_NAME";
	public static final String OVERRIDING_BUNDLE_ROOT = "nspyre.egf.portfolio.task.general.workflow.MweWSOverridingResourceLoader.OVERRIDING_BUNDLE_ROOT";
	
	protected String bundleName = System.getProperty(OVERRIDING_BUNDLE_NAME);
	protected String bundleRoot = System.getProperty(OVERRIDING_BUNDLE_ROOT);
	protected ResourceLoader delegate = new ResourceLoaderDefaultImpl();
	
	private boolean applyOverride = true;
	
	protected void setOverride(boolean override) {
		applyOverride = override;
	}
	
	private String composedPath(String path, String subpath) {
		String pathSep = "/";
		String composedPath = path + subpath;
		if (!path.endsWith(pathSep) && !subpath.startsWith(pathSep)) {
			composedPath = path + pathSep + subpath;
		}
		return composedPath.replaceAll(pathSep + pathSep, pathSep);
	}
	
	private URL getURL(String path) {
		
		try {
			URL url = new URL(path);
			if (url.getContent() != null) {
				return url;
			}
		} catch (Exception ignore) {
		};
		try { // try with prefix
			URL url = new URL("file:/" + path);
			if (url.getContent() != null) {
				return url;
			}
		} catch (Exception ignore) {
		};
		return null;
	}
	
/*
	private URL getLocalJavaProjectURL(String projectName, String path) {
		try {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
            IJavaProject javaProject = (IJavaProject)project.getNature(JavaCore.NATURE_ID);
	        Object[] resources = javaProject.getNonJavaResources();
	        Path thePath = new Path(path);
	        // res = null;
	        //boolean ok = javaProject.isOnClasspath(res);
	        IJavaElement el = javaProject.findElement(thePath);
	        
	        if (el != null) {
	        	URL url = el.getResource().getLocationURI().toURL();
	        	return url;
	        }
		} catch (Exception ignore) {
		}
		return null;
	}
*/
	
	@Override
	public InputStream getResourceAsStream(String path) {
		if (applyOverride) {
			InputStream stream = super.getResourceAsStream(path);
			if (stream != null) {
				return stream;
			}
		}
		return delegate.getResourceAsStream(path);
	}
	
	@Override
	public Class<?> loadClass(final String clazzName) {
		return delegate.loadClass(clazzName);
	}
	
	@Override
	public URL getResource(String path) {
		URL url = null;
//		if (url == null) {
//			url = getLocalJavaProjectURL(bundleName, path);
//		}
		if (applyOverride) {
			if (url == null) { // try reading paths from root
				url = getURL(composedPath(bundleRoot,path));
			}
			if (url == null) {
				url = getURL(composedPath(composedPath(bundleRoot,"/src"), path));
			}
			if (url == null) {
				url = getURL(composedPath(composedPath(bundleRoot,"/bin"), path));
			}
			if (url != null) {
				return url;
			}
		}
		return delegate.getResource(path);
	}
	
	@Override
	protected Class<?> tryLoadClass(final String clazzName) throws ClassNotFoundException {
		throw new ClassNotFoundException(); // effectively no used: only via loadClass, which is delegated...
	}
	
}
