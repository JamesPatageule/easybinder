package org.vaadin.easybinder.testentity;

import java.time.LocalDate;
import java.util.Date;

import org.vaadin.easybinder.annotations.FormField;

import lombok.Data;

@Data
public class Person {

    @FormField
    private Long id;

    @FormField
    private String name;

    @FormField
    private LocalDate birthDate;

    @FormField
    private Float amount;

    @FormField
    private Boolean active;

    @FormField
    private Date today;

    @FormField
    private Integer myInteger;
    @FormField
    private int myInt;
    @FormField
    private long myLong;
    @FormField
    private float myFloat;

}
