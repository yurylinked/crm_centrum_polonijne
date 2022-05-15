package com.example.application.controller;

import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.google.gson.Gson;
import com.google.common.base.Splitter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
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
    private static final Gson gson = new Gson();

    @CrossOrigin
    @PostMapping("/webhook")
    @RequestMapping("/webhook")
    public ResponseEntity<String> print(@RequestBody String requestBody) {
        String decodeValue = decodeValue(requestBody);
        System.out.println(decodeValue);

        return new ResponseEntity<>((requestBody), HttpStatus.OK);
    }
/*    public Map<String, String> convertWithGuava(String mapAsString) {
        return Splitter.on('&').withKeyValueSeparator('=').split(mapAsString);
    }
    public Map<String, String> convertWithStream(String mapAsString) {
        Map<String, String> map = Arrays.stream(mapAsString.split("&"))
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
        return map;
    }*/

@Controller
public class AccessDeniedController {

    @GetMapping("/access-denied")
    public String getAccessDenied() {
        return "/error/accessDenied";
    }

}

    public static String decodeValue(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }
}