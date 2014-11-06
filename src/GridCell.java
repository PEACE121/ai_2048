public class GridCell implements Comparable<GridCell>
{
	private final int	x;
	private final int	y;
	private final int	value;
	
	
	/**
	 * @param x
	 * @param y
	 * @param value
	 */
	public GridCell(int x, int y, int value)
	{
		super();
		this.x = x;
		this.y = y;
		this.value = value;
	}
	
	
	/**
	 * @return the x
	 */
	public int getX()
	{
		return x;
	}
	
	
	/**
	 * @return the y
	 */
	public int getY()
	{
		return y;
	}
	
	
	/**
	 * @return the value
	 */
	public int getValue()
	{
		return value;
	}
	
	
	@Override
	public int compareTo(GridCell o)
	{
		if (this.value < o.value)
		{
			return 1;
		} else if (this.value > o.value)
		{
			return -1;
		} else
		{
			return 0;
		}
	}
	
	
}
