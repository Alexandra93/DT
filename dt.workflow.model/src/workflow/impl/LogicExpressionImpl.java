/**
 */
package workflow.impl;

import org.eclipse.emf.ecore.EClass;

import workflow.LogicExpression;
import workflow.WorkflowPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Logic Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class LogicExpressionImpl extends BinaryExpressionImpl implements LogicExpression {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LogicExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WorkflowPackage.Literals.LOGIC_EXPRESSION;
	}

} //LogicExpressionImpl
