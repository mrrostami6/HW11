package consumerproducer;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        MyLockedQueue<String> products = new MyLockedQueue<>();

        Thread consumer = new Thread(new Consumer<>(products));
        Thread producer = new Thread(new Producer<>(products));

        consumer.start();
        producer.start();
    }
}
