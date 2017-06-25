package wizard;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dt.egf.utils.jobs.ActivityWorkspaceJobRunner;
//import nspyre.org.eclipse.egf.producer.ui.ActivityWorkspaceJobRunner;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.egf.common.log.IEGFLogger;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;


public class CreateClassDesignModelWizard extends BasicNewProjectResourceWizard {
	
	Map<String, String> string_inputs = new HashMap<String, String>();
	
	private WizardNewProjectCreationPage projectPage;
	
	@Override
	public void addPages() {
		// Don't use parent pages, these are not accessible afterwards.
		//super.addPages();
		/* Add project name page */
		projectPage = new WizardNewProjectCreationPage("projectPage") { //$NON-NLS-1$
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.ui.dialogs.WizardNewProjectCreationPage#createControl(org.eclipse.swt.widgets.Composite)
			 */
			public void createControl(Composite parent) {
				super.createControl(parent);
				createWorkingSetGroup(
						(Composite) getControl(),
						getSelection(),
						new String[] { "org.eclipse.ui.resourceWorkingSetPage" }); //$NON-NLS-1$
				Dialog.applyDialogFont(getControl());
			}
		};
		projectPage.setTitle("Project name");
		projectPage.setDescription("Specify the name of the project that will contain the Class Design model");
		addPage(projectPage);
		
	}
	
	protected WizardNewProjectCreationPage getProjectPage() {
		return projectPage;
	}
	
	protected void do_execute(IProgressMonitor monitor) {
		
		// Run EGF activity that creates the project
		execute_egf_activity(getEgfActivityURIstring());

		
		IProject created_project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				getProjectPage().getProjectName());
		if (created_project != null) {
			IWorkingSet[] workingSets = getProjectPage().getSelectedWorkingSets();
			getWorkbench().getWorkingSetManager().addToWorkingSets(created_project,
					workingSets);
	        
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
		// require this. however, it locks the treads then later on when adding viewpoint.
		//
		rule = ResourcesPlugin.getWorkspace().getRoot();
		
		final IRunnableWithProgress op = new WorkspaceModifyOperation(rule) {
			@Override
			protected void execute(IProgressMonitor monitor) throws CoreException, InterruptedException {
				do_execute(monitor);
			}
		};
		try {
			getContainer().run(false, true, op);
		} catch (final InvocationTargetException e) {
			if (e.getTargetException() instanceof CoreException) {
//				ErrorDialog.openError(getContainer().getShell(),
//						Messages.ModelWizard_UI_Error_CreatingLACEEssentialModelProject, null,
//						((CoreException)e.getTargetException()).getStatus());
			} else {
				//Activator.logError(Messages.ModelWizard_UI_Error_CreatingLACEEssentialModelProject, e);
			}
		} catch (final InterruptedException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean performFinish() {
		if ((getProjectPage().getProjectName() == null) ) {
			throw new IllegalArgumentException();
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
	 * Specify the URI string of the EGF actvitiy to execute
	 * e.g.:
	 * "platform:/plugin/nspyre.asml.drife.genmodel.wizard/EMF.model/drife.transform.fcore#_ZNsVAC-GEeSpMr5saWBQyQ"
	 * String value can be retrieved using EGF context function 
	 *     "Create EGF Activity Headless shell script to clipboard"
	 * on the activity in the fcore file. 
	 * @return String representing URI of activity
	 */
//	abstract protected String getEgfActivityURIstring();
	
	protected String getEgfActivityURIstring() {
		//ID	_2WN08OzkEeSl5L6wef2Yjw
		return "platform:/plugin/dt.class.design.egf/EGF/class.design.creation.fcore#_m6XjYICoEea-Q5dJI90J9w";
	}
	
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
		
		//String activityURIstring = "platform:/plugin/nspyre.asml.drife.genmodel.wizard/EMF.model/drife.transform.fcore#_ZNsVAC-GEeSpMr5saWBQyQ";
		ResourceSet resourceSet = new TargetPlatformResourceSet();
		try {
			URI activityURI = URI.createURI(activityURIstring);
			eObject = resourceSet.getEObject(activityURI, true);
		} catch (Throwable t) {
			String message = "Cannot load activity with URI " + activityURIstring;
			//EGFCorePlugin.getDefault().logError(message, t);
			System.out.println(message);
		}
		if (eObject == null || !(eObject instanceof Activity) ) {
			String message = "Object with URI " + activityURIstring + " is not an Activity";
			//EGFCorePlugin.getDefault().logError(message);
			//System.out.println(message);
		} else {
			// add to activities list...
			activities.add((Activity) eObject);
			
			specifyContractValues();  // allow user to specify contract values
		
			for (Activity activity : activities) {
				EList<Contract> inContracts = activity.getContracts(ContractMode.IN);
				setInputContractValues(inContracts);
			}
			
			//EGFConsolePlugin.getConsole().show(true);
			
			runActivities(activities);
			
		}
		
		return;
	}

	protected void setInputContractValues(EList<Contract> inContracts) {
		
		for (Contract inContract : inContracts) {
			String value = string_inputs.get(inContract.getName());
			if (inContract.getType() instanceof TypeString) {
				((TypeString)inContract.getType()).setValue(value);
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
// disadvantage is that it runs in a different context, only updating consoles
// after processing all.
//			try {
//				ActivityApplication.runHelper(activities);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		} else {
		
			for (Activity activity : activities) {
				boolean imres = runActivity(activity);
				if (imres == false) res = false;
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
		wjr.setInputs(string_inputs);
		wjr.RunActivityJob(activity);
		return true;
	}	
	protected boolean runActivityViaManager(Activity activity) {
		
		try {

			// Invoke Activity
			ActivityManagerProducer<Activity> producer = EGFProducerPlugin.getActivityManagerProducer(activity);
			IActivityManager<Activity> manager = producer.createActivityManager(activity);

			try {
				
				manager.initializeContext();
				manager.invoke(new CodeGenUtil.EclipseUtil.StreamProgressMonitor(System.out));
				//manager.invoke(new NullProgressMonitor());

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					manager.dispose();
				} catch (Exception e) {
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			//logger.logInfo("-");
			//EGFCorePlugin.getDefault().logError(">> Problem executing activity '" + activity.getName() + "'", e);
			return false;
		}
		//logger.logInfo("-");
		//logger.logInfo(">> Finished executing activity '" + activity.getName() + "'");
		return true;

	}
	
}
