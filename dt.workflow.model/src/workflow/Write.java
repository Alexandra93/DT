/**
 */
package workflow;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Write</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link workflow.Write#getWVar <em>WVar</em>}</li>
 * </ul>
 *
 * @see workflow.WorkflowPackage#getWrite()
 * @model
 * @generated
 */
public interface Write extends Statement {
	/**
	 * Returns the value of the '<em><b>WVar</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>WVar</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>WVar</em>' containment reference.
	 * @see #setWVar(Variable)
	 * @see workflow.WorkflowPackage#getWrite_WVar()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Variable getWVar();

	/**
	 * Sets the value of the '{@link workflow.Write#getWVar <em>WVar</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>WVar</em>' containment reference.
	 * @see #getWVar()
	 * @generated
	 */
	void setWVar(Variable value);

} // Write
