package com.example.application.data.service;

import com.example.application.data.entity.Course;
import com.example.application.data.entity.Payment;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.GroupOfStudents;

import com.example.application.data.repository.ContactRepository;
import com.example.application.data.repository.CourseRepository;
import com.example.application.data.repository.GroupsRepository;
import com.example.application.data.repository.PaymentsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {
    private static Logger logger = LogManager.getLogger();

    private final ContactRepository contactRepository;
    private final PaymentsRepository paymentsRepository;
    private final GroupsRepository groupsRepository;
    private final CourseRepository courseRepository;


    public CrmService(ContactRepository contactRepository,
                      PaymentsRepository paymentsRepository,
                      GroupsRepository groupsRepository, CourseRepository courseRepository) {
        this.contactRepository = contactRepository;
        this.paymentsRepository = paymentsRepository;
        this.groupsRepository = groupsRepository;
        this.courseRepository = courseRepository;
    }

    public List<Contact> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    public List<GroupOfStudents> findAllGroups(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return groupsRepository.findAll();
        } else {
            return groupsRepository.search(stringFilter);
        }
    }

    public List<Payment> findAllPayments(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return paymentsRepository.findAll();
        } else {
            return paymentsRepository.search(stringFilter);
        }
    }


    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }

    public void saveContact(Contact contact) {
        if (contact == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        contactRepository.save(contact);
    }

    public void deleteGroup(GroupOfStudents groupOfStudents) {
        groupsRepository.delete(groupOfStudents);
    }

    public void saveGroup(GroupOfStudents groupOfStudents) {
        if (groupOfStudents == null) {
            System.err.println("Group is null. Are you sure you have connected your form to the application?");
            return;
        }
        groupsRepository.save(groupOfStudents);
    }

    public List<Payment> findAllPayments() {
        return paymentsRepository.findAll();
    }

    public List<GroupOfStudents> findAllGroups() {
        return groupsRepository.findAll();
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public List<Contact> findAllContacts() {
        return contactRepository.findAll();
    }

    public Contact updateCustomerContacts(Contact contact, Double amount) {
        if (amount == null) {
            contact.getPayment().setAmount(amount);
            contactRepository.save(contact);
        } else {
            Double amount1 = contact.getPayment().getAmount();
            contact.getPayment().setAmount(amount + amount1);
            contactRepository.save(contact);
        }

        return contact;
    }

}
