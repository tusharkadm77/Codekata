import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class SpoiledMangoExample {

	static int rowInput = 0;
	static int colInput = 0;
	static int array[][];

	public static void main(String... str) {
		readInput();
	}
	
	/*
	 * [2 1  2]
	 * [2 1  1]
	 * [2 0  1]
	 */
	
	private static void readInput() {
		// Using Scanner for Getting Input from User 
        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter Matrix dimension");
        rowInput = in.nextInt();
        colInput = in.nextInt();
        
        Map<Integer, List<Integer>> spoiled = new HashMap<>();
        System.out.println("Enter Matrix value");
        array = new int[rowInput][colInput];
        for (int i = 0; i < rowInput; i++) {
        	for (int j = 0; j < colInput; j++) {
        		int value = in.nextInt();
        		array[i][j] = value;
        		if (value == 2) {
        			addInSpoiledMap(spoiled, i, j);
        		}
        	}
		}
        
        System.out.println("Final Matrix: " + Arrays.deepToString(array));
        System.out.println("IntialSpoiled Map: " + spoiled);
        
        calculateSpoiledIteration(spoiled);
        
	}
	
	
	private static void calculateSpoiledIteration(Map<Integer, List<Integer>> spoiled) {
		if (spoiled.isEmpty()) {
			System.out.println("All are safe");
			return;
		}
		
		int iteration = 0;
		// Check for spoiled element count, if count is zero consider it as no further 
		// element are there in array which will get spoiled.
		while(checkForSpoiledMango(spoiled) != 0) {
			iteration++;
		}
		System.out.println("All Element will get spolied after " + iteration + " iteration");
	}
	
	private static int checkForSpoiledMango(Map<Integer, List<Integer>> spoiledMap) {
		int spoiledMongo = 0;
		
		// Create copy so that iterator map will not change when original one changes.
		Map<Integer, List<Integer>> mapToIterate = generateCopyOfMap(spoiledMap);
		
		for(Entry<Integer, List<Integer>> entry: mapToIterate.entrySet()) {
			int row = entry.getKey();
			for (int column : entry.getValue()) {
				
				// Check and Spoil horizontal neighbor element.
				if(!checkForArrayOutOfBound(row +1, column)) {
					continue;
				} else {
					if(array[row + 1][column] == 1) {
						array[row + 1][column] = 2; // update value in array
						spoiledMongo++;
						addInSpoiledMap(spoiledMap, (row + 1), column); // Update map
					}	
				}
				
				// Check and Spoil vertical neighbor element.
				if(!checkForArrayOutOfBound(row, column + 1)) {
					continue;
				} else {
					if(array[row][column+1] == 1) {
						array[row][column+1] = 2; // update value in array
						spoiledMongo++;
						addInSpoiledMap(spoiledMap, row, (column + 1)); // Update map
					}	
				}
				
			}
		}
		
		System.out.println("Spoiled Element for current iteration: " + spoiledMongo);
		System.out.println("Matrix after element are spoiled: " + Arrays.deepToString(array));
		
		return spoiledMongo;
	}
	
	/**
	 * To add element in spoiled map. Same key will refer to list of value.
	 * @param spoiledMap
	 * @param key
	 * @param value
	 */
	private static void addInSpoiledMap(Map<Integer, List<Integer>> spoiledMap, int key, int value) {
		List<Integer> values = spoiledMap.get(key);
		if(null == values) {
			values = new ArrayList<Integer>();
			values.add(value);
			spoiledMap.put(key, values);
		} else {
			values.add(value);
		}
	}
	
	/**
	 * To return copy of original map so that iterator map will not change.
	 * @param spoiledMap
	 * @return
	 */
	private static Map<Integer, List<Integer>> generateCopyOfMap(Map<Integer, List<Integer>> spoiledMap) {
		Map<Integer, List<Integer>> mapToIterate = new HashMap<>();
		for (Entry<Integer, List<Integer>> entry: spoiledMap.entrySet()) {
			List<Integer> list = new ArrayList<>();
			list.addAll(entry.getValue());
			mapToIterate.put(entry.getKey(), list);
		}
		
		return mapToIterate;
	}
	
	private static boolean checkForArrayOutOfBound(int row, int column) {
		return row < rowInput && column < colInput;
	}
}


/*
 * Given a matrix of dimension r*c where each cell in the matrix can have values 0, 1 or 2 which has the following meaning:
0 : Empty cell
1 : Cells have fresh oranges
2 : Cells have rotten oranges

So, we have to determine what is the minimum time required to rot all oranges. A rotten orange at index [i,j] can rot other fresh orange at indexes [i-1,j], [i+1,j], [i,j-1], [i,j+1] (up, down, left and right) in unit time. If it is impossible to rot every orange then simply return -1.

Input:
The first line of input contains an integer T denoting the number of test cases. Each test case contains two integers r and c, where r is the number of rows and c is the number of columns in the array a[]. Next line contains space separated r*c elements each in the array a[].

Output:
Print an integer which denotes the minimum time taken to rot all the oranges (-1 if it is impossible).

Constraints:
1 <= T <= 100
1 <= r <= 100
1 <= c <= 100
0 <= a[i] <= 2

Example:
Input:
2
3 5
2 1 0 2 1 1 0 1 2 1 1 0 0 2 1
3 5
2 1 0 2 1 0 0 1 2 1 1 0 0 2 1

Output:
2
-1

Explanation:
Testcase 1:
2 | 1 | 0 | 2 | 1
1 | 0 | 1 | 2 | 1
1 | 0 | 0 | 2 | 1

Oranges at positions {0,0}, {0, 3}, {1, 3} and {2, 3} will rot oranges at {0, 1}, {1, 0}, {0, 4}, {1, 2}, {1, 4}, {2, 4} during 1st unit time. And, during 2nd unit time, orange at {1, 0} got rotten and will rot orange at {2, 0}. Hence, total 2 unit of time is required to rot all oranges.

Testcase 2:
2 | 1 | 0 | 2 | 1
0 | 0 | 1 | 2 | 1
1 | 0 | 0 | 2 | 1

Orange at position {2,0} will not rot as there is no path to it.


-------------------------
 */
