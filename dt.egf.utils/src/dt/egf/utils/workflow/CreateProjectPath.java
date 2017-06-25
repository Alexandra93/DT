package dt.egf.utils.workflow;


import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egf.common.activator.EGFAbstractPlugin;
import org.eclipse.egf.core.producer.InvocationException;
import org.eclipse.egf.ftask.producer.context.ITaskProductionContext;
import org.eclipse.egf.ftask.producer.invocation.ITaskProduction;

public class CreateProjectPath implements ITaskProduction {

	private String projectName = null; 
	private String projectSubPath = null;
	//private final EGFAbstractPlugin logger = Activator.getDefault();
	
	@Override
	public void preExecute(ITaskProductionContext productionContext,
			IProgressMonitor monitor) throws InvocationException {

		projectName = productionContext.getInputValue(KeysURI.KEY_PROJECTNAME, String.class); 
		projectSubPath = productionContext.getInputValue(KeysURI.KEY_PROJECTSUBPATH, String.class); 
		
	}

	@Override
	public void doExecute(ITaskProductionContext productionContext,
			IProgressMonitor monitor) throws InvocationException {

//		String taskinfo = MessageLogger.getQualifiedTaskInfo(productionContext, 0)
//						  +  System.getProperty("line.separator") + " - ";
		
		if ((projectName != null) && (projectSubPath != null)) {
			String msginfo = "folder [" + projectSubPath + "] in project [" + projectName + "]";
			//String errmsg = taskinfo + "Could not create " + msginfo;
			if (!projectName.isEmpty() && !projectSubPath.isEmpty()) {
				
				try {
				//	logger.logInfo("Creating path in " + msginfo);
					URIUtils.getNewPath(projectName, projectSubPath);
				} catch (CoreException e) {
				//	logger.logError(errmsg);
					//throw new InvocationException(msg);
				}
			} else {
				//logger.logError(errmsg);
			}
		}
		if (projectName == null) {
			//logger.logError(taskinfo + "projectName is <null>");
		}
		if (projectSubPath == null) {
			//logger.logError(taskinfo + "projectSubPath is <null>");
		}
		
	}

	@Override
	public void postExecute(ITaskProductionContext productionContext,
			IProgressMonitor monitor) throws InvocationException {
		// TODO Auto-generated method stub

	}


}
