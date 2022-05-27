package com.example.application.data.service;

import com.example.application.controller.MyRESTController;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Payment;
import com.example.application.data.repository.ContactRepository;
import com.example.application.data.repository.PaymentsRepository;
import com.example.application.exception.CustomException;
import com.example.application.views.list.ListView;
import com.google.common.base.Splitter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class WebhookService {
    private static Logger logger = LogManager.getLogger();
    @Autowired
    private ListView listView;

    @Autowired
    private PaymentsRepository paymentsRepository;
    @Autowired
    private CrmService service;
    @Autowired
    private ContactRepository contactRepository;

    private String course;
    private String firstName;
    private String phone;// поиск по телефону надо решить паттерн+ и сохранение и удаление -() пробел
    private Double amount;
    private String lastName;

    public void webhookMapToPayment(Map<String, String> properties) throws CustomException {

        course = properties.get("course");
        firstName = properties.get("firstName");
        lastName = properties.get("lastName");
        phone = properties.get("phone");
        amount = Double.parseDouble(properties.get("payment[amount]"));
        Payment payment = new Payment(firstName, lastName, phone, course, amount);


        List<?> byPhoneAndLastName = paymentsRepository.findByPhoneAndLastNameAndFirstName(phone, lastName, firstName);
       /* List<Payment> listFromStream = byPhoneAndLastName
                .stream()
                .filter(a -> (a.getPhone().equals(phone)
                        && (a.getLastName().equals(lastName)) && a.getFirstName().equals(firstName)))
                .collect(Collectors.toList());*/

        boolean firstPay = byPhoneAndLastName.isEmpty();

        System.out.println("Is empty (not found from repo)" + firstPay);

        if (firstPay) {// когда первый раз приходит ты не показываешь в PAY колонке ничего когда второй супер спасибо
            try {
                paymentsRepository.save(payment);
                Contact contact = paymentsRepository.searchPayFromContact(phone);
                contact.setPayment(payment);
                contactRepository.save(contact);
                //service.updateCustomerContacts(contact, amount);
                System.out.println("Block IF woriking-its first pay (not founded early pay) "
                        + "First save payment " + payment + " Contact is" + contact);
            } catch (JpaSystemException e) {
                logger.error("Contact not found. Payment not added. ", e.getClass(), e.getMessage());
                throw new CustomException(e);
            }
        } else
            try {
                Contact contact = paymentsRepository.searchPayFromContact(phone);
                service.updateCustomerContacts(contact, amount);
                System.out.println("Block ELSE woriking-its SECOND pay "
                        + " Amount is " + amount + " Contact is " + contact);
                /*Contact newContact = service.updateCustomerContacts(contact, amount);
                 *//*      listView.updateList();
                listView.configureGrid();
                listView.editContact(newContact);*/
            } catch (JpaSystemException e) {
                logger.error("Contact not found. Payment not added. ", e.getClass(), e.getMessage());
                throw new CustomException(e);
            }

        // когда первый раз приходит ты не показываешь ничего когда второй супер спасибо

        // refresh list view or added listener
        // listView.updateList();// не помогло   контакт как будто не ициализируется или не проходит оплата
    }

    public Map<String, String> decode(String requestBody) {
        String decodeValue = decodeValue(requestBody);
        Map<String, String> properties = Splitter.on("&")
                .withKeyValueSeparator("=")
                .split(decodeValue);
        return properties;
    }

    public String decodeValue(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }
}
