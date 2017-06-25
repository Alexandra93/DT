package dt.egf.utils.workflow;

import java.util.List;


import org.eclipse.emf.common.util.URI;

public class URIConstructor extends ValueComposer {

	public static final String KEY_PARSEDURI = "parsedUri"; // save uri that we parsed in the map. Can be retrieved by user.
	private String pathSep = getPathSep();
	private String defaultURIConstructionPattern = 
			wrapKeyToPattern(KeysURI.KEY_SCHEME) + ":/"
			+ wrapKeyToPattern(KeysURI.KEY_PROTOCOL) + pathSep 
			+ wrapKeyToPattern(KeysURI.KEY_PROJECTNAME) + pathSep
			+ wrapKeyToPattern(KeysURI.KEY_PROJECTSUBPATH) + pathSep
			+ wrapKeyToPattern(KeysURI.KEY_FILEBASENAME) + wrapKeyToPattern(KeysURI.KEY_FILESUBNAME) + "." + wrapKeyToPattern(KeysURI.KEY_FILEEXTENSION) 
			;

	public static String getPathSep() {
		return "/";
	}
	
	public URIConstructor() {
		// Set default uri construction properties:
		forceScheme(); 
		put(KeysURI.KEY_FILESUBNAME, "");
		put(KeysURI.KEY_PROJECTSUBPATH, "");
		put(KeysURI.KEY_WORKSPACEROOTPATH, getWorkspaceRootPath());
		put(KeysURI.KEY_WORKSPACEROOTOSPATH, getWorkspaceRootOSPath());
	}
	
	public void parseURI(URI uriInput, boolean forceScheme) {
		URI uri = uriInput;
		if (uri == null) {
			return;
		} else if (forceScheme) {
			uri = URIUtils.getSchemeURI(uri);
		}
		put(KEY_PARSEDURI, uriInput);
		put(KeysURI.KEY_URI, uri);
		put(KeysURI.KEY_AUTHORITY, uri.authority());
		put(KeysURI.KEY_DEVICE, uri.device());
		put(KeysURI.KEY_DEVICEPATH, uri.devicePath());
		put(KeysURI.KEY_FILEEXTENSION, uri.fileExtension());
		put(KeysURI.KEY_FRAGMENT, uri.fragment());
		put(KeysURI.KEY_HOST, uri.host());
		put(KeysURI.KEY_LASTSEGMENT, uri.lastSegment());
		put(KeysURI.KEY_SEGMENTS, uri.segmentsList()); // List
		put(KeysURI.KEY_SEGMENTCOUNT, uri.segmentCount()); // List
		put(KeysURI.KEY_OPAQUEPART, uri.opaquePart());
		put(KeysURI.KEY_PATH, uri.path());
		put(KeysURI.KEY_PORT, uri.port());
		put(KeysURI.KEY_QUERY, uri.query());
		put(KeysURI.KEY_SCHEME, uri.scheme());
		put(KeysURI.KEY_USERINFO, uri.userInfo());
		put(KeysURI.KEY_FILESTRING, getFileString(uri));
		put(KeysURI.KEY_FILEOSSTRING, getFileOSString(uri));
		put(KeysURI.KEY_PLATFORMSTRING, uri.toPlatformString(false));
		put(KeysURI.KEY_PLATFORMSTRINGDECODED, uri.toPlatformString(true));

		// non-standard additions
		put(KeysURI.KEY_FILENAME, getFilename(uri));
		put(KeysURI.KEY_FILEBASENAME, getFileBasename(uri));
		put(KeysURI.KEY_FILESUBNAME, getFileSubname(uri));
		put(KeysURI.KEY_PROJECTNAME, getProjectname(uri));
		put(KeysURI.KEY_PROJECTSUBPATH, getProjectSubpath(uri));
		put(KeysURI.KEY_PROJECTFILEPATH, getProjectFilepath(uri));
		put(KeysURI.KEY_BASEURI, getBaseURI(uri));
		put(KeysURI.KEY_PROJECTURI, getProjectURI(uri));
		put(KeysURI.KEY_FILEURI, getFileURI(uri));
		put(KeysURI.KEY_BASEPATH, getBaseURIFileString(uri));
		put(KeysURI.KEY_BASEOSPATH, getBaseURIFileOSString(uri));
		put(KeysURI.KEY_PROJECTPATH, getProjectURIFileString(uri));
		put(KeysURI.KEY_PROJECTOSPATH, getProjectURIFileOSString(uri));
		
	}
	
	public void setValue(String key, Object v) {
		if (v instanceof String) {
			// do pattern replacement conditionally:
			if (valueRequiresConstruction(v)) {
				// assume that it is a pattern
				v = constructValue((String)v);
			}
		}
		put(key, v);
	}
	public Object getValue(String key) {
		return get(key);
	}
	
	public static Boolean valueRequiresConstruction(Object v) {
		return ((v instanceof String) && ((String) v).contains("${"));
	}
	
	public String getConstructionPattern() {
		String constructionURIPattern = defaultURIConstructionPattern;
		if (keySet().contains(KeysURI.KEY_URICONSTRUCTIONPATTERN)) {
			constructionURIPattern = (String)get(KeysURI.KEY_URICONSTRUCTIONPATTERN);
		}
		return constructionURIPattern;
	}
	
	public URI constructURI() {
		URI result = null;
		String construction = constructValue(getConstructionPattern());
		construction = cleanupURIString(construction);
		result = URI.createURI(construction);
		put(KeysURI.KEY_URICONSTRUCTIONRESULT, result);
		return result;
	}
	
	public static String cleanupURIString(String input) {
		String pathSep = getPathSep();
		return input.replaceAll("([^:])" + pathSep + pathSep, "$1" + pathSep)  // redundant path separator
				.replaceAll("\\.+($|[#?])", "$1")
				.replaceAll("([^/])\\.\\.([^/])", "$1\\.$2")   // redundant ..
					;
	}
	
	public String getProjectFilepath(URI uri) {
		String projectSubPath = (String)getValue(KeysURI.KEY_PROJECTSUBPATH);
		if ((projectSubPath != null) && projectSubPath.length() > 0) {
			projectSubPath = projectSubPath + pathSep;
		}
		return cleanupURIString(projectSubPath + (String)getValue(KeysURI.KEY_FILENAME));
	}
	
	public static URI getBaseURI(URI uri) {
		return uri.trimFragment().trimQuery().trimFileExtension().trimSegments(1);
	}
	
	public static String getBaseURIFileString(URI uri) {
		return getFileString(getBaseURI(uri));
	}
	
	public static String getBaseURIFileOSString(URI uri) {
		return getFileOSString(getBaseURI(uri));
	}
	
	public static String getProjectURIFileString(URI uri) {
		return getFileString(getProjectURI(uri));
	}
	
	public static String getProjectURIFileOSString(URI uri) {
		return getFileOSString(getProjectURI(uri));
	}
	
	public static URI getProjectURI(URI uri) {
		URI trimmedURI = uri;//.trimFragment().trimQuery();
		if (uri.isPlatform() && uri.segmentCount() > 2) {
			trimmedURI = trimmedURI.trimSegments(uri.segmentCount() - 2);
		}
		return trimmedURI;
	}
	
	public static URI getProjectSubFilePathURI(URI uri) {
		return URIUtils.getProjectSubFilePathURI(uri);
	}

	public static URI getSchemeURI(URI uri) {
		return URIUtils.getSchemeURI(uri);
	}
	
	public static String getFileString(URI uri) {
		URI fileURI = getFileURI(uri);
		if (fileURI == null) return null;
		return URIUtils.getPortableString(fileURI.toFileString());
	}
	
	public static String getFileOSString(URI uri) {
		URI fileURI = getFileURI(uri);
		if (fileURI == null) return null;
		return URIUtils.getOSString(fileURI.toFileString());
	}
	
	public static URI getFileURI(URI uri) {
		return URIUtils.getFileURI(uri);
	}
	
	public static String getProjectSubpath(URI uri) {
		String projectSubPath = "";
		if ((uri.toPlatformString(false) != null) && uri.segmentCount() >= 3) {
			List<String> s = uri.segmentsList().subList(2, uri.segmentCount() - 1);
			String[] segments = s.toArray(new String[s.size()]);
			URI basePathURI = URI.createHierarchicalURI(segments, null, null);
			projectSubPath = basePathURI.toString();
		}
		return projectSubPath;
	}

	public static String getProjectname(URI uri) {
		return URIUtils.getProjectname(uri);
	}

	public static String getFilename(URI uri) {
		return uri.trimFragment().trimQuery().lastSegment();
	}
	
	public static String getFileBasename(URI uri) {
		return uri.trimFragment().trimQuery().trimFileExtension().lastSegment();
	}
	
	public static String getFileSubname(URI uri) {
		return "";
	}
	
	public static String getWorkspaceRootPath() {
		return URIUtils.getPortableString(URIUtils.getWorkspaceRootPath());
	}
	
	public static String getWorkspaceRootOSPath() {
		return URIUtils.getOSString(URIUtils.getWorkspaceRootPath());
	}
	
	private void forceScheme() {
		if (getValue(KeysURI.KEY_SCHEME) == null) {
			put(KeysURI.KEY_SCHEME, "platform");
		}
		if (getValue(KeysURI.KEY_PROTOCOL) == null) {
			put(KeysURI.KEY_PROTOCOL, "resource");
		}
	}
	
	protected Object getValueToApply(String key) {
		switch (key) {
		case KeysURI.KEY_FILESUBNAME:
			String fileSubName = (String) getValue(key);
			String fileBaseName = (String) getValue(KeysURI.KEY_FILEBASENAME);
			if ((fileBaseName != null) && (fileSubName != null)
					&& (!fileBaseName.isEmpty())
					&& (!fileSubName.isEmpty())
				) {
				if (fileBaseName.endsWith(fileSubName)) {
					fileSubName = "";
				}
			}
			return fileSubName;
		default:
			break;
		}
		return super.getValueToApply(key);
	}

}
