/**
 */
package workflow.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import workflow.Constant;
import workflow.WorkflowPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link workflow.impl.ConstantImpl#getAsBoolean <em>As Boolean</em>}</li>
 *   <li>{@link workflow.impl.ConstantImpl#getAsInteger <em>As Integer</em>}</li>
 *   <li>{@link workflow.impl.ConstantImpl#getAsReal <em>As Real</em>}</li>
 *   <li>{@link workflow.impl.ConstantImpl#getAsString <em>As String</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConstantImpl extends ExpressionImpl implements Constant {
	/**
	 * The default value of the '{@link #getAsBoolean() <em>As Boolean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAsBoolean()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean AS_BOOLEAN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAsBoolean() <em>As Boolean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAsBoolean()
	 * @generated
	 * @ordered
	 */
	protected Boolean asBoolean = AS_BOOLEAN_EDEFAULT;

	/**
	 * The default value of the '{@link #getAsInteger() <em>As Integer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAsInteger()
	 * @generated
	 * @ordered
	 */
	protected static final String AS_INTEGER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAsInteger() <em>As Integer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAsInteger()
	 * @generated
	 * @ordered
	 */
	protected String asInteger = AS_INTEGER_EDEFAULT;

	/**
	 * The default value of the '{@link #getAsReal() <em>As Real</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAsReal()
	 * @generated
	 * @ordered
	 */
	protected static final String AS_REAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAsReal() <em>As Real</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAsReal()
	 * @generated
	 * @ordered
	 */
	protected String asReal = AS_REAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getAsString() <em>As String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAsString()
	 * @generated
	 * @ordered
	 */
	protected static final String AS_STRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAsString() <em>As String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAsString()
	 * @generated
	 * @ordered
	 */
	protected String asString = AS_STRING_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConstantImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WorkflowPackage.Literals.CONSTANT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getAsBoolean() {
		return asBoolean;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAsBoolean(Boolean newAsBoolean) {
		Boolean oldAsBoolean = asBoolean;
		asBoolean = newAsBoolean;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.CONSTANT__AS_BOOLEAN, oldAsBoolean, asBoolean));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAsInteger() {
		return asInteger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAsInteger(String newAsInteger) {
		String oldAsInteger = asInteger;
		asInteger = newAsInteger;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.CONSTANT__AS_INTEGER, oldAsInteger, asInteger));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAsReal() {
		return asReal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAsReal(String newAsReal) {
		String oldAsReal = asReal;
		asReal = newAsReal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.CONSTANT__AS_REAL, oldAsReal, asReal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAsString() {
		return asString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAsString(String newAsString) {
		String oldAsString = asString;
		asString = newAsString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.CONSTANT__AS_STRING, oldAsString, asString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case WorkflowPackage.CONSTANT__AS_BOOLEAN:
				return getAsBoolean();
			case WorkflowPackage.CONSTANT__AS_INTEGER:
				return getAsInteger();
			case WorkflowPackage.CONSTANT__AS_REAL:
				return getAsReal();
			case WorkflowPackage.CONSTANT__AS_STRING:
				return getAsString();
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
			case WorkflowPackage.CONSTANT__AS_BOOLEAN:
				setAsBoolean((Boolean)newValue);
				return;
			case WorkflowPackage.CONSTANT__AS_INTEGER:
				setAsInteger((String)newValue);
				return;
			case WorkflowPackage.CONSTANT__AS_REAL:
				setAsReal((String)newValue);
				return;
			case WorkflowPackage.CONSTANT__AS_STRING:
				setAsString((String)newValue);
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
			case WorkflowPackage.CONSTANT__AS_BOOLEAN:
				setAsBoolean(AS_BOOLEAN_EDEFAULT);
				return;
			case WorkflowPackage.CONSTANT__AS_INTEGER:
				setAsInteger(AS_INTEGER_EDEFAULT);
				return;
			case WorkflowPackage.CONSTANT__AS_REAL:
				setAsReal(AS_REAL_EDEFAULT);
				return;
			case WorkflowPackage.CONSTANT__AS_STRING:
				setAsString(AS_STRING_EDEFAULT);
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
			case WorkflowPackage.CONSTANT__AS_BOOLEAN:
				return AS_BOOLEAN_EDEFAULT == null ? asBoolean != null : !AS_BOOLEAN_EDEFAULT.equals(asBoolean);
			case WorkflowPackage.CONSTANT__AS_INTEGER:
				return AS_INTEGER_EDEFAULT == null ? asInteger != null : !AS_INTEGER_EDEFAULT.equals(asInteger);
			case WorkflowPackage.CONSTANT__AS_REAL:
				return AS_REAL_EDEFAULT == null ? asReal != null : !AS_REAL_EDEFAULT.equals(asReal);
			case WorkflowPackage.CONSTANT__AS_STRING:
				return AS_STRING_EDEFAULT == null ? asString != null : !AS_STRING_EDEFAULT.equals(asString);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (asBoolean: ");
		result.append(asBoolean);
		result.append(", asInteger: ");
		result.append(asInteger);
		result.append(", asReal: ");
		result.append(asReal);
		result.append(", asString: ");
		result.append(asString);
		result.append(')');
		return result.toString();
	}

} //ConstantImpl
