/**
 */
package workflow;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link workflow.ParameterDeclaration#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see workflow.WorkflowPackage#getParameterDeclaration()
 * @model
 * @generated
 */
public interface ParameterDeclaration extends Declaration {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link workflow.Type}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see workflow.Type
	 * @see #setType(Type)
	 * @see workflow.WorkflowPackage#getParameterDeclaration_Type()
	 * @model
	 * @generated
	 */
	Type getType();

	/**
	 * Sets the value of the '{@link workflow.ParameterDeclaration#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see workflow.Type
	 * @see #getType()
	 * @generated
	 */
	void setType(Type value);

} // ParameterDeclaration
