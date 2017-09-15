package org.vaadin.easybinder.testentity;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class Person {

    private Long id;

    private String name;

    private LocalDate birthDate;

    private Float amount;

    private Boolean active;

    private Date today;

}
