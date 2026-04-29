package pay.movie;

import java.util.Date;
import java.util.UUID;

public class Payment {
    private double amount;
    private double OriginalAmount;
    private double discountApplied;
    private Date createdOn;
    private PaymentStatus paymentStatus;
    private String transactionID;
    private PaymentMethodType paymentMethodType;

    public Payment (double amount, PaymentMethodType paymentMethodType) {
        this.amount = amount;
        this.OriginalAmount = amount;
        this.transactionID= UUID.randomUUID().toString();
        this.createdOn= new Date();
        paymentStatus=PaymentStatus.PENDING;
    }

    public void markPay() {
        this.paymentStatus =PaymentStatus.SUCCESS;
    }

    public void markFail() {
        this.paymentStatus = PaymentStatus.FAILED;
    }

    public boolean isSuccessful() {
        return this.paymentStatus == PaymentStatus.SUCCESS;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    public double getAmount() {
        return amount;
    }

}
