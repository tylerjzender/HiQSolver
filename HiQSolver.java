/********************************************************************/
/* Tyler Zender                                                     */
/* AI Assignment 1                                                  */
/* HiQ_Solver class: Searches for solutions for the HiQ puzzle      */
/* primarily by using 2d arrays as representations of the board,    */
/* and then using BoardState objects to make and search             */
/* determinations to find a path to the end-state of the game.      */
/********************************************************************/

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Stack;

public class HiQSolver
{
	
	static Stack<BoardState> boardStack; // Stack of board states to traverse/find next board states of
	
	static HashSet<BoardState> seen; // HashSet of previously seen boards
	
	static long duration; // Measures run time
	
	public static void main(String[] args)
	{
	  duration = System.currentTimeMillis();
		System.out.println("Started search!");
		
		// Indicates the starting board state of the HiQ game.
		// '0' indicates the spot is empty
		// '1' indicates filled by a peg
		// '2' indicates a wall/boundary/out of bounds
		int[][] startingBoard = new int[][]{
		  {2, 2, 2, 2, 2, 2, 2, 2, 2},
      {2, 2, 2, 1, 1, 1, 2, 2, 2},
      {2, 2, 2, 1, 1, 1, 2, 2, 2},
      {2, 1, 1, 1, 1, 1, 1, 1, 2},
      {2, 1, 1, 1, 0, 1, 1, 1, 2},
      {2, 1, 1, 1, 1, 1, 1, 1, 2},
      {2, 2, 2, 1, 1, 1, 2, 2, 2},
      {2, 2, 2, 1, 1, 1, 2, 2, 2},
      {2, 2, 2, 2, 2, 2, 2, 2, 2}
    }; 
		  
    // Indicates the goal state/end state for the game
		int[][] goalBoard = new int[][]{
		  {2, 2, 2, 2, 2, 2, 2, 2, 2},
		  {2, 2, 2, 0, 0, 0, 2, 2, 2},
			{2, 2, 2, 0, 0, 0, 2, 2, 2},
			{2, 0, 0, 0, 0, 0, 0, 0, 2},
			{2, 0, 0, 0, 1, 0, 0, 0, 2},
			{2, 0, 0, 0, 0, 0, 0, 0, 2},
			{2, 2, 2, 0, 0, 0, 2, 2, 2},
			{2, 2, 2, 0, 0, 0, 2, 2, 2},
			{2, 2, 2, 2, 2, 2, 2, 2, 2}
		};
		  
		boardStack = new Stack<BoardState>(); // Stack of boards, done with int[][], used to evaluate moves of pegs currently on the board/the next board state.
		                                   // int arrays on the top of the stack are lower in the decision tree of the Hi-Q game/have less pegs, and 
		                                   // when int arrays are 'popped' off the stack, their children sub-trees are sorted in order of which decisions
		                                   // are most likely to reach the goal state.
		
		seen = new HashSet<BoardState>();     // Records the game states in the decision tree that have already been seen to prevent repeatedly processing a state.
		
		
		System.out.println(Arrays.deepToString(startingBoard).replace("], ", "]\n").replace("[[", "[").replace("]]", "]")); // Prints the board state to console
		  
		pushArrayList(boardStack, findNext(new BoardState(startingBoard, null))); // Finds the next board states based on the starting board state and then places those next states on the stack
	
		boardStack.pop(); // Even though this algorithm could find the right steps to finding a solution from any of the 4 children states of the starting state, 
		boardStack.pop(); // this algorithm happens to work fastest on the 3rd generated board state, and since all 4 states are essentially equivalent but rotated, we 
		                  // elect to pop off the first two children in order to save time while proving the algorithm functions correctly.
		
		BoardState currentBoard = boardStack.pop(); // We pop off the next board state and set it as our current board state to determine children of this state in the tree
		  
		 
		// This while loop is the primary operator to determine a solution path to the game's end state.
		// It operates primarily by getting the list of next possible states based on the 'currentBoard', sorting them by best choice, adding them to the
		// stack, and then popping a new board state off from the stack, and repeating the process.
		// When the 'currentBoard' has less than 15 pegs, this loop switches from depth-first to breadth-first search, speeding up the process,
		// while ensuring there is enough space to perform a depth-first search.
		while(true)
		{
		  // Prints current board
		  System.out.println("Current board: ");
		  System.out.println(Arrays.deepToString(currentBoard.getBoard()).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
		    
		  // If we have already seen and investigated the current board, skip it and move on to the next. This saves computation time.
			if (seen.contains(currentBoard))
			  System.out.println("Already seen current board, continuing to next...");
			
		  else // The current board has not been seen already
		  {
			  seen.add(currentBoard);
          
        if (currentBoard.getPegCount() < 15) // Enters a depth-first search mode, but only for the sub-tree with a head based on the 'currentBoard' variable.
        {
          ArrayList<BoardState> breadth = new ArrayList<BoardState>(); // Traverses the sub-tree of the full decision-tree that starts with the 'currentBoard' variable.
          breadth.add(currentBoard);
          
          while (!breadth.isEmpty()) // Continuously generates new children board states based on all the board states currently in the 'breadth' ArrayList.
          {
            ArrayList<BoardState> newBreadth = new ArrayList<BoardState>(); // Creates a new ArrayList to store the next level of the sub-tree in.
            
            for (int breadthCtr = 0; breadthCtr < breadth.size(); breadthCtr++) // Loops through the entire 'breadth' array list and generates all children of every member in the list.
            {
              BoardState breadthExpand = breadth.get(breadthCtr);  
                
              if (Arrays.deepEquals(goalBoard, breadthExpand.getBoard())) // Determines if any of the members in the deepest array list are identical to the goal state of the game.
              {
                duration = System.currentTimeMillis() - duration; 
                System.out.println("Execution time: " + duration + " ms.");
                System.out.println("1 peg on this board! Press any key to see the solution!");
                  
                try {
                  System.in.read();
                } catch (IOException e) {
                  e.printStackTrace();
                }
                
                System.out.println(Arrays.deepToString(breadthExpand.getBoard()).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
                
                BoardState climbParents = breadthExpand; // climbParents variable is used to move upwards in the decision tree
                while(climbParents.getParent() != null)
                {
                  System.out.println("");
                  climbParents = climbParents.getParent(); // Gets the parent of the climbParents variable until it reaches the head
                  System.out.println(Arrays.deepToString(climbParents.getBoard()).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
                }
                System.exit(0);
              }
              else 
                newBreadth.addAll(findNext(breadthExpand));
            }
            breadth = newBreadth;
            System.out.println("Expaning the lower tree, remaining expansions: " + breadth.size());
          }
          
        }
        
        else // Continues doing a depth-first search since there are 15 or more pegs on the board still
        {
          ArrayList<BoardState> nextArr = findNext(currentBoard); // Generates all children of the 'currentBoard' board state
          Collections.sort(nextArr); // Sorts the children boards based on which is most likely to reach the goal state
          pushArrayList(boardStack, nextArr); // Puts the sorted children on the stack of all board states to be processed.
        } 
			}
			  
			currentBoard = boardStack.pop(); // Gets the next board state on the stack and sets it as the 'currentBoard'
			System.gc();
		}
	}

	

  // Finds the next steps based on the current board state
	private static ArrayList<BoardState> findNext(BoardState parentBoard) 
	{
	  int[][] board = parentBoard.getBoard();  
		ArrayList<BoardState> nextStatesArr = new ArrayList<BoardState>(); // Array list of next possible board states
		
		for (int xCtr = 1; xCtr <= 7; xCtr++) // Cycles through all x positions on the board
		{
		  for (int yCtr = 1; yCtr <= 7; yCtr++) // Cycles through all y positions on the board
			{
				if (board[xCtr][yCtr] == 1) // Checks if the spot is filled with a peg
				{
				    
				  // Next four 'if' statements check if there is an adjacent spot that is filled, and the
					// next spot in that same direction is empty. This determines the next possible board states.
				    
				  // Looks in the rightwards direction of the peg
					if (board[xCtr+1][yCtr] == 1 && board[xCtr+2][yCtr] == 0)  
					{
					  int[][] boardstateCopy1 = copyArr(board);
						boardstateCopy1[xCtr][yCtr] = 0;   // Empties current spot
						boardstateCopy1[xCtr+1][yCtr] = 0; // Empties adjacent spot
						boardstateCopy1[xCtr+2][yCtr] = 1; // Fills the spot that was originally empty
						  
						nextStatesArr.add(new BoardState(boardstateCopy1, parentBoard));
					}
					  
				  // Looks in the leftwards direction of the peg
					if (board[xCtr-1][yCtr] == 1 && board[xCtr-2][yCtr] == 0)  
					{
						int[][] boardstateCopy2 = copyArr(board);
						boardstateCopy2[xCtr][yCtr] = 0;   // Empties current spot
						boardstateCopy2[xCtr-1][yCtr] = 0; // Empties adjacent spot
						boardstateCopy2[xCtr-2][yCtr] = 1; // Fills the spot that was originally empty
              
						nextStatesArr.add(new BoardState(boardstateCopy2, parentBoard)); 
					} 
					  
				  // Looks in the downwards direction of the peg
					if (board[xCtr][yCtr+1] == 1 && board[xCtr][yCtr+2] == 0)  
					{
						int[][] boardstateCopy3 = copyArr(board);
						boardstateCopy3[xCtr][yCtr] = 0;   // Empties current spot
						boardstateCopy3[xCtr][yCtr+1] = 0; // Empties adjacent spot
						boardstateCopy3[xCtr][yCtr+2] = 1; // Fills the spot that was originally empty
						
						nextStatesArr.add(new BoardState(boardstateCopy3, parentBoard)); 
					}
					  
			  	// Looks in the upwards direction of the peg
					if (board[xCtr][yCtr-1] == 1 && board[xCtr][yCtr-2] == 0)  
					{
						int[][] boardstateCopy4 = copyArr(board);
						boardstateCopy4[xCtr][yCtr] = 0;   // Empties current spot
						boardstateCopy4[xCtr][yCtr-1] = 0; // Empties adjacent spot
						boardstateCopy4[xCtr][yCtr-2] = 1; // Fills the spot that was originally empty
						  
						nextStatesArr.add(new BoardState(boardstateCopy4, parentBoard)); 
					}
				}
			} 
		}
		return nextStatesArr;
	}
	
	
	// Adds an array list of BoardState objects to a stack of BoardState objects and returns the stack.
	private static Stack<BoardState> pushArrayList(Stack<BoardState> stack, ArrayList<BoardState> list)
	{
		for (int ctr = 0; ctr < list.size(); ctr++)
			stack.push(list.get(ctr));
		return stack;
	}
	
	
	// Copies a 2d array to a new 2d array variable
	private static int[][] copyArr(int[][] origArr) {
	  
    int size = origArr.length; // Gets the size of the array in the argument
    int[][] newArr = new int[size][size]; // Creates the new array to be copied into
    
    for(int origX = 0; origX < size; origX++)
      for(int origY = 0; origY < size; origY++)
        newArr[origX][origY] = origArr[origX][origY];
    
    return newArr;
  }
  
} // End class