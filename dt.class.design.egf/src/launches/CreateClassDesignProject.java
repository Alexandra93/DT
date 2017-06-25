package launches;

import org.eclipse.egf.model.fcore.Activity;

import dt.egf.utils.launch.AbstractEGFLaunchHandler;

public class CreateClassDesignProject  extends AbstractEGFLaunchHandler {

	public CreateClassDesignProject(){
		super();
		// set the state of the message dialog window to NO_DIALOG
		setConfirmation(MessageConfirmation.NO_DIALOG); 
	}
	
	@Override
	protected boolean runActivity(Activity activity) {
		/*
		 * Due to synchronization issues when creating UML Designer project and
		 * representations, use the "Manager" instead of the WorkspaceJobRunner to 
		 * launch the activity.
		 */
		return runActivityViaManager(activity);
	}

	@Override
	protected String getEgfActivityURIstring() {
		return "platform:/plugin/dt.class.design.egf/EGF/class.design.creation.fcore#_m6XjYICoEea-Q5dJI90J9w";
	}

}
