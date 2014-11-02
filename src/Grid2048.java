import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;


public class Grid2048 extends JPanel
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2838947983271757774L;
	
	static final int				MAX_SIZE				= 400;
	private final int				rectSize				= 100;
	
	private int[][]				positions;
	
	
	public Grid2048()
	{
		positions = new int[4][4];
		for (int i = 0; i < positions.length; i++)
		{
			for (int j = 0; j < positions[0].length; j++)
			{
				positions[i][j] = 0;
			}
		}
	}
	
	
	// -------------------------------------------------------------------------
	// ------------------------------------ GUI --------------------------------
	// -------------------------------------------------------------------------
	
	public void refreshField()
	{
		this.repaint();
	}
	
	
	public void showField(int[][] field)
	{
		setPositions(field);
		refreshField();
	}
	
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}
	
	
	public void draw(Graphics g)
	{
		if (positions != null)
		{
			for (int i = 0; i < positions.length; i++)
			{
				for (int j = 0; j < positions[0].length; j++)
				{
					if (positions[i][j] != 0)
					{
						switch (positions[i][j])
						{
							case 2:
								g.setColor(new Color(215, 235, 240));
								break;
							case 4:
								g.setColor(new Color(173, 216, 230));
								break;
							case 8:
								g.setColor(new Color(0, 255, 255));
								break;
							case 16:
								g.setColor(new Color(0, 0, 255));
								break;
							case 32:
								g.setColor(new Color(0, 0, 192));
								break;
							case 64:
								g.setColor(new Color(0, 0, 128));
								break;
							case 128:
								g.setColor(new Color(0, 0, 64));
								break;
							case 256:
								g.setColor(new Color(255, 255, 0));
								break;
							case 512:
								g.setColor(new Color(255, 64, 32));
								break;
							case 1024:
								g.setColor(new Color(255, 128, 64));
								break;
							case 2048:
								g.setColor(new Color(255, 192, 128));
								break;
							default:
								g.setColor(new Color(255, 0, 0));
								break;
						
						}
						g.fillRect(rectSize * i, (rectSize * (positions[0].length - 1)) - rectSize * j, rectSize, rectSize);
						g.setColor(Color.black);
						g.setFont(new Font("number", Font.BOLD, 80));
						g.drawString(positions[i][j] + "", rectSize * i + 20,
								((rectSize * (positions[0].length - 1)) - rectSize * j) + 80);
					}
				}
			}
		}
		
		// Display the window.
		setVisible(true);
	}
	
	
	// -------------------------------------------------------------------------
	// --------------------------- Getter and Setter ---------------------------
	// -------------------------------------------------------------------------
	
	public int getGridSize()
	{
		return positions.length;
	}
	
	
	/**
	 * @return a copy of the positions array
	 */
	public int[][] getPositions()
	{
		return Helper.deepCopyIntMatrix(positions);
	}
	
	
	/**
	 * @param positions the positions to set
	 */
	public void setPositions(int[][] positions)
	{
		this.positions = positions;
	}
	
	
	// -------------------------------------------------------------------------
	// -------------------------------- Helper ---------------------------------
	// -------------------------------------------------------------------------
	
	
}