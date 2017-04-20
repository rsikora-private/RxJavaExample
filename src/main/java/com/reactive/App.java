package com.reactive;

import rx.Observable;

/**
 * Created by rsikora on 4/14/2017.
 */
public class App {

    public static final void main(String ... args) {


        long start = System.currentTimeMillis();

        final Observable<Task> observable_1 = new HystrixReactiveCommand("Command Group 1").toObservable();
/*
        observable_1.subscribe( task -> {
            System.out.println("Task: " + task);
        },
        throwable -> {
            System.out.println(throwable);
        },
        () -> System.out.println("On complete")
        );*/

        final Observable<Task> observable_2 = new HystrixReactiveCommand("Command Group 2").toObservable();
/*
        observable_2.subscribe( task -> {
                    System.out.println("Task: " + task);
                },
                throwable -> {
                    System.out.println(throwable);
                },
                () -> System.out.println("On complete")
        ); */

        final Observable<Task> observable_3 = new HystrixReactiveCommand("Command Group 3").toObservable();
/*
        observable_3.subscribe( task -> {
                    System.out.println("Task: " + task);
                },
                throwable -> {
                    System.out.println(throwable);
                },
                () -> System.out.println("On complete")
        );*/


       // observable_1.zipWith(observable_2, observable_3, (result1, result2) -> {})

       /* Observable.zip(observable_1, observable_2, observable_3, (Task task1, Task task2, Task task3) -> {

            System.out.println(task1);
            System.out.println(task2);
            System.out.println(task3);

            return null;
        }).subscribe(System.out::println);*/

        Observable.merge(observable_1, observable_2, observable_3)
                .map(task -> new Task(task.getName() + " task"))
                .toBlocking()
                .subscribe( task -> System.out.println("Task: " + task),
                            throwable -> System.out.println(throwable),
                            () -> System.out.println("On complete")
        );

        System.out.println("Took: " + (System.currentTimeMillis() - start));
    }
}
