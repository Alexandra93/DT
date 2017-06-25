/**
 */
package workflow;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Procedure Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link workflow.ProcedureDeclaration#getVariables <em>Variables</em>}</li>
 *   <li>{@link workflow.ProcedureDeclaration#getReturnType <em>Return Type</em>}</li>
 *   <li>{@link workflow.ProcedureDeclaration#getBody <em>Body</em>}</li>
 *   <li>{@link workflow.ProcedureDeclaration#getParameter <em>Parameter</em>}</li>
 *   <li>{@link workflow.ProcedureDeclaration#getAccessModifier <em>Access Modifier</em>}</li>
 * </ul>
 *
 * @see workflow.WorkflowPackage#getProcedureDeclaration()
 * @model
 * @generated
 */
public interface ProcedureDeclaration extends Declaration {
	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
	 * The list contents are of type {@link workflow.VariableDeclaration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see workflow.WorkflowPackage#getProcedureDeclaration_Variables()
	 * @model containment="true"
	 * @generated
	 */
	EList<VariableDeclaration> getVariables();

	/**
	 * Returns the value of the '<em><b>Return Type</b></em>' attribute.
	 * The literals are from the enumeration {@link workflow.Type}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Return Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Return Type</em>' attribute.
	 * @see workflow.Type
	 * @see #setReturnType(Type)
	 * @see workflow.WorkflowPackage#getProcedureDeclaration_ReturnType()
	 * @model required="true"
	 * @generated
	 */
	Type getReturnType();

	/**
	 * Sets the value of the '{@link workflow.ProcedureDeclaration#getReturnType <em>Return Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Return Type</em>' attribute.
	 * @see workflow.Type
	 * @see #getReturnType()
	 * @generated
	 */
	void setReturnType(Type value);

	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(Block)
	 * @see workflow.WorkflowPackage#getProcedureDeclaration_Body()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Block getBody();

	/**
	 * Sets the value of the '{@link workflow.ProcedureDeclaration#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Block value);

	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' containment reference list.
	 * The list contents are of type {@link workflow.ParameterDeclaration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter</em>' containment reference list.
	 * @see workflow.WorkflowPackage#getProcedureDeclaration_Parameter()
	 * @model containment="true"
	 * @generated
	 */
	EList<ParameterDeclaration> getParameter();

	/**
	 * Returns the value of the '<em><b>Access Modifier</b></em>' attribute.
	 * The literals are from the enumeration {@link workflow.AccessModifiers}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access Modifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access Modifier</em>' attribute.
	 * @see workflow.AccessModifiers
	 * @see #setAccessModifier(AccessModifiers)
	 * @see workflow.WorkflowPackage#getProcedureDeclaration_AccessModifier()
	 * @model
	 * @generated
	 */
	AccessModifiers getAccessModifier();

	/**
	 * Sets the value of the '{@link workflow.ProcedureDeclaration#getAccessModifier <em>Access Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access Modifier</em>' attribute.
	 * @see workflow.AccessModifiers
	 * @see #getAccessModifier()
	 * @generated
	 */
	void setAccessModifier(AccessModifiers value);

} // ProcedureDeclaration
