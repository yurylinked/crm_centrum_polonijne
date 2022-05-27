package com.example.application.data.repository;

import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Course;
import com.example.application.data.entity.GroupOfStudents;
import com.example.application.data.entity.Payment;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
public class CourseGenerator {

    @Bean
    public CommandLineRunner loadData(
            CourseRepository courseRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (courseRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }

            List<Course> courses = courseRepository
                    .saveAll(Stream.of("Минск - Курс с 0 до А1", "Минск - Курс с 0 до А2",
                                    "Минск - Курс с А1 до В1", "Минск - Курс с А2 до В1",
                                    "Минск - Курс с В1 до В2", "Минск - Интенсивный курс с 0 до В1",
                                    "Минск - Интенсивный курс с А2 до В2", "Минск - Курс для Карты Поляка",
                                    "Минск - Дошколята", "Минск - 1-2 класс", "Минск - 3-5 класс",
                                    "Минск - 6-8 класс", "Онлайн - Общий курс с 0 до А1",
                                    "Онлайн - Общий курс с 0 до А2", "Онлайн - Общий курс с А2 до В1", "Онлайн - Общий курс с В2 до C1",
                                    "Онлайн - интенсив с А2 до В2", "Онлайн - интенсив с 0 до В1", "Онлайн - Детские группы",
                                    "Онлайн - курс После Карты Поляка", "Онлайн - курс для Карты Поляка", "Гродно - Интенсив с  0 до В1",
                                    "Гродно - Курс с В1 до В2", "Гродно - Курс с А2 до В1", "Гродно - Курс с 0 до А2",
                                    "Гродно - Курс с 0 до А1", "Гродно - 6-8 класс", "Медицинский Польский",
                                    "Информационные услуги", "Летний лагерь")
                            .map(Course::new).collect(Collectors.toList()));


       /*     logger.info("... generating 50 Contact entities...");
            ExampleDataGenerator<Contact> contactGenerator = new ExampleDataGenerator<>(Contact.class,
                    LocalDateTime.now());
            contactGenerator.setData(Contact::setFirstName, DataType.FIRST_NAME);
            contactGenerator.setData(Contact::setLastName, DataType.LAST_NAME);
            contactGenerator.setData(Contact::setPhone, DataType.PHONE_NUMBER);*/

            courseRepository.saveAll(courses);


        /*    Random r = new Random(seed);
            List<Contact> contacts = contactGenerator.create(50, seed).stream().peek(contact -> {
                contact.setPayment(companies.get(r.nextInt(companies.size())));
                contact.setGroupOfStudents(statuses.get(r.nextInt(statuses.size())));
            }).collect(Collectors.toList());

            contactRepository.saveAll(contacts);*/

            logger.info("Generated demo data");
        };
    }

}