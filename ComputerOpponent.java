// import java.util.Map;
import java.util.stream.*;
import java.util.*;

public class ComputerOpponent {
    
	private GameLogic logic;

    //This is where the magic happens - tells the computer how advantageous a given
    //configuration is
    private static final Map<String, Integer> SCORING_MAP = Stream.of(new Object[][] {
    //region patterns and their corresponding scores
		
		//Guaranteed win for computer immediately
	    	{"OOOOO", 1000000000},
		//Guaranteed win for player immediately
			{"XXXX_", -100000000},
	    	{"XXX_X", -100000000},
			{"XX_XX", -100000000},
			{"X_XXX", -100000000},
			{"_XXXX", -100000000},
		//Guaranteed win for computer in 2 moves
		{"OOOO_", 1000},
		{"OOO_O", 1000},
		{"OO_OO", 1000},
		{"O_OOO", 1000},
		{"_OOOO", 1000},
        {"_XX(?=(_))", -4},
        {"_XX(?=(O))", -2},
        {"OXX(?=(_))", -2},
        {"OXX(?=(O))", -1},
        {"__XXX(?=(_))", -100},
        {"_XXX(?=(__))", -100},
	    {"_XX_X(?=(_))", -100},
        {"_X_XX(?=(_))", -100},
		{"_OO(?=(_))", 3},
		{"_OO(?=(X))", 2},
	    {"XOO(?=(_))", 2},
		{"XOO(?=(X))", 1},
		{"__OOO(?=(_))", 15},
		{"_OOO(?=(__))", 15},
	    {"_OO_O(?=(_))", 12},
		{"_O_OO(?=(_))", 12},
		{"OX", 1},
		{"XO", 1}
        //endregion
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));


	public ComputerOpponent(GameLogic logic){this.logic = logic;}

    public void move(){
		final int l = GameLogic.BOARD_SIZE;

		//If board is empty, go in middle space
		if(logic.isEmpty()){
			logic.setPieceAt(this, l / 2, l / 2, 2); return;
		}

		//Store best moves & score so far
		List<Util.Pair<Integer,Integer>> bestMoves = new ArrayList<>();
		int bestScore = Integer.MIN_VALUE;

		//For every empty space on the board
		for(int i = 0; i < l; i++) for(int j = 0; j < l; j++){
			if(logic.hasPieceAt(i, j)) continue;

			//Set the space to 2 for now
			logic.setPieceAt(this, i, j, 2);

			//See how advantageous the board is
			int score = score();

			//If it is better than the current best board(s), set it to the new best board
			if(score > bestScore){
				bestMoves.clear();
				bestMoves.add(new Util.Pair<>(i, j));
				bestScore = score;
			}
			//Otherwise if its just as good as the best board(s), add it the the set of best boards
			else if(score == bestScore) bestMoves.add(new Util.Pair<>(i, j));

			//Reset the space to 0
			logic.setPieceAt(this, i, j, 0);
		}

		//Find a random move out of the best moves
		var move = bestMoves.get((int)(Math.random() * bestMoves.size()));

		//Carry out the move
		logic.setPieceAt(this, move.first, move.second, 2);
	}

	//Find out how advantageous a given board configuration is
    public int score(){
		int score = 0;
		//All rows/columns/diagonals of the board, converted to a string and joined with '|'s
		String s = logic.getRows() + logic.getCols() + logic.getDiagonals(true) + logic.getDiagonals(false);
		//For each regex
		for(Map.Entry<String,Integer> a: SCORING_MAP.entrySet()){
			//Count occurences
			int count = 0;
			for(char c: s.replaceAll(a.getKey(), "!").toCharArray()) if(c == '!') count++;
			//Multiply by score per match
			score += count * a.getValue();
		}
		//Return total score
		return score;
    }
}
