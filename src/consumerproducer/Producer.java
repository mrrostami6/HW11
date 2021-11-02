package consumerproducer;

import java.util.Random;

public record Producer<E>(MyLockedQueue<E> products) implements Runnable {

    @Override
    public void run() {
        synchronized (products) {
            String obj = generate();
            products.add((E) obj);
            System.out.println(Thread.currentThread().getName() + " Produced : " + obj);
            products.notifyAll();
            System.out.println("notified");
            System.out.println(products);
            System.out.println(products.length());
        }
    }

    private String generate() {
        return
                "a" + new Random().nextInt() + "z";
    }
}

