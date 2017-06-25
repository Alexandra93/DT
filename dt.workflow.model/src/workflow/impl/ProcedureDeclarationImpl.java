/**
 */
package workflow.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import workflow.AccessModifiers;
import workflow.Block;
import workflow.ParameterDeclaration;
import workflow.ProcedureDeclaration;
import workflow.Type;
import workflow.VariableDeclaration;
import workflow.WorkflowPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Procedure Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link workflow.impl.ProcedureDeclarationImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link workflow.impl.ProcedureDeclarationImpl#getReturnType <em>Return Type</em>}</li>
 *   <li>{@link workflow.impl.ProcedureDeclarationImpl#getBody <em>Body</em>}</li>
 *   <li>{@link workflow.impl.ProcedureDeclarationImpl#getParameter <em>Parameter</em>}</li>
 *   <li>{@link workflow.impl.ProcedureDeclarationImpl#getAccessModifier <em>Access Modifier</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProcedureDeclarationImpl extends DeclarationImpl implements ProcedureDeclaration {
	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<VariableDeclaration> variables;

	/**
	 * The default value of the '{@link #getReturnType() <em>Return Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnType()
	 * @generated
	 * @ordered
	 */
	protected static final Type RETURN_TYPE_EDEFAULT = Type.INT;

	/**
	 * The cached value of the '{@link #getReturnType() <em>Return Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnType()
	 * @generated
	 * @ordered
	 */
	protected Type returnType = RETURN_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected Block body;

	/**
	 * The cached value of the '{@link #getParameter() <em>Parameter</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameter()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterDeclaration> parameter;

	/**
	 * The default value of the '{@link #getAccessModifier() <em>Access Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccessModifier()
	 * @generated
	 * @ordered
	 */
	protected static final AccessModifiers ACCESS_MODIFIER_EDEFAULT = AccessModifiers.DEFAULT;

	/**
	 * The cached value of the '{@link #getAccessModifier() <em>Access Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccessModifier()
	 * @generated
	 * @ordered
	 */
	protected AccessModifiers accessModifier = ACCESS_MODIFIER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcedureDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WorkflowPackage.Literals.PROCEDURE_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<VariableDeclaration> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentEList<VariableDeclaration>(VariableDeclaration.class, this, WorkflowPackage.PROCEDURE_DECLARATION__VARIABLES);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type getReturnType() {
		return returnType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturnType(Type newReturnType) {
		Type oldReturnType = returnType;
		returnType = newReturnType == null ? RETURN_TYPE_EDEFAULT : newReturnType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.PROCEDURE_DECLARATION__RETURN_TYPE, oldReturnType, returnType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Block getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBody(Block newBody, NotificationChain msgs) {
		Block oldBody = body;
		body = newBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WorkflowPackage.PROCEDURE_DECLARATION__BODY, oldBody, newBody);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBody(Block newBody) {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WorkflowPackage.PROCEDURE_DECLARATION__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WorkflowPackage.PROCEDURE_DECLARATION__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.PROCEDURE_DECLARATION__BODY, newBody, newBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParameterDeclaration> getParameter() {
		if (parameter == null) {
			parameter = new EObjectContainmentEList<ParameterDeclaration>(ParameterDeclaration.class, this, WorkflowPackage.PROCEDURE_DECLARATION__PARAMETER);
		}
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AccessModifiers getAccessModifier() {
		return accessModifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAccessModifier(AccessModifiers newAccessModifier) {
		AccessModifiers oldAccessModifier = accessModifier;
		accessModifier = newAccessModifier == null ? ACCESS_MODIFIER_EDEFAULT : newAccessModifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.PROCEDURE_DECLARATION__ACCESS_MODIFIER, oldAccessModifier, accessModifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case WorkflowPackage.PROCEDURE_DECLARATION__VARIABLES:
				return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
			case WorkflowPackage.PROCEDURE_DECLARATION__BODY:
				return basicSetBody(null, msgs);
			case WorkflowPackage.PROCEDURE_DECLARATION__PARAMETER:
				return ((InternalEList<?>)getParameter()).basicRemove(otherEnd, msgs);
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
			case WorkflowPackage.PROCEDURE_DECLARATION__VARIABLES:
				return getVariables();
			case WorkflowPackage.PROCEDURE_DECLARATION__RETURN_TYPE:
				return getReturnType();
			case WorkflowPackage.PROCEDURE_DECLARATION__BODY:
				return getBody();
			case WorkflowPackage.PROCEDURE_DECLARATION__PARAMETER:
				return getParameter();
			case WorkflowPackage.PROCEDURE_DECLARATION__ACCESS_MODIFIER:
				return getAccessModifier();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case WorkflowPackage.PROCEDURE_DECLARATION__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends VariableDeclaration>)newValue);
				return;
			case WorkflowPackage.PROCEDURE_DECLARATION__RETURN_TYPE:
				setReturnType((Type)newValue);
				return;
			case WorkflowPackage.PROCEDURE_DECLARATION__BODY:
				setBody((Block)newValue);
				return;
			case WorkflowPackage.PROCEDURE_DECLARATION__PARAMETER:
				getParameter().clear();
				getParameter().addAll((Collection<? extends ParameterDeclaration>)newValue);
				return;
			case WorkflowPackage.PROCEDURE_DECLARATION__ACCESS_MODIFIER:
				setAccessModifier((AccessModifiers)newValue);
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
			case WorkflowPackage.PROCEDURE_DECLARATION__VARIABLES:
				getVariables().clear();
				return;
			case WorkflowPackage.PROCEDURE_DECLARATION__RETURN_TYPE:
				setReturnType(RETURN_TYPE_EDEFAULT);
				return;
			case WorkflowPackage.PROCEDURE_DECLARATION__BODY:
				setBody((Block)null);
				return;
			case WorkflowPackage.PROCEDURE_DECLARATION__PARAMETER:
				getParameter().clear();
				return;
			case WorkflowPackage.PROCEDURE_DECLARATION__ACCESS_MODIFIER:
				setAccessModifier(ACCESS_MODIFIER_EDEFAULT);
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
			case WorkflowPackage.PROCEDURE_DECLARATION__VARIABLES:
				return variables != null && !variables.isEmpty();
			case WorkflowPackage.PROCEDURE_DECLARATION__RETURN_TYPE:
				return returnType != RETURN_TYPE_EDEFAULT;
			case WorkflowPackage.PROCEDURE_DECLARATION__BODY:
				return body != null;
			case WorkflowPackage.PROCEDURE_DECLARATION__PARAMETER:
				return parameter != null && !parameter.isEmpty();
			case WorkflowPackage.PROCEDURE_DECLARATION__ACCESS_MODIFIER:
				return accessModifier != ACCESS_MODIFIER_EDEFAULT;
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
		result.append(" (returnType: ");
		result.append(returnType);
		result.append(", accessModifier: ");
		result.append(accessModifier);
		result.append(')');
		return result.toString();
	}

} //ProcedureDeclarationImpl
