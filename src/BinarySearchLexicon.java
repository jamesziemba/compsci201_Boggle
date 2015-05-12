import java.util.*;



public class BinarySearchLexicon implements ILexicon {

	private ArrayList<String> myWords;

	public BinarySearchLexicon() {
		myWords = new ArrayList<String>();
	}

	public void load(Scanner s) {
		myWords.clear();
		while (s.hasNext()){
			myWords.add(s.next().toLowerCase());
		}
		Collections.sort(myWords);
	}

	public void load(ArrayList<String> list) {
		myWords.clear();
		myWords.addAll(list);
		Collections.sort(myWords);
	}

	public LexStatus wordStatus(StringBuilder s) {
		return wordStatus(s.toString());
	}

	public LexStatus wordStatus(String s) {
		int BS = Collections.binarySearch(myWords, s);
		if(myWords.size()==(BS*(-1)-1)){
			return LexStatus.NOT_WORD;
		}
		if(BS>-1){
			return LexStatus.WORD;
		}
		if(myWords.get(BS*(-1)-1).startsWith(s)){
			return LexStatus.PREFIX;
		}
		else{
			return LexStatus.NOT_WORD;
		}
//		return LexStatus.NOT_WORD;


	}

	public Iterator<String> iterator() {
		return myWords.iterator();
	}

	public int size() {
		return myWords.size();
	}

}
