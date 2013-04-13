package uclouvain.ingi2325.exception;

/**
 * Represents a parsing error
 * 
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 */
public class ParseError extends Error {
	public ParseError(String message) {
		super(message);
	}

	public ParseError(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseError(Throwable cause) {
		super("Error during parsing", cause);
	}
}
