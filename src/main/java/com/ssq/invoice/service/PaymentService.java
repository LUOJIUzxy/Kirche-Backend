package com.ssq.invoice.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.model.Price;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public String createPayment() throws StripeException {
        Stripe.apiKey = "sk_test_51O82iyAENojRZzJndZtOUCIsGWa8k8vY0c9Gjtc0KeIN41PVLZjBjQSX7sByxZkOdqWmv0ordtdHK94bIaW8U2Ki00WkjHdEFS";
        //Stripe.apiKey = "rk_test_51O82iyAENojRZzJnXTt8sGh66UC21lVVXGwHPLA5hyygmFxt86xm4nkwu4KBHTKTSl2Ixa8hhyYIgMMxi1GX1zlV00nKeWHBOF";

        ProductCreateParams productParams =
                ProductCreateParams.builder()
                        .setName("Donate To the Church")
                        .setDescription("Any Kindness is Welcome, Thank You!")
                        .build();

        Product product = Product.create(productParams);
        System.out.println("Success! Here is your Donate To the Church product id: " + product.getId());

        PriceCreateParams priceParams =
                PriceCreateParams.builder()
                        .setCurrency("eur")
                        .setCustomUnitAmount(
                                PriceCreateParams.CustomUnitAmount.builder().setEnabled(true).build()
                        )
                        .setProduct(product.getId())
                        .build();

        Price price = Price.create(priceParams);

        PaymentLinkCreateParams paymentLinkParams =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setAfterCompletion(
                                PaymentLinkCreateParams.AfterCompletion.builder()
                                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                        .setRedirect(
                                                PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                        .setUrl("http://localhost:3000")
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();

        PaymentLink paymentLink = PaymentLink.create(paymentLinkParams);
        System.out.println("Success! Here is your starter subscription price id: " + paymentLink.getUrl());

        return paymentLink.getUrl();
    }
//    public static void main(String[] args) throws StripeException {
//        //'Stripe CLI test_api_key': rk_test_51O82iyAENojRZzJnXTt8sGh66UC21lVVXGwHPLA5hyygmFxt86xm4nkwu4KBHTKTSl2Ixa8hhyYIgMMxi1GX1zlV00nKeWHBOF
//        //private key: sk_test_51O82iyAENojRZzJndZtOUCIsGWa8k8vY0c9Gjtc0KeIN41PVLZjBjQSX7sByxZkOdqWmv0ordtdHK94bIaW8U2Ki00WkjHdEFS
//        Stripe.apiKey = "sk_test_51O82iyAENojRZzJndZtOUCIsGWa8k8vY0c9Gjtc0KeIN41PVLZjBjQSX7sByxZkOdqWmv0ordtdHK94bIaW8U2Ki00WkjHdEFS";
//        //Stripe.apiKey = "rk_test_51O82iyAENojRZzJnXTt8sGh66UC21lVVXGwHPLA5hyygmFxt86xm4nkwu4KBHTKTSl2Ixa8hhyYIgMMxi1GX1zlV00nKeWHBOF";
//
//        ProductCreateParams productParams =
//                ProductCreateParams.builder()
//                        .setName("Donate To the Church")
//                        .setDescription("Any Kindness is Welcome, Thank You!")
//                        .build();
//
//        Product product = Product.create(productParams);
//        System.out.println("Success! Here is your Donate To the Church product id: " + product.getId());
//
//        PriceCreateParams priceParams =
//                PriceCreateParams.builder()
//                        .setCurrency("eur")
//                        .setCustomUnitAmount(
//                                PriceCreateParams.CustomUnitAmount.builder().setEnabled(true).build()
//                        )
//                        .setProduct(product.getId())
//                        .build();
//
//        Price price = Price.create(priceParams);
//
//        PaymentLinkCreateParams paymentLinkParams =
//                PaymentLinkCreateParams.builder()
//                        .addLineItem(
//                                PaymentLinkCreateParams.LineItem.builder()
//                                        .setPrice(price.getId())
//                                        .setQuantity(1L)
//                                        .build()
//                        )
//                        .setAfterCompletion(
//                                PaymentLinkCreateParams.AfterCompletion.builder()
//                                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
//                                        .setRedirect(
//                                                PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
//                                                        .setUrl("http://localhost:3000")
//                                                        .build()
//                                        )
//                                        .build()
//                        )
//                        .build();
//
//        PaymentLink paymentLink = PaymentLink.create(paymentLinkParams);
//        System.out.println("Success! Here is your starter subscription price id: " + paymentLink.getUrl());
//
////        ProductCreateParams productParams =
////                ProductCreateParams.builder()
////                        .setName("Donate To the Church")
////                        .setDescription("Any Kindness is Welcome, Thank You!")
////                        .build();
////        Product product = Product.create(productParams);
////        System.out.println("Success! Here is your Donate To the Church product id: " + product.getId());
//
////        PriceCreateParams params =
////                PriceCreateParams
////                        .builder()
////                        .setProduct(product.getId())
////                        .setCurrency("usd")
////                        .setUnitAmount(1200L)
////                        .setRecurring(
////                                PriceCreateParams.Recurring
////                                        .builder()
////                                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
////                                        .build())
////                        .build();
////        Price price = Price.create(params);
////        System.out.println("Success! Here is your starter subscription price id: " + price.getId());
//    }
}

