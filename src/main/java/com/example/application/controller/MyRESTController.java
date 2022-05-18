package com.example.application.controller;

import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Payment;
import com.example.application.data.repository.PaymentsRepository;
import com.example.application.data.service.CrmService;


import com.google.common.base.Splitter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Long.decode;
import static java.util.stream.Collectors.joining;


@AnonymousAllowed
@CrossOrigin
@RestController
@Route(value = "/api")
@RequestMapping("/api")
public class MyRESTController {
    @Autowired
    private CrmService crmService;

    PaymentsRepository paymentsRepository;


    String course;
    String firstName;
    String phone;
    String amount;
    String lastName;

    public static String decodeValue(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    @CrossOrigin
    @PostMapping("/webhook")
    @RequestMapping("/webhook")
    public ResponseEntity<String> print(@RequestBody String requestBody) {
        String decodeValue = decodeValue(requestBody);
        Map<String, String> properties = Splitter.on("&")
                .withKeyValueSeparator("=")
                .split(decodeValue);

        course = properties.get("course");
        firstName = properties.get("firstName");
        lastName = properties.get("lastName");
        phone = properties.get("phone");
        amount = properties.get("amount");

        paymentsRepository.save(new Payment(firstName, lastName,phone,course,amount));

        return new ResponseEntity<>("Wszystko ok", HttpStatus.OK);

    }

    /*@Bean
    private Payment payment() {
        paymentsRepository.save(new Payment(firstName, lastName,phone,course,amount));
        return null;
    }*/
}
   /* public Map<String, String> convertWithGuava(String mapAsString) {
        return Splitter.on('&').withKeyValueSeparator('=').split(mapAsString);
    }

    public Map<String, String> convertWithStream(String mapAsString) {
        Map<String, String> map = Arrays.stream(mapAsString.split("&"))
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
        return map;
    }*/

