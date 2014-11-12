import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;


public class Start
{
	
	private static String	TEXT_FILE	= "output.txt";
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		
		int runsAmount = 1;
		switch (args.length)
		{
			case 2:
				SearchState.weights[0] = 1.7f;
				SearchState.weights[1] = 0.5f;
				SearchState.weights[2] = 2.5f;
				SearchState.weights[3] = 0f;
				SearchState.weights[4] = 0f;
				SearchState.weights[5] = 3.0f;
				run(Integer.parseInt(args[0]));
				break;
			case 1:
				runsAmount = Integer.parseInt(args[0]);
			case 0:
				Writer fw = null;
				try
				{
					TEXT_FILE = "output" + System.currentTimeMillis() + ".txt";
					fw = new FileWriter(TEXT_FILE, true);
					fw.append("Optimal Field,Multiple,Empty Fields,Heighest in the Edge,Left Column,Lost Situation,32,64,128,256,512,1024,2048,4096,8192"
							+ System.getProperty("line.separator"));
					fw.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				
				// optimal field
				for (float i = 1.6f; i <= 1.91f; i = i + 0.1f)
				{
					SearchState.weights[0] = i;
					
					// multiple
					for (float j = 0.4f; j <= 0.71f; j = j + 0.1f)
					{
						SearchState.weights[1] = j;
						
						// empty fields
						for (float j2 = 1.5f; j2 <= 3.01f; j2 = j2 + 0.5f)
						{
							SearchState.weights[2] = j2;
							
							// heighest in the edge
							for (float k = 0; k <= 0; k = k + 1)
							{
								SearchState.weights[3] = k;
								
								// left column
								for (float k2 = 0; k2 <= 0; k2 = k2 + 1)
								{
									SearchState.weights[4] = k2;
									
									for (float l = 1; l <= 3.01f; l = l + 0.5f)
									{
										SearchState.weights[5] = l;
										run(Integer.parseInt(args[0]));
									}
								}
							}
						}
					}
				}
				break;
			default:
				System.out.println("Usage: Start <amount>");
				break;
		}
	}
	
	
	private static void run(int runsAmount)
	{
		Map<Integer, Integer> amounts = new HashMap<Integer, Integer>();
		for (int i = 0; i < runsAmount; i++)
		{
			// System.out.println("Run " + i);
			Puzzle2048 puzzle = new Puzzle2048();
			puzzle.run();
			puzzle.getGrid().dispatchEvent(new WindowEvent(puzzle.getFrame(), WindowEvent.WINDOW_CLOSING));
			int max = puzzle.getMax();
			if (!amounts.containsKey(max))
			{
				amounts.put(max, 0);
			}
			amounts.put(max, amounts.get(max) + 1);
		}
		try
		{
			Writer fw = null;
			fw = new FileWriter(TEXT_FILE, true);
			int[] interestingNumbers = { 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192 };
			String line = SearchState.weights[0] + "," + SearchState.weights[1] + "," + SearchState.weights[2] + ","
					+ SearchState.weights[3] + "," + SearchState.weights[4] + "," + SearchState.weights[5] + ",";
			for (int number : interestingNumbers)
			{
				if (amounts.containsKey(number))
				{
					line += amounts.get(number) + ",";
					System.out.println(number + ": " + amounts.get(number));
				} else
				{
					line += "0,";
				}
			}
			// System.out.println(line);
			fw.append(line + System.getProperty("line.separator"));
			fw.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // e.g. "\n"
	}
}
