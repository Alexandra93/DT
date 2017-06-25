package dt.egf.utils.workflow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egf.core.producer.InvocationException;
import org.eclipse.egf.ftask.producer.context.ITaskProductionContext;
import org.eclipse.egf.ftask.producer.invocation.ITaskProduction;
import org.eclipse.egf.model.fcore.Contract;
import org.eclipse.emf.common.util.URI;


public class DisassembleUri implements ITaskProduction {

//	EGFAbstractPlugin logger = EGFCorePlugin.getDefault();
	//EGFAbstractPlugin logger = Activator.getDefault();
	URI uri = null;
	URIConstructor uriConstructor = new URIConstructor();
	
	static String KEY_FORCESCHEME = "forceScheme";

	public void preExecute(ITaskProductionContext productionContext, IProgressMonitor monitor) throws InvocationException {

		Boolean forceScheme = true; // if uri has no scheme, apply default one
		try {
			forceScheme = productionContext.getInputValue(KEY_FORCESCHEME, Boolean.class);
		} catch (Exception e) {
			// forceScheme is allowed to be empty/non specified
			forceScheme = true;
		}
		if (productionContext.getInputValueType(KeysURI.KEY_URI) == URI.class) {
			uri = productionContext.getInputValue(KeysURI.KEY_URI, URI.class);
		} else if (productionContext.getInputValueType(KeysURI.KEY_URI) == String.class) {
		    String uriString = productionContext.getInputValue(KeysURI.KEY_URI, String.class);
		    if (uriString != null && uriString.trim().length() > 0) {
			    uri = URI.createURI(uriString);
		    }			
		}
		if (uri == null) {
			//logger.logError("DisassembleURI: Cannot retrieve URI from input");
		} else if (productionContext.getOutputValueKeys().isEmpty()) {
			//logger.logError("DisassembleURI: No output requested.");
		} else {
			uriConstructor.parseURI(uri, forceScheme); // get default values for URI properties
		}
	}

	public void doExecute(ITaskProductionContext productionContext, IProgressMonitor monitor) throws InvocationException {

		if ((uri != null) && (!productionContext.getOutputValueKeys().isEmpty())) {
//			logger.logInfo("DisassembleURI: uri = " + uri); 		  
	
			for (Iterator<Contract> iterator = productionContext.getOutputValueKeys().iterator(); iterator
					.hasNext();) {
				Contract contract = (Contract) iterator.next();
				String key = contract.getName();
				writeValue(productionContext, key, uriConstructor.getValue(key));
			}
			
//		    logger.logInfo("DisassembleURI: OK"); 		  
		}
	}

	public void postExecute(ITaskProductionContext productionContext, IProgressMonitor monitor) throws InvocationException {
		// Nothing to do
	}
	
	private void writeValue(ITaskProductionContext productionContext, String keyname, Object value) {
		try {
			Object writeValue = value;
			if (value instanceof String) {
				writeValue = (value == null ? "": value);
			} else if (value instanceof List) {
				writeValue = (value == null || ((List)value).isEmpty() ? new ArrayList<String>(): value);
			} else if (value instanceof Integer) {
				writeValue = (value == null ? 0: value);
			}
			if ((productionContext.getOutputValueType(keyname) == String.class )
					&& !(writeValue instanceof String)
				) {
				writeValue = (value == null ? "": writeValue.toString());
			}
			productionContext.setOutputValue(keyname, writeValue);
//			logger.logInfo("DisassembleURI: Write contract '" + keyname + "' = " + writeValue);
		} catch (InvocationException e) {
			// ignore, log message
			//logger.logWarning("DisassembleURI: Cannot write contract '" + keyname + "'", e);
		}
	}
}
