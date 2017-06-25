package dt.egf.utils.launch;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;

public abstract class AbstractEGFResourceSelectionLaunchHandler extends AbstractEGFAnySelectionLaunchHandler {

	@Override
	protected String getSelectionContractValueString(ISelection selection) {
		return getSelectionResourceURIstring(selection);
	}
	
	protected String getSelectionResourceURIstring(ISelection selection) {
		IResource context = getSelectionResource(selection);
		String resourceURIstring = context.getFullPath().toString();
		return resourceURIstring;
	}
	
	protected IResource getSelectionResource(ISelection selection) {
		IResource res = null;
		Object first = getSelectionObject(selection);
		if (first instanceof IResource) {
			res = (IResource)first;
		};
		return res;
	}
	
	protected String getSelectionInfo(ISelection selection) {
		IResource res = getSelectionResource(selection);
		if (res != null) {
			return res.getName();
		}
		return super.getSelectionInfo(selection);
	}
}
