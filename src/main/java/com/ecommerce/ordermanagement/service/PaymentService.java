package com.ecommerce.ordermanagement.service;

import com.ecommerce.ordermanagement.exception.PaymentFailedException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class PaymentService {
    public String createPaymentIntent(BigDecimal amount) {
        try {
            PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                .setAmount(amount.multiply(new BigDecimal(100)).longValue())
                .setCurrency("usd")
                .build();

            PaymentIntent intent = PaymentIntent.create(createParams);
            return intent.getId();
        } catch (StripeException e) {
            throw new PaymentFailedException("Failed to create payment intent: " + e.getMessage());
        }
    }
}