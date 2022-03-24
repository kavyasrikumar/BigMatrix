/* Kavya Srikumar
 * Last Edited: 3/23/22
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
	// creates a way to store row, column and value together in the same object within hashmaps
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
	
	// method to convert small i int to big I integer and retrieve its hashcode
	public int getHashCode(int i) {
		Integer temp = (Integer) i;
		
		return temp.hashCode();
	}
	
	// Use two different hash maps, one's key based on row, the other on column. 
	// This will allow for independent row and column operations. The value stored in the hash tables are row, column, value.
	private HashMap<Integer, HashMap<Integer,Entry>> rowMap;
	private HashMap<Integer, HashMap<Integer,Entry>> colMap;
	
	// empty constructor that initializes rowMap and colMap
	public BigMatrix()
	{
		rowMap = new HashMap<Integer, HashMap<Integer,Entry>>();
		colMap = new HashMap<Integer, HashMap<Integer,Entry>>();
	}	
	
	// takes parameters and sets the value in the matrix accordingly
	// by editing the values in the nested hashMaps
	public void setValue(int row, int col, int value)
	{
		// if the value is 0 and is in the matrix already
		if (value == 0 && rowMap.containsKey(row) && rowMap.get(row).containsKey(col) 
				&& rowMap.get(row).get(col).value == 0)
		{
			// do nothing and return
			return;
		}
		
		// if the row value is already present (stores a non-zero value) in the matrix		
		if (rowMap.containsKey(row))
		{
			// if the value being inserted is zero
			if (value == 0)
			{
				// check to see if removal of this value will cause the row to be empty
				// if so, delete the row altogether
				if (rowMap.get(row).size() == 1)
				{
					rowMap.remove(row);
				}
				// otherwise simply remove the corresponding column and value from the nested hash map
				else
				{
					rowMap.get(row).remove(col);
				}
			}
			
			// if the value is non-zero but there is a hash map at that row index in the row map
			// add this value to the nested hash map
			else
			{
				rowMap.get(row).put(col, new Entry(row, col, value));
			}
		}
		
		// if the value is non-zero and there is not a value at that index in the matrix
		// add the value to the row map
		//
		else
		{
			HashMap<Integer, Entry> temp = new HashMap<Integer, Entry>();
			temp.put(col, new Entry(row, col, value));
			rowMap.put(row, temp);
		}
		
		// if the col value is already present (stores a non-zero value) in the matrix	
		if (colMap.containsKey(col))
		{
			// if the value being inserted is zero
			if (value == 0)
			{
				// check to see if removal of this value will cause the column to be empty
				// if so, delete the column altogether
				if (colMap.get(col).size() == 1)
				{
					colMap.remove(col);
				}
				// otherwise simply remove the corresponding row and value from the nested hash map
				else
				{
					colMap.get(col).remove(row);
				}
			} 
			
			// if the value is non-zero but there is a hash map at that column index in the col map
			// add this value to the nested hash map
			else
			{
				colMap.get(col).put(row, new Entry(row, col, value));
			}
		}
		
		// if the value is non-zero and there is not a value at that index in the matrix
		// add the value to the col map
		//
		else
		{
			HashMap<Integer, Entry> temp = new HashMap<Integer, Entry>();
			temp.put(row, new Entry(row, col, value));
			colMap.put(col, temp);
		}
	}
	
	// use the row and columns to retrieve the value at that index in the matrix
	public int getValue(int row, int col)
	{
		// check to see if the row and column store a non-zero value in the matrix
		if (rowMap.containsKey(row) && rowMap.get(row).containsKey(col))
		{	
			// retrieve the corresponding value
			return rowMap.get(row).get(col).value;
		}
		
		// return 0 if the row and column do not store a non-zero value
		return 0;		
	}
	
	// because only non-zero values are stored, every key in the row map represents a 
	// non-empty row
	public List<Integer> getNonEmptyRows()
	{
		// Create an empty list of integers to store the row values
		List<Integer> rowVals = new ArrayList<Integer>();
		
		// For each key in the row hash table
		for(int row: rowMap.keySet()) 
		{
			// add the row number to the list
			rowVals.add(row);
		}
		
		// Return the list
		return rowVals;
	}
	
	// subset of non empty rows
	// all of the keys in the nested hash map represent the column number
	public List<Integer> getNonEmptyRowsInColumn(int col)
	{
		// Create an empty list of integers to store the column values 
		List<Integer> rowVals = new ArrayList<Integer>();;
		
		if(colMap.get(col) != null)
		{		
			// store all the keys of the hash map at the specific column
			//
			rowVals = new ArrayList<Integer>(colMap.get(col).keySet());
		}
		
		return rowVals;
	}
	
	// because only non-zero values are stored, every key in the col map represents a 
	// non-empty col
	public List<Integer> getNonEmptyCols()
	{
		// Create an empty list of integers to store the col values
		List<Integer> colVals = new ArrayList<Integer>();
		
		// For each key in the column hash table
		for(int col: colMap.keySet()) 
		{
			// add the column number to the list
			colVals.add(col);
		}
		
		// Return the list
		return colVals;
	}
	
	// subset of non empty columns
	// all of the keys in the nested hash map represent the column number
	public List<Integer> getNonEmptyColsInRow(int row)
	{
		// Create an empty list of integers to store the column values 
		List<Integer> colVals = new ArrayList<Integer>();
		
		if(rowMap.get(row) != null)
		{		
			// store all the keys of the hash map at the specific column
			//
			colVals = new ArrayList<Integer>(rowMap.get(row).keySet());
		}
		return colVals;

	}
	
	// sum of all the entries in a given row
	public int getRowSum(int row)
	{
		// Create an integer sum
		int sum = 0;
		
		// for each of the entries in the row map (which only stores non-zero values)
		if (rowMap.get(row) != null)
		{
			// add the value of the entry to the sum
			for (Entry e : rowMap.get(row).values())
			{
				sum += e.value;
			}
		}

		return sum;
	}
	
	// sum of all the entries in a given column
	public int getColSum(int col)
	{
		// Create an integer sum
		int sum = 0;
		
		// for each of the entries in the col map 
		if(colMap.get(col) != null)
		{
			// add the value of the entry to the sum
			for (Entry e : colMap.get(col).values())
			{
				sum += e.value;
			}
		}
		
		return sum;
	}
	
	// sum of all values in teh matrix
	public int getTotalSum()
	{
		//Create an integer sum
		int sum = 0;
		
		// For each entry in the row hash map (which will take all the rows)
		for(HashMap<Integer, Entry> map : rowMap.values())
		{
			// add each value to the sum
			for(Entry e : map.values())
			{
				sum += e.value;
			}
		}
		
		//Return the sum
		return sum;
	}
	
	// multiply each value in the matrix by a constant
	public BigMatrix multiplyByConstant(int constant)
	{
		// Create a new big matrix
		BigMatrix result = new BigMatrix();
		
		// if the constant is 0 the matrix will be empty, so return it as is
		if(constant == 0) 
		{
			return result;
		}
		
		// otherwise, set each value in the resulting matrix to the same as the original
		// but multiplied by the specified constant
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
		
		// initialize all the values in the result to that of the current matrix
		// so that the data is accessible
		result.rowMap = this.rowMap;
		result.colMap = this.colMap;
		
		// for all the non empty rows in the passed in matrix
		for (int r : other.getNonEmptyRows())
		{
			// take all the columns in that row that store a non-zero value
			// and add it to the value in the original matrix (stored in result)
			for (int c : other.getNonEmptyColsInRow(r))
			{
				result.setValue(r,  c,  (other.getValue(r, c) + result.getValue(r, c)));
			}
		}
		
		// return the resulting matrix
		return result;
	}
}