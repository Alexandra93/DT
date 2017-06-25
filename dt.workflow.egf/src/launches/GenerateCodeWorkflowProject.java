package launches;

import org.eclipse.jface.viewers.ISelection;

import dt.egf.utils.launch.AbstractEGFSelectionLaunchHandler;

public class GenerateCodeWorkflowProject extends AbstractEGFSelectionLaunchHandler {

	public GenerateCodeWorkflowProject(){
		super();
		// set the state of the message dialog window to NO_DIALOG
		setConfirmation(MessageConfirmation.NO_DIALOG); 
	}
	
	@Override
	protected String getDialogTitle(ISelection selection) {
		return "Workflow Generate Code";
	}
	
	@Override
	protected String getDialogDescription(ISelection selection) {
		return "Generator code Workflow using generator model " + getSelectionInfo(selection);
	}

	@Override
	protected String getEgfActivityURIstring() {
		return "platform:/plugin/dt.workflow.egf/EGF/workflow.generation.fcore#_iFZfwFGyEeexQMyTVIP4WQ";
	}
}
