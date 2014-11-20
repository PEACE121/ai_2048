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
		boolean turnOnUI = true;
		int delay = 0;
		EAlgorithm algorithm = EAlgorithm.ALPHABETA;
		switch (args.length)
		{
			case 6:
				algorithm = EAlgorithm.valueOf(args[5]);
			case 5:
				MiniMax.doubleC = Boolean.parseBoolean(args[4]);
			case 4:
				turnOnUI = Boolean.parseBoolean(args[3]);
			case 3:
				delay = Integer.parseInt(args[2]);
			case 2:
				runsAmount = Integer.parseInt(args[1]);
			case 1:
				switch (ERuntype.valueOf(args[0]))
				{
					case EVALUATION:
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
						for (float i = 1.0f; i <= 2.01f; i = i + 0.5f)
						{
							SearchState.weights[0] = i;
							
							// multiple
							for (float j = 0; j <= 1f; j = j + 0.5f)
							{
								SearchState.weights[1] = j;
								
								// empty fields
								for (float j2 = 1.0f; j2 <= 3.01f; j2 = j2 + 0.5f)
								{
									SearchState.weights[2] = j2;
									
									// heighest in the edge
									for (float k = 0f; k <= 0.0f; k = k + 1.0f)
									{
										SearchState.weights[3] = k;
										
										// left column
										for (float k2 = 0; k2 <= 0; k2 = k2 + 1)
										{
											SearchState.weights[4] = k2;
											
											// lost situation
											for (float l = 1; l <= 3.01f; l = l + 0.5f)
											{
												SearchState.weights[5] = l;
												System.out.println(i + "," + j + "," + j2 + "," + k + "," + k2 + "," + l);
												run(runsAmount, turnOnUI, delay, algorithm);
											}
										}
									}
								}
							}
						}
						break;
					case PLAY:
						Puzzle2048 puzzle = new Puzzle2048(turnOnUI, delay);
						puzzle.playManually();
						break;
					case RUN:
						switch (algorithm)
						{
							case ALPHABETA:
								SearchState.weights[0] = 1.7f;
								SearchState.weights[1] = 0.5f;
								SearchState.weights[2] = 2.5f;
								SearchState.weights[3] = 5f;
								SearchState.weights[4] = 0f;
								SearchState.weights[5] = 3.0f;
								break;
							case EXPECTI:
								SearchState.weights[0] = 2.0f;
								SearchState.weights[1] = 0.5f;
								SearchState.weights[2] = 1.5f;
								SearchState.weights[3] = 0f;
								SearchState.weights[4] = 0f;
								SearchState.weights[5] = 3.0f;
								break;
						}
						
						run(runsAmount, turnOnUI, delay, algorithm);
						break;
				}
			case 0:
			default:
				System.out
						.println("Usage: Start <type> <amount> <delay> <turnOnUI> <doubleC> <algorithm>\n"
								+ "type: RUN, PLAY or EVALUATION\n"
								+ "amount: amount of rounds (default: 1)\n"
								+ "delay: ms between the actions (default: 0)\n"
								+ "turnOnUI: false if the UI should not be shown (default:true)\n"
								+ "doubleC: false if the faster versions with less children in the max node should be used (default:true)\n"
								+ "algorithm: EXPECTI or ALPHABETA (default: ALPHABETA");
				break;
		}
	}
	
	
	private static void run(int runsAmount, boolean turnOnUI, int delay, EAlgorithm algorithm)
	{
		Map<Integer, Integer> amounts = new HashMap<Integer, Integer>();
		amounts.put(2048, 0);
		amounts.put(4096, 0);
		for (int i = 0; i < runsAmount; i++)
		{
			long time = System.nanoTime();
			Puzzle2048 puzzle = new Puzzle2048(turnOnUI, delay);
			puzzle.playArtificially(algorithm);
			int max = puzzle.getMax();
			if (!amounts.containsKey(max))
			{
				amounts.put(max, 0);
			}
			amounts.put(max, amounts.get(max) + 1);
			
			System.out.println("Run " + i + ": " + max + " ("
					+ Math.round((((amounts.get(2048) + amounts.get(4096)) / (float) (i + 1)) * 100)) + "%), Time: "
					+ (System.nanoTime() - time) / 1000000 + " ms");
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
