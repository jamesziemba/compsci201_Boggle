import java.util.ArrayList;
import java.util.List;



public class BoardFirstAutoPlayer extends AbstractAutoPlayer {
	private List<BoardCell> myList;
    @Override
    public void findAllValidWords(BoggleBoard board, ILexicon lex, int minLength) {
	    clear();
	    for(int i = 0;i<board.size();i++){
	    	for(int j = 0; j<board.size();j++){
	    		StringBuilder s = new StringBuilder();
	    		s.append("");
//	    		ArrayList<BoardCell> list = new ArrayList<BoardCell>();
	    		myList = new ArrayList<BoardCell>();
	    		findHelper(s,i,j,board,lex, minLength);
	    		myList.clear();
	    	}
	    }
	    
    }
    private void findHelper(StringBuilder word, int row, int col, 
			BoggleBoard board, ILexicon lex, int minLength){
		if (row < 0 || row >= board.size()) return;
		if (col < 0 || col >= board.size()) return;
    	String currentCellVal = board.getFace(row,col);
    	BoardCell currentBoardCell = new BoardCell(row, col);
    	if (myList.contains(currentBoardCell)) return;
    	word.append(currentCellVal);
    	if(lex.wordStatus(word.toString())==LexStatus.PREFIX ||lex.wordStatus(word.toString())==LexStatus.WORD){
    		myList.add(currentBoardCell);
    		if(lex.wordStatus(word.toString())==LexStatus.WORD && word.length()>=minLength){
    			add(word.toString());
    		}
    		int[] rdelta = {-1,-1,-1, 0, 0, 1, 1, 1}; 
    		int[] cdelta = {-1, 0, 1,-1, 1,-1, 0, 1};
    		for (int j = 0; j < rdelta.length ; j++){
    			findHelper(word,row+rdelta[j],col+cdelta[j],board,lex, minLength);
    		}
    		myList.remove(currentBoardCell);
    		
    	}
    	if(currentCellVal.equals("qu")){
			word.deleteCharAt(word.length()-1);
			word.deleteCharAt(word.length()-1);
		}
		else{
			word.deleteCharAt(word.length()-1);
		}
    }
    

}
