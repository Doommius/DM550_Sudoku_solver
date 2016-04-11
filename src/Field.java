import java.io.*;
import java.util.*;

/**
 * Abstract Data Type for Sudoku playing field
 */
public class Field {

  public static final int SIZE = 9;

  private int model[][] ;

  public Field() {
    // make new array of size SIZExSIZE
    this.model = new int[SIZE][SIZE];
    // initialize with empty cells
    init(SIZE-1, SIZE-1);
  }

  private void init(int i, int j) {
    if (i < 0) {
      // all rows done!
    } else if (j < 0) {
      // this row done - go to next!
      init(i-1, SIZE-1);
    } else {
      this.clear(i,j);
      init(i, j-1);
    }
  }

  public void fromFile(String fileName) {
    try {
      Scanner sc = new Scanner(new File(fileName));
      fromScanner(sc, 0, 0);
    } catch (FileNotFoundException e) {
      //
    }
  }

  private void fromScanner(Scanner sc, int i, int j) {
    if (i >= SIZE) {
      // all rows done!
    } else if (j >= SIZE) {
      // this row done - go to next!
      fromScanner(sc, i+1, 0);
    } else {
      try {
        int val = Integer.parseInt(sc.next());
        this.model[i][j] = val;
      } catch (NumberFormatException e) {
        // skip this cell
      }
      fromScanner(sc, i, j+1);
    }
  }

  public String toString() {
    StringBuffer res = new StringBuffer();
    for (int i = 0; i < SIZE; i++) {
      if (i % 3 == 0) {
        res.append("+-------+-------+-------+\n");
      }
      for (int j = 0; j < SIZE; j++) {
        if (j % 3 == 0) {
          res.append("| ");
        }
        int val = this.model[i][j];
        res.append(val > 0 ? val+" " : "  ");
      }
      res.append("|\n");
    }
    res.append("+-------+-------+-------+");
    return res.toString();
  }

  /** returns false if the value val cannot be placed at
   *  row i and column j. returns true and sets the cell
   *  to val otherwise.
   */
  public boolean tryValue(int val, int i, int j) {
    if (!checkRow(val, i)) {
      return false;
    }
    if (!checkCol(val, j)) {
      return false;
    }
    if (!checkBox(val, i, j)) {
      return false;
    }
    this.model[i][j] = val;
    return true;
  }
  public int checkvalue(int i, int j){
    return this.model[i][j];

  }



  /** checks if the cell at row i and column j is empty,
   *  i.e., whether it contains 0
   */
  public boolean isEmpty(int i, int j) {
    if (this.model[i][j] == 0)
      return true;
    else
      return false;
  }

  /** sets the cell at row i and column j to be empty, i.e.,				
   *  to be 0
   */																		//done
  public void clear(int i, int j) {
    this.model[i][j] = 0;
  }

  /** checks if val is an acceptable value for the row i */					//done
  public boolean checkRow(int val, int row) {
    for( int col = 0; col < 9; col++ )
      if( model[row][col] == val )
        return false ;

    return true ;
  }

  /** checks if val is an acceptable value for the column j */				//done
  public boolean checkCol(int val, int col) {
    for( int row = 0; row < 9; row++ )
      if( model[row][col] == val )
        return false ;

    return true ;
  }

  /** checks if val is an acceptable value for the box around
   *  the cell at row i and column j
   */																		//done
  public boolean checkBox(int val, int row, int col) {
    row = (row / 3) * 3 ;
    col = (col / 3) * 3 ;

    for( int r = 0; r < 3; r++ )
      for( int c = 0; c < 3; c++ )
        if( model[row+r][col+c] == val )
          return false ;

    return true ;
  }


}
