package pay.movie;

public class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}

