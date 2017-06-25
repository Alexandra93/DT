/**
 */
package workflow;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link workflow.Constant#getAsBoolean <em>As Boolean</em>}</li>
 *   <li>{@link workflow.Constant#getAsInteger <em>As Integer</em>}</li>
 *   <li>{@link workflow.Constant#getAsReal <em>As Real</em>}</li>
 *   <li>{@link workflow.Constant#getAsString <em>As String</em>}</li>
 * </ul>
 *
 * @see workflow.WorkflowPackage#getConstant()
 * @model
 * @generated
 */
public interface Constant extends Expression {
	/**
	 * Returns the value of the '<em><b>As Boolean</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>As Boolean</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>As Boolean</em>' attribute.
	 * @see #setAsBoolean(Boolean)
	 * @see workflow.WorkflowPackage#getConstant_AsBoolean()
	 * @model
	 * @generated
	 */
	Boolean getAsBoolean();

	/**
	 * Sets the value of the '{@link workflow.Constant#getAsBoolean <em>As Boolean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>As Boolean</em>' attribute.
	 * @see #getAsBoolean()
	 * @generated
	 */
	void setAsBoolean(Boolean value);

	/**
	 * Returns the value of the '<em><b>As Integer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>As Integer</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>As Integer</em>' attribute.
	 * @see #setAsInteger(String)
	 * @see workflow.WorkflowPackage#getConstant_AsInteger()
	 * @model
	 * @generated
	 */
	String getAsInteger();

	/**
	 * Sets the value of the '{@link workflow.Constant#getAsInteger <em>As Integer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>As Integer</em>' attribute.
	 * @see #getAsInteger()
	 * @generated
	 */
	void setAsInteger(String value);

	/**
	 * Returns the value of the '<em><b>As Real</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>As Real</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>As Real</em>' attribute.
	 * @see #setAsReal(String)
	 * @see workflow.WorkflowPackage#getConstant_AsReal()
	 * @model
	 * @generated
	 */
	String getAsReal();

	/**
	 * Sets the value of the '{@link workflow.Constant#getAsReal <em>As Real</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>As Real</em>' attribute.
	 * @see #getAsReal()
	 * @generated
	 */
	void setAsReal(String value);

	/**
	 * Returns the value of the '<em><b>As String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>As String</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>As String</em>' attribute.
	 * @see #setAsString(String)
	 * @see workflow.WorkflowPackage#getConstant_AsString()
	 * @model
	 * @generated
	 */
	String getAsString();

	/**
	 * Sets the value of the '{@link workflow.Constant#getAsString <em>As String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>As String</em>' attribute.
	 * @see #getAsString()
	 * @generated
	 */
	void setAsString(String value);

} // Constant
