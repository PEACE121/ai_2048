import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Start
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Puzzle2048 puzzle = null;
		int runsAmount = 1;
		switch (args.length)
		{
			case 1:
				runsAmount = Integer.parseInt(args[0]);
			case 0:
				Map<Integer, Integer> amounts = new HashMap<Integer, Integer>();
				for (int i = 0; i < runsAmount; i++)
				{
					// System.out.println("Run " + i);
					puzzle = new Puzzle2048();
					puzzle.run();
					puzzle.getGrid().dispatchEvent(new WindowEvent(puzzle.getFrame(), WindowEvent.WINDOW_CLOSING));
					int max = puzzle.getMax();
					if (!amounts.containsKey(max))
					{
						amounts.put(max, 0);
					}
					amounts.put(max, amounts.get(max) + 1);
					// try
					// {
					// Thread.sleep(5000);
					// } catch (InterruptedException e)
					// {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
				}
				// System.out.println(j);
				for (Entry amount : amounts.entrySet())
				{
					System.out.println(amount.getKey() + ": " + amount.getValue());
				}
				// }
				break;
			default:
				System.out.println("Usage: Start <amount>");
				break;
		}
	}
}
