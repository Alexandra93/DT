package dt.egf.utils.jobs;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.egf.model.fcore.Activity;
import org.eclipse.emf.common.util.Diagnostic;

public interface IActivityRunnerLogger {
	public static final String NEWLINE = System.lineSeparator();
	public static final String BEGINSTARTLINE = ">> ";
	public static final String BEGINFINISHLINE = ">> ";
	public static final String BEGINRESULTLINE = ">> ";
	
	public String getExecutingPluginId(Activity activity);
	public boolean setEnableLogging(boolean enable); // returns current state
	
	public String getActivityStartMessage(Activity activity);
	public String getActivityFinishMessage(Activity activity, Diagnostic d);
	public String getActivityResultSummary(Activity activity, Diagnostic d);
	
	public void logStart(Activity activity);
	public void logFinish(IStatus status);
	public void logSummary(IStatus status);

	public IStatus handleActivityFinishLogging(Activity activity, Diagnostic activityDiagnostic);
}
