package dt.egf.utils.jobs;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.egf.model.fcore.Activity;
import org.eclipse.egf.model.fcore.Contract;
import org.eclipse.egf.model.fcore.ContractMode;
import org.eclipse.egf.model.types.TypeString;
import org.eclipse.egf.producer.EGFProducerPlugin;
import org.eclipse.egf.producer.manager.ActivityManagerProducer;
import org.eclipse.egf.producer.manager.IActivityManager;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;

public class ActivityManagerRunner {

	// Currently only string inputs: contract name - contract value.
    private Map<String, String> string_inputs = new HashMap<String, String>();

    //private IActivityRunnerLogger logger = ActivityRunnerLogger.INSTANCE;
//    
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
	
	public boolean runActivityViaManager(Activity activity) {

		boolean result = false;
		Diagnostic activityDiagnostic = Diagnostic.CANCEL_INSTANCE;

//		System.out.println("runActivityViaManager : " + logger.getActivityStartMessage(activity));
//		logger.logStart(activity);

		try {
			// assign input values to contracts:
			EList<Contract> inContracts =
			activity.getContracts(ContractMode.IN);
			for (Contract inoutcontract : inContracts) {
				if (string_inputs.containsKey(inoutcontract.getName())) {
					String value = string_inputs.get(inoutcontract.getName());
					if (inoutcontract.getType() instanceof TypeString) {
						((TypeString)inoutcontract.getType()).setValue(value);
					}
				}
			}

			// Invoke Activity
			ActivityManagerProducer<Activity> producer = EGFProducerPlugin
					.getActivityManagerProducer(activity);
			IActivityManager<Activity> manager = producer
					.createActivityManager(activity);

			try {

				manager.initializeContext();
				activityDiagnostic = 
						manager.invoke(new CodeGenUtil.EclipseUtil.StreamProgressMonitor(
						System.out));
				// manager.invoke(new NullProgressMonitor());

			} catch (Exception e) {
				e.printStackTrace();
				activityDiagnostic = BasicDiagnostic.toDiagnostic(e);
				//logger.logInfo("-");
				//EGFCorePlugin.getDefault().logError(
				//		">> Problem executing activity '" + activity.getName()
				//				+ "'", e);
			} finally {
				try {
					manager.dispose();
				} catch (Exception ignore) {
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			activityDiagnostic = BasicDiagnostic.toDiagnostic(e);
//			logger.logInfo("-");
//			EGFCorePlugin.getDefault().logError(
//					">> Problem executing activity '" + activity.getName()
//							+ "'", e);
//			return false;
		}
		
//		IStatus summaryStatus = logger.handleActivityFinishLogging(activity, activityDiagnostic);
//		System.out.println("runActivityViaManager : " + logger.getActivityFinishMessage(activity, activityDiagnostic));
		
//		switch (summaryStatus.getSeverity()) {
//			case IStatus.CANCEL:
//			case IStatus.ERROR:
//				result = false;
//			default:
//				result = true;
//		}		
		return result;
	}

}
