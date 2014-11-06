import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Helper
{
	
	public static int[][] deepCopyIntMatrix(int[][] input)
	{
		if (input == null)
			return null;
		int[][] result = new int[input.length][];
		for (int r = 0; r < input.length; r++)
		{
			result[r] = input[r].clone();
		}
		return result;
	}
	
	
	public static List<GridCell> gridToSortedList(int[][] input)
	{
		List<GridCell> sortedList = new ArrayList<GridCell>();
		for (int i = 0; i < input.length; i++)
		{
			for (int j = 0; j < input[0].length; j++)
			{
				if (input[i][j] != 0)
				{
					sortedList.add(new GridCell(i, j, input[i][j]));
				}
			}
		}
		Collections.sort(sortedList);
		return sortedList;
	}
	
}
