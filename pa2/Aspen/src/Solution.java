import java.util.*;
import java.lang.*;
import java.io.*;

class Point {
	double x; 
	double y;
	Point(double x, double y){
		this.x = x; 
		this.y = y;
	}
}
public class Solution {
	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public static double findDist(Point u, Point v){
		double ans = Math.sqrt(Math.pow((u.x-v.x), 2) + Math.pow((u.y-v.y), 2));
		return ans;
	}
	
	public static void main(String[]args) throws IOException{
		int N;
		double L, W; 
		
		//find total number of trees 
		String totalTrees = reader.readLine(); 
		N = Integer.parseInt(totalTrees);
		ArrayList <Point> list = new ArrayList<Point>();
		
		//find length and width of road 
		String [] LW = reader.readLine().split(" ");
		L = Double.parseDouble(LW[0]);
		W = Double.parseDouble(LW[1]);
		
		//populate the list of trees 
		String line;
		for(int i = 0; i < N; i++) {
			line = reader.readLine();
			Point temp = new Point(0.0, Double.parseDouble(line)); 
			list.add(temp);
		}
		
		//sort input trees in ascending order 
		Collections.sort(list, new Comparator<Point>() {
			public int compare(Point a, Point b) {
				return Double.compare(a.y, b.y);
			}
		});

		/*
		 * find the proper location for left and right side of road. 
		 * If we have N trees in total, we must have N/2 on each side 
		 * Keep in mind we need to dedicate 4 trees to the front and the back 
		 * LeftList = where the trees proper location should be on the left side
		 * RightList = where the trees proper location should be on the right side
		*/
		
		int n = N/2;
		ArrayList<Point> LeftList = new ArrayList<Point>();
		ArrayList<Point> RightList = new ArrayList<Point>();
		
		for(int i = 0; i < n; i++) {
			double yVal = i*L/(n-1);
			Point left = new Point(0.0, yVal);
			Point right = new Point(W, yVal);
			LeftList.add(left);
			RightList.add(right);
		}
		
		double [][] result = new double [n+1][n+1];
		result[0][0] =0;
		/*
		 * Idea Behind the Table:
		 * We can think of the i and j as the left and right side (respectively)
		 * These values are our "recursive" inputs for how many trees on the right and how many on the left. 
		 * [0][1] = 1 tree on the right, [1][0] = 1 tree on the right
		 * [2][1] = 2 trees on the left, 1 tree on the right, [1][2] = 1 tree on the left 2 trees on the right. 
		 * If we have 4 trees to start with, want our value at [2][2] because that means we have 2 trees on both sides 
		 * This would give us the minimum distance because we store the minimum value of the chosen trees from before
		 * then build our most current tree from the minimum distance of previous trees. 
		 */
		for(int i = 1; i < n+1; i++)
			result[i][0] = result[i-1][0] + findDist(list.get(i-1), LeftList.get(i-1));
		for(int j = 1; j < n+1; j++)
			result[0][j] = result[0][j-1] + findDist(list.get(j-1), RightList.get(j-1));
		for(int i = 1; i<n+1; i++) {
			for(int j = 1; j < n+1; j++) {
				result[i][j] = Math.min(result[i-1][j]+findDist(list.get(i+j-1), LeftList.get(i-1)), result[i][j-1]+findDist(list.get(i+j-1), RightList.get(j-1)));
			}
		}
		System.out.println(result[n][n]);

	}

}
