/**
 */
package workflow.tests;

import junit.textui.TestRunner;

import workflow.WorkflowFactory;
import workflow.Write;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Write</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class WriteTest extends StatementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(WriteTest.class);
	}

	/**
	 * Constructs a new Write test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WriteTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Write test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Write getFixture() {
		return (Write)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(WorkflowFactory.eINSTANCE.createWrite());
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

} //WriteTest
