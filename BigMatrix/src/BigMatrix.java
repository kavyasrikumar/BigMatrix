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
	
	public static void main (String[] args) {
		BigMatrix myMatrix = new BigMatrix();
		myMatrix.setValue(0, 0, 1);
		myMatrix.setValue(0, 1000, 4);
		myMatrix.setValue(0, 10, 6);
		
		myMatrix.getValue(0,  0);
		myMatrix.getValue(0, 1000);
		myMatrix.getValue(0,  10);
		
		myMatrix.getRowSum(0);
	}
	
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
	
	public int getHashCode(int i) {
		Integer temp = (Integer) i;
		
		return temp.hashCode();
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
		
		if(value == 0 && rowMap.get(row) != null && colMap.get(col) != null) {
			
			int rowInd = -1;
			int colInd = -1;
			
			for(int i : rowMap.get(row).keySet()) 
			{
				if(rowMap.get(row).get(i).column == col) {
					rowInd = i;
				}
			}
			for(int i : colMap.get(col).keySet()) 
			{
				if(colMap.get(col).get(i).row == row) {
					colInd = i;
				}
			}
			
			if (rowInd != -1 && colInd != -1)
			{
				rowMap.get(row).remove(rowInd);
				colMap.get(col).remove(colInd);
			}
		}
		
		if (value != 0)
		{
			Entry e = new Entry(row, col, value);
			
			HashMap<Integer, Entry> rowTemp = new HashMap<Integer, Entry>();
			HashMap<Integer, Entry> colTemp = new HashMap<Integer, Entry>();
			
			if (rowMap.get(row) != null)
			{
				rowTemp = rowMap.get(row);
			}
			
			if (colMap.get(col) != null)
			{
				colTemp = colMap.get(col);
			}
			 
			rowTemp.put(rowTemp.size(), e);
			rowMap.put(getHashCode(row), rowTemp );
			//System.out.println("Set value in rowMap --> row: " + row + " col: " + col + " value: " + value);
			
			colTemp.put(colTemp.size(), e);
			colMap.put(getHashCode(col), colTemp);
			//System.out.println("Set value in colMap --> row: " + row + " col: " + col + " value: " + value);
		}
	}
	
	public int getValue(int row, int col)
	{
		if (rowMap.get(row) != null)
		{
			HashMap<Integer, Entry> rowSubMap = rowMap.get(row);
		
			for (Entry e : rowSubMap.values())
			{
				if(e.column == col && e.row == row)
				{
					//System.out.println(e.value);
					return e.value;
				}
			}
		}
		
		return 0;		
	}
	
	public List<Integer> getNonEmptyRows()
	{
		// Create an empty list of integers to store the row values
		List<Integer> rowVals = new ArrayList<Integer>();
		
		// For each key in the row hash table
		for(int row: rowMap.keySet()) 
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
		
		HashMap<Integer, Entry> colSubMap = colMap.get(col);
		
		// For each key in the column hash table
		for(int c: colSubMap.keySet()) 
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
		for(int col: colMap.keySet()) 
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

		HashMap<Integer, Entry> rowSubMap = rowMap.get(row);
		
		// For each key in the column hash table
		for(int r: rowSubMap.keySet()) 
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
		
		if (rowMap.get(row) != null)
		{
			HashMap<Integer, Entry> temp = rowMap.get(row);
			
			for (int i : temp.keySet()) {
				sum += temp.get(i).value;
			}
		}

		//System.out.print(sum);
		return sum;
	}
	
	public int getColSum(int col)
	{
		// Create an integer sum
		int sum = 0;
		
		if(colMap.get(col) != null)
		{
			HashMap<Integer, Entry> temp = colMap.get(col);
			
			for (int i : temp.keySet()) {
				sum += temp.get(i).value;
			}
		}

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
		
		for(HashMap<Integer, Entry> map : rowMap.values())
		{
			for(Entry e : map.values())
			{
				result.setValue(e.row, e.column, ((e.value) * constant));
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
