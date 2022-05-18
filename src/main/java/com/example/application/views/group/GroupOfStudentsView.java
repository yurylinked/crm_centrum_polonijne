package com.example.application.views.group;


import com.example.application.data.entity.GroupOfStudents;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;


@Component
@Scope("prototype")
@Route(value="groupOfStudents", layout = MainLayout.class)
@PageTitle("Groups | Centrum Polonujne CRM")
@PermitAll
public class GroupOfStudentsView extends VerticalLayout {
    Grid<GroupOfStudents> grid = new Grid<>(GroupOfStudents.class);
    TextField filterText = new TextField();
    GroupForm form;
    CrmService service;

    public GroupOfStudentsView(CrmService service) {
        this.service = service;
        addClassName("groupOfStudents-view");
        setSizeFull();
        configureGrid();

        form = new GroupForm(service.findAllPayments(), service.findAllGroups());
        form.setWidth("25em");
        form.addListener(GroupForm.SaveEvent.class, this::saveGroup);
        form.addListener(GroupForm.DeleteEvent.class, this::deleteGroup);
        form.addListener(GroupForm.CloseEvent.class, e -> closeEditor());

        FlexLayout content = new FlexLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setFlexShrink(0, form);
        content.addClassNames("content", "gap-m");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();
        grid.asSingleSelect().addValueChangeListener(event ->
                editGroup(event.getValue()));
    }

    private void configureGrid() {
        grid.addClassNames("groupOfStudents-grid");
        grid.setSizeFull();
        grid.setColumns("name", "course", "date", "teacher");
        //grid.addColumn(contact -> contact.getGroup().getName()).setHeader("Group");
        // grid.addColumn(contact -> contact.getPaymentFromData().getName()).setHeader("Payment");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add group");
        addContactButton.addClickListener(click -> addGroup());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveGroup(GroupForm.SaveEvent event) {
        service.saveGroup(event.getGroupOfStudents());
        updateList();
        closeEditor();
    }

    private void deleteGroup(GroupForm.DeleteEvent event) {
        service.deleteGroup(event.getGroupOfStudents());
        updateList();
        closeEditor();
    }

    public void editGroup(GroupOfStudents groupOfStudents) {
        if (groupOfStudents == null) {
            closeEditor();
        } else {
            form.setGroupOfStudents(groupOfStudents);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    void addGroup() {
        grid.asSingleSelect().clear();
        editGroup(new GroupOfStudents());
    }

    private void closeEditor() {
        form.setGroupOfStudents(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllGroups(filterText.getValue()));
    }


}
