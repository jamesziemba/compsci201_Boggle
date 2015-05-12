import java.util.ArrayList;
import java.util.List;


public class GoodWordOnBoardFinder implements IWordOnBoardFinder {

	@Override
	public List<BoardCell> cellsForWord(BoggleBoard board, String word) {
		ArrayList<BoardCell> list = new ArrayList<BoardCell>();
		for (int r=0; r<board.size(); r++) {
			for (int c=0; c<board.size(); c++) {
				int index = 0;
				if (findHelper(word, index, r, c, board, list)) {
					return list;

				}
			}
		}
		list.clear();
		return list;
	}
	private boolean findHelper(String word, int index, int row, int col, 
			BoggleBoard board, List<BoardCell> list){

		if (row < 0 || row >= board.size()) return false;
		if (col < 0 || col >= board.size()) return false;
		if (index >= word.length()) return true;
		String currentChar = "";
		if (word.substring(index, index + 1).equalsIgnoreCase("q") && index + 1 != word.length()) {
			currentChar = word.substring(index, index + 2);
		} 
		else{
			currentChar = word.substring(index, index + 1);
		}

		String currentCellVal = board.getFace(row,col);
		BoardCell currentBoardCell = new BoardCell(row, col);

		if (!currentChar.equals(currentCellVal)) return false;
		if (list.contains(currentBoardCell)) return false;
		list.add(currentBoardCell);

		int[] rdelta = {-1,-1,-1, 0, 0, 1, 1, 1}; 
		int[] cdelta = {-1, 0, 1,-1, 1,-1, 0, 1};

		for (int j = 0; j < rdelta.length ; j++){
			if (findHelper(word, index + currentChar.length(), row + rdelta[j], col + cdelta[j], board, list)) return true;
		}
		list.remove(currentBoardCell);
		return false;
	}

}
