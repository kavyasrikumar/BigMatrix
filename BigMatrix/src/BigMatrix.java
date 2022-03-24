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
		// if the value is 0 and is in the matrix
		if (value == 0 && rowMap.get(row).get(col).value == 0 )
		{
			return;
		}
		
		if (rowMap.containsKey(row))
		{
			if (value == 0)
			{
				if (rowMap.get(row).size() == 1)
				{
					rowMap.remove(row);
				}
				else
				{
					rowMap.get(row).remove(col);
				}
			}
			else
			{
				rowMap.get(row).put(col, new Entry(row, col, value));
			}
		}
		else
		{
			HashMap<Integer, Entry> temp = new HashMap<Integer, Entry>();
			temp.put(col, new Entry(row, col, value));
			rowMap.put(row, temp);
		}
		
		if (colMap.containsKey(col))
		{
			if (value == 0)
			{
				if (colMap.get(col).size() == 1)
				{
					colMap.remove(col);
				}
				else
				{
					colMap.get(col).remove(row);
				}
			} 
			else
			{
				colMap.get(col).put(row, new Entry(row, col, value));
			}
		}
		else
		{
			HashMap<Integer, Entry> temp = new HashMap<Integer, Entry>();
			temp.put(row, new Entry(row, col, value));
			colMap.put(col, temp);
		}
	}
	
	public int getValue(int row, int col)
	{
		if (rowMap.containsKey(row) && rowMap.get(row).containsKey(col))
		{	
			return rowMap.get(row).get(col).value;
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
			rowVals.add(row);
		}
		
		// Return the list
		return rowVals;
	}
	
	public List<Integer> getNonEmptyRowsInColumn(int col)
	{
		
		// Create an empty list of integers to store the column values 
		List<Integer> rowVals = new ArrayList<Integer>();;
		
		if(colMap.get(col) != null)
		{		
			rowVals = new ArrayList<Integer>(colMap.get(col).keySet());
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
			colVals.add(col);
		}
		
		// Return the list
		return colVals;
	}
	
	public List<Integer> getNonEmptyColsInRow(int row)
	{
		// Create an empty list of integers to store the column values 
		List<Integer> colVals = new ArrayList<Integer>();
		
		if(rowMap.get(row) != null)
		{		
			for (int i : rowMap.get(row).keySet())
			{
				colVals.add(i);
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
			for (Entry e : rowMap.get(row).values())
			{
				sum += e.value;
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
			for (Entry e : colMap.get(col).values())
			{
				sum += e.value;
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
		
		result.rowMap = this.rowMap;
		result.colMap = this.colMap;
		
		for (int r : other.getNonEmptyRows())
		{
			for (int c : other.getNonEmptyColsInRow(r))
			{
				result.setValue(r,  c,  (other.getValue(r, c) + result.getValue(r, c)));
			}
		}
	
		return result;
	}
}