//Program:	Puzzle_8.java (named Boucher2.java)
//Course:	COSC470
//Description:	Program for solving the 8, 15, 24, or 35 puzzle using (only) breadth first search.  
//Author:	Garrett Boucher 
//Revised:	3/4/15
//Language:	Java
//IDE:		NetBeans 8.0.2

//**********************************************************************************************************************
//**********************************************************************************************************************

//Class:	 Puzzle_8 (named Boucher2)
//Description:	Solves the 8, 15, 24, or 35 puzzle of the user's choice using breadth first search, and outputs each 
//                  move from the starting board to the goal board

import java.util.LinkedList;
import java.util.Queue;

public class Puzzle_8 {
    static int[][] goalBoard8 = {//hard-coded goal boards for each puzzle size
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 0}
    };
    static int[][] goalBoard15 = {
        {1, 2, 3, 4},
        {5, 6, 7, 8},
        {9, 10, 11, 12},
        {13, 14, 15, 0}
    };
    static int[][] goalBoard24 = {
        {1, 2, 3, 4, 5},
        {6, 7, 8, 9, 10},
        {11, 12, 13, 14, 15},
        {16, 17, 18, 19, 20},
        {21, 22, 23, 24, 0}
    };
    static int[][] goalBoard35 = {
        {1, 2, 3, 4, 5, 6},
        {7, 8, 9, 10, 11, 12},
        {13, 14, 15, 16, 17, 18},
        {19, 20, 21, 22, 23, 24},
        {25, 26, 27, 28, 29, 30},
        {31, 32, 33, 34, 35, 0}
    };
    static int size;//the dimension for the square 2 dimensional array representing a board
    static BoardClass currentBoard;
    static BoardClass goalBoard;//the board the program is trying to get to
    static KeyboardInputClass keyboardInput = new KeyboardInputClass();
    static Queue<BoardClass> open = new LinkedList<>();//the queue of nodes (boards) not already evaluated
    static Queue<BoardClass> closed = new LinkedList<>();//the queue of nodes (boards) already evaluated
    static int openNumberOfBoards;//number of boards in the open queue
    static int closedNumberOfBoards;//number of boards in the closed queue
    static int numberOfBoardsConsidered = 0;
//***********************************************************************************************************************
//***********************************************************************************************************************

    //*******************************************************************************************************************
    //Method:		main
    //Description:	Prompts the user to choose a puzzle size, then to choose the number of shuffles from the goal 
    //                          board or to input a board of the user's choosing.  Loads the correct goal board for the 
    //                          user-defined/chosen puzzle. Calls a method to execute breadth first search on the board.
    //Parameters:	none
    //Returns:		nothing
    //Throws:		nothing
    //Calls:		loadGoalBoard, userInputBoard, shuffleBoard, breadthFirstSearch
    public static void main(String[] args) {

        System.out.println("This program solves the 8, 15, 24, or 35 puzzle using Breadth First Search only.");
        System.out.println("This program was written by: Garrett Boucher");
        System.out.println("");

        int userInput = -1;
        while (userInput != 0) {

            userInput = keyboardInput.getInteger(true, size, 0, 35, "Specify the puzzle size (8, 15, 24, or 35; 0 to "
                    + "exit):");
            size = (int) Math.sqrt(userInput + 1);//size is the square root of (userInput +1)

            currentBoard = new BoardClass(size);
            goalBoard = new BoardClass(size);
            
            if (userInput == 0) {
                System.exit(0);
            } else if (userInput == 8) {
                loadGoalBoard(goalBoard8);
            } else if (userInput == 15) {
                loadGoalBoard(goalBoard15);
            } else if (userInput == 24) {
                loadGoalBoard(goalBoard24);
            } else if (userInput == 35) {
                loadGoalBoard(goalBoard35);
            } else {
                System.out.println("You did not input a valid puzzle size. ");
                System.exit(0);
            }

            loadBoard(currentBoard, goalBoard);
            System.out.println("Goal Board:");
            goalBoard.printBoard();

            userInput = keyboardInput.getInteger(false, -1, 0, 0, "Number of shuffle moves desired? (press ENTER "
                    + "alone to specify starting board)");
            if (userInput == -1) {
                userInputBoard();
                System.out.println("Here is the user-defined board (Start state): ");
                currentBoard.printBoard();
            } else {
                shuffleBoard(userInput);
                System.out.println("Shuffled Board (Start state):");
                currentBoard.printBoard();
            }

            System.out.println("");
            breadthFirstSearch(currentBoard);
            System.out.println("");

        }//end of while

    }//end of main method
    //*******************************************************************************************************************
    //Method:		loadGoalBoard
    //Description:	Sets the goalBoard BoardClass object's array equal to the appropriate goalBoard array. 
    //Parameters:	board       -the array to which the goalBoard BoardClass object's array is to be set
    //Returns:		nothing
    //Throws:		nothing
    //Calls:		nothing
    public static void loadGoalBoard(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                goalBoard.setBoardArray(row, column, board[row][column]);
            }
        }
    }
    //*******************************************************************************************************************
    //Method:		loadBoard
    //Description:	Sets a BoardClass object's array equal to a second BoardClass object's array. Read: load board1's
    //                          array with board2's array.
    //Parameters:	board1       -the board who's array is to be changed
    //                  board2      -the board who's array is to be copied
    //Returns:		nothing
    //Throws:		nothing
    //Calls:		nothing
    public static void loadBoard(BoardClass board1, BoardClass board2) {

        for (int row = 0; row < board2.boardArray.length; row++) {
            for (int column = 0; column < board2.boardArray[row].length; column++) {
                board1.setBoardArray(row, column, board2.getBoardArray(row, column));
            }
        }
    }
    //*******************************************************************************************************************
    //Method:		isGoalBoard
    //Description:	Determines whether a board has the same array as the goal board or not.
    //Parameters:	board       -the board to be compared to the goalBoard
    //Returns:		boolean
    //Throws:		nothing
    //Calls:		nothing
    public static boolean isGoalBoard(BoardClass board) {
        for (int row = 0; row < board.boardArray.length; row++) {
            for (int column = 0; column < board.boardArray[row].length; column++) {
                if (board.getBoardArray(row, column) != goalBoard.getBoardArray(row, column)) {
                    return false;
                }
            }
        }
        return true;
    }
    //*******************************************************************************************************************
    //Method:		isSameBoard
    //Description:	Determines whether one board has the same array as another board or not.
    //Parameters:	board1       -the board to be compared to the second board
    //                  board2       -the board to be compared with the first board
    //Returns:		boolean
    //Throws:		nothing
    //Calls:		nothing
    public static boolean isSameBoard(BoardClass board1, BoardClass board2) {
        for (int row = 0; row < board2.boardArray.length; row++) {
            for (int column = 0; column < board2.boardArray[row].length; column++) {
                if (board1.getBoardArray(row, column) != board2.getBoardArray(row, column)) {
                    return false;
                }
            }
        }
        return true;
    }
    //*******************************************************************************************************************
    //Method:		userInputBoard
    //Description:	Prompts the user with instructions to manually enter a board, and sets the current board's array
    //                          with the user-entered board.
    //Parameters:	none
    //Returns:		nothing
    //Throws:		nothing
    //Calls:		nothing
    public static void userInputBoard() {
        System.out.println("Enter the board in order from left to right, top to bottom.  Enter a '0' for the blank "
                + "space. Press ENTER after each number.");
        for (int row = 0; row < currentBoard.boardArray.length; row++) {
            for (int column = 0; column < currentBoard.boardArray.length; column++) {
                currentBoard.setBoardArray(row, column, keyboardInput.getInteger(false, -1, 0, 0, "Enter next value:"));
            }
        }
    }
    //*******************************************************************************************************************
    //Method:		shuffleBoard
    //Description:	Randomly shuffles the current board from the goal state a number of times as specified by the 
    //                          user.  The board cannot be shuffled back into the goal state.
    //Parameters:	desiredNumberOfShuffles     -the number of shuffles the user specifies
    //Returns:		boolean
    //Throws:		nothing
    //Calls:		canMoveBlankLeft, canMoveBlankUp, canMoveBlankRight, canMoveBlankDown, moveBlankLeft, 
    //                          moveBlankUp, moveBlankRight, moveBlankDown
    public static void shuffleBoard(int desiredNumberOfShuffles) {
        int shufflesPerformed = 0;

        while (shufflesPerformed < desiredNumberOfShuffles) {
            int random = (int) (Math.random() * 4) + 1;

            switch (random) {
                case 1:
                    if (canMoveBlankLeft(currentBoard)) {
                        moveBlankLeft(currentBoard);
                        if (!isGoalBoard(currentBoard)) {//check to make sure it does not shuffle into goalBoard
                            shufflesPerformed++;
                        } else {
                            moveBlankRight(currentBoard);
                        }
                        break;
                    }
                case 2:
                    if (canMoveBlankUp(currentBoard)) {
                        moveBlankUp(currentBoard);
                        if (!isGoalBoard(currentBoard)) {//check to make sure it does not shuffle into goalBoard
                            shufflesPerformed++;
                        } else {
                            moveBlankDown(currentBoard);
                        }
                        break;
                    }
                case 3:
                    if (canMoveBlankRight(currentBoard)) {
                        moveBlankRight(currentBoard);
                        if (!isGoalBoard(currentBoard)) {//check to make sure it does not shuffle into goalBoard
                            shufflesPerformed++;
                        } else {
                            moveBlankLeft(currentBoard);
                        }
                        break;
                    }
                case 4:
                    if (canMoveBlankDown(currentBoard)) {
                        moveBlankDown(currentBoard);
                        if (!isGoalBoard(currentBoard)) {//check to make sure it does not shuffle into goalBoard
                            shufflesPerformed++;
                        } else {
                            moveBlankUp(currentBoard);
                        }
                        break;
                    }
            }//end of switch
        }//end of while
    }
    //*******************************************************************************************************************
    //Method:		canMoveBlankLeft
    //Description:	Determines whether the parameter board's blank can move left and still be within the bounds of 
    //                      the array.
    //Parameters:	board     -the board on which the check is being performed
    //Returns:		boolean
    //Throws:		nothing
    //Calls:		nothing
    public static boolean canMoveBlankLeft(BoardClass board) {
        for (int row = 0; row < board.boardArray.length; row++) {
            for (int column = 0; column < board.boardArray.length; column++) {
                if (board.getBoardArray(row, column) == 0 && column - 1 >= 0) {
                    return true;//returns true if it is possible to make this move 
                }
            }
        }
        return false;
    }
    //*******************************************************************************************************************
    //Method:		canMoveBlankUp
    //Description:	Determines whether the parameter board's blank can move up and still be within the bounds of the 
    //                      array.
    //Parameters:	board     -the board on which the check is being performed
    //Returns:		boolean
    //Throws:		nothing
    //Calls:		nothing
    public static boolean canMoveBlankUp(BoardClass board) {
        for (int row = 0; row < board.boardArray.length; row++) {
            for (int column = 0; column < board.boardArray.length; column++) {
                if (board.boardArray[row][column] == 0 && row - 1 >= 0) {
                    return true;//returns true if it is possible to make this move
                }
            }
        }
        return false;
    }
    //*******************************************************************************************************************
    //Method:		canMoveBlankRight
    //Description:	Determines whether the parameter board's blank can move right and still be within the bounds of 
    //                      the array.
    //Parameters:	board     -the board on which the check is being performed
    //Returns:		boolean
    //Throws:		nothing
    //Calls:		nothing
    public static boolean canMoveBlankRight(BoardClass board) {
        for (int row = 0; row < board.boardArray.length; row++) {
            for (int column = 0; column < board.boardArray.length; column++) {
                if (board.boardArray[row][column] == 0 && column + 1 <= board.boardArray[row].length - 1) {
                    return true;//returns true if it is possible to make this move
                }
            }
        }
        return false;
    }
    //*******************************************************************************************************************
    //Method:		canMoveBlankDown
    //Description:	Determines whether the parameter board's blank can move left and still be within the bounds of 
    //                      the array.
    //Parameters:	board     -the board on which the check is being performed
    //Returns:		boolean
    //Throws:		nothing
    //Calls:		nothing
    public static boolean canMoveBlankDown(BoardClass board) {
        for (int row = 0; row < board.boardArray.length; row++) {
            for (int column = 0; column < board.boardArray.length; column++) {
                if (board.boardArray[row][column] == 0 && row + 1 <= board.boardArray.length - 1) {
                    return true;//returns true if it is possible to make this move
                }
            }
        }
        return false;
    }
    //*******************************************************************************************************************
    //Method:		moveBlankLeft
    //Description:	Moves the parameter board's blank left one column.
    //Parameters:	board     -the board who's array is being changed
    //Returns:		boolean
    //Throws:		nothing
    //Calls:		nothing
    public static boolean moveBlankLeft(BoardClass board) {
        int temp;
        for (int row = 0; row < board.boardArray.length; row++) {
            for (int column = 0; column < board.boardArray.length; column++) {
                if (board.boardArray[row][column] == 0 && column - 1 >= 0) {
                    temp = board.boardArray[row][column - 1];
                    board.boardArray[row][column - 1] = 0;
                    board.boardArray[row][column] = temp;
                    return true;//returns true if it is possible to make this move (and the move has been made)
                }
            }
        }
        return false;
    }
    //*******************************************************************************************************************
    //Method:		moveBlankUp
    //Description:	Moves the parameter board's blank up one row.
    //Parameters:	board     -the board who's array is being changed
    //Returns:		boolean
    //Throws:		nothing
    //Calls:		nothing
    public static boolean moveBlankUp(BoardClass board) {
        int temp;
        for (int row = 0; row < board.boardArray.length; row++) {
            for (int column = 0; column < board.boardArray.length; column++) {
                if (board.boardArray[row][column] == 0 && row - 1 >= 0) {
                    temp = board.boardArray[row - 1][column];
                    board.boardArray[row - 1][column] = 0;
                    board.boardArray[row][column] = temp;
                    return true;//returns true if it is possible to make this move (and the move has been made)
                }
            }
        }
        return false;
    }
    //*******************************************************************************************************************
    //Method:		moveBlankRight
    //Description:	Moves the parameter board's blank right one column.
    //Parameters:	board     -the board who's array is being changed
    //Returns:		boolean
    //Throws:		nothing
    //Calls:		nothing
    public static boolean moveBlankRight(BoardClass board) {
        int temp;
        for (int row = 0; row < board.boardArray.length; row++) {
            for (int column = 0; column < board.boardArray.length; column++) {
                if (board.boardArray[row][column] == 0 && column + 1 <= board.boardArray[row].length - 1) {
                    temp = board.boardArray[row][column + 1];
                    board.boardArray[row][column + 1] = 0;
                    board.boardArray[row][column] = temp;
                    return true;//returns true if it is possible to make this move (and the move has been made)
                }
            }
        }
        return false;
    }
    //*******************************************************************************************************************
    //Method:		moveBlankDown
    //Description:	Moves the parameter board's blank down one column.
    //Parameters:	board     -the board who's array is being changed
    //Returns:		boolean
    //Throws:		nothing
    //Calls:		nothing
    public static boolean moveBlankDown(BoardClass board) {
        int temp;
        for (int row = 0; row < board.boardArray.length; row++) {
            for (int column = 0; column < board.boardArray.length; column++) {
                if (board.boardArray[row][column] == 0 && row + 1 <= board.boardArray.length - 1) {
                    temp = board.boardArray[row + 1][column];
                    board.boardArray[row + 1][column] = 0;
                    board.boardArray[row][column] = temp;
                    return true;//returns true if it is possible to make this move (and the move has been made)
                }
            }
        }
        return false;
    }
    //*******************************************************************************************************************
    //Method:		breadthFirstSearch
    //Description:	Conducts a breadth first search with the parameter node as the root to find the goal board. 
    //                          The process goes as follows: Enqueues the root. While the open queue is not empty, checks
    //                          if the root has a child with the blank moved to any possible direction.  If so, and this 
    //                          child board has not already been evaluated, checks if the child board is the goal board
    //                          and breaks from the while loop if so. Removes front of open queue and adds it to the 
    //                          closed queue otherwise. Outside of while loops, clears both queues so they will be empty
    //                          for a new puzzle.
    //Parameters:	root     -the shuffled starting board from which the BFS begins
    //Returns:		nothing
    //Throws:		nothing
    //Calls:		canMoveBlankLeft, canMoveBlankUp, canMoveBlankRight, canMoveBlankDown, moveBlankLeft, 
    //                          moveBlankUp, moveBlankRight, moveBlankDown, loadBoard, solutionFound
    public static void breadthFirstSearch(BoardClass root) {

        System.out.println("Working...");
        System.out.println("");

        open.offer(root);

        while (!open.isEmpty()) {

            if (canMoveBlankLeft(open.peek())) {

                BoardClass childBoard = new BoardClass(size, open.peek().getLevel() + 1);
                loadBoard(childBoard, open.peek());
                moveBlankLeft(childBoard);
                childBoard.setParentBoard(open.peek());
               
                if (!inOpen(childBoard) && !inClosed(childBoard)) {
                    numberOfBoardsConsidered++;
                    if (isGoalBoard(childBoard)) {
                        solutionFound(childBoard);
                        break;
                    }
                    open.offer(childBoard);
                }
            }
            if (canMoveBlankUp(open.peek())) {
                BoardClass childBoard = new BoardClass(size, open.peek().getLevel() + 1);
                loadBoard(childBoard, open.peek());
                moveBlankUp(childBoard);
                childBoard.setParentBoard(open.peek());
                if (!inOpen(childBoard) && !inClosed(childBoard)) {
                    numberOfBoardsConsidered++;
                    if (isGoalBoard(childBoard)) {
                        solutionFound(childBoard);
                        break;
                    }
                    open.offer(childBoard);
                }
            }
            if (canMoveBlankRight(open.peek())) {
                BoardClass childBoard = new BoardClass(size, open.peek().getLevel() + 1);
                loadBoard(childBoard, open.peek());
                moveBlankRight(childBoard);
                childBoard.setParentBoard(open.peek());
                if (!inOpen(childBoard) && !inClosed(childBoard)) {
                    numberOfBoardsConsidered++;
                    if (isGoalBoard(childBoard)) {
                        solutionFound(childBoard);
                        break;
                    }
                    open.offer(childBoard);
                }
            }
            if (canMoveBlankDown(open.peek())) {
                BoardClass childBoard = new BoardClass(size, open.peek().getLevel() + 1);
                loadBoard(childBoard, open.peek());
                moveBlankDown(childBoard);
                childBoard.setParentBoard(open.peek());
                if (!inOpen(childBoard) && !inClosed(childBoard)) {
                    numberOfBoardsConsidered++;
                    if (isGoalBoard(childBoard)) {
                        solutionFound(childBoard);
                        break;
                    }
                    open.offer(childBoard);
                }
            }
            closed.offer(open.poll());
            openNumberOfBoards = open.size();
            closedNumberOfBoards = closed.size();
        }//end while
        open.clear();
        closed.clear();
    }
    //*******************************************************************************************************************
    //Method:		inOpen
    //Description:	Determines whether the parameter board is already in the open queue.
    //Parameters:	board     -the the board on which the check is being performed
    //Returns:		boolean
    //Throws:		nothing
    //Calls:		isSameBoard
    public static boolean inOpen(BoardClass board) {
        for (BoardClass enqueuedBoard : open) {
            for (int row = 0; row < board.boardArray.length; row++) {
                for (int column = 0; column < board.boardArray[row].length; column++) {
                    if (isSameBoard(board, enqueuedBoard)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //*******************************************************************************************************************
    //Method:		inClosed
    //Description:	Determines whether the parameter board is already in the closed queue.
    //Parameters:	board     -the the board on which the check is being performed
    //Returns:		boolean
    //Throws:		nothing
    //Calls:		isSameBoard
    public static boolean inClosed(BoardClass board) {
        for (BoardClass enqueuedBoard : closed) {
            for (int row = 0; row < board.boardArray.length; row++) {
                for (int column = 0; column < board.boardArray[row].length; column++) {
                    if (isSameBoard(board, enqueuedBoard)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }
    //*******************************************************************************************************************
    //Method:		solutionFound
    //Description:	Handles the process for when the goalBoard state is found.  Prints the solution path from 
    //                          shuffled board to goal board.  Iterates through the parent nodes and sets the correct 
    //                          child relationships.  This is done in this method, when the solution path is already 
    //                          found instead of the breadthFirstSearch method because of the one to many relationship 
    //                          of parent to children.
    //Parameters:	board     -the the board on which the process is being performed
    //Returns:		nothing
    //Throws:		nothing
    //Calls:		nothing
    public static void solutionFound(BoardClass board) {

        int solutionLevel = board.getLevel();
        System.out.println("Solution found!");
        System.out.println("");
        while (board.getParentBoard() != null) {
            board.getParentBoard().setChildBoard(board);//sets child relationship according to parent relationship
            board = board.getParentBoard();
        }

        board.printBoard();
        System.out.println("");
        while (board.getChildBoard() != null) {
            board.getChildBoard().printBoard();
            System.out.println("");
            board = board.getChildBoard();
        }
        System.out.println("Solution was found at level: " + solutionLevel + " (number of moves)");
        System.out.println("Number of boards considered: " + numberOfBoardsConsidered);
        System.out.println("Number of nodes left in OPEN: " + openNumberOfBoards);
        numberOfBoardsConsidered = 0;
        System.out.println("***************************************");
    }
    //*******************************************************************************************************************
}//end of class
//***********************************************************************************************************************
//***********************************************************************************************************************