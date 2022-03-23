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
	public static void main (String[] args)
	{
		//Matrix 1:
		BigMatrix m1 = new BigMatrix();
		m1.setValue(0, 0, 1);
		m1.setValue(1000, 10, 2);
		m1.setValue(10, 1000, 3);
		m1.setValue(0, 1000, 4);
		m1.setValue(1000, 0, 5);
		m1.setValue(0, 10, 6);
		m1.setValue(10, 0, 7);
		m1.setValue(10, 1000, 0);
		m1.setValue(10, 0, 0);
		m1.setValue(1000, 10, 0);
		m1.setValue(0, 10, 0);

		System.out.println(m1.getRowSum(0));
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
		// if the value is 0 and is in the matrix
		if (value == 0 && rowMap.get(row).containsKey(col))
		{
			// remove the entry if there are multiple in the row/column
			// remove the row/column if it is the only one
			//
			if (rowMap.get(row).size() == 1) 
			{
				rowMap.remove(row);
			} 
			else 
			{
				rowMap.get(row).remove(col);
			}
			
			if (colMap.get(col).size() == 1)
			{
				colMap.remove(col);
			}
			else
			{
				colMap.get(col).remove(row);
			}
		}
		
		// if the value is not 0
		// add it to the matrix by hashing the row & column
		//
		else
		{
			Entry e = new Entry(row, col, value);
			
			if(rowMap.get(row) != null) 
			{
				rowMap.put(getHashCode(row), rowMap.get(row));
				rowMap.get(row).put(col, e);
			}
			else
			{
				rowMap.put(getHashCode(row), new HashMap<Integer, Entry>());
				rowMap.get(row).put(col, e);
			}
			
			if(colMap.get(col) != null) 
			{
				colMap.put(getHashCode(col), colMap.get(col));
				rowMap.get(col).put(row, e);
			}
			else
			{
				colMap.put(getHashCode(col), new HashMap<Integer, Entry>());
				rowMap.get(col).put(row, e);
			}
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
			rowVals.add(row);
		}
		
		// Return the list
		return rowVals;
	}
	
	public List<Integer> getNonEmptyRowsInColumn(int col)
	{
		
		// Create an empty list of integers to store the row values
		List<Integer> rowVals = new ArrayList<Integer>();
		
		/*HashMap<Integer, Entry> colSubMap = colMap.get(col);
		
		// For each key in the column hash table
		for(int c: colSubMap.keySet()) 
		{
			// If the key is not already in the list, add it to list
			//if (rowVals.contains(colSubMap.get(c).row) == false) 
			//{
				rowVals.add(colSubMap.get(c).row);
			//}
		}*/
		
		if(colMap.get(col) != null)
		{		
			for (int i : colMap.get(col).keySet())
			{
				rowVals.add(i);
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
			colVals.add(col);
		}
		
		// Return the list
		return colVals;
	}
	
	public List<Integer> getNonEmptyColsInRow(int row)
	{
		// Create an empty list of integers to store the column values 
		List<Integer> colVals = new ArrayList<Integer>();
		
		/*HashMap<Integer, Entry> rowSubMap = rowMap.get(row);
		
		// For each key in the row hash table
		for(int r: rowSubMap.keySet()) 
		{
			// If the key is not already in the list, add it to list
			colVals.add(rowSubMap.get(r).column);
		}*/
		
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
			for (int i : rowMap.get(row).keySet())
			{
				sum += rowMap.get(row).get(i).value;
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

		List<Entry> coordsChecked = new ArrayList<Entry>();

		for(HashMap<Integer, Entry> temp : rowMap.values())
		{
			for (Entry e : temp.values() ) {
				result.setValue(e.row, e.column, (e.value + other.getValue(e.row, e.column) ));
				coordsChecked.add(e);
			}
		}

		for(int i : other.getNonEmptyCols()) 
		{
			List<Integer> needToAdd = other.getNonEmptyRowsInColumn(i);

			for (int j : needToAdd)
			{
				if (result.getValue(i, j) == 0)
				{
					result.setValue(i, j, other.getValue(i,  j));
				}
			}

		}



		return result;
	}
}