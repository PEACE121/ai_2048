import java.util.ArrayList;
import java.util.List;


public class SearchState
{
	private final int[][]				grid;
	
	private final List<SearchState>	children	= new ArrayList<SearchState>();
	
	private final SearchState			parent;
	
	private float							h;
	
	
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
		float row = heuristicGreatestInRow();
		float emptyField = heuristicEmptyFields();
		System.out.println(row + " / " + emptyField);
		return (row + emptyField);
	}
	
	
	private float heuristicGreatestInRow()
	{
		float penalty = 0;
		List<GridCell> sorted = Helper.gridToSortedList(grid);
		if (!isInEdge(sorted.get(0)))
		{
			penalty += 30;
		}
		
		List<GridCell> twosAndFours = new ArrayList<GridCell>();
		for (GridCell gridCell : sorted)
		{
			if (gridCell.getValue() == 2 || gridCell.getValue() == 4)
			{
				twosAndFours.add(gridCell);
			}
		}
		sorted.removeAll(twosAndFours);
		for (int i = 1; i < sorted.size(); i++)
		{
			if (i == 8)
			{
				break;
			}
			if (!isNeighbour(sorted.get(i - 1), sorted.get(i)))
			{
				penalty += sorted.size() - i;
			}
		}
		return penalty;
	}
	
	
	private boolean isNeighbour(GridCell cell1, GridCell cell2)
	{
		return Math.abs(cell1.getX() - cell2.getX()) + Math.abs(cell1.getY() - cell2.getY()) == 1;
	}
	
	
	private boolean isInEdge(GridCell cell)
	{
		if ((cell.getX() == 0 || cell.getX() == 3) && (cell.getY() == 0 || cell.getY() == 3))
		{
			return true;
		}
		return false;
	}
	
	
	private float heuristicEmptyFields()
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
	
	
	/**
	 * @param h the h to set
	 */
	public void setH(float h)
	{
		this.h = h;
	}
	
	
}
