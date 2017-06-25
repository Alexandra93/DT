package launches;

import org.eclipse.jface.viewers.ISelection;

import dt.egf.utils.launch.AbstractEGFSelectionLaunchHandler;

public class GenerateCodeClassDesignProject extends AbstractEGFSelectionLaunchHandler {

	public GenerateCodeClassDesignProject(){
		super();
		// set the state of the message dialog window to NO_DIALOG
		setConfirmation(MessageConfirmation.NO_DIALOG); 
	}
	
	@Override
	protected String getDialogTitle(ISelection selection) {
		return "Class Design Generate Code";
	}
	
	@Override
	protected String getDialogDescription(ISelection selection) {
		return "Generator Class Design code using generator model " + getSelectionInfo(selection);
	}

	@Override
	protected String getEgfActivityURIstring() {
		return "platform:/plugin/dt.class.design.egf/EGF/class.design.generation.fcore#_iFZfwFGyEeexQMyTVIP4WQ";
	}
}
