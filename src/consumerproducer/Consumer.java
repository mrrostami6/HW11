package consumerproducer;

public record Consumer<E>(MyLockedQueue<E> products) implements Runnable {

    @Override
    public void run() {
        synchronized (products) {
            while (!products.isEmpty()) {
                try {
                    System.out.println("waiting");
                    products.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(Thread.currentThread().getName() + " consumed : " + products.pick());
    }
}
