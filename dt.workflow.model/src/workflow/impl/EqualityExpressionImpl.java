/**
 */
package workflow.impl;

import org.eclipse.emf.ecore.EClass;

import workflow.EqualityExpression;
import workflow.WorkflowPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Equality Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class EqualityExpressionImpl extends BinaryExpressionImpl implements EqualityExpression {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EqualityExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WorkflowPackage.Literals.EQUALITY_EXPRESSION;
	}

} //EqualityExpressionImpl
