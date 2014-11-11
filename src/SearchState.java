import java.util.ArrayList;
import java.util.List;


public class SearchState
{
	private final int[][]				grid;
	
	private final List<SearchState>	children	= new ArrayList<SearchState>();
	
	private final SearchState			parent;
	
	private float							h;
	
	public static float[]				weights	= { 2.0f, 0.7f, 1f, 1f, 1f, 1f };
	
	
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
	
	
	public float calcHeuristic()
	{
		return calcHeuristic(false);
	}
	
	
	public float calcHeuristic(boolean output)
	{
		float row = heuristicGreatestInRow();
		float emptyField = heuristicEmptyFields();
		// System.out.println(row + " / " + emptyField);
		float multiple = heuristicPenalizeMultiple();
		// System.out.println(multiple + " / " + emptyField);
		float optimalField = heuristicOptimalField();
		float heighestInEdge = heuristicHeighestInEdge();
		float leftColumn = heuristicCompleteLeftColum();
		float lostSituation = heuristicLostSituation();
		if (output)
		{
			System.out.println(optimalField + " / " + multiple + " / " + emptyField + " / " + heighestInEdge + " / "
					+ leftColumn + " / " + lostSituation);
		}
		return (weights[0] * optimalField) + (weights[1] * multiple) + (weights[2] * emptyField)
				+ (weights[3] * heighestInEdge) + (weights[4] * leftColumn) + (weights[5] * lostSituation);
	}
	
	
	private float heuristicLostSituation()
	{
		if (Puzzle2048.isNoMovePossible(grid))
		{
			return 50;
		}
		return 0;
	}
	
	
	private float heuristicCompleteLeftColum()
	{
		int penalty = 0;
		for (int j = 0; j < grid[0].length; j++)
		{
			if (grid[0][j] == 0)
			{
				penalty += 4 - j;
			}
		}
		return penalty;
	}
	
	
	private float heuristicHeighestInEdge()
	{
		List<GridCell> sorted = Helper.gridToSortedList(grid);
		for (int i = 0; i < sorted.size() && sorted.get(0).getValue() == sorted.get(i).getValue(); i++)
		{
			if (isInEdge(sorted.get(i)))
			{
				return 0;
			}
		}
		return 10;
	}
	
	
	private float heuristicOptimalField()
	{
		int penalty = 1;
		List<GridCell> sorted = Helper.gridToSortedList(grid);
		int priority = 0;
		int jDir = 1;
		int start = 0;
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = start; j < grid[0].length && j >= 0; j = j + jDir)
			{
				if (priority < sorted.size() && (priority <= 8 && sorted.get(priority).getValue() > 4))// &&
																																	// grid[i][j] <
				// sorted.get(priority).getValue())
				{
					double logOfGridCell = 0;
					if (grid[i][j] > 0)
					{
						logOfGridCell = Math.log(grid[i][j]) / Math.log(2);
					}
					penalty += Math.abs((Math.log(sorted.get(priority).getValue()) / Math.log(2) - logOfGridCell));//
					// penalty += Math.abs(sorted.get(priority).getValue() - grid[i][j]);
					// grid[i][j];
					
				}
				priority++;
				
			}
			jDir = -1;
			start = 3;
		}
		return penalty;
	}
	
	
	private float heuristicPenalizeMultiple()
	{
		float penalty = 0;
		List<GridCell> sorted = Helper.gridToSortedList(grid);
		
		float lastValue = 2;
		if (sorted.size() > 0)
		{
			lastValue = sorted.get(0).getValue();
		}
		int count = 0;
		for (int i = 1; i < sorted.size(); i++)
		{
			count++;
			if ((sorted.get(i).getValue() == lastValue))
			{
				penalty += (Math.log(lastValue) / Math.log(2)) * count;
			} else
			{
				count = 0;
			}
			lastValue = sorted.get(i).getValue();
		}
		return penalty;
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
			boolean isNeighbour = false;
			int k = i - 1;
			do
			{
				k++;
				if (isNeighbour(sorted.get(i - 1), sorted.get(k)))
				{
					isNeighbour = true;
				}
			} while ((k + 1) < sorted.size() && sorted.get(k).getValue() == sorted.get(k + 1).getValue());
			if (!isNeighbour)
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
