package dt.egf.utils.workflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.egf.core.EGFCorePlugin;
import org.eclipse.egf.core.producer.InvocationException;
import org.eclipse.egf.ftask.producer.context.ITaskProductionContext;
import org.eclipse.egf.model.domain.Domain;
import org.eclipse.egf.model.domain.TypeDomain;
import org.eclipse.egf.model.fcore.Contract;
import org.eclipse.egf.model.fcore.ContractMode;
import org.eclipse.egf.model.ftask.Task;
import org.eclipse.egf.model.types.*;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

public class ContractHelper {
	
	public ArrayList<Contract> getOrderedInputContracts(ITaskProductionContext productionContext) throws InvocationException {
		ArrayList<Contract> contracts = new ArrayList<Contract>();
		contracts.addAll(productionContext.getInputValueKeys());
		Collections.sort(contracts, new CompareContracts(productionContext));
		return reduceToUniqueNamedList(contracts);
	}
	
	public ArrayList<Contract> getOrderedOutputContracts(ITaskProductionContext productionContext) throws InvocationException {
		ArrayList<Contract> contracts = new ArrayList<Contract>();
		contracts.addAll(productionContext.getOutputValueKeys());
		Collections.sort(contracts, new CompareContracts(productionContext));
		return reduceToUniqueNamedList(contracts);
	}
	
	protected ArrayList<Contract> reduceToUniqueNamedList(ArrayList<Contract> contract) {
		HashMap<String, Contract> simpleMap = new HashMap<String, Contract>();
		for (Contract ctr : contract) {
			simpleMap.put(ctr.getName(), ctr);
		}
		// we need to maintain the order
		// now create a new list by removing all contracts that are not in the map:
		ArrayList<Contract> remaining_contracts = new ArrayList<Contract>();
		for (Contract ctr : contract) {
			if (simpleMap.containsValue(ctr)) {
				remaining_contracts.add(ctr);
			}
		}
		return remaining_contracts;
	}
	
	class CompareContracts implements Comparator<Contract> {
		EList<Contract> clist = new BasicEList<Contract>();
		public CompareContracts(ITaskProductionContext productionContext) {
			// Construct list of contracts, taking inheritance into account
			addTaskContractsToList(clist, productionContext.getElement());
		}
		public int compare(Contract o1, Contract o2) {
			return clist.indexOf(o1) - clist.indexOf(o2);
		};
		private EList<Contract> addTaskContractsToList(EList<Contract> contracts, Task task) {
			// recursive: need to add contracts of supertask first:
			if (task.getSuperTask() != null) {
				addTaskContractsToList(contracts, task.getSuperTask());
			}
			// now we can add the contracts of this task:
			contracts.addAll(task.getContracts());
			return contracts;
		}
	}
	
	public static boolean isInput(Contract contract) {
		return (contract.getMode().equals(ContractMode.IN) 
				|| contract.getMode().equals(ContractMode.IN_OUT) );
	}
	
	public static boolean isOutput(Contract contract) {
		return (contract.getMode().equals(ContractMode.OUT) 
				|| contract.getMode().equals(ContractMode.IN_OUT) );
	}
	
	public static boolean isInOut(Contract contract) {
		return (contract.getMode().equals(ContractMode.IN_OUT) );
	}
	
	public static Contract getContract(ITaskProductionContext productionContext, String keyname) {
		try {
			for (Contract c : productionContext.getInputValueKeys()) {
				if (c.getName().equals(keyname)) return c;
			}
			for (Contract c : productionContext.getOutputValueKeys()) {
				if (c.getName().equals(keyname)) return c;
			}
		} catch (InvocationException e) {
			//e.printStackTrace();
		}
		return null;
	}

	public static URI getInputURI(ITaskProductionContext productionContext, String keyname) throws InvocationException {
		URI uri = null;
		try {
			if (productionContext.getInputValueType(keyname) == URI.class) {
				uri = productionContext.getInputValue(keyname, URI.class);
			} else if (productionContext.getInputValueType(keyname) == String.class) {
			    String uriString = productionContext.getInputValue(keyname, String.class);
			    if (uriString != null && uriString.trim().length() > 0) {
			    	uri = URI.createURI(uriString);
			    }			
			}
		} catch (InvocationException e) {
			EGFCorePlugin.getDefault().logError(productionContext.getName() + ": Cannot read URI from contract " + keyname, e);
			throw e;
		}
		return uri;
	}

	public static void writeOutputURI(ITaskProductionContext productionContext, String keyname, URI uri) throws InvocationException {
		try {
			if ((productionContext.getOutputValueType(keyname) == String.class )) {
				productionContext.setOutputValue(keyname, uri.toString());
			} else {
				productionContext.setOutputValue(keyname, uri);
			}
		} catch (InvocationException e) {
			EGFCorePlugin.getDefault().logError(productionContext.getName() + ": Cannot write URI to contract " + keyname, e);
			throw e;
		}
	}

	public static void writeOutput(ITaskProductionContext productionContext, String keyname, Object value) throws InvocationException {
		try {
			Class<?> outputValueType = productionContext.getOutputValueType(keyname);
			if (outputValueType == String.class) {
				// force to write as string
				productionContext.setOutputValue(keyname, value.toString());
			} else if (Collection.class.isAssignableFrom(outputValueType)) {
				// delegate to collection writer
				writeOptionalOutputCollectionValue(productionContext, keyname, value);
			} else {
				productionContext.setOutputValue(keyname, value);
			}
		} catch (InvocationException e) {
			EGFCorePlugin.getDefault().logError(productionContext.getName() + ": Cannot write contract " + keyname, e);
			throw e;
		}
	}

	public static boolean hasInputContract(ITaskProductionContext productionContext, String keyname) {
		try {
			for (Contract c : productionContext.getInputValueKeys()) {
				if (c.getName().equals(keyname)) return true;
			}
		} catch (InvocationException e) {
			//e.printStackTrace();
			EGFCorePlugin.getDefault().logError(productionContext.getName() + ": Problem iterating input contracts to find " + keyname, e);
		}
		return false;
	}

	public static boolean hasInputContractValue(ITaskProductionContext productionContext, String keyname) {
		try {
			if (hasInputContract(productionContext, keyname)) {
				return (productionContext.getInputValue(keyname, Object.class) != null);
			}
		} catch (InvocationException e) {
			//e.printStackTrace();
			EGFCorePlugin.getDefault().logError(productionContext.getName() + ": Problem reading input value for contract " + keyname, e);
		}
		return false;
	}

	public static boolean hasOutputContract(ITaskProductionContext productionContext, String keyname) {
		try {
			for (Contract c : productionContext.getOutputValueKeys()) {
				if (c.getName().equals(keyname)) return true;
			}
		} catch (InvocationException e) {
			//e.printStackTrace();
			EGFCorePlugin.getDefault().logError(productionContext.getName() + ": Problem iterating output contracts to find " + keyname, e);
		}
		return false;
	}
	
    public static <R> R readOptionalInputValue(ITaskProductionContext productionContext, 
    				String keyname, Class<R> clazz, R defaultValue) {
    	if (hasInputContract(productionContext, keyname)) {
    		try {
				R res =  productionContext.getInputValue(keyname, clazz);
				if (res != null) {
					return res;
				}
			} catch (InvocationException e) {
				// Ignore
				EGFCorePlugin.getDefault().logWarning(productionContext.getName() + ": Problem reading input value for contract " + keyname, e);
			}
    	}
    	return defaultValue;
    }

    public static boolean writeOptionalOutputURIValue(ITaskProductionContext productionContext, String keyname, URI value) {
		if (hasOutputContract(productionContext, keyname)) {
			try {
				writeOutputURI(productionContext, keyname, value);
				return true;
			} catch (InvocationException e) {
				// Ignore
			}
		}
		return false;
    }

    public static boolean writeOptionalOutputValue(ITaskProductionContext productionContext, String keyname, Object value) {
		if (hasOutputContract(productionContext, keyname)) {
			try {
				writeOutput(productionContext, keyname, value);
				return true;
			} catch (InvocationException e) {
				// Ignore
			}
		}
		return false;
    }
    
    public static boolean writeOptionalOutputCollectionValue(ITaskProductionContext productionContext, String keyname, Object value) {
    	if (hasOutputContract(productionContext, keyname)
    			&& isCollectionContract(productionContext, keyname)) {
			try {
				boolean isAssigned = false;
				Class<?> cls = productionContext.getOutputValueType(keyname);
				Collection<Object> outVals = null;
				if (cls.isAssignableFrom(value.getClass())) {
					productionContext.setOutputValue(keyname, value);
					isAssigned = true;
				} else {
					if (Set.class.isAssignableFrom(cls)) {
						if (value instanceof Collection) {
							outVals = new HashSet<Object>((Collection<?>)value);
						} else if (value instanceof Iterable) {
							outVals = new HashSet<Object>();
							for (Object object : outVals) {
								outVals.add(object);
							}
						} else {
							outVals = new HashSet<Object>();
							outVals.add(value);
						}
					} else	{
						if (value instanceof Collection) {
							outVals = new ArrayList<Object>((Collection<?>)value);
						} else if (value instanceof Iterable) {
							outVals = new ArrayList<Object>();
							for (Object object : outVals) {
								outVals.add(object);
							}
						} else {
							outVals = new ArrayList<Object>();
							outVals.add(value);
						}
					}
					productionContext.setOutputValue(keyname, outVals);
					isAssigned = true;
				}
				return isAssigned;
			} catch (InvocationException e) {
				e.printStackTrace();
			}
    	}
		return false;
    }

    public static boolean assignContract(Contract contract, Object value) {
    	if (value == null) return false;
    	boolean isAssigned = false;
    	try {
        	if (contract.getType().getType().isAssignableFrom(value.getClass())) {
        		if (contract.getType() instanceof TypeAbstractClass) {
        			((TypeAbstractClass)contract.getType()).setValue(value.toString());
        		} else if (contract.getType() instanceof TypeString) {
        			((TypeString)contract.getType()).setValue(value.toString());
        		} else if (contract.getType() instanceof TypeInteger) {
        			((TypeInteger)contract.getType()).setValue((Integer)value);
        		} else if (contract.getType() instanceof TypeBoolean) {
        			((TypeBoolean)contract.getType()).setValue((Boolean)value);
        		} else if (contract.getType() instanceof TypeByte) {
        			((TypeByte)contract.getType()).setValue((Byte)value);
        		} else if (contract.getType() instanceof TypeCharacter) {
        			((TypeCharacter)contract.getType()).setValue((Character)value);
        		} else if (contract.getType() instanceof TypeDate) {
        			((TypeDate)contract.getType()).setValue((Date)value);
        		} else if (contract.getType() instanceof TypeDomain) {
        			((TypeDomain)contract.getType()).setValue((Domain)value);
        		} else if (contract.getType() instanceof TypeDouble) {
        			((TypeDouble)contract.getType()).setValue((Double)value);
        		} else if (contract.getType() instanceof TypeFloat) {
        			((TypeFloat)contract.getType()).setValue((Float)value);
        		} else if (contract.getType() instanceof TypeLong) {
        			((TypeLong)contract.getType()).setValue((Long)value);
        		} else if (contract.getType() instanceof TypeShort) {
        			((TypeShort)contract.getType()).setValue((Short)value);
        		} else if (contract.getType() instanceof TypeURI) {
        			((TypeURI)contract.getType()).setValue((URI)value);
        		};
        		isAssigned = (contract.getType().getValue().equals(value));
        		
        		if (contract.getType() instanceof TypeList) {
        			((TypeList)contract.getType()).setInstance(value);
        			isAssigned = true;
        		} else if (contract.getType() instanceof TypeMap) {
        			((TypeMap)contract.getType()).setInstance(value);
        			isAssigned = true;
        		} else if (contract.getType() instanceof TypeCollection) {
        			((TypeCollection)contract.getType()).setInstance(value);
        			isAssigned = true;
        		}
        	}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return isAssigned;
    }
    
    public static boolean isCollectionContract(ITaskProductionContext productionContext, 
    				String keyname) {
    	try {
    		Class<?> cls = null;
			if (hasInputContract(productionContext, keyname)) {
				cls = productionContext.getInputValueType(keyname);
			} else if (hasOutputContract(productionContext, keyname)) {
				cls = productionContext.getOutputValueType(keyname);
			}
			if (cls != null) {
				return Collection.class.isAssignableFrom(cls);
			}
		} catch (InvocationException e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    public static boolean isCollectionContract(Contract contract) {
    	Class<?> cls = contract.getType().getType();
    	if (cls != null) {
			return Collection.class.isAssignableFrom(cls);
    	}
    	return false;
	}    
    
    @SuppressWarnings("unchecked")
	public static <T> List<T> readOptionalInputAsList(ITaskProductionContext productionContext, 
    				String keyname, List<T> defaultValue) {
		if (hasInputContract(productionContext, keyname)) {
			
			try {
				List<T> valueList = Collections.emptyList();
				Class<?> cls = productionContext.getInputValueType(keyname);
				if (Collection.class.isAssignableFrom(cls)) {
					// is an iterable type:
					Collection<T> inputValues = productionContext.getInputValue(keyname, Collection.class);
					if (inputValues != null) {
						valueList = new ArrayList<T>(inputValues);
					} else {
						if (defaultValue != null) {
							valueList = new ArrayList<T>(defaultValue);
						}
// is optional, so let it be...
//						EGFCorePlugin.getDefault().logError(productionContext.getName() + 
//								": Problem reading input value for contract " + keyname);
					}
				} else {
					Object inputValue = productionContext.getInputValue(keyname, Object.class);
					if (inputValue != null) {
						valueList = new ArrayList<T>();
						valueList.add((T)inputValue);
					} else {
						if (defaultValue != null) {
							valueList.addAll(defaultValue);
						}
// is optional, so let it be...
//						EGFCorePlugin.getDefault().logError(productionContext.getName() + 
//								": Problem reading input value for contract " + keyname);
					}
				}
		    	return valueList;
			} catch (InvocationException e) {
				e.printStackTrace();
				EGFCorePlugin.getDefault().logError(productionContext.getName() + 
				": Problem reading input value for contract " + keyname, e);
			} catch (ClassCastException e) {
				e.printStackTrace();
				EGFCorePlugin.getDefault().logError(productionContext.getName() + 
				": Problem reading input value for contract " + keyname, e);
			}
		}
    	
    	return defaultValue;
    }
}
