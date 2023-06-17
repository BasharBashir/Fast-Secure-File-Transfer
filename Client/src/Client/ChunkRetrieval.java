package Client;

import java.util.*;

public class ChunkRetrieval {
    
    static class Chunk implements Comparable<Chunk> {
        int id;
        int time;
        
        public Chunk(int id, int time) {
            this.id = id;
            this.time = time;
        }
         
        public int compareTo(Chunk other) {
            return Integer.compare(time, other.time);
        }
    }
    
    public static void main(String[] args) {
        int numChunks = 20;
        int[] timesPerClient = {1, 2, 3, 4};
        
        int[] numChunksPerClient = new int[timesPerClient.length];
        int remainingChunks = numChunks;
        
        // Distribute chunks among clients based on their retrieval times
        for (int i = 0; i < timesPerClient.length; i++) {
            int chunksForClient = Math.min((int) Math.floor((double)numChunks/(timesPerClient.length-i)), remainingChunks);
            numChunksPerClient[i] = chunksForClient;
            numChunks -= chunksForClient;
            remainingChunks -= chunksForClient;
        }
        
        PriorityQueue<Chunk> queue = new PriorityQueue<>();
        for (int i = 1; i <= numChunksPerClient.length; i++) {
            for (int j = 1; j <= numChunksPerClient[i-1]; j++) {
                queue.add(new Chunk(i*100+j, timesPerClient[i-1]));
            }
        }
        
        int[] chunksPerClient = new int[numChunksPerClient.length];
        while (!queue.isEmpty()) {
            Chunk chunk = queue.poll();
            int clientIndex = chunk.id / 100 - 1;
            chunksPerClient[clientIndex]++;
            if (chunksPerClient[clientIndex] <= numChunksPerClient[clientIndex]) {
                System.out.println("Client " + (clientIndex+1) + " got chunk " + chunk.id%100);
            } else {
                System.out.println("Client " + (clientIndex+1) + " is done.");
            }
        }
    }
}
