import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Ninteen {
	private static int initZeroX;
	private static int initZeroY;
	private static int count=0;
	/**
	 * Function with a switch statement that contains ideal locations for each possible value
	 * @param value - value of node that is being analyized
	 * @return integer array representing coordinates where 0=x and 1=y
	 */
	private static int[] idealSpot(int value) {
		switch(value) {
			case 0: return new int[] {2,0};
			case 1: return new int[] {3,0};
			case 2: return new int[] {2,1};
			case 3: return new int[] {3,1};
			case 4: return new int[] {0,2};
			case 5: return new int[] {1,2};
			case 6: return new int[] {2,2};
			case 7: return new int[] {3,2};
			case 8: return new int[] {4,2};
			case 9: return new int[] {5,2};
			case 10: return new int[] {0,3};
			case 11: return new int[] {1,3};
			case 12: return new int[] {2,3};
			case 13: return new int[] {3,3};
			case 14: return new int[] {4,3};
			case 15: return new int[] {5,3};
			case 16: return new int[] {2,4};
			case 17: return new int[] {3,4};
			case 18: return new int[] {2,5};
			case 19: return new int[] {3,5};
			default: return new int[] {-1,-1};
		}
	}
	/**
	 * Check a state to see if it's a goal or not.
	 * @param arr - state you want to check
	 * @return
	 */
	private static boolean isGoal(int[][] arr){
		for(int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				int d = arr[i][j];
				if(d == 0 || d == -1) {
					continue;
				}
				else {
					int[] coord = idealSpot(d);
					if(j == coord[0] && i == coord[1]) continue;
					else return false;					
				}
			}
		}
		System.out.println("A path to a goal state has been found!\n");
		System.out.println("-----------------");
		return true;
	}
	/**
	 * Prints the current board.
	 * @param board - the board to print.
	 */
	private static void printBoard(int[][] board) {
		String line = "";
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j<6; j++) {
				int val = board[i][j];
				if(val == -1) 
					line += " - ";
				else if(val < 10) 
					line += "0" + val + " ";
				else
					line += val + " ";
			}
			line += "\n";
		}
		System.out.println(line);
	}
	private static int[][] setCorners(int[][] board){
		board = setBoard(board,0,1,0,new int[]{-1,-1});
		board = setBoard(board,4,5,0,new int[]{-1,-1});
		board = setBoard(board,0,1,1,new int[]{-1,-1});
		board = setBoard(board,4,5,1,new int[]{-1,-1});
		board = setBoard(board,0,1,4,new int[]{-1,-1});
		board = setBoard(board,4,5,4,new int[]{-1,-1});
		board = setBoard(board,0,1,5,new int[]{-1,-1});
		board = setBoard(board,4,5,5,new int[]{-1,-1});
		return board;
	}
	/**
	 * Helper function to add empty values to the array and 'put them on the board'
	 * @param start - starting col value
	 * @param end - ending col value
	 * @param y - the row number
	 * @param value - an int array of what to be passed
	 */
	private static int[][] setBoard(int[][] board, int start, int end, int y, int[] value){
		int i = 0;
		for(int x = start; x <= end; x++) {
			board[y][x] = value[i++];
		}
		return board;
	}
	/**
	 * Calculates the heuristic value of the node
	 * @param value the value of the node
	 * @param x the current column the node is in
	 * @param y the current row the node is in
	 * @return the calculated heuristic measure
	 */
	private static int heuristic(int[][] state) {
		//set heuristic to be "the sum of the x distance and y distance from the number's ideal spot"
		int h = 0;
		for(int i = 0; i < 6; i++) {
			for( int j = 0; j < 6; j++) {
				int val = state[i][j];
				//ignore empty spaces
				if(val != -1 || val != 0) {
					int[] coord = idealSpot(val);
					h += Math.abs((coord[0])-j)+Math.abs((coord[1])-i);
				}
			}
		}
		return h;
	}
	/**
	 * Checks if the coordinates of a possible neighbor are valid
	 * @param y row-value
	 * @param x col-value
	 * @param state - board state the swap would occur in
	 * @return boolean depending on validity of neighbor
	 */
	private static boolean validNeighbor(int y, int x, int[][] state) {
		if((x<0)||(x>=6)||(y<0)||(y>=6)||(state[y][x]==-1)||(state[y][x]==0)) return false;
		else return true;
	}
	/**
	 * Swaps the chosen node with the zero spot
	 * @param n the chosen node
	 * @param x col-val of the node to swap
	 * @param y row-val of the node to swap
	 * @return new board state
	 */
	private static int[][] swap(Node n, int x, int y){
		int[][] cp = cloneArr(n.state);
		
		int temp = cp[n.zeroy][n.zerox];
		cp[n.zeroy][n.zerox] = cp[y][x];
		cp[y][x] = temp;
		
		return cp;
	}
	/**
	 * Initializes the board with the start state of the problem
	 */
	private static int[][] initBoard(int[][] board) {
		ArrayList<Integer> unique = new ArrayList<Integer>(20);
		//initalize the empty corners
		board = setCorners(board); 
		
		Scanner sc = new Scanner(System.in);
		int value = 0;		
		//initialize the rest of the board
		for(int y = 0; y < 6; y++) {
			for(int x = 0; x < 6 ; x++) {
				if(board[y][x] == 0) {
					value = sc.nextInt();
					if(unique.contains(value)) {
						System.out.println("Error: Duplicate Number");
						System.exit(0);
					}
					else {
						//Save the position of the zero loc so we don't need to find it later.
						if(value == 0) {
							initZeroX = x; 
							initZeroY = y;
						}
						board[y][x] = value;						
						unique.add(value);
					}
				}
			}
		}
		sc.close();
		System.out.println();
		System.out.println("-- Start State --\n");
		printBoard(board);
		return board;
	}
	/**
	 * Function to clone an array
	 * @param arr to clone
	 * @return new cloned array
	 */
	private static int[][] cloneArr(int[][] arr){
		int[][] clone = new int[6][6];
		for(int i =0; i < 6; i++) {
			for(int j = 0; j < 6; j++) {
				clone[i][j]=arr[i][j];
			}
		}
		return clone;
	}
	/**
	 * Print's the path from a goal state to the start state
	 * @param d - current node
	 */
	private static void printPath(Node n) {
		if(n == null) return;
		printPath(n.parent);
		printBoard(n.state);
		count++;
	}
	/**
	 * Returns an arraylist containing all potential neighbors of the data node
	 * @param n node in question
	 * @return arraylist of neighbors
	 */
	private static ArrayList<State> getNeighbors(Node n) {
		ArrayList<State> neighbors = new ArrayList<State>();
		if(validNeighbor(n.zeroy-1,n.zerox,n.state)) {
			State child = new State(swap(n,n.zerox,n.zeroy-1));
			//Node child = new Node(swap(n,n.zerox,n.zeroy-1), n.zerox, n.zeroy-1, n, n.state[n.zeroy-1][n.zerox]);
			//child.g=child.parent.g+1;
			neighbors.add(child);
		}
		if(validNeighbor(n.zeroy,n.zerox-1,n.state)) {
			State child = new State(swap(n,n.zerox-1,n.zeroy));
			//Node child = new Node(swap(n,n.zerox-1,n.zeroy), n.zerox-1, n.zeroy,n,n.state[n.zeroy][n.zerox-1]);
			//child.g=child.parent.g+1;
			neighbors.add(child);
		}
		if(validNeighbor(n.zeroy+1,n.zerox,n.state)) {
			State child = new State(swap(n,n.zerox,n.zeroy+1));
			//Node child = new Node(swap(n,n.zerox,n.zeroy+1), n.zerox, n.zeroy+1,n, n.state[n.zeroy+1][n.zerox]);
			//child.g=child.parent.g+1;
			neighbors.add(child);
		}
		if(validNeighbor(n.zeroy,n.zerox+1,n.state)) {
			State child = new State(swap(n,n.zerox+1,n.zeroy));
			//Node child = new Node(swap(n,n.zerox+1,n.zeroy), n.zerox+1, n.zeroy,n,n.state[n.zeroy][n.zerox+1]);
			//child.g=child.parent.g+1;
			neighbors.add(child);
		}
		return neighbors;
	}
	/**
	 * Find's the location of the zero in a board state
	 * @param s the state
	 * @return coordinates of the zero - arr[0] = y, arr[1] = x
	 */
	private static int[] findZero(State s){
		int[][] board = s.board;
		for(int i = 0; i<6;i++){
			for(int j = 0; j<6;j++) {
				if (board[i][j] ==0) {
					return new int[]{i,j};
				}
			}
		}
		return new int[] {0,0};
	}
	/**
	 * Function that performs A* to find the shortest path to a successful state
	 * @param minPQ -the minpriority queue
	 * @param hm - the hashmap
	 * @return success or failure
	 */
	private static boolean aStar(MinPQ<Node> minPQ, HashMap<State, Node> hm){
		while(!minPQ.isEmpty()){			
			//choose a node in the frontier with the smallest (g+h) value
			Node data = minPQ.remove();			
			
			printBoard(data.state);			
			
			data.inFrontier = false; //it's no longer in the frontier
			
			if(isGoal(data.state)) { //if it's a goal, then we're done
				printPath(data);
				return true;
			}
			
			//Expand the chosen node
			//getNeighbors returns an arraylist of at most 4 nodes
			//These nodes contain the board state as a field
			//thus we can create a new state from these nodes, and then test to see if that state is
			//in the hashmap already or not
			for(State neighbor: getNeighbors(data)){ 
				//If the neighbor state is a new state that hasn't been seen before
				if(!hm.containsKey(neighbor)) {
					int[] arr = findZero(neighbor);
					Node n = new Node(neighbor.board, arr[1], arr[0],data);
					n.parent = data; //set parent
					n.g = data.g+1; //set new path
					n.inFrontier = true; //add it to the frontier
					n.h = heuristic(n.state); //calculate the heuristic value of the neighbor
					minPQ.add(n); //add it to the priority queue
					hm.put(neighbor, n); //add it to the hashmap
				}
				//same state already exists
				else if(hm.containsKey(neighbor)){
					Node old = hm.get(neighbor);
					//if old node is in the frontier AND the new node has a lower f value
					if(old.inFrontier && ((old.g+old.h) > (heuristic(neighbor.board)+data.g+1))){ 
						int[] arr = findZero(neighbor);
						old.parent = data; //update the node to have the new parent 
						old.g = data.g+1; //update to the new g
						old.h = heuristic(neighbor.board); //update to the new h
						old.zerox = arr[1]; //update x
						old.zeroy = arr[0]; //update y
						hm.replace(neighbor, old); //replace the old node in the hashmap
						minPQ.update(old); //update the priority of the old node
					} 
					//else the state has already been visited (in hash + no longer in frontier)
				}
			}
		}
		return false;
	}
	public static void main(String args[]) {
		int[][] board = new int[6][6]; //create the board
		board = initBoard(board); //initialize it with the read-in input
		if(isGoal(board)) { //check to see if it's a goal already
			System.out.println("No swaps, start state is a success! :D");
			System.exit(0);
		}
		MinPQ<Node> pq = new MinPQ<Node>();		
		Node root = new Node(board,initZeroX, initZeroY, null);
		root.h = heuristic(root.state);
		root.g = 0;
		pq.add(root);

		HashMap<State, Node> hm = new HashMap<State, Node>();
		hm.put(new State(root.state), root);
		
		if(aStar(pq,hm)) System.out.println("Success! Finished in "+(count-1)+" moves.");
		else System.out.println("Failure! :(");
	}
}