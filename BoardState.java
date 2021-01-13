/********************************************************************/
/* Tyler Zender                                                     */
/* AI Assignment 1                                                  */
/* BoardState class: Handles 2d arrays as representations of a      */
/* HiQ board and returns characteristics of the board to the        */
/* 'HiQSolver' class.                                               */
/********************************************************************/

public class BoardState implements Comparable<BoardState> {

  int[][] board; // Current board of Hi-Q as a 2d int array representation.
                   // '0' indicates the spot is empty
                   // '1' indicates filled by a peg
                   // '2' indicates a wall/boundary/out of bounds
  
  int priority; // Priority, acts as an indicator of which moves are the best to take.
                // Lower = better
  
  int pegCount; // Count of pegs on the board
  
  BoardState parent; // Parent BoardState, used to 'move up' in the tree to illustrate solution path through the decision tree.
  
  
  // Constructor for boardstate. A boardstate indicates the position of all pegs on the board currently.
  public BoardState(int[][] layout, BoardState parentBoard)
  {
    this.parent = parentBoard;
    board = layout;
    this.priority = 0;
    applyPriority();
  }

  
  // Implements comparable for this object. In this case, the Boardstate with the lower priority is preferred.
  public int compareTo(BoardState target) {
    return (int) (target.getPriority() - this.priority); 
  }
  
  
  // Determines a value for a BoardState's 'priority' variable. 
  // Since we know the end state for the game is one peg in the center of the board,
  // Priority is calculated in two different methods. The first is done by summing the 
  // distance of every peg on the board by their max displacement (either in x or y) from
  // the center of the board. The other method is simply the summation of manhattan distances
  // of every peg on the board to the center. The preferred method for the current board state
  // for determining priority is decided by the number of pegs on the board.
  void applyPriority()
  {
    
    // Determines the number of pegs still on the board
    for (int xCtr = 1; xCtr <= 7; xCtr++) // Cycles through all x positions on the board
      for (int yCtr = 1; yCtr <= 7; yCtr++) // Cycles through all y positions on the board
        if (this.board[yCtr][xCtr] == 1) // Checks if the spot is filled
          this.pegCount++;
     
    
    // 
     for (int xCtr = 1; xCtr <= 7; xCtr++) // Cycles through all x positions on the board
     {
     for (int yCtr = 1; yCtr <= 7; yCtr++) // Cycles through all y positions on the board
       {
       if (this.board[yCtr][xCtr] == 1) // Checks if the spot is filled
       {
         
         // The next set of math calculations determine priority of the board by summing the 
         // distance of every peg on the board by their max displacement (either in x or y) from
         // the center of the board while keeping in mind that the board actually has an 
         // offset of 1 on every side.
         
         if (this.pegCount < 29)
         {
           if (xCtr >= 4)
           {
             if (yCtr >= 4)
               this.priority = this.priority + Math.max(Math.abs(xCtr-4), Math.abs(yCtr-4));
             else if (yCtr < 4)
               this.priority = this.priority + Math.max(Math.abs(xCtr-4), Math.abs(4-yCtr));
           }
         
           else if (xCtr < 4)
           {
             if (yCtr >= 4)
               this.priority = this.priority + Math.max(Math.abs(4-xCtr), Math.abs(yCtr-4));
             else if (yCtr < 4)
               this.priority = this.priority + Math.max(Math.abs(4-xCtr), Math.abs(4-yCtr));
           }
         } 
         
         
         // The next set of math calculations determine priority of the board by summing the 
         // distance of every peg on the board by their manhattan distance to the center of
         // the board while keeping in mind that the board actually has an 
         // offset of 1 on every side.
         else
         {
           if (xCtr >= 4)
           {
             if (yCtr >= 4)
               this.priority = this.priority + Math.abs(xCtr-4) + Math.abs(yCtr-4);
             else if (yCtr < 4)
               this.priority = this.priority + Math.abs(xCtr-4) + Math.abs(4-yCtr);
           }
           else if (xCtr < 4)
           {
             if (yCtr >= 4)
               this.priority = this.priority + Math.abs(4-xCtr) + Math.abs(yCtr-4);
             else if (yCtr < 4)
               this.priority = this.priority + Math.abs(4-xCtr) + Math.abs(4-yCtr);
           }
         }
       }
     }
   }
  }
  
  
  //Getter and Setter methods below
  //############################################
  
  public int getPriority()
  {
    return this.priority;
  }
  
  public int[][] getBoard()
  {
    return this.board;
  }
  
  public int getPegCount()
  {
    return this.pegCount;
  }
  
  public BoardState getParent()
  {
    return this.parent;
  }
  
} // End class
