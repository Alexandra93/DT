package dt.egf.utils.workflow;

import org.eclipse.egf.core.producer.InvocationException;

public class MweException extends InvocationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4671844267657272530L;

	/**
	 * @param message
	 * @param cause
	 */
	public MweException(String message, Throwable cause) {
		super(message, cause);
	}
	public MweException(String message) {
		super(message);
	}
}
