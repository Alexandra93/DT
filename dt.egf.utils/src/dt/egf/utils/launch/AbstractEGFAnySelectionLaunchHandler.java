package dt.egf.utils.launch;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.egf.model.fcore.Contract;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractEGFAnySelectionLaunchHandler extends AbstractEGFLaunchHandler {

	private ISelection activeSelection;
	
	@Override
	protected boolean isContextValid(ExecutionEvent event)
	{
//		if (activeSelection == null)
//		{
			activeSelection = getActiveSelectionFromEvent(event);
//		}	
		return isSelectionValid(activeSelection);
	}
	
	protected ISelection getActiveSelection() {
		return activeSelection;
	}
	
	@Override
	protected String getDialogTitle(ExecutionEvent event) {
		return getDialogTitle(activeSelection);
	}
	
	@Override
	protected String getDialogDescription(ExecutionEvent event) {
		return getDialogDescription(activeSelection);
	}
	
	@Override
	protected void setInputContractValues(EList<Contract> inContracts,
			ExecutionEvent event) {
		setInputContractValues(inContracts, activeSelection);
		
		super.setInputContractValues(inContracts, event);
	}
	
	protected ISelection getActiveSelectionFromEvent(ExecutionEvent event) {
		 ISelection selection = HandlerUtil.getActiveMenuSelection(event);
		return selection;
	}
	
	protected String getDialogTitle(ISelection selection) {
		return "<Specify Dialog Title>";
	}
	
	protected String getDialogDescription(ISelection selection) {
		return "<Specify Dialog Description>\nSelected: " + getSelectionInfo(selection);
	}
	
	protected boolean isSelectionValid(ISelection selection) {
		return getSelectionObject(selection) != null;
	}
	
	protected String getSelectionContractValueString(ISelection selection) {
		Object context = getSelectionObject(selection);
		String contractValuestring = context.toString();
		return contractValuestring;
	}
	
	protected void setInputContractValues(EList<Contract> inContracts,
			ISelection selection) {
		// by default, assume useful thing selected, set to first contract (if any)
		if (inContracts.isEmpty()) return;
		Contract inoutcontract = inContracts.get(0);
		if (!string_inputs.containsKey(inoutcontract.getName())) {  // do not overwrite previously set value
			string_inputs.put(inoutcontract.getName(), getSelectionContractValueString(selection));
		}
	}
	
	protected Object getSelectionObject(ISelection selection) {
		Object res = null;
		if (selection instanceof StructuredSelection) {
			StructuredSelection sel_struct = (StructuredSelection)selection;
			res = sel_struct.getFirstElement();
		}
		return res;
	}
	
	protected String getSelectionInfo(ISelection selection) {
		Object res = getSelectionObject(selection);
		if (res != null) {
			return res.toString();
		}
		return selection.toString();
	}
}
