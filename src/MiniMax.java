import java.util.ArrayList;
import java.util.List;


public class MiniMax
{
	private static List<SearchState> generateChildrenMax(SearchState node)
	{
		List<SearchState> children = new ArrayList<SearchState>();
		int[][] field = node.getGrid();
		for (int i = 0; i < field.length; i++)
		{
			for (int j = 0; j < field[0].length; j++)
			{
				if (field[i][j] == 0)
				{
					int[][] newChild = Helper.deepCopyIntMatrix(field);
					newChild[i][j] = 2;
					children.add(new SearchState(newChild, node));
					newChild = Helper.deepCopyIntMatrix(field);
					newChild[i][j] = 4;
					children.add(new SearchState(newChild, node));
				}
			}
		}
		return children;
	}
	
	
	private static List<SearchState> generateChildrenMaxWeighted(SearchState node)
	{
		List<SearchState> children = new ArrayList<SearchState>();
		int[][] field = node.getGrid();
		for (int i = 0; i < field.length; i++)
		{
			for (int j = 0; j < field[0].length; j++)
			{
				if (field[i][j] == 0)
				{
					int[][] newChild2 = Helper.deepCopyIntMatrix(field);
					newChild2[i][j] = 2;
					SearchState state2 = new SearchState(newChild2, node);
					// state2.setH(state2.getH() * 0.9f);
					children.add(state2);
					
					int[][] newChild4 = Helper.deepCopyIntMatrix(field);
					newChild4[i][j] = 4;
					SearchState state4 = new SearchState(newChild4, node);
					// state4.setH(state4.getH() * 0.1f);
					children.add(state4);
				}
			}
		}
		return children;
	}
	
	
	private static List<SearchState> generateChildrenMin(SearchState node)
	{
		List<SearchState> children = new ArrayList<SearchState>();
		int[][] field = node.getGrid();
		// TODO we hope that the priorization of the direction has an effect
		if (Puzzle2048.isShiftPossible(field, EDirection.LEFT))
		{
			children.add(new SearchState(Puzzle2048.shift(Helper.deepCopyIntMatrix(field), EDirection.LEFT), node));
		}
		if (Puzzle2048.isShiftPossible(field, EDirection.TOP))
		{
			children.add(new SearchState(Puzzle2048.shift(Helper.deepCopyIntMatrix(field), EDirection.TOP), node));
		}
		if (Puzzle2048.isShiftPossible(field, EDirection.RIGHT))
		{
			children.add(new SearchState(Puzzle2048.shift(Helper.deepCopyIntMatrix(field), EDirection.RIGHT), node));
		}
		if (Puzzle2048.isShiftPossible(field, EDirection.DOWN))
		{
			children.add(new SearchState(Puzzle2048.shift(Helper.deepCopyIntMatrix(field), EDirection.DOWN), node));
		}
		return children;
	}
	
	
	public static SearchState alphabeta(SearchState node, int depth, SearchState alpha, SearchState beta,
			boolean maximizingPlayer)
	{
		if (depth == 0 || Puzzle2048.isNoMovePossible(node.getGrid()))
		{
			return node;
		}
		if (maximizingPlayer)
		{
			List<SearchState> children = generateChildrenMax(node);
			for (SearchState child : children)
			{
				SearchState v = alphabeta(child, depth - 1, alpha, beta, false);
				if (v.getH() > alpha.getH())
				{
					alpha = v;
				}
				if (beta.getH() <= alpha.getH())
				{
					break;
				}
			}
			if (children.size() == 0)
			{
				return node;
			} else
			{
				return alpha;
			}
		} else
		{
			List<SearchState> children = generateChildrenMin(node);
			for (SearchState child : children)
			{
				SearchState v = alphabeta(child, depth - 1, alpha, beta, true);
				if (v.getH() < beta.getH())
				{
					beta = v;
				}
				if (beta.getH() <= alpha.getH())
				{
					break;
				}
			}
			if (children.size() == 0)
			{
				return node;
			} else
			{
				return beta;
			}
		}
	}
	
	
	public static SearchState expectiminimax(SearchState node, int depth, SearchState alpha, SearchState beta,
			boolean maximizingPlayer)
	{
		if (depth == 0 || Puzzle2048.isNoMovePossible(node.getGrid()))
		{
			return node;
		}
		if (maximizingPlayer)
		{
			List<SearchState> children = generateChildrenMaxWeighted(node);
			int a = 0;
			for (SearchState child : children)
			{
				SearchState best = expectiminimax(child, depth - 1, alpha, beta, false);
				a += (1 / (children.size())) * best.getH(); // (1 / (children.size() / 2)) * best.getH();
				// if (v.getH() > alpha.getH())
				// {
				// alpha = v;
				// }
				// if (beta.getH() <= alpha.getH())
				// {
				// break;
				// }
			}
			node.setH(a);
			return node;
		} else
		{
			List<SearchState> children = generateChildrenMin(node);
			for (SearchState child : children)
			{
				SearchState v = expectiminimax(child, depth - 1, alpha, beta, true);
				if (v.getH() < beta.getH())
				{
					beta = v;
				}
				// if (beta.getH() <= alpha.getH())
				// {
				// break;
				// }
			}
			if (children.size() == 0)
			{
				return node;
			} else
			{
				return beta;
			}
		}
	}
}
