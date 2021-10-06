// import java.util.Map;
import java.util.stream.*;
import java.util.*;

public class ComputerOpponent {
    
    //This is where the magic happens - tells the computer how advantageous a given
    //configuration is
    private static final Map<String, Integer> SCORING_MAP = Stream.of(new Object[][] {
        //region regexes and their corresponding scores
        {"_XX(?=(_))", -4},
        {"_XX(?=(O))", -2},
        {"OXX(?=(_))", -2},
        {"OXX(?=(O))", -1},
        {"__XXX(?=(_))", -100},
        {"_XXX(?=(__))", -100},
	    {"_XX_X(?=(_))", -100},
        {"_X_XX(?=(_))", -100},
		{"XXXX(?=(_))", -400},
	    {"XXX_X", -400},
		{"XX_XX", -400},
		{"X_XXX", -400},
		{"_XXXX", -400},
		{"XXXXX", -1600},
		{"_OO(?=(_))", 3},
		{"_OO(?=(X))", 2},
	    {"XOO(?=(_))", 2},
		{"XOO(?=(X))", 1},
		{"__OOO(?=(_))", 15},
		{"_OOO(?=(__))", 15},
	    {"_OO_O(?=(_))", 12},
		{"_O_OO(?=(_))", 12},
		{"OOOO(?=(_))", 155},
		{"OOO_O", 150},
		{"OO_OO", 150},
		{"O_OOO", 150},
		{"_OOOO", 155},
	    {"OOOOO", 2000},
		{"OX", 1},
		{"XO", 1}
        //endregion
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

	public static final int[] offset = {-2,-1,0,1,2};
    public void move(int[][] board){
		final int SIZE = board.length;

		if(Arrays.stream(board).flatMapToInt(Arrays::stream).sum() == 0){
			board[SIZE / 2][SIZE / 2] = 2; return;
		}
		List<Util.Pair<Integer,Integer>> bestMoves = new ArrayList<>();
		int bestScore = Integer.MIN_VALUE;

		for(int i = 0; i < SIZE; i++) for(int j = 0; j < SIZE; j++){
			boolean hasNeighbors = false;
			checkNeighbors: for(int dx : offset) for(int dy : offset){
				int x = i + dx, y = j + dy;
				if(x < 0 || x >= SIZE || y < 0 || y >= SIZE) continue;
				if(board[x][y] > 0){hasNeighbors = true; break checkNeighbors;}
			}
			if(!hasNeighbors || board[i][j] > 0) continue;

			board[i][j] = 2;
			int score = score(board);
			if(score > bestScore){
				bestMoves.clear();
				bestMoves.add(new Util.Pair<>(i, j));
			}
			else if(score == bestScore) bestMoves.add(new Util.Pair<>(i, j));
			board[i][j] = 0;
		}

		var move = bestMoves.get((int)(Math.random() * bestMoves.size()));
		board[move.first][move.second] = 2;
	}

    public int score(int[][]board){
		int score = 0;
		String rows = rows(board);
		String cols = cols(board);
		String diag1 = diagonals(board,false);
		String diag2 = diagonals(board,true);
		for(Map.Entry<String,Integer> a: SCORING_MAP.entrySet()){
			int count = 0;
			for(char c:  rows.replaceAll(a.getKey(), "!").toCharArray()) if(c == '!') count++;
			for(char c:  cols.replaceAll(a.getKey(), "!").toCharArray()) if(c == '!') count++;
			for(char c: diag1.replaceAll(a.getKey(), "!").toCharArray()) if(c == '!') count++;
			for(char c: diag2.replaceAll(a.getKey(), "!").toCharArray()) if(c == '!') count++;
			score += count * a.getValue();
		}
		return score;
    }


	private static final char[] m = {'_','X','O'};
	private String diagonals(int[][] arr, boolean direction){
		StringBuilder res = new StringBuilder();
		int i,j,x,y,l = arr.length;
		for (i = l - 1; i > 0; i--) {
			for (j = 0, x = i; x < l; j++, x++)
				res.append(m[arr[direction ? l - x - 1 : x][j]]);
			res.append('|');
		}
		for (i = 0; i < l; i++) {
			for (j = 0, y = i; y < l; j++, y++)
				res.append(m[arr[direction ? l - j - 1 : j][y]]);
			res.append('|');
		}
		return res.toString();
	}
	private String rows(int[][] arr){
		StringBuilder res = new StringBuilder();
		int i,j,l = arr.length;
		for(i = 0; i < l; i++){
			for(j = 0; j < l; j++) res.append(m[arr[i][j]]);
			res.append('|');
		}
		return res.toString();
	}
	private String cols(int[][] arr){
		StringBuilder res = new StringBuilder();
		int i,j,l = arr.length;
		for(i = 0; i < l; i++){
			for(j = 0; j < l; j++) res.append(m[arr[j][i]]);
			res.append('|');
		}
		return res.toString();
	}
}
