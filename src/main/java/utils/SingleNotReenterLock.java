package utils;

public class SingleNotReenterLock {
    private static SingleNotReenterLock ourInstance = new SingleNotReenterLock();
    private boolean isLocked = false;
    public static SingleNotReenterLock getInstance() {
        return ourInstance;
    }

    private SingleNotReenterLock() {
    }

    public synchronized void lock() throws InterruptedException{
        while(isLocked){
            wait();
        }
        isLocked = true;
    }
    public synchronized void unlock(){
        isLocked = false;
        notify();
    }

}
