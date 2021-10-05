import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    
    public void move(int[][]board){

    }
}
