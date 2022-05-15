package com.example.application.views.list;

import com.example.application.data.entity.Payment;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Group;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ContactFormTest {
    private List<Payment> companies;
    private List<Group> groups;
    private Contact marcUsher;
    private Payment payment1;
    private Payment payment2;
    private Group group1;
    private Group group2;

    @Before
    public void setupData() {
        companies = new ArrayList<>();
        payment1 = new Payment();
        payment1.setName("Vaadin Ltd");
        payment2 = new Payment();
        payment2.setName("IT Mill");
        companies.add(payment1);
        companies.add(payment2);

        groups = new ArrayList<>();
        group1 = new Group();
        group1.setName("Status 1");
        group2 = new Group();
        group2.setName("Status 2");
        groups.add(group1);
        groups.add(group2);

        marcUsher = new Contact();
        marcUsher.setFirstName("Marc");
        marcUsher.setLastName("Usher");

        marcUsher.setGroup(group1);
        marcUsher.setPaymentFromData(payment2);
    }

    @Test
    public void formFieldsPopulated() {
        ContactForm form = new ContactForm(companies, groups);
        form.setContact(marcUsher);
        Assert.assertEquals("Marc", form.firstName.getValue());
        Assert.assertEquals("Usher", form.lastName.getValue());
        Assert.assertEquals(payment2, form.payment.getValue());
        Assert.assertEquals(group1, form.group.getValue());
    }

    @Test
    public void saveEventHasCorrectValues() {
        ContactForm form = new ContactForm(companies, groups);
        Contact contact = new Contact();
        form.setContact(contact);
        form.firstName.setValue("John");
        form.lastName.setValue("Doe");
        form.payment.setValue(payment1);
        form.group.setValue(group2);

        AtomicReference<Contact> savedContactRef = new AtomicReference<>(null);
        form.addListener(ContactForm.SaveEvent.class, e -> {
            savedContactRef.set(e.getContact());
        });
        form.save.click();
        Contact savedContact = savedContactRef.get();

        Assert.assertEquals("John", savedContact.getFirstName());
        Assert.assertEquals("Doe", savedContact.getLastName());
        Assert.assertEquals(payment1, savedContact.getPayment());
        Assert.assertEquals(group2, savedContact.getGroup());
    }
}