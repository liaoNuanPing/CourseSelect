package utils;

public class SingleNotReenterLock {
    private static SingleNotReenterLock ourInstance = new SingleNotReenterLock();

    public static SingleNotReenterLock getInstance() {
        return ourInstance;
    }

    private SingleNotReenterLock() {
    }
}
