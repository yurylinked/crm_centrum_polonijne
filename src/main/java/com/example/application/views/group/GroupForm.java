package com.example.application.views.group;


import com.example.application.data.entity.Payment;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class GroupForm extends FormLayout {
    private GroupOfStudents groupOfStudents;

    TextField name = new TextField("Name of Group");
    TextField teacher = new TextField("Teacher");
    DatePicker datePicker = new DatePicker("Date of Payment");
    ComboBox<GroupOfStudents> group = new ComboBox<>("Group");

    Binder<GroupOfStudents> binder = new BeanValidationBinder<>(GroupOfStudents.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");


    public GroupForm(List<Payment> payments, List<GroupOfStudents> contacts) {
        addClassName("group-form");
        //Date input = groupOfStudents.getDate();
        //LocalDate date = LocalDate.ofInstant(input.toInstant(), ZoneId.systemDefault());
       /* binder.forField(paymentFromData)
                .bind(Contact::getPaymentFromData, null);*/
        binder.forField(name)
                .bind(GroupOfStudents::getName, GroupOfStudents::setName);
        binder.forField(teacher)
                .bind(GroupOfStudents::getTeacher, GroupOfStudents::setTeacher);
        binder.forField(datePicker)
                .bind(GroupOfStudents::getDate, GroupOfStudents::setDate);
        // binder.bindInstanceFields(this);
       /* payment.setItems(payments);
        payment.setItemLabelGenerator(Payment::getName);*/
        group.setItems((ComboBox.ItemFilter<GroupOfStudents>) groupOfStudents.getCourse());
        add(name,
                teacher,
                datePicker,
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
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, groupOfStudents)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setGroupOfStudents(GroupOfStudents groupOfStudents) {
        this.groupOfStudents = groupOfStudents;
        binder.readBean(groupOfStudents);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(groupOfStudents);
            fireEvent(new SaveEvent(this, groupOfStudents));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class GroupFormEvent extends ComponentEvent<GroupForm> {
        private GroupOfStudents groupOfStudents;

        protected GroupFormEvent(GroupForm source, GroupOfStudents groupOfStudents) {
            super(source, false);
            this.groupOfStudents = groupOfStudents;
        }

        public GroupOfStudents getGroupOfStudents() {
            return groupOfStudents;
        }
    }

    public static class SaveEvent extends GroupFormEvent {
        SaveEvent(GroupForm source, GroupOfStudents groupOfStudents) {
            super(source, groupOfStudents);
        }
    }

    public static class DeleteEvent extends GroupFormEvent {
        DeleteEvent(GroupForm source, GroupOfStudents groupOfStudents) {
            super(source, groupOfStudents);
        }

    }

    public static class CloseEvent extends GroupFormEvent {
        CloseEvent(GroupForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}