package cu.lacumbre.auditor.exceptions;

public class EmptyWorkersException extends Exception {

        public EmptyWorkersException(String message) {
            super(message);
        }

        public EmptyWorkersException(String message, Throwable cause) {
            super(message, cause);
        }

    }