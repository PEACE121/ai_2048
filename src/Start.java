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
				for (int i = 0; i < runsAmount; i++)
				{
					System.out.println("Run " + i);
					puzzle = new Puzzle2048();
					puzzle.run();
				}
				break;
			default:
				System.out.println("Usage: Start <amount>");
				break;
		}
	}
}
