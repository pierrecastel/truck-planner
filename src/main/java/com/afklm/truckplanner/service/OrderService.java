package com.afklm.truckplanner.service;

import com.afklm.truckplanner.domain.Order;
import com.afklm.truckplanner.domain.TruckCompany;
import org.springframework.stereotype.Service;

/**
 * The type Order service.
 */
@Service
public class OrderService {

    private static final String ORDER_MAIL_MESSAGE = "Hello,\nYou recieved a new odrer from Air France KLM.\n" +
        "Please confirm this order at http://localhost:4200/truckCompanyOrder/";
    private static final String CARGO_MAIL_OBJECT = "Cargo order number ";
    private static final String ORDER_MAIL_MESSAGE_2 = " within 1 hour, or we probably will have to cancel this order.\nRegards.";
    private final MailService mailService;

    /**
     * Instantiates a new Order service.
     *
     * @param mailService the mail service
     */
    public OrderService(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * Notify truck company.
     *
     * @param order the order
     */
    public void notifyTruckCompany(Order order) {
        final TruckCompany truckCompany = order.getTruckCompany();
        if (truckCompany == null) {
            return;
        }
        switch (truckCompany.getComunicationMode()) {
            case API:
                break;
            case MAIL:
                sendOrderEmail(order);
                break;
        }

    }

    private void sendOrderEmail(Order order) {
        mailService.sendEmail(order.getTruckCompany().getEmail(),
            CARGO_MAIL_OBJECT + order.getNumber(),
            ORDER_MAIL_MESSAGE + order.getId() + ORDER_MAIL_MESSAGE_2,
            false,
            false);
    }

//    public void notifyTruckCompany(Order order) {
//        Locale locale = Locale.forLanguageTag(order.getTruckCompany().getLangKey());
//        mailService.sendEmail(order.getTruckCompany().getEmail(),
//            messageSource.getMessage(titleKey, {order.getId()}, locale) + order.getNumber(),
//            ORDER_MAIL_MESSAGE + order.getId(),
//            false,
//            false);
//    }
}
