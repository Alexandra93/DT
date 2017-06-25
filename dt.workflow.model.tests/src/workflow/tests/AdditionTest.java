/**
 */
package workflow.tests;

import junit.textui.TestRunner;

import workflow.Addition;
import workflow.WorkflowFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Addition</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class AdditionTest extends ArithmeticExpressionTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(AdditionTest.class);
	}

	/**
	 * Constructs a new Addition test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdditionTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Addition test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Addition getFixture() {
		return (Addition)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(WorkflowFactory.eINSTANCE.createAddition());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //AdditionTest
