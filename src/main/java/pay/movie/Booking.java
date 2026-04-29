package pay.movie;

public class Booking {
    private String bookingId;
    private Payment payment;

    public Booking(String bookingId) {
        this.bookingId = bookingId;
    }

    public void makePayment(PaymentProcessor paymentProcessor, double amount) {
        this.payment = paymentProcessor.processCreditCardPayment(amount);

        if (!payment.isSuccessful()) {
            throw new RuntimeException("Payment failed. Booking cancelled.");
        }

        System.out.println("Booking confirmed!");
    }

    public void makePayment(PaymentProcessor paymentProcessor, double amount, double cashGiven) {
        this.payment = paymentProcessor.processCashPayment(amount, cashGiven);
        if (!payment.isSuccessful()) {
            throw new RuntimeException("Payment failed. Booking cancelled.");
        }

        System.out.println("Booking confirmed!");
    }
}