/**
 */
package workflow;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Assignment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link workflow.VariableAssignment#getLValue <em>LValue</em>}</li>
 *   <li>{@link workflow.VariableAssignment#getRValue <em>RValue</em>}</li>
 * </ul>
 *
 * @see workflow.WorkflowPackage#getVariableAssignment()
 * @model
 * @generated
 */
public interface VariableAssignment extends Statement {
	/**
	 * Returns the value of the '<em><b>LValue</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>LValue</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>LValue</em>' containment reference.
	 * @see #setLValue(Variable)
	 * @see workflow.WorkflowPackage#getVariableAssignment_LValue()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Variable getLValue();

	/**
	 * Sets the value of the '{@link workflow.VariableAssignment#getLValue <em>LValue</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>LValue</em>' containment reference.
	 * @see #getLValue()
	 * @generated
	 */
	void setLValue(Variable value);

	/**
	 * Returns the value of the '<em><b>RValue</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>RValue</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>RValue</em>' containment reference.
	 * @see #setRValue(Expression)
	 * @see workflow.WorkflowPackage#getVariableAssignment_RValue()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getRValue();

	/**
	 * Sets the value of the '{@link workflow.VariableAssignment#getRValue <em>RValue</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RValue</em>' containment reference.
	 * @see #getRValue()
	 * @generated
	 */
	void setRValue(Expression value);

} // VariableAssignment
