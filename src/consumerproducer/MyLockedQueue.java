package consumerproducer;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLockedQueue<E> {
    private Object[] elementData;
    private final Lock lock;
    private static final int MINIMUM_SIZE = 10;
    private int size;
    private int counter;

    public MyLockedQueue(int size) {
        elementData = new Object[size];
        this.size = size;
        lock = new ReentrantLock();
    }

    public MyLockedQueue() {
        elementData = new Object[MINIMUM_SIZE];
        this.size = MINIMUM_SIZE;
        lock = new ReentrantLock();
    }

    public void add(E element) {
        try {
            lock.lock();
            if (counter == size)
                grow();
            elementData[counter++] = element;
        } finally {
            lock.unlock();
        }
    }

    public E pick() {
        try {
            lock.lock();
            if (length() != 0) {
                Object element = elementData[0];
                elementData[0] = null;
                return (E) element;
            } else
                return null;
        } finally {
            lock.unlock();
        }
    }

    private void grow() {
        size = counter + MINIMUM_SIZE;
        elementData = Arrays.copyOf(elementData, size);
    }

    public boolean isEmpty() {
        return length() == 0;
    }

    public int length() {
        int count = 0;
        for (Object obj : elementData) {
            if (obj != null)
                count++;
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (Object obj : elementData) {
            if (obj != null)
                stringBuilder.append(obj).append(",");
        }
        return stringBuilder + "]";
    }
}
