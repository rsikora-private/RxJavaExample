package com.reactive;

/**
 * Created by rsikora on 4/14/2017.
 */
public class Task {

    private final String name;

    public Task(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                '}';
    }
}
