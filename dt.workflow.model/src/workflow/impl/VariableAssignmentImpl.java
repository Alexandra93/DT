/**
 */
package workflow.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import workflow.Expression;
import workflow.Variable;
import workflow.VariableAssignment;
import workflow.WorkflowPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variable Assignment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link workflow.impl.VariableAssignmentImpl#getLValue <em>LValue</em>}</li>
 *   <li>{@link workflow.impl.VariableAssignmentImpl#getRValue <em>RValue</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VariableAssignmentImpl extends StatementImpl implements VariableAssignment {
	/**
	 * The cached value of the '{@link #getLValue() <em>LValue</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLValue()
	 * @generated
	 * @ordered
	 */
	protected Variable lValue;

	/**
	 * The cached value of the '{@link #getRValue() <em>RValue</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRValue()
	 * @generated
	 * @ordered
	 */
	protected Expression rValue;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariableAssignmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WorkflowPackage.Literals.VARIABLE_ASSIGNMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getLValue() {
		return lValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLValue(Variable newLValue, NotificationChain msgs) {
		Variable oldLValue = lValue;
		lValue = newLValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WorkflowPackage.VARIABLE_ASSIGNMENT__LVALUE, oldLValue, newLValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLValue(Variable newLValue) {
		if (newLValue != lValue) {
			NotificationChain msgs = null;
			if (lValue != null)
				msgs = ((InternalEObject)lValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WorkflowPackage.VARIABLE_ASSIGNMENT__LVALUE, null, msgs);
			if (newLValue != null)
				msgs = ((InternalEObject)newLValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WorkflowPackage.VARIABLE_ASSIGNMENT__LVALUE, null, msgs);
			msgs = basicSetLValue(newLValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.VARIABLE_ASSIGNMENT__LVALUE, newLValue, newLValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getRValue() {
		return rValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRValue(Expression newRValue, NotificationChain msgs) {
		Expression oldRValue = rValue;
		rValue = newRValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WorkflowPackage.VARIABLE_ASSIGNMENT__RVALUE, oldRValue, newRValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRValue(Expression newRValue) {
		if (newRValue != rValue) {
			NotificationChain msgs = null;
			if (rValue != null)
				msgs = ((InternalEObject)rValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WorkflowPackage.VARIABLE_ASSIGNMENT__RVALUE, null, msgs);
			if (newRValue != null)
				msgs = ((InternalEObject)newRValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WorkflowPackage.VARIABLE_ASSIGNMENT__RVALUE, null, msgs);
			msgs = basicSetRValue(newRValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.VARIABLE_ASSIGNMENT__RVALUE, newRValue, newRValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case WorkflowPackage.VARIABLE_ASSIGNMENT__LVALUE:
				return basicSetLValue(null, msgs);
			case WorkflowPackage.VARIABLE_ASSIGNMENT__RVALUE:
				return basicSetRValue(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case WorkflowPackage.VARIABLE_ASSIGNMENT__LVALUE:
				return getLValue();
			case WorkflowPackage.VARIABLE_ASSIGNMENT__RVALUE:
				return getRValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case WorkflowPackage.VARIABLE_ASSIGNMENT__LVALUE:
				setLValue((Variable)newValue);
				return;
			case WorkflowPackage.VARIABLE_ASSIGNMENT__RVALUE:
				setRValue((Expression)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case WorkflowPackage.VARIABLE_ASSIGNMENT__LVALUE:
				setLValue((Variable)null);
				return;
			case WorkflowPackage.VARIABLE_ASSIGNMENT__RVALUE:
				setRValue((Expression)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case WorkflowPackage.VARIABLE_ASSIGNMENT__LVALUE:
				return lValue != null;
			case WorkflowPackage.VARIABLE_ASSIGNMENT__RVALUE:
				return rValue != null;
		}
		return super.eIsSet(featureID);
	}

} //VariableAssignmentImpl
