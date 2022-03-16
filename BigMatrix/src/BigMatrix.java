/* Kavya Srikumar
 * Last Edited: 3/15/22
 * Ms. Kankelborg 
 * 
 * BigMatrix Project
 * 
 * Project Summary: Creating a big matrix that  represents a 2 dimensional array of 2 billion by 2 billion 
 * 					using HashMaps.The matrix should have the operations: add, remove, lookup, sum, and 
 * 					other arithmetic functions. The big matrix should also be able to be multiplied and added 
 * 					to another matrix, and the result be consolidated in a new matrix.
 */

import java.util.*;
import java.util.HashMap;

public class BigMatrix 
{
	public class Entry 
	{
		int row;
		int column;
		int value;
		
		public Entry(int rowNum, int colNum, int valNum) {
			row = rowNum;
			column = colNum;
			value = valNum;
		}
	}
	
	// Use two different hash maps, one's key based on row, the other on column. 
	// This will allow for independent row and column operations. The value stored in the hash tables are row, column, value.
	private HashMap<Integer, HashMap<Integer,Entry>> rowMap;
	private HashMap<Integer, HashMap<Integer,Entry>> colMap;
	
	public BigMatrix()
	{
		rowMap = new HashMap<Integer, HashMap<Integer,Entry>>();
		colMap = new HashMap<Integer, HashMap<Integer,Entry>>();

	}	
	
	public void setValue(int row, int col, int value)
	{
		// If the value is not zero:
		if(value != 0) 
		{
			Integer rowInt = (Integer) row;
			Integer colInt = (Integer) col;
			
			Entry entry = new Entry(row, col, value);
			HashMap<Integer, Entry> temp = new HashMap<Integer,Entry>();
			temp.put(row, entry);
			
			rowMap.put(rowInt.hashCode(), temp);
			colMap.put(colInt.hashCode(), temp);
		}
	}
	
	public int getValue(int row, int col)
	{
		// Check the number of entries in both the row hash map and column hash map, 
		// and choose the one with the smaller number of entries (for efficiency)
		if(rowMap.size() < colMap.size()) 
		{
			Integer rowInt = (Integer) row;
			Entry temp = rowMap.get(rowInt.hashCode());
			
			if (temp.column == col && temp.row == row)
			{
				return temp.value;
			}
		} 
		else 
		{
			Integer colInt = (Integer) col;
			Entry temp = colMap.get(colInt.hashCode());
			
			if (temp.column == col && temp.row == row)
			{
				return temp.value;
			}
		}
		return 0;		
	}
	
	public List<Integer> getNonEmptyRows()
	{
		// Create an empty list of integers to store the row values
		List<Integer> rowVals = new ArrayList<Integer>();
		// For each key in the row hash table
			// If the key is not already in the list, add it to list
		// Return the list
		
		for(int row: rowMap.keySet()) {
			if (rowVals.contains(row) == false) 
			{
				rowVals.add(row);
			}
		}
		
		return rowVals;
	}
	
	public List<Integer> getNonEmptyRowsInColumn(int col)
	{
		// Create an empty list of integers to store the row values 
		List<Integer> rowVals = new ArrayList<Integer>();
		
		// Hash the column number 
		Integer colInt = (Integer) col;
		//int colHash = colInt.hashCode();
		
		// For each entry in the correlating hash map
			// If the entry belongs to the column 
					// Add its correlating row number to the list if it is not already in it 
			// Return the list
		
		for(Entry e : rowMap.values()) 
		{
			if(e.column == col && !rowVals.contains(e.column)) {
				rowVals.add(e.column);
			}
		}
		return rowVals;
	}
	
	public List<Integer> getNonEmptyCols()
	{
		// Create an empty list of integers to store the column values
		List<Integer> colVals = new ArrayList<Integer>();
		
		// For each key in the column hash table
			// If the key is not already in the list, add it to list
		// Return the list		
		
		for(int col: colMap.keySet()) {
			if (colVals.contains(col) == false) 
			{
				colVals.add(col);
			}
		}
		
		return colVals;
	}
	
	public List<Integer> getNonEmptyColsInRow(int row)
	{
		// Create an empty list of integers to store the column values 
		List<Integer> colVals = new ArrayList<Integer>();
		
		// Hash the row number 
			// For each entry in the correlating hash map
				// If the entry belongs to the row 
					// Add its correlating column number to the list if it is not already in it 
		// Return the list
		
		for(Entry e : colMap.values()) 
		{
			if(e.row == row && !colVals.contains(e.column)) {
				colVals.add(e.column);
			}
		}
		return colVals;
	}
	
	public int getRowSum(int row)
	{
		// Create an integer sum
		int sum = 0;
		
		// Hash the row number
		Integer rowInt = (Integer) row;
		int rowHash = rowInt.hashCode();
			// For each entry in the correlating hash map
				// If the entry belongs to the row
					// Add the value of that entry to the sum
	// Return the sum
		throw new UnsupportedOperationException();
	}
	
	public int getColSum(int col)
	{
		//Create an integer sum
		//Hash the column number
			//For each entry in the correlating hash map
				//If the entry belongs to the column
					//Add the value of that entry to the sum
	//Return the sum
		throw new UnsupportedOperationException();
	}
	
	public int getTotalSum()
	{
		//Create an integer sum
		//For each entry in the row hash map
			//Add the corresponding value to sum
//Return the sum
		throw new UnsupportedOperationException();
	}
	
	public BigMatrix multiplyByConstant(int constant)
	{
		//		Create a new big matrix
		//For each entry in the row hash map
		//Call set value on the new big matrix with the corresponding row and column, and multiply the value by the constant if the result of multiplication is non-zero
//Return the new big matrix
		throw new UnsupportedOperationException();
	}
	
	public BigMatrix addMatrix(BigMatrix other)
	{
		//Create a new big matrix
		//Use the method get all non empty rows in a column on the current matrix
		//For each of the non empty rows 
			//If the value at the same (row, column) pair in other is non-zero
				//Sum the values and set the value of the entry in the new matrix accordingly
				
		//Use the method get all non empty rows in a column on the other matrix
			//If the value at the same (row, column) pair in the current matrix is zero
				//Set the value of the entry in the new matrix accordingly
//Return the new matrix
		throw new UnsupportedOperationException();
	}
}
