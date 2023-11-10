package threadrace;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        // Generate a list which include 1 to 10000 sequential numbers
        List<Integer> list = IntStream.rangeClosed(1, 10000).boxed().toList();

        // Create lists to store numbers by odd or even
        List<Integer> oddNumbers = new ArrayList<>();
        List<Integer> evenNumbers = new ArrayList<>();

        List<Thread> threads = new ArrayList<>();

        // Looking for odd numbers
        for (int i = 0; i < 4; i++) {
            CustomThread thread = new CustomThread(
                    (n) -> n % 2 == 1, list.subList(i * 2500, (i + 1) * 2500), oddNumbers);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        threads.clear();

        // Looking for even numbers
        for (int i = 0; i < 4; i++) {
            CustomThread thread = new CustomThread(
                    (n) -> n % 2 == 0, list.subList(i * 2500, (i + 1) * 2500), evenNumbers);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        threads.clear();

        // Printing Result
        String builder = "Odd Numbers: " + oddNumbers + "\n" +
                "Length: " + oddNumbers.size() + "\n" +
                "Even Numbers: " + evenNumbers + "\n" +
                "Length: " + evenNumbers.size() + "\n";

        System.out.print(builder);
    }
}