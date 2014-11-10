import java.util.Scanner;

import javax.swing.JFrame;


public class Puzzle2048
{
	private final Grid2048	grid;
	
	private final JFrame		frame;
	
	private int					max;
	
	
	/**
	 * 
	 */
	public Puzzle2048()
	{
		super();
		
		grid = new Grid2048();
		
		frame = new JFrame();
		frame.getContentPane().add(grid);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Grid2048.MAX_SIZE, Grid2048.MAX_SIZE);
		frame.setVisible(true);
		
	}
	
	
	public void run()
	{
		
		long start = System.currentTimeMillis();
		// TODO run the algorithm
		int[][] field = grid.getPositions();
		field = spawnRandom(field);
		grid.showField(field);
		
		SearchState nextState = new SearchState(field, null);
		boolean first = true;
		while (nextState.getGrid() != null && !isNoMovePossible(nextState.getGrid()))
		{
			grid.showField(nextState.getGrid());
			// try
			// {
			// Thread.sleep(500);
			// } catch (InterruptedException e)
			// {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			
			if (!first)
			{
				// SPAWN
				nextState = new SearchState(spawnRandom(nextState.getGrid()), null);
				grid.showField(nextState.getGrid());
			}
			first = false;
			
			// MOVE
			SearchState bestState = MiniMax.alphabeta(nextState, 5, new SearchState(Float.MIN_VALUE), new SearchState(
					Float.MAX_VALUE), false);
			
			// bestState.calcHeuristic(true);
			// SearchState bestState = MiniMax.expectiminimax(nextState, 3, false);
			while (bestState.getParent() != null && bestState.getParent().getParent() != null)
			{
				bestState = bestState.getParent();
			}
			nextState = bestState;
			
			
			// try
			// {
			// Thread.sleep(500);
			// } catch (InterruptedException e)
			// {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			
		}
		
		long runtime = System.currentTimeMillis() - start;
		// System.out.println("Runtime: " + runtime + " ms");
		
		max = 0;
		for (int i = 0; i < nextState.getGrid().length; i++)
		{
			for (int j = 0; j < nextState.getGrid()[0].length; j++)
			{
				if (nextState.getGrid()[i][j] > max)
				{
					max = nextState.getGrid()[i][j];
				}
			}
		}
		// System.out.println(max);
	}
	
	
	private void playManually()
	{
		int[][] field = grid.getPositions();
		spawnRandom(field);
		grid.showField(field);
		while (true)
		{
			if (!isShiftPossible(field, EDirection.LEFT) && !isShiftPossible(field, EDirection.RIGHT)
					&& !isShiftPossible(field, EDirection.TOP) && !isShiftPossible(field, EDirection.DOWN))
			{
				System.out.println("Lost");
				break;
			}
			Scanner keyboard = new Scanner(System.in);
			String input = keyboard.next();
			EDirection dir = EDirection.TOP;
			switch (input)
			{
				case "w":
					dir = EDirection.TOP;
					break;
				case "s":
					dir = EDirection.DOWN;
					break;
				case "a":
					dir = EDirection.LEFT;
					break;
				case "d":
					dir = EDirection.RIGHT;
					break;
			}
			
			if (isShiftPossible(field, dir))
			{
				shift(field, dir);
				spawnRandom(field);
			} else
			{
				System.out.println("Shift is not possible in direction " + dir.name());
			}
			grid.showField(field);
		}
	}
	
	
	// -------------------------------------------------------------------------
	// ------------------------------- 2048 actions ----------------------------
	// -------------------------------------------------------------------------
	
	public int[][] spawnRandom(int[][] field)
	{
		int[][] newField = Helper.deepCopyIntMatrix(field);
		do
		{
			int x = (int) Math.floor(Math.random() * 4);
			int y = (int) Math.floor(Math.random() * 4);
			if (newField[x][y] == 0)
			{
				if (Math.random() > 0.9)
				{
					newField[x][y] = 4;
				} else
				{
					newField[x][y] = 2;
				}
				break;
			}
		} while (true);
		return newField;
	}
	
	
	public static int[][] shift(int[][] field, EDirection dir)
	{
		int dirx = getDirAsInt(dir)[0];
		int diry = getDirAsInt(dir)[1];
		
		boolean[][] alreadyMerged = new boolean[4][4];
		for (int i = 0; i < alreadyMerged.length; i++)
		{
			for (int j = 0; j < alreadyMerged.length; j++)
			{
				alreadyMerged[i][j] = false;
			}
		}
		
		// move all parts as far as possible
		boolean changed = true;
		while (changed)
		{
			changed = false;
			int initi = 0;
			int diri = 1;
			if (dir == EDirection.RIGHT)
			{
				initi = 3;
				diri = -1;
			}
			for (int i = initi; i < field.length && i >= 0; i = i + diri)
			{
				int initj = 0;
				int dirj = 1;
				if (dir == EDirection.TOP)
				{
					initj = 3;
					dirj = -1;
				}
				for (int j = initj; j < field[0].length && j >= 0; j = j + dirj)
				{
					// for every not empty field
					if (field[i][j] != 0)
					{
						// check if the neighbour is in the field
						if ((i + dirx) <= 3 && (i + dirx) >= 0 && (j + diry) <= 3 && (j + diry) >= 0)
						{
							// check if the neighbour is empty
							if (field[i + dirx][j + diry] == 0)
							{
								// move
								field[i + dirx][j + diry] = field[i][j];
								alreadyMerged[i + dirx][j + diry] = alreadyMerged[i][j];
								alreadyMerged[i][j] = false;
								field[i][j] = 0;
								changed = true;
							} else if (field[i][j] == field[i + dirx][j + diry] && alreadyMerged[i + dirx][j + diry] == false
									&& alreadyMerged[i][j] == false)
							{
								field[i + dirx][j + diry] = 2 * field[i + dirx][j + diry];
								field[i][j] = 0;
								alreadyMerged[i + dirx][j + diry] = true;
								changed = true;
							}
						}
					}
				}
			}
		}
		
		
		// check for merges
		return field;
	}
	
	
	public static boolean isNoMovePossible(int[][] field)
	{
		return !isShiftPossible(field, EDirection.LEFT) && !isShiftPossible(field, EDirection.RIGHT)
				&& !isShiftPossible(field, EDirection.TOP) && !isShiftPossible(field, EDirection.DOWN);
	}
	
	
	public static boolean isShiftPossible(int[][] field, EDirection dir)
	{
		int dirx = getDirAsInt(dir)[0];
		int diry = getDirAsInt(dir)[1];
		for (int i = 0; i < field.length; i++)
		{
			for (int j = 0; j < field[0].length; j++)
			{
				// for every not empty field
				if (field[i][j] != 0)
				{
					// check if the neighbour is in the field
					if ((i + dirx) <= 3 && (i + dirx) >= 0 && (j + diry) <= 3 && (j + diry) >= 0)
						// check if the neighbour is either empty or a field of the same number
						if (field[i][j] == field[i + dirx][j + diry] || field[i + dirx][j + diry] == 0)
						{
							return true;
						}
				}
			}
		}
		
		return false;
	}
	
	
	private static int[] getDirAsInt(EDirection dir)
	{
		int dirx = 0;
		int diry = 0;
		switch (dir)
		{
			case LEFT:
				dirx = -1;
				break;
			case RIGHT:
				dirx = 1;
				break;
			case TOP:
				diry = 1;
				break;
			case DOWN:
				diry = -1;
				break;
		}
		int[] out = { dirx, diry };
		return out;
	}
	
	
	/**
	 * @return the grid
	 */
	public Grid2048 getGrid()
	{
		return grid;
	}
	
	
	/**
	 * @return the frame
	 */
	public JFrame getFrame()
	{
		return frame;
	}
	
	
	public int getMax()
	{
		return max;
	}
}
