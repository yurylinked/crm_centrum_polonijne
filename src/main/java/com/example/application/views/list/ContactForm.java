package com.example.application.views.list;

import com.example.application.data.entity.Course;
import com.example.application.data.entity.Payment;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.GroupOfStudents;
import com.example.application.data.repository.CourseGenerator;
import com.example.application.data.repository.CourseRepository;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class ContactForm extends FormLayout {
    private Contact contact = new Contact();
    // private GroupOfStudents groupOfStudents = new GroupOfStudents();

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField phone = new TextField("Phone");

    NumberField coursePrice = new NumberField("Course price");
    // TextField groupName = new TextField("Group Name");
    TextField groupName = new TextField("Group Name");

    ComboBox<Course> course1 = new ComboBox<>("Course");
    Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);
    Binder<GroupOfStudents> bindergroup = new BeanValidationBinder<>(GroupOfStudents.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public ContactForm(List<GroupOfStudents> groupOfStudents, List<Course> courses, List<Contact> contacts) {
        addClassName("contact-form");
        //contact.getPayment().getAmount();
        binder.forField(firstName)
                .bind(Contact::getFirstName, Contact::setFirstName);
        binder.forField(lastName)
                .bind(Contact::getLastName, Contact::setLastName);
        binder.forField(coursePrice)
                .bind(Contact::getCoursePrice, Contact::setCoursePrice);
        binder.forField(phone)
                .bind(Contact::getPhone, Contact::setPhone);
   /*    *//* binder.forField(groupName).withConverter(((ValueProvider<Contact, String>)
                a -> a.getGroupOfStudents().getName()))*//*
                .bind(Contact::getGroupOfStudents, Contact::setGroupOfStudents);*/

        bindergroup.forField(groupName).bind(GroupOfStudents::getName, GroupOfStudents::setName);


      /*  groupName.setItemLabelGenerator(GroupOfStudents::getName);
        groupName.setItems(groupOfStudents);*/
        //  binder.forField(groupName).bind(GroupOfStudents::getNameOfGroup, GroupOfStudents::setNameOfGroup);

        course1.setItemLabelGenerator(Course::getName);
        course1.setItems(courses);
        binder.forField(course1).bind(Contact::getCourse, Contact::setCourse);
        binder.bindInstanceFields(this);

        add(this.firstName,
                lastName,
                phone,
                coursePrice,
                groupName,
                course1,
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
            binder.forField(course1).bind(Contact::getCourse, Contact::setCourse);
            bindergroup.forField(groupName).bind(GroupOfStudents::getName, GroupOfStudents::setName);
            binder.bindInstanceFields(this);
            binder.writeBean(contact);
            fireEvent(new SaveEvent(this, contact));
        } catch (ValidationException e) {
            Notification.show("Validation error count: "
                    + e.getValidationErrors().size());
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