package com.example.application.views.list;

import com.example.application.data.entity.Payment;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.GroupOfStudents;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ContactFormTest {
    private List<Payment> companies;
    private List<GroupOfStudents> groupOfStudents;
    private Contact marcUsher;
    private Payment payment1;
    private Payment payment2;
    private GroupOfStudents groupOfStudents1;
    private GroupOfStudents groupOfStudents2;

    @Before
    public void setupData() {
        companies = new ArrayList<>();
        payment1 = new Payment();

        companies.add(payment1);
        companies.add(payment2);

        groupOfStudents = new ArrayList<>();
        groupOfStudents1 = new GroupOfStudents();
        groupOfStudents1.setName("Status 1");
        groupOfStudents2 = new GroupOfStudents();
        groupOfStudents2.setName("Status 2");
        groupOfStudents.add(groupOfStudents1);
        groupOfStudents.add(groupOfStudents2);

        marcUsher = new Contact();
        marcUsher.setFirstName("Marc");
        marcUsher.setLastName("Usher");

        marcUsher.setGroup(groupOfStudents1);
        marcUsher.setPayment(payment2);
    }

    @Test
    public void formFieldsPopulated() {
        ContactForm form = new ContactForm(companies, groupOfStudents);
        form.setContact(marcUsher);
        Assert.assertEquals("Marc", form.firstName.getValue());
        Assert.assertEquals("Usher", form.lastName.getValue());
        //Assert.assertEquals(payment2, form.payment.getValue());
        Assert.assertEquals(groupOfStudents1, form.group.getValue());
    }

    @Test
    public void saveEventHasCorrectValues() {
        ContactForm form = new ContactForm(companies, groupOfStudents);
        Contact contact = new Contact();
        form.setContact(contact);
        form.firstName.setValue("John");
        form.lastName.setValue("Doe");
        //form.payment.setValue(payment1);
        form.group.setValue(groupOfStudents2);

        AtomicReference<Contact> savedContactRef = new AtomicReference<>(null);
        form.addListener(ContactForm.SaveEvent.class, e -> {
            savedContactRef.set(e.getContact());
        });
        form.save.click();
        Contact savedContact = savedContactRef.get();

        Assert.assertEquals("John", savedContact.getFirstName());
        Assert.assertEquals("Doe", savedContact.getLastName());
        Assert.assertEquals(payment1, savedContact.getPaymentFromAdmin());
        Assert.assertEquals(groupOfStudents2, savedContact.getGroup());
    }
}