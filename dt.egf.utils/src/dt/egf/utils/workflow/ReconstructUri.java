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

/**
 * 
 * @author marc.hamilton
 * 
 * Reconstruction and parsing of uri is based on the {@link KeysURI} class.
 * See {@link KeysURI} for which keys are recognized in URI construction.
 * 
 * Other possible keys:
 * forceScheme : Boolean : true implies to apply scheme when uri doesn't provide one
 * 
 * The reconstruction is delegated to the {@link URIConstructor} class.
 * 
 */
public class ReconstructUri implements ITaskProduction {

//	EGFAbstractPlugin logger = EGFCorePlugin.getDefault();
	//EGFAbstractPlugin logger = Activator.getDefault();
	URI uri = null;
	URIConstructor uriConstructor = new URIConstructor();
	
	public static final String KEY_FORCESCHEME = "forceScheme";
	
	public void preExecute(ITaskProductionContext productionContext, IProgressMonitor monitor) throws InvocationException {
		
		Boolean forceScheme = true; // if uri has no scheme, apply default one
		try {
			forceScheme = productionContext.getInputValue(KEY_FORCESCHEME, Boolean.class);
		} catch (Exception e) {
			// forceScheme is allowed to be empty/non specified
			forceScheme = true;
		}
		try {
			if (productionContext.getInputValueType(KeysURI.KEY_URI) == URI.class) {
				uri = productionContext.getInputValue(KeysURI.KEY_URI, URI.class);
			} else if (productionContext.getInputValueType(KeysURI.KEY_URI) == String.class) {
			    String uriString = productionContext.getInputValue(KeysURI.KEY_URI, String.class);
			    if (uriString != null && uriString.trim().length() > 0) {
				    uri = URI.createURI(uriString);
			    }			
			}
		} catch (Exception e) {
			// uri is allowed to be empty/non specified
		}
		if (productionContext.getOutputValueKeys().isEmpty()) {
			//logger.logError("ReconstructURI: No output requested.");
		} else {
			uriConstructor.parseURI(uri, forceScheme); // get default values for URI properties, if any.
			for (Iterator<Contract> iterator = new ContractHelper().getOrderedInputContracts(productionContext).iterator(); iterator
					.hasNext();) {
				Contract contract = (Contract) iterator.next();
				String name = contract.getName();
				if (name.equals(KeysURI.KEY_URI)) continue;
				if (name.equals(KEY_FORCESCHEME)) continue;
				@SuppressWarnings("unchecked")
				Object value = productionContext.getInputValue(name, Object.class);
//				if (value == null) value = contract.getType().getValue(); // get default
				if (contract.isMandatory() || value != null) {
//					logger.logInfo("ReconstructURI: Read contract '" + name + "' = " + value);
					uriConstructor.setValue(name, value);
				} // else we have a non-mandatory input which is just not specified -- which is ok: use the default.
			}
		}
	}

	public void doExecute(ITaskProductionContext productionContext, IProgressMonitor monitor) throws InvocationException {

		if (!productionContext.getOutputValueKeys().isEmpty()) {
//			logger.logInfo("ReconstructURI: write outputs"); 		  
	
			uriConstructor.constructURI();
			
			for (Iterator<Contract> iterator = productionContext.getOutputValueKeys().iterator(); iterator
					.hasNext();) {
				Contract contract = (Contract) iterator.next();
				String key = contract.getName();
				writeValue(productionContext, key, uriConstructor.getValue(key));
			}
			
//		    logger.logInfo("ReconstructURI: OK"); 		  
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
//			logger.logInfo("ReconstructURI: Write contract '" + keyname + "' = " + writeValue);
		} catch (InvocationException e) {
			// ignore, log message
			//logger.logWarning("ReconstructURI: Cannot write contract '" + keyname + "'", e);
		} catch (NullPointerException e) {
			//logger.logError("ReconstructURI: NULL value writing contract '" + keyname + "'", e);
		}
	}
	
}
