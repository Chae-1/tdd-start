package com.example.chapter3;

import java.time.LocalDate;

public class PayData {
    private LocalDate firstBillingDate;
    private LocalDate billingDate;
    private int payAmount;


    private PayData() {}

    public PayData(LocalDate firstBillingDate, LocalDate billingAmount, int payAmount) {
        this.firstBillingDate = firstBillingDate;
        this.billingDate = billingAmount;
        this.payAmount = payAmount;
    }

    public LocalDate getBillingDate() {
        return billingDate;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public LocalDate getFirstBillingDate() {
        return firstBillingDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private LocalDate firstBillingDate;
        private LocalDate billingDate;
        private int payAmount;

        private Builder() {}

        public Builder payAmount(int payAmount) {
            this.payAmount = payAmount;
            return this;
        }

        public Builder billingDate(LocalDate payDate) {
            this.billingDate = payDate;
            return this;
        }

        public Builder firstBillingDate(LocalDate firstBillingDate) {
            this.firstBillingDate = firstBillingDate;
            return this;
        }

        public PayData build() {
            return new PayData(firstBillingDate, billingDate, payAmount);
        }
    }
}
