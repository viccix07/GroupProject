package pay.movie;

public class CashPayment implements PaymentProcessor {

    @Override
    public Payment processCreditCardPayment(double amount) {
        throw new PaymentException("Please Enter Insert All Requirements for Cash Payment");
    }

    @Override
    public Payment processCashPayment(double amount, double cashGiven) {
        Payment payment = new Payment(amount, PaymentMethodType.CASH);
        if (cashGiven < amount) {
            payment.markFail();
            throw new RuntimeException("Not enough cash");
        }

        double change = cashGiven - amount;
        System.out.println("Change: " + change);
        payment.markPay();
        return payment;
    }
}