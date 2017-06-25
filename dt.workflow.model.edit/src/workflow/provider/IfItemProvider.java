/**
 */
package workflow.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import workflow.If;
import workflow.WorkflowFactory;
import workflow.WorkflowPackage;

/**
 * This is the item provider adapter for a {@link workflow.If} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class IfItemProvider extends StatementItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IfItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

		}
		return itemPropertyDescriptors;
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(WorkflowPackage.Literals.IF__CONDITION);
			childrenFeatures.add(WorkflowPackage.Literals.IF__BODY);
			childrenFeatures.add(WorkflowPackage.Literals.IF__ALTERNATIVE);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns If.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/If"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_If_type");
	}
	

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(If.class)) {
			case WorkflowPackage.IF__CONDITION:
			case WorkflowPackage.IF__BODY:
			case WorkflowPackage.IF__ALTERNATIVE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createConstant()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createProcedureCall()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createNot()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createUMinus()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createAnd()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createOr()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createEqual()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createGreaterThan()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createLessThan()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createGreaterThanOrEqual()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createLessThanOrEqual()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createAddition()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createSubtraction()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createMultiplication()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createDivision()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createVariable()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__CONDITION,
				 WorkflowFactory.eINSTANCE.createNotEqual()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__BODY,
				 WorkflowFactory.eINSTANCE.createBlock()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.IF__ALTERNATIVE,
				 WorkflowFactory.eINSTANCE.createBlock()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == WorkflowPackage.Literals.IF__BODY ||
			childFeature == WorkflowPackage.Literals.IF__ALTERNATIVE;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
