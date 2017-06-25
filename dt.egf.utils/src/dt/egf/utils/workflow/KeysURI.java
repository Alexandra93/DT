package dt.egf.utils.workflow;

public class KeysURI {
	static public final String KEY_URI = "uri";  // scheme:/
	static public final String KEY_AUTHORITY = "authority";
	static public final String KEY_DEVICE = "device";
	static public final String KEY_DEVICEPATH = "devicePath";
	static public final String KEY_FILEEXTENSION = "fileExtension";
	static public final String KEY_FRAGMENT = "fragment";
	static public final String KEY_HOST = "host";
	static public final String KEY_LASTSEGMENT = "lastSegment";
	static public final String KEY_SEGMENTS = "segments";
	static public final String KEY_SEGMENTCOUNT = "segmentCount";
	static public final String KEY_OPAQUEPART = "opaquePart";
	static public final String KEY_PATH = "path";
	static public final String KEY_PORT = "port";
	static public final String KEY_QUERY = "query";
	static public final String KEY_SCHEME = "scheme";
	static public final String KEY_USERINFO = "userInfo";

	static public final String KEY_FILENAME = "fileName";
	static public final String KEY_FILEBASENAME = "fileBaseName";
	static public final String KEY_FILESTRING = "fileString"; // portable format e.g. C:/path/sub...
	public static final String KEY_FILEOSSTRING = "fileOSString"; // OS format e.g. C:\path\sub...
	static public final String KEY_PLATFORMSTRING = "platformString";
	static public final String KEY_PLATFORMSTRINGDECODED = "platformStringDecoded";
	static public final String KEY_PROJECTNAME = "projectName";
	static public final String KEY_PROJECTSUBPATH = "projectSubPath"; // segments between project and optional filename (without ending "/")
	static public final String KEY_PROJECTFILEPATH = "projectFilePath"; // segments between project and optional fragment (without ending "#")
	static public final String KEY_PROTOCOL = "protocol";

	static public final String KEY_BASEURI = "baseURI";
	static public final String KEY_PROJECTURI = "projectURI";
	static public final String KEY_SCHEMEURI = "schemeURI";
	static public final String KEY_BASEPATH = "basePath"; // portable format e.g. C:/path/sub...
	static public final String KEY_BASEOSPATH = "baseOSPath";  // OS format e.g. C:\path\sub...
	static public final String KEY_PROJECTPATH = "projectPath"; // portable format e.g. C:/path/sub...
	static public final String KEY_PROJECTOSPATH = "projectOSPath";  // OS format e.g. C:\path\sub...
	
	static public final String KEY_WORKSPACEROOTPATH = "workspaceRootPath"; // portable format e.g. C:/path/sub... 
	static public final String KEY_WORKSPACEROOTOSPATH = "workspaceRootOSPath"; // OS format e.g. C:\path\sub...
	static public final String KEY_FILEURI = "fileURI";
	static public final String KEY_FILESUBNAME = "fileSubName"; // fileSubName ONLY applied when fileBaseName not ending with subname

	// URI Reconstruction specific
	static public final String KEY_URICONSTRUCTIONPATTERN = "URIConstructionPattern";
	static public final String KEY_URICONSTRUCTIONRESULT = "URIConstructionResult";
}
