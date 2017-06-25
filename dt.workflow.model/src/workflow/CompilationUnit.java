/**
 */
package workflow;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compilation Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link workflow.CompilationUnit#getDeclarations <em>Declarations</em>}</li>
 *   <li>{@link workflow.CompilationUnit#getName <em>Name</em>}</li>
 *   <li>{@link workflow.CompilationUnit#getLanguage <em>Language</em>}</li>
 * </ul>
 *
 * @see workflow.WorkflowPackage#getCompilationUnit()
 * @model
 * @generated
 */
public interface CompilationUnit extends EObject {
	/**
	 * Returns the value of the '<em><b>Declarations</b></em>' containment reference list.
	 * The list contents are of type {@link workflow.ProcedureDeclaration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declarations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declarations</em>' containment reference list.
	 * @see workflow.WorkflowPackage#getCompilationUnit_Declarations()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProcedureDeclaration> getDeclarations();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see workflow.WorkflowPackage#getCompilationUnit_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link workflow.CompilationUnit#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Language</b></em>' attribute.
	 * The literals are from the enumeration {@link workflow.Languages}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Language</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Language</em>' attribute.
	 * @see workflow.Languages
	 * @see #setLanguage(Languages)
	 * @see workflow.WorkflowPackage#getCompilationUnit_Language()
	 * @model
	 * @generated
	 */
	Languages getLanguage();

	/**
	 * Sets the value of the '{@link workflow.CompilationUnit#getLanguage <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Language</em>' attribute.
	 * @see workflow.Languages
	 * @see #getLanguage()
	 * @generated
	 */
	void setLanguage(Languages value);

} // CompilationUnit
