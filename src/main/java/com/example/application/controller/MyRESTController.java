package com.example.application.controller;

import com.example.application.data.entity.Contact;
import com.example.application.data.entity.GroupOfStudents;
import com.example.application.data.entity.Payment;
import com.example.application.data.repository.PaymentsRepository;
import com.example.application.data.service.CrmService;


import com.example.application.data.service.WebhookService;
import com.example.application.exception.CustomException;
import com.google.common.base.Splitter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static Logger logger = LogManager.getLogger();
    @Autowired
    private WebhookService webhookService;

    @CrossOrigin
    @PostMapping("/webhook")
    @RequestMapping("/webhook")
    public ResponseEntity<String> print(@RequestBody String requestBody) {
        Map<String, String> decode = webhookService.decode(requestBody);
        try {
            webhookService.webhookMapToPayment(decode);
        } catch (NullPointerException | CustomException exception){
            logger.error(" Webhook to Map operation failed.");
        }
        return new ResponseEntity<>("Wszystko ok", HttpStatus.OK);

    }
}
