package dt.egf.utils.launch;

import org.eclipse.egf.model.fcore.Activity;
import org.eclipse.jface.viewers.ISelection;

public class RunSelectedEGFActivityViaManager extends RunSelectedEGFActivity {

	@Override
	protected boolean runActivity(Activity activity) {
		return runActivityViaManager(activity);
	}
	
	@Override
	protected String getDialogTitle(ISelection selection) {
		return "Altran - Run EGF Activity via Manager";
	}
	
}
