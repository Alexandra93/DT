package dt.egf.utils.launch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egf.console.EGFConsolePlugin;
import org.eclipse.egf.core.EGFCorePlugin;
import org.eclipse.egf.core.domain.TargetPlatformResourceSet;
import org.eclipse.egf.model.fcore.Activity;
import org.eclipse.egf.model.fcore.Contract;
import org.eclipse.egf.model.fcore.ContractMode;
import org.eclipse.egf.model.types.TypeString;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import dt.egf.utils.jobs.ActivityManagerRunner;
import dt.egf.utils.jobs.ActivityWorkspaceJobRunner;

public abstract class AbstractEGFLaunchHandler extends AbstractHandler {

	Map<String, String> string_inputs = new HashMap<String, String>();

	public enum MessageConfirmation {
		CONFIRMATION, INFORMATION, NO_DIALOG
	}

	// store the status of the message dialog window: confirmation, information
	// or no_dialog
	private MessageConfirmation askConfirmation;

	// make the constructor public, otherwise no class outside the package can
	// access it
	public AbstractEGFLaunchHandler() {
		// invoke immediate parent class constructor
		super();
		// set the default value of the message dialog window to CONFIRMATION
		setConfirmation(MessageConfirmation.CONFIRMATION);
	}

	public void setInput(String contractName, String value) {
		string_inputs.put(contractName, value);
	}

	protected MessageConfirmation getConfirmation() {
		return askConfirmation;
	}

	protected void setConfirmation(MessageConfirmation confirmation) {
		askConfirmation = confirmation;
	}

	protected String getDialogTitle(ExecutionEvent event) {
		return "<Specify Dialog Title>";
	}

	protected String getDialogDescription(ExecutionEvent event) {
		return "<Specify Dialog Description>";
	}

	protected boolean isContextValid(ExecutionEvent event) {
		return true;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);

		if (isContextValid(event)) {
			resetContractValues();
			if (MessageConfirmation.CONFIRMATION == getConfirmation()) {
				if (MessageDialog.openConfirm(window.getShell(),
						getDialogTitle(event), getDialogDescription(event)) == true) {
					// user pressed OK
					execute_egf_activity(event, getEgfActivityURIstring());
				}
			} else // ( MessageConfirmation.NO_DIALOG ==
					// getConfirmation(selection) ||
			{ 
				// MessageConfirmation.INFORMATION == getConfirmation(selection) )
				// MessageConfirmation.INFORMATION is for further development
				execute_egf_activity(event, getEgfActivityURIstring());
			}
		}
		return null;
	}

	/**
	 * Specify the URI string of the EGF actvitiy to execute e.g.:
	 * "platform:/plugin/nspyre.asml.drife.genmodel.wizard/EMF.model/drife.transform.fcore#_ZNsVAC-GEeSpMr5saWBQyQ"
	 * String value can be retrieved using EGF context function
	 * "Create EGF Activity Headless shell script to clipboard" on the activity
	 * in the fcore file.
	 * 
	 * @return String representing URI of activity
	 */
	abstract protected String getEgfActivityURIstring();

	protected void execute_egf_activity(ExecutionEvent event,
			String activityURIstring) {

		EObject eObject = null;
		// option to run a list of activities...
		List<Activity> activities = new UniqueEList<Activity>();

		// String activityURIstring =
		// "platform:/plugin/nspyre.asml.drife.genmodel.wizard/EMF.model/drife.transform.fcore#_ZNsVAC-GEeSpMr5saWBQyQ";
		ResourceSet resourceSet = new TargetPlatformResourceSet();

		//save all opened editors
		if(false == saveRelevantContent()) {
			//user pressed CANCEL
			return;
		}

		try {
			URI activityURI = URI.createURI(activityURIstring);
			eObject = resourceSet.getEObject(activityURI, true);
		} catch (Throwable t) {
			String message = "Cannot load activity with URI "
					+ activityURIstring;
			EGFCorePlugin.getDefault().logError(message, t);
			System.out.println(message);
		}
		if (eObject == null || !(eObject instanceof Activity)) {
			String message = "Object with URI " + activityURIstring
					+ " is not an Activity";
			EGFCorePlugin.getDefault().logError(message);
			System.out.println(message);
		} else {
			// add to activities list...
			activities.add((Activity) eObject);

			specifyContractValues(); // allow user to specify contract values

			for (Activity activity : activities) {
				EList<Contract> inContracts = activity
						.getContracts(ContractMode.IN);
				setInputContractValues(inContracts, event);
			}

			EGFConsolePlugin.getConsole().show(true);

			runActivities(activities);

		}

		return;
	}

	protected void resetContractValues() {
		string_inputs.clear();
	}

	protected void specifyContractValues() {
		// do nothing by default
	}

	protected void setInputContractValues(EList<Contract> inContracts,
			ExecutionEvent event) {

		for (Contract inContract : inContracts) {
			if (string_inputs.containsKey(inContract.getName())) {
				String value = string_inputs.get(inContract.getName());
				if (inContract.getType() instanceof TypeString) {
					((TypeString) inContract.getType()).setValue(value);
				}
			}
		}
	}

	protected boolean useRunHelper() {
		return false;
	}

	protected boolean runActivities(List<Activity> activities) {
		boolean res = true;

		if (useRunHelper()) {
			// option to run a list of activities using runHelper...?
			// org.eclipse.egf.application.activity.ActivityApplication
			// disadvantage is that it runs in a different context, only
			// updating consoles
			// after processing all.
			// try {
			// ActivityApplication.runHelper(activities);
			// } catch (Exception e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

		} else {

			for (Activity activity : activities) {
				boolean imres = runActivity(activity);
				if (imres == false)
					res = false;
			}
		}
		return res;
	}

	protected boolean runActivity(Activity activity) {

		boolean result = false;

		result = runActivityAsWorkspaceJob(activity);

		return result;
	}

	protected boolean runActivityAsWorkspaceJob(Activity activity) {

		System.out.println("runActivityAsWorkspaceJob : " + activity.getName());
		ActivityWorkspaceJobRunner wjr = new ActivityWorkspaceJobRunner();
		wjr.setInputs(string_inputs); // needed for asynchronous running...
		wjr.RunActivityJob(activity);
		return true;
	}

	protected boolean runActivityViaManager(Activity activity) {
		System.out.println("runActivityViaManager : " + activity.getName());
		ActivityManagerRunner mr = new ActivityManagerRunner();
		mr.setInputs(string_inputs);  // not needed, but symmetrical...
		return mr.runActivityViaManager(activity);
	}	
	
	protected boolean saveRelevantContent() {

		boolean savedStatus = true;

		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		if(false == page.saveAllEditors(true))	{
			savedStatus = false;
		}
		
	return savedStatus;
	}
	
}