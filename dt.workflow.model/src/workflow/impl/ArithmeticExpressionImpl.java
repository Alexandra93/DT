/**
 */
package workflow.impl;

import org.eclipse.emf.ecore.EClass;

import workflow.ArithmeticExpression;
import workflow.WorkflowPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Arithmetic Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class ArithmeticExpressionImpl extends BinaryExpressionImpl implements ArithmeticExpression {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArithmeticExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WorkflowPackage.Literals.ARITHMETIC_EXPRESSION;
	}

} //ArithmeticExpressionImpl
