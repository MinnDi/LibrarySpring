package ru.example.mindi.model;

import javax.validation.constraints.*;

public class Person {
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(max = 150, min = 8, message = "Name should be no shorter that 8 characters and no longer thar 150 characters")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+", message = "Your name should match pattern: Surname Name Middlename")
    private String name;
    @Min(value = 1900, message = "Year should be more than 1900")
    @Max(value = 2200, message = "Year should be less than 2200")
    private int year;

    public Person(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public Person() {
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
