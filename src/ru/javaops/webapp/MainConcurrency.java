package ru.javaops.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static int counter = 0;
    private static final Object LOCK = new Object();
    private static final int THREADS_NUMBER = 10_000;

    public static void main(String[] args) throws InterruptedException {
        // Threads examples
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException(); // Exception example
            }
        };

        thread0.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }
        }).start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();

        System.out.println(thread0.getName() + ", " + thread0.getState());



        // Synchronization example
        // without threads
        for (int i = 0; i < THREADS_NUMBER; i++) {
            for (int j = 0; j < 100; j++) {
                incOne();
            }
        }
        System.out.println(counter);
        counter = 0;



        // with threads
        for (int i = 0; i < THREADS_NUMBER; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    incOne();
                }
            }).start();
        }
        Thread.sleep(1000); // если убрать слип, то вывод значения счетчика может произойти раньше чем закончится работа потоков
        System.out.println(counter);
        counter = 0;



        // Using block synchronized
        for (int i = 0; i < THREADS_NUMBER; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    incTwo();
                }
            }).start();
        }
        Thread.sleep(1000); // если убрать слип, то вывод значения счетчика может произойти раньше чем закончится работа потоков
        System.out.println(counter);
        counter = 0;



        // Non static method
        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.incThree();
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(counter);
        counter = 0;
    }

    private static synchronized void incOne() {
        counter++;
    }

//    private static synchronized void incOne() {
//        synchronized (MainConcurrency.class) {
//            counter++;
//        }
//    }

    private static void incTwo() {
        double a = Math.sin(30);
        synchronized (LOCK) {
            counter++;
        }
    }

    private synchronized void incThree() {
        counter++;
    }

//    private void incThree() {
//        synchronized (this) {
//            counter++;
//        }
//    }
}
