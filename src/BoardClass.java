
//***********************************************************************************************************************
//***********************************************************************************************************************

//Class:	 BoardClass
//Description:	Defines BoardClass objects to be used in the 8, 15, 24, or 35 puzzle.
//Author:       Garrett Boucher
//Date:         3/4/15
public class BoardClass {

    public int level = 0; //the board's level in the breadth first search
    public int[][] boardArray;//the array representing the board's composition
    public BoardClass parentBoard = null;//the board's parent node
    public BoardClass childBoard = null;//the board's child node
//***********************************************************************************************************************
//***********************************************************************************************************************

    //*******************************************************************************************************************
    //Method:		BoardClass
    //Description:	Constructor which sets the size of the boardArray
    //Parameters:	size        -the dimension for the square 2 dimensional array representing a board
    //Returns:		nothing
    //Throws:		nothing
    //Calls:		nothing
    public BoardClass(int size) {
        boardArray = new int[size][size];
    }
    //*******************************************************************************************************************
    //Method:		BoardClass
    //Description:	Constructor which sets the size and level of the boardArray
    //Parameters:	size        -the dimension for the square 2 dimensional array representing a board
    //                  newLevel    -the level to be set of the board in the breadth first search
    //Returns:		nothing
    //Throws:		nothing
    //Calls:		nothing
    public BoardClass(int size, int newLevel) {
        boardArray = new int[size][size];
        level = newLevel;
    }
    //*******************************************************************************************************************
    //Method:		printBoard
    //Description:	Prints the boardArray of the BoardClass object calling the method
    //Parameters:	none
    //Returns:		nothing
    //Throws:		nothing
    //Calls:		nothing
    public void printBoard() {
        for (int row = 0; row < boardArray.length; row++) {
            for (int column = 0; column < boardArray[row].length; column++) {
                if (boardArray[row][column] == 0) {
                    System.out.printf("%2c" + " ", (char) 32);
                } else {
                    System.out.printf("%2d" + " ", boardArray[row][column]);
                }
            }
            System.out.println("");
        }
    }
    //*******************************************************************************************************************
    //Method:		setBoardArray
    //Description:	Called within a nested for loop, sets the boardArray of that index equal to a parameter value
    //Parameters:	row     -the row of the boardArray
    //                  column  -the column of the boardArray
    //                  value   -the value to be set to that particular location in the array
    //Returns:		nothing
    //Throws:		nothing
    //Calls:		nothing
    public void setBoardArray(int row, int column, int value) {
        boardArray[row][column] = value;
    }
    //*******************************************************************************************************************
    //Method:		getBoardArray
    //Description:	Called within a nested for loop, returns the value of a particular index in the boardArray.
    //Parameters:	row     -the row of the boardArray
    //                  column  -the column of the boardArray
    //Returns:		boardArray[row][column]
    //Throws:		nothing
    //Calls:		nothing
    public int getBoardArray(int row, int column) {
        return boardArray[row][column];
    }
    //*******************************************************************************************************************
    //Method:		getLevel
    //Description:	Returns the level of the board 
    //Parameters:	none
    //Returns:		level
    //Throws:		nothing
    //Calls:		nothing
    public int getLevel() {
        return level;
    }
    //*******************************************************************************************************************
    //Method:		setLevel
    //Description:	Sets the level of the board equal to the parameter
    //Parameters:	level   -the level to be set
    //Returns:		nothing
    //Throws:		nothing
    //Calls:		nothing
    public void setLevel(int level) {
        this.level = level;
    }
    //*******************************************************************************************************************
    //Method:		getParentBoard
    //Description:	Returns the parentBoard
    //Parameters:	none
    //Returns:		parentBoard
    //Throws:		nothing
    //Calls:		nothing
    public BoardClass getParentBoard() {
        return parentBoard;
    }
    //*******************************************************************************************************************
    //Method:		setParentBoard
    //Description:	Sets the parentBoard equal to the parameter board
    //Parameters:	parentBoard     -the board to be set as the parentBoard
    //Returns:		nothing
    //Throws:		nothing
    //Calls:		nothing
    public void setParentBoard(BoardClass parentBoard) {
        this.parentBoard = parentBoard;
    }
    //*******************************************************************************************************************
    //Method:		getChildBoard
    //Description:	Returns the childBoard
    //Parameters:	none
    //Returns:		childBoard
    //Throws:		nothing
    //Calls:		nothing
    public BoardClass getChildBoard() {
        return childBoard;
    }
    //*******************************************************************************************************************
    //Method:		setChildBoard
    //Description:	Sets the childBoard equal to the parameter board
    //Parameters:	childBoard      -the board to be set as the childBoard
    //Returns:		nothing
    //Throws:		nothing
    //Calls:		nothing
    public void setChildBoard(BoardClass childBoard) {
        this.childBoard = childBoard;
    }
    //*******************************************************************************************************************
}//end of class
//***********************************************************************************************************************
//***********************************************************************************************************************