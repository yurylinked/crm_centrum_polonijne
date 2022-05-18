package com.example.application.views.list;

import com.example.application.data.entity.Payment;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.GroupOfStudents;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ContactForm extends FormLayout {
    private Contact contact;

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField phone = new TextField("Phone");
    TextField coursePrice = new TextField("Course price");
   // TextField paymentFromData = new TextField("Payment From Data");
    ComboBox<GroupOfStudents> group = new ComboBox<>("Group");
   // ComboBox<Payment> payment = new ComboBox<>("Payment");
    Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    // DatePicker datePicker = new DatePicker("Start date");

    public ContactForm(List<Payment> payments, List<GroupOfStudents> groupOfStudents) {
        addClassName("contact-form");
       /* binder.forField(paymentFromData)
                .bind(Contact::getPaymentFromData, null);*/
        binder.forField(firstName)
                .bind(Contact::getFirstName, Contact::setFirstName);
        binder.forField(lastName)
                .bind(Contact::getLastName, Contact::setLastName);
        binder.forField(coursePrice)
                .bind(Contact::getCoursePrice, Contact::setCoursePrice);
        binder.forField(phone)
                .bind(Contact::getPhone, Contact::setPhone);

        /*String name = this.contact.getPaymentFromData().getName();
        binder.forMemberField(this.paymentFromData)
                .bind(name);*/

       /* binder.bind(streetAddressField,
                person -> person.getAddress().getStreet(),
                (person, street) -> person.getAddress().setStreet(street));*/
       // binder.bindInstanceFields(this);
       /* payment.setItems(payments);
        payment.setItemLabelGenerator(Payment::getName);*/
        group.setItems(groupOfStudents);
        group.setItemLabelGenerator(GroupOfStudents::getName);
        add(firstName,
                lastName,
                phone,
                coursePrice,
                //payment,
                group,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, contact)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setContact(Contact contact) {
        this.contact = contact;
        binder.readBean(contact);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(contact);
            fireEvent(new SaveEvent(this, contact));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<ContactForm> {
        private Contact contact;

        protected ContactFormEvent(ContactForm source, Contact contact) {
            super(source, false);
            this.contact = contact;
        }

        public Contact getContact() {
            return contact;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ContactForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}