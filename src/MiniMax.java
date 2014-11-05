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
}
