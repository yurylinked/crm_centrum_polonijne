package com.example.application.views.payment;

import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Payment;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.example.application.views.list.ContactForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;


@Component
@Scope("prototype")
@Route(value = "payment", layout = MainLayout.class)
@PageTitle("Payment | Centrum Polonujne CRM")
@PermitAll
public class PaymentView extends VerticalLayout {
    Grid<Payment> grid = new Grid<>(Payment.class);
    TextField filterText = new TextField();
    //PaymentF form;
    CrmService service;

    public PaymentView(CrmService service) {
        this.service = service;
        addClassName("payment-view");
        setSizeFull();
        configureGrid();

        // form = new ContactForm(service.findAllPayments(), service.findAllGroups());
//        form.setWidth("25em");
//        form.addListener(ContactForm.SaveEvent.class, this::saveContact);
//        form.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
//        form.addListener(ContactForm.CloseEvent.class, e -> closeEditor());

        FlexLayout content = new FlexLayout(grid);//
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1);
        content.setFlexShrink(0);
        content.addClassNames("content", "gap-m");
        content.setSizeFull();
        add(getToolbar(), content);
        updateList();
        //closeEditor();
//        grid.asSingleSelect().addValueChangeListener(event ->
//                editContact(event.getValue()));
    }

    private void configureGrid() {
        grid.addClassNames("payment-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "phone", "amount", "course");
       // grid.addColumn(payment -> payment.getGroupOfStudents().getCourse()).setHeader("Group");

        // grid.addColumn(contact -> contact.getPaymentFromData().getName()).setHeader("Payment");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

//        Button addContactButton = new Button("Add contact");
//        addContactButton.addClickListener(click -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText /*addContactButton*/);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    /*private void saveContact(ContactForm.SaveEvent event) {
        service.saveContact(event.getContact());
        updateList();
        closeEditor();
    }

    private void deleteContact(ContactForm.DeleteEvent event) {
        service.deleteContact(event.getContact());
        updateList();
        closeEditor();
    }

    public void editContact(Contact contact) {
        if (contact == null) {
            closeEditor();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }*/

    private void updateList() {
        grid.setItems(service.findAllPayments(filterText.getValue()));
    }


}
