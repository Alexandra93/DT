package dt.egf.utils.jobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egf.common.helper.EMFHelper;
import org.eclipse.egf.common.l10n.EGFCommonMessages;
import org.eclipse.egf.common.ui.helper.ThrowableHandler;
import org.eclipse.egf.core.EGFCorePlugin;
import org.eclipse.egf.core.domain.RuntimePlatformResourceSet;
import org.eclipse.egf.core.fcore.IPlatformFcore;
import org.eclipse.egf.core.fcore.IPlatformFcoreProvider;
import org.eclipse.egf.core.l10n.EGFCoreMessages;
import org.eclipse.egf.core.preferences.IEGFModelConstants;
import org.eclipse.egf.core.producer.InvocationException;
import org.eclipse.egf.core.session.ProjectBundleSession;
import org.eclipse.egf.core.ui.EGFCoreUIPlugin;
import org.eclipse.egf.core.ui.diagnostic.EGFValidator;
import org.eclipse.egf.model.fcore.Activity;
import org.eclipse.egf.model.fcore.Contract;
import org.eclipse.egf.model.fcore.ContractMode;
import org.eclipse.egf.model.ftask.Task;
import org.eclipse.egf.model.types.TypeString;
import org.eclipse.egf.producer.EGFProducerPlugin;
import org.eclipse.egf.producer.l10n.ProducerMessages;
import org.eclipse.egf.producer.manager.ActivityManagerProducer;
import org.eclipse.egf.producer.manager.IActivityManager;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.progress.IProgressConstants;

import dt.egf.utils.ui.Activator;

public class ActivityWorkspaceJobRunner {

    /**
     * Activities to validate (if any)
     */
    private List<Activity> _validates = null;

    // Currently only string inputs: contract name - contract value.
    private Map<String, String> string_inputs = new HashMap<String, String>();

    //private IActivityRunnerLogger logger = ActivityRunnerLogger.INSTANCE;
    
//    public IActivityRunnerLogger getLogger() {
//		return logger;
//	}
//
//	public void setLogger(IActivityRunnerLogger logger) {
//		this.logger = logger;
//	}

	public void setInputs(Map<String, String> inputs) {
    	string_inputs.putAll(inputs);
    }

	public void RunActivityJob(Activity activityToExecute) {

		String activityName = activityToExecute.getName();
        _validates = null;
		
        final Activity[] activity = new Activity[] { activityToExecute };

        // 2 - Validation
        IPreferenceStore store = EGFCoreUIPlugin.getDefault().getPreferenceStore();
        String validate = store.getString(IEGFModelConstants.VALIDATE_MODEL_INSTANCES_BEFORE_LAUNCH);
        List<Activity> activities = activity[0].getActivities();
        int status = showValidateDialog(activities, validate.equals(MessageDialogWithToggle.NEVER) == false, validate.equals(MessageDialogWithToggle.PROMPT));
        if (status != IDialogConstants.OK_ID) {
            return;
        }
        if (_validates != null && _validates.size() != 0) {
            EGFValidator validator = new EGFValidator(_validates);
            Diagnostic validationDiag = validator.validate();
            // Stop when an error is found
            if (validationDiag.getSeverity() == Diagnostic.ERROR) {
                return;
            }
        }
        // Build a subset of task
        final List<Task> tasks = new BasicEList<Task>();
        for (Activity innerActivity : activities) {
            if (innerActivity instanceof Task) {
                tasks.add((Task) innerActivity);
            }
        }

        // Prepare our activity job
        WorkspaceJob activityJob = new WorkspaceJob("Run EGF Activity " + activityName) {

        	Diagnostic activityDiagnostic = Diagnostic.CANCEL_INSTANCE;
        	
            @Override
            public boolean belongsTo(Object family) {
                return EGFCorePlugin.FAMILY_MANUAL_BUILD.equals(family);
            }

            @Override
            public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {

                // Prepare a dynamic bundle session
//                ProjectBundleSession session = new ProjectBundleSession(EGFProducerUIPlugin.getDefault().getBundle().getBundleContext());
                ProjectBundleSession session = new ProjectBundleSession(Activator.getDefault().getBundle().getBundleContext());
                IActivityManager<?> activityManager = null;
                SubMonitor subMonitor = null;

                // 3 - Locate workspace fcore
                List<IPlatformFcore> fcores = new UniqueEList<IPlatformFcore>();
                for (Resource resource : activity[0].getResources()) {
                    if (resource instanceof IPlatformFcoreProvider) {
                        IPlatformFcore fcore = ((IPlatformFcoreProvider) resource).getIPlatformFcore();
                        // Runtime or target associated with a runtime bundle
                        if (fcore.isRuntime() || fcore.getBundle() != null) {
                            continue;
                        }
                        // Workspace Bundle
                        if (fcore.isWorkspace()) {
                            fcores.add(fcore);
                            continue;
                        }
                        // Cannot associate a Bundle to a target platform fcore
                        throw new CoreException(EGFProducerPlugin.getDefault().newStatus(IStatus.ERROR, NLS.bind(EGFCoreMessages.TargetPlatform_ExtensionPoint_no_bundle, fcore.getPlatformBundle().getBundleId()), null));
                    }
                }

                subMonitor = SubMonitor.convert(monitor, NLS.bind(EGFCoreMessages.Production_Invoke, EMFHelper.getText(activity[0])), (100 * fcores.size()) + (1000 * tasks.size()));

            	//logger.logStart(activity[0]);

                try {
                	
                    // 4 - Load workspace bundles in runtime
                    for (IPlatformFcore fcore : fcores) {
                        SubMonitor childSubMonitor = subMonitor.newChild(100, SubMonitor.SUPPRESS_NONE);
                        childSubMonitor = SubMonitor.convert(childSubMonitor, NLS.bind(EGFCoreMessages.Production_Load_Bundle, fcore.getPlatformBundle().getBundleId()), 100);
                        session.getBundle(fcore.getPlatformBundle().getProject());
                    }

                    // 5 - Switch to runtime mode
                    ResourceSet resourceSet = new RuntimePlatformResourceSet();
                    activity[0] = (Activity) resourceSet.getEObject(EcoreUtil.getURI(activity[0]), true);

                    // 5a - Set the input contract values:
                    // Currently only string inputs:
        			// assign input values to contracts:
        			EList<Contract> inContracts = activity[0].getContracts(ContractMode.IN);
        			for (Contract inoutcontract : inContracts) {
        				String contractName = inoutcontract.getName();
        				if (string_inputs.containsKey(contractName) ) {
	        				String value = string_inputs.get(contractName);
	        				if (inoutcontract.getType() instanceof TypeString) {
	        					((TypeString)inoutcontract.getType()).setValue(value);
	                            //Activator.getDefault().logInfo("Setting input contract " + contractName + " : " + value );
	        				}
        				}
        			}
                    
                    // 6 - Locate an ActivityManagerProducer and create an
                    // ActivityManager
                    ActivityManagerProducer<Activity> producer = EGFProducerPlugin.getActivityManagerProducer(activity[0]);
                    activityManager = producer.createActivityManager(activity[0]);
                    // Assign a ProjectBundleSession
                    activityManager.setProjectBundleSession(session);

                    // 7 - Initialize Context
                    activityManager.initializeContext();

                    // 8 - PreInvoke Validation
                    final Diagnostic preInvokeDiag = activityManager.canInvoke();
                    if (preInvokeDiag.getSeverity() != Diagnostic.OK) {
                        if (Activator.getWorkbenchDisplay() != null) {
                        	Activator.getWorkbenchDisplay().asyncExec(new Runnable() {
//                        if (Activator.getDefault().getWorkbench().getDisplay() != null) {
//                        	Activator.getDefault().getWorkbench().getDisplay().asyncExec(new Runnable() {

                                public void run() {
                                    EGFValidator.handleDiagnostic(ProducerUIMessages.ActivityValidationSelectionDialog_Title, ProducerUIMessages._UI_PreInvokeProblems_message, preInvokeDiag);
                                }

                            });
                        }
                        if (preInvokeDiag.getSeverity() == Diagnostic.ERROR) {
                            return Status.OK_STATUS;
                        }
                    }

                    // 9 - Run activity
                    if (Activator.getDefault().isDebugging()) {
                        if (tasks.size() == 1) {
                        	Activator.getDefault().logInfo(NLS.bind(ProducerMessages.Activity_Invocation, EMFHelper.getText(activity)));
                        } else {
                        	Activator.getDefault().logInfo(NLS.bind(ProducerMessages.Activity_Invocations, EMFHelper.getText(activity), tasks.size()));
                        }
                    }
                    final Diagnostic diagnostic = activityManager.invoke(subMonitor.newChild(1000 * tasks.size(), SubMonitor.SUPPRESS_NONE));
                    activityDiagnostic = diagnostic;
                    if (subMonitor.isCanceled()) {
                    	String message = "Cancelled executing '" + activity[0].getName() + "'";
                        if (activityDiagnostic != null && activityDiagnostic.getSeverity() != Diagnostic.OK) {
                        	if (activityDiagnostic.getMessage() != null) {
                        		message = message + System.lineSeparator() + " - " + activityDiagnostic.getMessage();
                        	}
                        } else {
                        	activityDiagnostic = Diagnostic.CANCEL_INSTANCE;
                        }
                        //logger.handleActivityFinishLogging(activity[0], activityDiagnostic);
                    	OperationCanceledException oce = new OperationCanceledException(message);
                        oce.initCause(oce);
                        throw oce;
                    }

                    // 10 - PostInvoke Validation
                    if (diagnostic != null && diagnostic.getSeverity() != Diagnostic.OK) {
                        if (Activator.getWorkbenchDisplay() != null) {
                            Activator.getWorkbenchDisplay().asyncExec(new Runnable() {
//                        if (PlatformUI.getWorkbench().getDisplay() != null) {
//                        	PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

                                public void run() {
                                    EGFValidator.handleDiagnostic(ProducerUIMessages.ActivityValidationSelectionDialog_Title, ProducerUIMessages._UI_PostInvokeProblems_message, diagnostic);
                                }

                            });
                        }
                    }

                } catch (InvocationException ie) {
    				activityDiagnostic = BasicDiagnostic.toDiagnostic(ie);
                    if (ie.getCause() != null && ie.getCause() instanceof CoreException) {
                        throw (CoreException) ie.getCause();
                    }
                    ThrowableHandler.handleThrowable(Activator.getDefault().getBundle().getSymbolicName(), ie);
                } catch (Throwable t) {
                    throw new CoreException(Activator.getDefault().newStatus(IStatus.ERROR, EGFCommonMessages.Exception_unexpectedException, t));
                } finally {
                    monitor.done();
                    try {
                        if (activityManager != null)
                            activityManager.dispose();
                    } catch (Throwable t) {
                        Activator.getDefault().logError(t);
                    }
                }

               // logger.handleActivityFinishLogging(activity[0], activityDiagnostic);
            	
                return Status.OK_STATUS;

            }

        };

        // Lock all the workspace
        activityJob.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
        activityJob.setProperty(IProgressConstants.ICON_PROPERTY, IProducerUIImages.EGF_RUN_ACTIVITY);
        activityJob.setPriority(Job.LONG);
        activityJob.setUser(true);
        activityJob.setSystem(false);
        activityJob.schedule();

        return;
		
	}
	

    private int showValidateDialog(List<Activity> activities, boolean validate, boolean prompt) {
        if (validate) {
            if (prompt && activities != null && activities.size() > 0) {
//                ActivityValidationSelectionDialog dialog = new ActivityValidationSelectionDialog(EGFProducerUIPlugin.getActiveWorkbenchShell(), activities);
                ActivityValidationSelectionDialog dialog = new ActivityValidationSelectionDialog(getActiveWorkbenchShell(), activities);
                if (dialog.open() == IDialogConstants.CANCEL_ID) {
                    return IDialogConstants.CANCEL_ID;
                }
                Object[] objects = dialog.getResult();
                _validates = new ArrayList<Activity>(objects.length);
                for (int i = 0; i < objects.length; i++) {
                    _validates.add((Activity) objects[i]);
                }
            } else {
                _validates = activities;
            }
        }
        return IDialogConstants.OK_ID;
    }
/*
	protected String getExecutingPluginId(Activity activity) {
		return ActivityRunnerLogger.INSTANCE.getExecutingPluginId(activity);
	}
	protected String getActivityStartMessage(Activity activity) {
		return ActivityRunnerLogger.INSTANCE.getActivityStartMessage(activity);
	}

	protected String getActivityFinishMessage(Activity activity, Diagnostic d) {
		return ActivityRunnerLogger.INSTANCE.getActivityFinishMessage(activity, d);
	}

	protected String getActivityResultSummary(Activity activity, Diagnostic d) {
		return ActivityRunnerLogger.INSTANCE.getActivityResultSummary(activity, d);
	}
	
	protected void logStart(Activity activity) {
		ActivityRunnerLogger.INSTANCE.logStart(activity);
	}
	protected void logFinish(IStatus status) {
		ActivityRunnerLogger.INSTANCE.logFinish(status);
	}
	protected void logSummary(IStatus status) {
		ActivityRunnerLogger.INSTANCE.logSummary(status);
	}
	protected IStatus handleActivityFinishLogging(Activity activity, Diagnostic activityDiagnostic) {
		return ActivityRunnerLogger.INSTANCE.handleActivityFinishLogging(activity, activityDiagnostic);
	}
*/
    /**
     * Returns the currently active shell for this workbench (if any).
     * 
     * @return the active workbench shell.
     */
    public static Shell getActiveWorkbenchShell() {
        IWorkbenchWindow window = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow();
        if (window == null) {
            IWorkbenchWindow[] windows = Activator.getDefault().getWorkbench().getWorkbenchWindows();
            if (windows.length > 0) {
                return windows[0].getShell();
            }
        } else {
            return window.getShell();
        }
        return null;
    }

}
