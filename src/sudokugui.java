import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class sudokugui extends JFrame implements ActionListener {

	private JPanel contentPane;
	private static JTable table;
	JButton btnSave, btnOpen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sudokugui frame = new sudokugui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public sudokugui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"0", "1", "2", "3", "4", "5", "6", "7", "8"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(1).setPreferredWidth(15);
		table.getColumnModel().getColumn(2).setPreferredWidth(15);
		table.getColumnModel().getColumn(3).setPreferredWidth(15);
		table.getColumnModel().getColumn(4).setPreferredWidth(15);
		table.getColumnModel().getColumn(5).setPreferredWidth(15);
		table.getColumnModel().getColumn(6).setPreferredWidth(15);
		table.getColumnModel().getColumn(7).setPreferredWidth(15);
		table.getColumnModel().getColumn(8).setPreferredWidth(15);
		contentPane.add(table, BorderLayout.NORTH);
		
		this.btnOpen = new JButton("Open file and solve");
		btnOpen.addActionListener(this);
		contentPane.add(btnOpen, BorderLayout.WEST);
		
		this.btnSave = new JButton("Save to file");
		btnSave.addActionListener(this);
		contentPane.add(btnSave, BorderLayout.EAST);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOpen) {
			//code for opening a file, solving the sudoku and loading it into table
			Field field = new Field();
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = chooser.showOpenDialog(getParent());
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				field.fromFile(chooser.getSelectedFile().getAbsolutePath());
				System.out.print((field));
				try {
				      solve(field, 0, 0);
				      System.out.print((field));
				      } catch (SolvedException ex) {}
			}
		 			
		} else if (e.getSource() == btnSave) {
			PrintWriter out = null;
			try {
				out = new PrintWriter("solvedSudoku.txt");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Printing Solved Sudoku to file solvedSudoku.txt");
			for (int row = 0; row < table.getRowCount(); row++) {
			    for (int col = 0; col < table.getColumnCount(); col++) {
			        //out.print(table.getColumnName(col));
			        //out.print(" ");
			        out.print(table.getValueAt(row, col));
			       
			    }
			    out.println("");
			}
			out.close();
						
		}
		
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
		    				table.getModel().setValueAt(val,i,j);
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
				  table.getModel().setValueAt(f.checkvalue(i,j),i,j);
				  if(j < 8) {solve(f,i,j+1);}
		        
				  else{solve(f,i+1,0);}
		        }
		    ;
	  }
	  
}
