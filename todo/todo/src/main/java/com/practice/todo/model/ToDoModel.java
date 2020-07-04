package com.practice.todo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class ToDoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String item;

    @Column(columnDefinition = "boolean default false")
    private boolean completed;

    public ToDoModel() {
    }

    public ToDoModel(Integer id, String item, boolean completed ) {
        this.id = id;
        this.item = item;
        this.completed =  completed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
