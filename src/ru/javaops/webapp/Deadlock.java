package ru.javaops.webapp;

public class Deadlock {
    static class Lock {
        private String lockName;
        private boolean isLocked;

        Lock(String lockName) {
            this.lockName = lockName;
            isLocked = false;
        }

        synchronized void unlock(Lock otherLock) throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " is in the " + lockName);
            isLocked = true;
            System.out.println(Thread.currentThread().getName() + " locked the " + lockName);
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + " try to unlock the " + otherLock.lockName);
            synchronized (otherLock) {
                otherLock.isLocked = false;
            }
            System.out.println(Thread.currentThread().getName() + " unlocked the " + otherLock.lockName);
            isLocked = false;
        }
    }

    public static void main(String[] args) {
        Lock lockOne = new Lock("First lock");
        Lock lockTwo = new Lock("Second lock");

        new Thread(() -> {
            try {
                lockOne.unlock(lockTwo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                lockTwo.unlock(lockOne);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
