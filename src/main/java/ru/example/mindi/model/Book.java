package ru.example.mindi.model;

import javax.validation.constraints.*;

public class Book {
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(max = 500, min = 1, message = "Name should be no shorter that 1 characters and no longer thar 500 characters")
    @Pattern(regexp = "[А-Я 0-9][а-я А-Я 0-9]*", message = "Book name should start from capital letter or digit")
    private String name;
    @NotEmpty
    @Size(max = 500, message = "Author name should be no longer thar 500 characters")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+", message = "Author name should match pattern: Surname Name")
    private String author;
    @Min(value = 1500, message = "Year should be more than 1500")
    private int year;

    public Book(int id, String name, String author, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Book() {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}