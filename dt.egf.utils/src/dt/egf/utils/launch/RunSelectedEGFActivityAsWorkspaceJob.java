package dt.egf.utils.launch;

import org.eclipse.egf.model.fcore.Activity;
import org.eclipse.jface.viewers.ISelection;

public class RunSelectedEGFActivityAsWorkspaceJob extends RunSelectedEGFActivity {

	@Override
	protected boolean runActivity(Activity activity) {
		return runActivityAsWorkspaceJob(activity);
	}
	
	@Override
	protected String getDialogTitle(ISelection selection) {
		return "Altran - Run EGF Activity as WorkspaceJob";
	}
}
