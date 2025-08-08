package cu.lacumbre.auditor.exceptions;

 public class DocumentAlreadyCompletedException extends Exception {

        public DocumentAlreadyCompletedException(String message) {
            super(message);
        }

        public DocumentAlreadyCompletedException(String message, Throwable cause) {
            super(message, cause);
        }

    }
