package dt.egf.utils.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.egf.common.log.IEGFLogger;
import org.eclipse.egf.console.EGFConsolePlugin;
import org.eclipse.egf.core.EGFCorePlugin;
import org.eclipse.egf.core.domain.TargetPlatformResourceSet;
import org.eclipse.egf.model.fcore.Activity;
import org.eclipse.egf.model.fcore.Contract;
import org.eclipse.egf.model.fcore.ContractMode;
import org.eclipse.egf.model.types.TypeString;
import org.eclipse.egf.producer.EGFProducerPlugin;
import org.eclipse.egf.producer.manager.ActivityManagerProducer;
import org.eclipse.egf.producer.manager.IActivityManager;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import dt.egf.utils.helpers.ValidationTextField;
import dt.egf.utils.jobs.ActivityManagerRunner;
import dt.egf.utils.jobs.ActivityWorkspaceJobRunner;
import dt.egf.utils.ui.Activator;
import dt.egf.utils.ui.Messages;

public abstract class AbstractNewModelProjectWizard extends
		BasicNewProjectResourceWizard {

	protected Map<String, String> string_inputs = new HashMap<String, String>();

	private WizardNewProjectCreationPage projectPage;
	private ValidationTextField validationTextField = new ValidationTextField();
	private static boolean finishPressed = false;
	private static String projectNameRegex = "[a-zA-Z0-9\\._-]+";
	private String pageDescriptor = "Specify the name of the project that will contain the essential model";

	// Define a getter and setter for pageDescriptor
	public String getPageDescriptor() {
		return pageDescriptor;
	}

	public void setPageDescriptor(String pageDescriptor) {
		this.pageDescriptor = pageDescriptor;
	}

	// Define a getter and setter for finishPressed
	public static boolean isFinishPressed() {
		return finishPressed;
	}

	public static void setFinishPressed(boolean finishPressed) {
		AbstractNewModelProjectWizard.finishPressed = finishPressed;
	}

	@Override
	public void addPages() {
		// Don't use parent pages, these are not accessible afterwards.
		// super.addPages();
		/* Add project name page */
		projectPage = new WizardNewProjectCreationPage("projectPage") { //$NON-NLS-1$
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.ui.dialogs.WizardNewProjectCreationPage#createControl
			 * (org.eclipse.swt.widgets.Composite)
			 */
			public void createControl(Composite parent) {
				super.createControl(parent);
				createWorkingSetGroup(
						(Composite) getControl(),
						getSelection(),
						new String[] { "org.eclipse.ui.resourceWorkingSetPage" }); //$NON-NLS-1$
				Dialog.applyDialogFont(getControl());
			}
			
		    protected boolean validatePage() {

		        String projectFieldContents = getProjectName();

		        if (validationTextField.validateTextField(projectFieldContents, projectNameRegex) == false) {
		            setErrorMessage("Please select a Project Name that only contains letters, numbers and the characters . - _");
		            return false;
		        }

		        return super.validatePage();
		    }
		};

		// Initialize the finishPressed variable every time a new project is
		// created
		setFinishPressed(false);

		projectPage.setTitle("Project name");
		projectPage.setDescription(this.getPageDescriptor());
		addPage(projectPage);
	}

	protected WizardNewProjectCreationPage getProjectPage() {
		return projectPage;
	}

	protected boolean checkInputComplete() {
		return (allPagesComplete()
				&& (getProjectPage().getProjectName() != null));
	}

	protected void do_execute(IProgressMonitor monitor) {

		// Run EGF activity that creates the project
		execute_egf_activity(getEgfActivityURIstring());

		IProject created_project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(getProjectPage().getProjectName());
		if (created_project != null) {
			IWorkingSet[] workingSets = getProjectPage()
					.getSelectedWorkingSets();
			getWorkbench().getWorkingSetManager().addToWorkingSets(
					created_project, workingSets);

			updatePerspective();
			selectAndReveal(created_project);
		}
	}

	protected boolean wrap_do_execute_in_WorkspaceModifyOperation() {
		return false;
	}

	protected boolean wrapped_do_execute() {

		ISchedulingRule rule = null;
		// we should have workspace level rule because adding nature will
		// require this. however, it locks the treads then later on when adding
		// viewpoint.
		//
		rule = ResourcesPlugin.getWorkspace().getRoot();

		final IRunnableWithProgress op = new WorkspaceModifyOperation(rule) {
			@Override
			protected void execute(IProgressMonitor monitor)
					throws CoreException, InterruptedException {
				do_execute(monitor);
			}
		};
		try {
			getContainer().run(false, true, op);
		} catch (final InvocationTargetException e) {
			if (e.getTargetException() instanceof CoreException) {
				ErrorDialog.openError(getContainer().getShell(),
						Messages.ModelWizard_UI_Error_CreatingModelProject,
						null,
						((CoreException) e.getTargetException()).getStatus());
			} else {
				Activator.getDefault().logError(
						Messages.ModelWizard_UI_Error_CreatingModelProject, e);
			}
		} catch (final InterruptedException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean performFinish() {
		// disable finish button
		setFinishPressed(true);
		getContainer().updateButtons();
		
		if (false == checkInputComplete()) {
			//be more gentle
			//throw new IllegalArgumentException();
			// do not enable button, it should apparently not have been 
			// enabled in the first place...
			return false;
		}
		
		resetContractValues();

		if (wrap_do_execute_in_WorkspaceModifyOperation()) {
			return wrapped_do_execute();
		} else {
			do_execute(new NullProgressMonitor());
		}

		return true;
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

	protected void resetContractValues() {
		string_inputs.clear();
	}

	protected void specifyContractValues() {

		string_inputs.put("projectName", getProjectPage().getProjectName());
	}

	protected void execute_egf_activity(String activityURIstring) {

		EObject eObject = null;
		// option to run a list of activities...
		List<Activity> activities = new UniqueEList<Activity>();

		// String activityURIstring =
		// "platform:/plugin/nspyre.asml.drife.genmodel.wizard/EMF.model/drife.transform.fcore#_ZNsVAC-GEeSpMr5saWBQyQ";
		ResourceSet resourceSet = new TargetPlatformResourceSet();
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
				setInputContractValues(inContracts);
			}

			EGFConsolePlugin.getConsole().show(true);

			runActivities(activities);

		}

		return;
	}

	protected void setInputContractValues(EList<Contract> inContracts) {

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

		result = runActivityViaManager(activity);

		return result;
	}

	protected boolean runActivityAsWorkspaceJob(Activity activity) {

		ActivityWorkspaceJobRunner wjr = new ActivityWorkspaceJobRunner();
		//wjr.setInputs(string_inputs);
		wjr.RunActivityJob(activity);
		return true;
	}

	protected boolean runActivityViaManager(Activity activity) {

		System.out.println("runActivityViaManager : " + activity.getName());
		ActivityManagerRunner mr = new ActivityManagerRunner();
		//mr.setInputs(string_inputs);
		return mr.runActivityViaManager(activity);
	}

	protected boolean allPagesComplete() {
		for (IWizardPage page : getPages()) {
			if (!page.isPageComplete()) return false;
		}
		return true;
	}
	
	@Override
	public boolean canFinish() {
		// Disable the Finish button after it's pressed once
		if (allPagesComplete() && !isFinishPressed()) {
			return true;
		}

		return false;
	}

}
