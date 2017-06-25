/**
 */
package workflow.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import workflow.ProcedureCall;
import workflow.WorkflowFactory;
import workflow.WorkflowPackage;

/**
 * This is the item provider adapter for a {@link workflow.ProcedureCall} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ProcedureCallItemProvider extends ExpressionItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcedureCallItemProvider(AdapterFactory adapterFactory) {
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

			addNamePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ProcedureCall_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ProcedureCall_name_feature", "_UI_ProcedureCall_type"),
				 WorkflowPackage.Literals.PROCEDURE_CALL__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
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
			childrenFeatures.add(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT);
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
	 * This returns ProcedureCall.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ProcedureCall"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((ProcedureCall)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_ProcedureCall_type") :
			getString("_UI_ProcedureCall_type") + " " + label;
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

		switch (notification.getFeatureID(ProcedureCall.class)) {
			case WorkflowPackage.PROCEDURE_CALL__NAME:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case WorkflowPackage.PROCEDURE_CALL__ARGUMENT:
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
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createConstant()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createProcedureCall()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createNot()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createUMinus()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createAnd()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createOr()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createEqual()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createGreaterThan()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createLessThan()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createGreaterThanOrEqual()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createLessThanOrEqual()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createAddition()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createSubtraction()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createMultiplication()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createDivision()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createVariable()));

		newChildDescriptors.add
			(createChildParameter
				(WorkflowPackage.Literals.PROCEDURE_CALL__ARGUMENT,
				 WorkflowFactory.eINSTANCE.createNotEqual()));
	}

}
