# HiQSolver

Practically all university computer science students are conditioned to expect
some obscure toy or game to act as a front for some computer science concept 
hidden beneath the surface. The peg game of Hi-Q is yet another game that presents 
core concepts from computer science and AI to those pundits of the fields. This
project of a Hi-Q solver allows for the exploration of applying concepts
from the discipline of artifical intelligence. 

![Solitaire_01](https://user-images.githubusercontent.com/77171947/104413765-edbe4300-553c-11eb-9cd4-67174284833f.jpg)

Figure 1: Starting board for the peg game of Hi-Q

With with pegs in the shape of a '+' and an empty space in the center of the board,
the goal of hi-q is to jump pegs over eachother and remove the peg that was jumped
over. This is done until only one peg remains in the center of the board.

To program an agent to solve the game, a variety of problem-solving techniques are
utilized. At the most basic level, we first acknowledge that this game may be
expressed as one large decision tree expanding out from the starting board. At the
start, 4 moves may be made by moving a pin up, down, left, or right to the empty
center position. Afterwards, 7 additional possible moves may be made. In this way,
a seemingly ever-expanding decision tree is imaginable. Though with infinite time
this is solvable by searching for frontier nodes in the tree which have a peg in
the center, this is not a realistic approach.

To eliminate subtrees which will not eventually lead to our desired end condition,
we can use simple heuristics to guide our agent to ideal movements. Since we 
know our desired condition has a single peg ending in the center, we can utilize
a sum the manhattan distance for each peg on the board to the center to rank 
boards with lower manhattan distance sums higher than boards with higher distance 
sums. This significantly speeds up solving the puzzle.

Next, we can implemnent simple tree searching techniques to reach an end state quicker.
This program uses a combination of depth-first and bredth-first search to solve
the game quicker. Since the program would quickly run out of memory of breadth-first
search was utlilized from the first move to expand the tree, depth-first search is used
early on. Once the number of pegs on the board is reduced below 15, breadth-first
search is used. This allows for a proper balance between memory usage and 
speed of the algorithm.

![HiQSolver_End](https://user-images.githubusercontent.com/77171947/104414117-af755380-553d-11eb-9504-c484b0404153.JPG)

Figure 2: Console print-out of the current board state

The board is represented as a print-out to the console with '1's representing pegs,
'2's indicating walls/out-of-bounds, and '0's representing empty spaces. After
each move, the board is updated and printed out to the console. This occurs until
depth-first search is used and the tree is expanded at every frontier node. Once
a solution is found, this solution is indicated in the console and the solution
is printed out backwards.

![HiQSolver_Solution](https://user-images.githubusercontent.com/77171947/104414118-b00dea00-553d-11eb-8842-57f5c989413a.JPG)

Figure 3: Console indication of a solution being found after expading the tree with DFS

![HiQSolver_Start](https://user-images.githubusercontent.com/77171947/104414119-b00dea00-553d-11eb-9fcc-0966798d4b12.JPG)

Figure 4: Console indication of steps taken in backwards order to solve the game

This program was created as an assignment for an artificial intelligence elective 
for my degree in computer engineering and computer science minor.

