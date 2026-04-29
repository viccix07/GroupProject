package pay.movie;

public interface PaymentProcessor {
    Payment processCreditCardPayment(double amount) ;
    Payment processCashPayment(double amount, double cashGiven) ;

}