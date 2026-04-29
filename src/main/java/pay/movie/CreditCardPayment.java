package pay.movie;

public class CreditCardPayment implements PaymentProcessor {
    private String creditCard;
    private int cvv;
    private String date;

    public CreditCardPayment(String creditCard, int cvv, String date) {
        this.creditCard=creditCard;
        this.cvv=cvv;
        this.date=date;
    }








































































































    @Override
    public Payment processCreditCardPayment(double amount) {
        Payment payment = new Payment(amount, PaymentMethodType.CREDIT_CARD);
        payment.markPay();
        return payment;
    }

    @Override
    public Payment processCashPayment(double amount, double cashGiven) {
        throw new PaymentException("Please Insert All Requirements for Credit Card Payment");
    }
}
