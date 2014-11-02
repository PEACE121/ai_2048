import javax.swing.JFrame;


public class Puzzle2048
{
	private final Grid2048	grid;
	
	
	/**
	 * 
	 */
	public Puzzle2048()
	{
		super();
		
		grid = new Grid2048();
		
		JFrame f = new JFrame();
		f.getContentPane().add(grid);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(Grid2048.MAX_SIZE, Grid2048.MAX_SIZE);
		f.setVisible(true);
		
	}
	
	
	public void run()
	{
		
		long start = System.currentTimeMillis();
		// TODO run the algorithm
		int[][] field = grid.getPositions();
		spawnRandom(field);
		grid.showField(field);
		spawnRandom(field);
		grid.showField(field);
		spawnRandom(field);
		grid.showField(field);
		
		long runtime = System.currentTimeMillis() - start;
		System.out.println("Runtime: " + runtime + " ms");
	}
	
	
	// -------------------------------------------------------------------------
	// ------------------------------- 2048 actions ----------------------------
	// -------------------------------------------------------------------------
	
	public int[][] spawnRandom(int[][] field)
	{
		do
		{
			int x = (int) Math.floor(Math.random() * 4);
			int y = (int) Math.floor(Math.random() * 4);
			if (field[x][y] == 0)
			{
				if (Math.random() > 0.9)
				{
					field[x][y] = 4;
				} else
				{
					field[x][y] = 2;
				}
				break;
			}
		} while (true);
		return field;
	}
	
	
	public void shift()
	{
		
	}
	
	
}
