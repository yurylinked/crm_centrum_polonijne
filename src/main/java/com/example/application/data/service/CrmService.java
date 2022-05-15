package com.example.application.data.service;

import com.example.application.data.entity.Payment;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Group;

import com.example.application.data.repository.ContactRepository;
import com.example.application.data.repository.GroupsRepository;
import com.example.application.data.repository.PaymentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {

    private final ContactRepository contactRepository;
    private final PaymentsRepository paymentsRepository;
    private final GroupsRepository groupsRepository;

    public CrmService(ContactRepository contactRepository,
                      PaymentsRepository paymentsRepository,
                      GroupsRepository groupsRepository) {
        this.contactRepository = contactRepository;
        this.paymentsRepository = paymentsRepository;
        this.groupsRepository = groupsRepository;
    }

    public List<Contact> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    public long countContacts() {
        return contactRepository.count();
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

    public List<Payment> findAllPayments() {
        return paymentsRepository.findAll();
    }

    public List<Group> findAllGroups(){
        return groupsRepository.findAll();
    }
}
