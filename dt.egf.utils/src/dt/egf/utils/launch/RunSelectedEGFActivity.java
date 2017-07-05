package dt.egf.utils.launch;

import org.eclipse.egf.model.fcore.Activity;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ISelection;

public class RunSelectedEGFActivity extends
		AbstractEGFAnySelectionLaunchHandler {

	protected String getActivityURIstringFromSelection(ISelection selection) {
		Object obj = getSelectionObject(selection);
		if (obj instanceof Activity) {
			EObject eObject = (EObject)obj;
			return EcoreUtil.getURI(eObject).toString();
		}
		return null;
	}
	
	@Override
	protected String getEgfActivityURIstring() {
		return getActivityURIstringFromSelection(getActiveSelection());
	}
	
	@Override
	protected String getSelectionInfo(ISelection selection) {
		Object obj = getSelectionObject(selection);
		if (obj instanceof Activity) {
			return ((Activity)obj).getName();
		}
		return super.getSelectionInfo(selection);
	}
	
	@Override
	protected String getDialogDescription(ISelection selection) {
		return "Run activity '" + getSelectionInfo(selection) + "'";
	}
	
	@Override
	protected String getDialogTitle(ISelection selection) {
		return "Run EGF Activity...";
	}
	
	@Override
	protected boolean runActivity(Activity activity) {
		return runActivityAsWorkspaceJob(activity);
	}
	
}
