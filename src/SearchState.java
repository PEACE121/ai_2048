import java.util.ArrayList;
import java.util.List;


public class SearchState
{
	private final int[][]				grid;
	
	private final List<SearchState>	children	= new ArrayList<SearchState>();
	
	private final SearchState			parent;
	
	private final float					h;
	
	
	/**
	 * @param grid
	 */
	public SearchState(int[][] grid, SearchState parent)
	{
		super();
		this.grid = grid;
		this.parent = parent;
		this.h = calcHeuristic();
	}
	
	
	public SearchState(float h)
	{
		super();
		this.h = h;
		this.grid = null;
		this.parent = null;
	}
	
	
	/**
	 * @return the grid
	 */
	public int[][] getGrid()
	{
		return grid;
	}
	
	
	private float calcHeuristic()
	{
		int emptyFields = 0;
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[0].length; j++)
			{
				if (grid[i][j] == 0)
				{
					emptyFields++;
				}
			}
		}
		return 16 - emptyFields;
	}
	
	
	/**
	 * @return the children
	 */
	public List<SearchState> getChildren()
	{
		return children;
	}
	
	
	/**
	 * @return the parent
	 */
	public SearchState getParent()
	{
		return parent;
	}
	
	
	/**
	 * @return the h
	 */
	public float getH()
	{
		return h;
	}
	
	
}
