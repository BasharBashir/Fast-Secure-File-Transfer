package Client;

import java.util.Arrays;

public class ItemDistribution {
    public static void main(String[] args) throws InterruptedException {
        // Time required for each client to get an item
        double[] clientTimes = {1, 2.8, 3.2, 1, 4.0};

        // Number of items to get
        int numItems = 4;

        // Calculate the time required to get each item for each client
        double[][] times = new double[clientTimes.length][numItems];
        for (int i = 0; i < clientTimes.length; i++) {
            for (int j = 0; j < numItems; j++) {
                times[i][j] = clientTimes[i] * (j + 1);
            }
        }

        // Sort the times for each client
        for (int i = 0; i < clientTimes.length; i++) {
            Arrays.sort(times[i]);
        }

        // Assign items to clients
        int numClients = clientTimes.length;
        int itemsPerClient = numItems / numClients;
        int remainingItems = numItems % numClients;

        Thread[] threads = new Thread[numClients];
        int currentItemIndex = 0;

        for (int i = 0; i < numClients; i++) {
            final int clientIndex = i;
            final int startIndex = currentItemIndex;

            int numItemsForClient = itemsPerClient;
            if (remainingItems > 0) {
                numItemsForClient++;
                remainingItems--;
            }

            final int endIndex = currentItemIndex + numItemsForClient;

            threads[i] = new Thread(new Runnable() {
                public void run() {
                    for (int j = startIndex; j < endIndex; j++) {
                        System.out.println("Item " + (j+1) + " sent by client " + (clientIndex+1));
                    }
                }
            });

            currentItemIndex += numItemsForClient;
        }

        // Start the threads
        for (Thread thread : threads) {
            thread.start();
        }

        // Wait for the threads to finish
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
