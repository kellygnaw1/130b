//Kelly Wang
//5351010

import java.util.*;
import java.io.*;
public class Magic {
	
	public static int n;
	public static int m; 
	public static long [][] matrix;
	
	
	//helper function to clone 2-D Array
	public static long [][] cloneArray(long[][]arr){
		long [][] arrCopy = new long[arr.length][];
		for(int i = 0; i < arr.length; i++)
			arrCopy[i] = arr[i].clone();
		return arrCopy; 
	}
	
	//helper function to fill checkerboard
	/*
	 * 1. basically need to check the cell above and below to fit increasing restriction
	 * 2. 4 parity cases --> row of all even then all odd, row of all odd then all even, row of even odd, or row of odd even
	 */

	public static long fill(long[][]arr, int [] parity, int n, int m) {
		long sum = 0; 
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				int oddEven = parity[(i+j)%2];
				long above = 0; 
				long left = 0; 
				if(i > 0) above = arr[i-1][j];
				if(j > 0) left = arr[i][j-1];
				
				if(arr[i][j] == 0) {
					long pv = Math.max(above, left) +1;
					if(pv % 2 != oddEven) pv++;
					arr[i][j] = pv;
				}else if(oddEven != arr[i][j] % 2 || arr[i][j] <= left || arr[i][j] <= above) return -1;
				sum += arr[i][j];
			}
			parity[0] = 1-parity[0];
			parity[1] = 1-parity[1];
		}
		return sum;
	}
	

	//case 1: user provides one row or one column --> reduce run time
	public static long single() {
		long [] arr = new long[n*m];		
		for(int i = 0; i < n*m; i++) 
			arr[i] = matrix[i%n][i/n];
		
		long current_min = 1; 
		long sum = 0;
		for(int i = 0; i < n*m; i++) {
			if(i != 0)
				current_min = arr[i-1]+1;
			if(arr[i] == 0) {
				arr[i] = current_min;
			}
			else if(arr[i] < current_min)
				return -1;
			sum += arr[i];
		}
		
		return sum;
	}
	
	//case 2: user provides matrix with more than 1 row and 1 column AKA general solution 
	public static long general(){
		long [] min = new long[4];
		
		int[] parity1 = {1,1};
		min[0] = (fill(cloneArray(matrix), parity1, n, m));
		
		int [] parity2 = {0,0};
		min[1] = fill(cloneArray(matrix), parity2, n, m);
		
		int [] parity3 = {1,0};
		min[2] = fill(cloneArray(matrix), parity3, n, m);
		
		int [] parity4 = {0, 1};
		min[3] = fill(cloneArray(matrix), parity4, n, m);
		
		Arrays.sort(min);
		
//		System.out.println(Arrays.toString(min));
		for(int i = 0; i < 4; i++) {
			if(min[i] != -1)
				return min[i];
		}
		return -1;
	}
	
	public static void main(String [] args) throws Exception{
		//initialize from user input
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer token = new StringTokenizer(input.readLine());
		n = Integer.parseInt(token.nextToken());
		m = Integer.parseInt(token.nextToken());
		matrix = new long[n][m];
		
		//fill matrix
		for(int i = 0; i < n; i++) {
			token = new StringTokenizer(input.readLine());
			for(int j = 0; j < m; j++) {
				matrix[i][j] = Integer.parseInt(token.nextToken());
			}
		}
		
		//only one row or one column 
		if(n == 1 || m == 1)
			System.out.println(single());
		
		else {
			System.out.println(general());
		}
	}
	
}
