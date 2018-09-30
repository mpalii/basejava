package ru.javaops.webapp;

public class Deadlock {
    static class Lock {
        synchronized void unlock(Lock otherLock) throws InterruptedException {
            Thread.sleep(500);
            synchronized (otherLock) {
                System.out.println("This text will never be printing!");
            }
        }
    }

    public static void main(String[] args) {
        Lock lockOne = new Lock();
        Lock lockTwo = new Lock();

        startThread(lockOne, lockTwo);
        startThread(lockTwo, lockOne);
    }

    private static void startThread(Lock lockOne, Lock lockTwo) {
        new Thread(() -> {
            try {
                lockTwo.unlock(lockOne);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
