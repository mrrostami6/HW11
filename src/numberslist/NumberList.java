package numberslist;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class NumberList {
    public static void main(String[] args) throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please Enter Number : ");
        int number = scan.nextInt();
        AtomicInteger oddStart = new AtomicInteger(1);
        AtomicInteger evenStart = new AtomicInteger(0);

        List<Integer> numbers = new ArrayList<>();

        Thread even = new Thread(() -> {
            synchronized (numbers) {
                while (true) {
                    if (numbers.size() == number || numbers.size() == number + 1) return;
                    numbers.add(evenStart.getAndAdd(2));
                    try {
                        numbers.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        even.start();

        Thread odd = new Thread(() -> {
            while (true) {
                if (numbers.size() == number || numbers.size() == number + 1) return;
                synchronized (numbers) {
                    numbers.add(oddStart.getAndAdd(2));
                    numbers.notifyAll();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        odd.start();

        even.join();
        odd.join();

        System.out.println("Result : " + numbers);
    }
}