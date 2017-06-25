/**
 */
package workflow;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Read</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link workflow.Read#getRVar <em>RVar</em>}</li>
 *   <li>{@link workflow.Read#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see workflow.WorkflowPackage#getRead()
 * @model
 * @generated
 */
public interface Read extends Statement {
	/**
	 * Returns the value of the '<em><b>RVar</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>RVar</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>RVar</em>' containment reference.
	 * @see #setRVar(Variable)
	 * @see workflow.WorkflowPackage#getRead_RVar()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Variable getRVar();

	/**
	 * Sets the value of the '{@link workflow.Read#getRVar <em>RVar</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RVar</em>' containment reference.
	 * @see #getRVar()
	 * @generated
	 */
	void setRVar(Variable value);

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
	 * @see workflow.WorkflowPackage#getRead_Type()
	 * @model
	 * @generated
	 */
	Type getType();

	/**
	 * Sets the value of the '{@link workflow.Read#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see workflow.Type
	 * @see #getType()
	 * @generated
	 */
	void setType(Type value);

} // Read
