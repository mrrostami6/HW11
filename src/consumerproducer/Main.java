package consumerproducer;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Queue<String> products = new ArrayDeque<>();
        List<Thread> consumerThreads = new ArrayList<>();
        List<Thread> producerThreads = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            producerThreads.add(getProducerThread(products));
            consumerThreads.add(getConsumerThread(products));
        }

        for (int i = 0; i < 5; i++) {
            consumerThreads.get(i).start();
            producerThreads.get(i).start();
        }

    }

    public static Thread getProducerThread(Queue<String> products){
        return new Thread(() -> {
            synchronized (products){
                String product = generateName();
                products.add(product);
                products.notifyAll();
                System.out.println(Thread.currentThread().getName() + " Added : " + product);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Thread getConsumerThread(Queue<String> products) {
        return new Thread(() -> {
            synchronized (products){
                while (products.isEmpty()) {
                    try {
                        products.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String removed = products.poll();
                System.out.println(Thread.currentThread().getName() + " Removed : " + removed);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static String generateName(){
        StringBuilder name = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            name.append((char) (random.nextInt(27) + 96));
        }
        return name.toString();
    }
}
