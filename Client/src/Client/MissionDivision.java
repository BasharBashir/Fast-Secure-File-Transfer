package Client;
import java.util.*;

public class MissionDivision {
	public static void main(String[] args) {
	    // Time required for each client to get an item
	    double[] clientTimes = {2.5, 2.8, 3.2, 2.1, 4.0};

	    // Number of items to get
	    int numItems = 5;

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
	    int[] assignments = new int[numItems];
	    Arrays.fill(assignments, -1);
	    for (int i = 0; i < numItems; i++) {
	        int bestClient = -1;
	        double bestTime = Double.MAX_VALUE;
	        for (int j = 0; j < clientTimes.length; j++) {
	            if (assignments[i] == -1 && times[j][i] < bestTime) {
	                bestClient = j;
	                bestTime = times[j][i];
	            }
	        }
	        assignments[i] = bestClient;
	    }

	    // Output the assignments
	    for (int i = 0; i < numItems; i++) {
	        System.out.println("Item " + (i+1) + " assigned to client " + (assignments[i]+1));
	    }
	}
}
