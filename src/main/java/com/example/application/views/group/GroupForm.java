package com.example.application.views.group;


import com.example.application.data.entity.Course;
import com.example.application.data.entity.CourseInterface;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;


import java.util.List;

public class GroupForm extends FormLayout {

    private GroupOfStudents groupOfStudents = new GroupOfStudents();

    TextField name = new TextField("Name of Group");
    TextField teacher = new TextField("Teacher");
    DatePicker datePicker = new DatePicker("Date of Payment");

    ComboBox<Course> course1 = new ComboBox<>("Course");

    Binder<GroupOfStudents> binder = new BeanValidationBinder<>(GroupOfStudents.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");


    public GroupForm(List<GroupOfStudents> groupOfStudentsList, List<Course> courses, CourseGenerator courseGenerator,
                     CourseRepository courseRepository) {
        addClassName("group-form");
        binder.forField(name)
                .bind(GroupOfStudents::getName, GroupOfStudents::setName);
        binder.forField(teacher)
                .bind(GroupOfStudents::getTeacher, GroupOfStudents::setTeacher);
        binder.forField(datePicker)
                .bind(GroupOfStudents::getDate, GroupOfStudents::setDate);

        course1.setItemLabelGenerator(Course::getName);
        course1.setItems(courses);

        add(this.name,
                teacher,
                datePicker,
                course1,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());//не проходит валидацию тут 1 ошибка
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, groupOfStudents)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setGroupOfStudents(GroupOfStudents groupOfStudents) {
        this.groupOfStudents = groupOfStudents;
        binder.readBean(groupOfStudents);
    }

    private void validateAndSave() {//не сохраняется  проблема изза course

        try {
            binder.forField(course1).bind(GroupOfStudents::getCourse, GroupOfStudents::setCourse);
            binder.writeBean(groupOfStudents);//

            fireEvent(new SaveEvent(this, groupOfStudents));
        } catch (ValidationException e) {
            Notification.show("Validation error count: "
                    + e.getValidationErrors().size());
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