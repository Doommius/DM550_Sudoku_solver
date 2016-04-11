import java.util.Scanner;

public class Sudoku {
	
  private static Scanner scanner;

public static void main(String[] args) {
	String s;
	System.out.println("please enter the file name!");
	scanner = new Scanner(System.in);
	s = scanner.nextLine();
    Field field = new Field();
    field.fromFile("C:/Users/Mark/workspace/java sudoku/src/"+(s)+".txt");
    try {
      solve(field, 0, 0);
      System.out.print((field));
      } catch (SolvedException e) {}
    }

  public static void solve(Field f, int i, int j) throws SolvedException {
	  //controls is the sudoku is done, and stops the program if it is.
	  if (i >= Field.SIZE) {
		  {throw new SolvedException();}
    }
	  else
		  //checks if a cell is empty and returns true if it is, and false if it isnt, if its false if goes to the next recursion dept, and next cell of the Sudoku.
		  if(f.isEmpty(i, j) == true){
	    		//For loop for trying values from 1 to 9.
	    		for(int val = 1; val <= 9 ; ++val) {
	    			//tries using the value val in the selected cell. and returns true, and saves the value to the cell. else tries with next value.
	    			if(f.tryValue(val,i, j) == true) {
	    				System.out.println(f);
	    				//checks if at end of row and goes to next cell in same row if true, and goes to next row. and resets to the first cell.
	    				if(j < 8) {solve(f,i,j+1);}
	    				
	    				else {solve(f,i+1,0);} 
	                       }
	                } 
	    		// if none of the values from the for loop works the programs goes to the higher up recursion level and backtracks until a new usable value is found.
	    		f.clear(i, j);
	}// 
	  	  //Goes to next cell if there is already a value in the cell. and if false going to the next row and goes to the first cell
		  else{
			  if(j < 8) {solve(f,i,j+1);}
	        
			  else{solve(f,i+1,0);}
	        }
	    ;
  }

}