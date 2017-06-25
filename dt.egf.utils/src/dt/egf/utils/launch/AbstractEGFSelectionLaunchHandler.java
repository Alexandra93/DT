package dt.egf.utils.launch;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IResource;
import org.eclipse.egf.model.fcore.Contract;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractEGFSelectionLaunchHandler extends AbstractEGFLaunchHandler {

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
		return getSelectionResource(selection) != null;
	}
	
	protected String getSelectionResourceURIstring(ISelection selection) {
		IResource context = getSelectionResource(selection);
		String resourceURIstring = context.getFullPath().toString();
		return resourceURIstring;
	}
	
	protected void setInputContractValues(EList<Contract> inContracts,
			ISelection selection) {
		// by default, assume resource selected, set to first contract (if any)
		if (inContracts.isEmpty()) return;
		Contract inoutcontract = inContracts.get(0);
		if (!string_inputs.containsKey(inoutcontract.getName())) {  // do not overwrite previously set value
			string_inputs.put(inoutcontract.getName(), getSelectionResourceURIstring(selection));
		}
	}
	
	protected IResource getSelectionResource(ISelection selection) {
		IResource res = null;
		if (selection instanceof TreeSelection) {
			TreeSelection sel_tree = (TreeSelection)selection;
			Object first = sel_tree.getFirstElement();
			if (first instanceof IResource) {
				res = (IResource)first;
			};
		}
		return res;
	}
	
	protected String getSelectionInfo(ISelection selection) {
		IResource res = getSelectionResource(selection);
		if (res != null) {
			return res.getName();
		}
		return selection.toString();
	}
}
