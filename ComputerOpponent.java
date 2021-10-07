import java.util.stream.*;
import java.util.*;

public class ComputerOpponent {
    
	private final GameLogic logic;

    //This is where the magic happens - tells the computer how advantageous a given
    //configuration is
    private static final Map<String, Integer> SCORING_MAP = Util.toMap(new Object[][] {
    //#region# patterns and their corresponding scores
		
		//Guaranteed win for computer immediately
	    	{"OOOOO", 1000000000},
		//Guaranteed win for player in 1 moves
			{"XXXX_", -100000000},
	    	{"XXX_X", -100000000},
			{"XX_XX", -100000000},
			{"X_XXX", -100000000},
			{"_XXXX", -100000000},
		//Guaranteed win for computer in 2 moves
			{"_OOOO_",  20000000},
		//Possible win for computer in 2 moves
			{"OOO_O", 10000000},
			{"OO_OO", 10000000},
			{"O_OOO", 10000000},
			{"_OOOO", 10000000},
		//Possible win for player in 3 moves
			{"_XXX__",-1000000},
			{"__XXX_",-1000000},
			{"_XX_X_",-1000000},
			{"_X_XX_",-1000000},
			{"_XXX_",-500000},
		//Possible win for computer in 4 moves
			{"_OOO__", 100000},
			{"__OOO_", 100000},
			{"_OO_O_", 100000},
			{"_O_OO_", 100000},
			{"_OOO_", 50000},
		//Good moves for computer
			{"__OO__", 1200},
			{"_OO__", 1000},
			{"__OO_", 1000},
			{"XXO_", 500},
			{"_OXX", 500},
		//Barely disatvantageous situations for computer
			{"__XX_", -1100},
			{"_XX__", -1100},
			{"OXO", -100},
		//Don't go in random places
			{"OX", 1},
			{"XO", 1}
	//#endregion#
    });


	public ComputerOpponent(GameLogic logic){this.logic = logic;}

    public void move(){
		final int l = GameLogic.BOARD_SIZE;

		//If board is empty, go in middle space
		if(logic.isEmpty()){
			logic.setPieceAt(this, l / 2, l / 2, GameLogic.BOARD_CELL.OPPONENT); return;
		}

		//Store best moves & score so far
		List<Util.Pair<Integer,Integer>> bestMoves = new ArrayList<>();
		int bestScore = Integer.MIN_VALUE;

		//For every empty space on the board
		for(int i = 0; i < l; i++) for(int j = 0; j < l; j++){
			if(logic.hasPieceAt(i, j)) continue;

			//Set the space to 2 for now
			logic.setPieceAt(this, i, j, GameLogic.BOARD_CELL.OPPONENT);

			//See how advantageous the board is
			int score = score();

			//If it is better than the current best board(s), set it to the new best board
			if(score > bestScore){
				bestMoves.clear();
				bestMoves.add(new Util.Pair<>(i, j));
				bestScore = score;
			}
			//Otherwise, if it's just as good as the best board(s), add it the set of best boards
			else if(score == bestScore) bestMoves.add(new Util.Pair<>(i, j));

			//Reset the space to 0
			logic.setPieceAt(this, i, j, GameLogic.BOARD_CELL.EMPTY);
		}

		//Find a random move out of the best moves
		var move = bestMoves.get((int)(Math.random() * bestMoves.size()));

		//Carry out the move
		logic.setPieceAt(this, move.first, move.second, GameLogic.BOARD_CELL.OPPONENT);
	}

	//Find out how advantageous a given board configuration is
    public int score(){
		int score = 0;
		//All rows/columns/diagonals of the board, converted to a string and joined with '|'s
		String s = logic.getRows() + logic.getCols() + logic.getDiagonals(true) + logic.getDiagonals(false);
		//For each regex
		for(Map.Entry<String,Integer> a: SCORING_MAP.entrySet()){
			//Count occurrences of each regex
			int count = 0;
			for(char c: s.replaceAll(a.getKey(), "!").toCharArray()) if(c == '!') count++;
			//Multiply by score per match
			score += count * a.getValue();
		}
		//Return total score
		return score;
    }
}
