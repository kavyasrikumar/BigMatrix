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
			HashMap<Integer, Entry> temp = new HashMap<Integer,  Entry>();
			temp.put(row, entry);
			
			rowMap.put(rowInt.hashCode(), temp);
			temp.remove(row, entry);
			
			temp.put(col, entry);
			colMap.put(colInt.hashCode(), temp);
		}
	}
	
	public int getValue(int row, int col)
	{
		
		HashMap<Integer, Entry> temp = new HashMap<Integer, Entry>();
		
		
		/*
		 * // Check the number of entries in both the row hash map and column hash map, 
		// and choose the one with the smaller number of entries (for efficiency)
		if(rowMap.size() < colMap.size()) 
		{
			Integer rowInt = (Integer) row;
			
			temp = rowMap.get(rowInt.hashCode());
		} 
		
		else 
		{ */
		
		Integer colInt = (Integer) col;
		
		if (colMap.get(colInt.hashCode()) != null) 
		{
			temp = colMap.get(colInt.hashCode());
		}
		
		for(Integer i : temp.keySet())
		{
			if (temp.get(i).column == col && temp.get(i).row == row)
			{
				return temp.get(i).value;
			}
		}
		
		return 0;		
	}
	
	public List<Integer> getNonEmptyRows()
	{
		// Create an empty list of integers to store the row values
		List<Integer> rowVals = new ArrayList<Integer>();
		
		// For each key in the row hash table
		for(Integer row: rowMap.keySet()) 
		{
			// If the key is not already in the list, add it to list
			if (rowVals.contains(row) == false) 
			{
				rowVals.add(row);
			}
		}
		
		// Return the list
		return rowVals;
	}
	
	public List<Integer> getNonEmptyRowsInColumn(int col)
	{
		
		// Create an empty list of integers to store the row values
		List<Integer> rowVals = new ArrayList<Integer>();
		
		Integer colInt = (Integer) col;		
		HashMap<Integer, Entry> colSubMap = colMap.get(colInt.hashCode());
		
		// For each key in the column hash table
		for(Integer c: colSubMap.keySet()) 
		{
			// If the key is not already in the list, add it to list
			if (rowVals.contains(colSubMap.get(c).row) == false) 
			{
				rowVals.add(colSubMap.get(c).row);
			}
		}
		
		return rowVals;
	}
	
	public List<Integer> getNonEmptyCols()
	{
		// Create an empty list of integers to store the row values
		List<Integer> colVals = new ArrayList<Integer>();
		
		// For each key in the column hash table
		for(Integer col: colMap.keySet()) 
		{
			// If the key is not already in the list, add it to list
			if (colVals.contains(col) == false) 
			{
				colVals.add(col);
			}
		}
		
		// Return the list
		return colVals;
	}
	
	public List<Integer> getNonEmptyColsInRow(int row)
	{
		// Create an empty list of integers to store the column values 
		List<Integer> colVals = new ArrayList<Integer>();
		
		Integer rowInt = (Integer) row;		
		HashMap<Integer, Entry> rowSubMap = rowMap.get(rowInt.hashCode());
		
		// For each key in the column hash table
		for(Integer r: rowSubMap.keySet()) 
		{
			// If the key is not already in the list, add it to list
			if (colVals.contains(rowSubMap.get(r).row) == false) 
			{
				colVals.add(rowSubMap.get(r).row);
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
		
		if (rowMap.get(rowInt.hashCode()) != null )
		{
			HashMap<Integer, Entry> rowIndMap = rowMap.get(rowInt.hashCode());
			
			// For each entry in the correlating hash map
			for(Entry e : rowIndMap.values())
			{
				// Add the value of that entry to the sum
				sum += e.value;
			}
		}

		// Return the sum
		return sum;
	}
	
	public int getColSum(int col)
	{
		// Create an integer sum
		int sum = 0;
		
		// Hash the row number
		Integer colInt = (Integer) col;
		
		if (colMap.get(colInt.hashCode()) != null) 
		{
			HashMap<Integer, Entry> colIndMap = colMap.get(colInt.hashCode());
			
			// For each entry in the correlating hash map
			for(Entry e : colIndMap.values())
			{
				// Add the value of that entry to the sum
				sum += e.value;
			}
		}

		// Return the sum
		return sum;
	}
	
	public int getTotalSum()
	{
		//Create an integer sum
		int sum = 0;
		
		//For each entry in the row hash map
		for(HashMap<Integer, Entry> map : rowMap.values())
		{
			for(Entry e : map.values())
			{
				sum += e.value;
			}
		}
		
		//Return the sum
		return sum;
	}
	
	public BigMatrix multiplyByConstant(int constant)
	{
		// Create a new big matrix
		BigMatrix result = new BigMatrix();
		
		if(constant == 0) 
		{
			return result;
		}
		
		// For each entry in the row hash map
		for(Integer i : rowMap.keySet())
		{
			if ( rowMap.get(i).keySet() != null )
			{
				for (Integer r : rowMap.get(i).keySet())
				{
					//Call set value on the new big matrix with the corresponding row and column, 
					// and multiply the value by the constant if the result of multiplication is non-zero
					if (rowMap.get(i).get(r).value != 0) {
						result.setValue( rowMap.get(i).get(r).row, rowMap.get(i).get(r).column, (rowMap.get(i).get(r).value * constant));
					}
					else
					{
						result.setValue( rowMap.get(i).get(r).row, rowMap.get(i).get(r).column, rowMap.get(i).get(r).value);
					}
				}
			}

		}
		
		//Return the new big matrix
		return result;
	}
	
	public BigMatrix addMatrix(BigMatrix other)
	{
		// Create a new big matrix
		BigMatrix result = new BigMatrix();
		
		// Use the method get all non empty rows in a column on the current matrix
		//List<Integer> nonEmptyRows = colMap.getAllNonEmptyRowsInColumn();
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
