import java.io.InputStream;
import java.util.*;

import javax.swing.ProgressMonitorInputStream;

public class BoggleStats {

	ArrayList<Integer> myBoardScores, myLexiconScores;

	private static final int MIN_WORD = 3;
	private static final int NUM_TRIALS = 10000;
	private int max = 0;
	private static BoggleBoard maxBoard;
	private static int index = -1;
	private ArrayList<BoggleBoard> collector;
	public BoggleStats(){
		myBoardScores = new ArrayList<Integer>();
		myLexiconScores = new ArrayList<Integer>();

	}

	public String wordTester(IAutoPlayer player, ILexicon lex, ArrayList<Integer> log, int count){
		ArrayList<BoggleBoard> collector = new ArrayList<BoggleBoard>();
		BoggleBoardFactory.setRandom(new Random(12345));
		log.clear();
		double start = System.currentTimeMillis();

		for(int k=0; k < count; k++){
			BoggleBoard board = BoggleBoardFactory.getBoard(4);

			player.findAllValidWords(board, lex, MIN_WORD);

			if(player.getScore()>max){
				max = player.getScore();
				maxBoard = board;
				index = k;
				//				if(index !=0){
				//					System.out.println(index);
				//					System.out.println(board);
				//				}
			}
			log.add(player.getScore());
			collector.add(board);
		}

		double end = System.currentTimeMillis();
		int max = Collections.max(log);
		return String.format("%s %s\t count: %d\tmax: %d\ttime: %f", 
				player.getClass().getName(),
				lex.getClass().getName(),count,max,(end-start)/1000.0);
	}

	public void doTests(ILexicon lex){
		IAutoPlayer ap1 = new LexiconFirstAutoPlayer(); 
		String result = wordTester(ap1,lex,myBoardScores,NUM_TRIALS);
		System.out.println(result);
		IAutoPlayer ap2 = new BoardFirstAutoPlayer();
		String result2 = wordTester(ap2,lex,myLexiconScores, NUM_TRIALS);
		System.out.println(result2);
		for(int k=0; k < NUM_TRIALS; k++) {
			if (!myBoardScores.get(k).equals(myLexiconScores.get(k))){
				System.out.println(k+"\t"+myBoardScores.get(k)+"\t"+myLexiconScores.get(k));
				//				System.out.println(collector.get(k));
			}
		}
	}

	public void runTests(ILexicon lex, ArrayList<String> list){
		lex.load(list);
		doTests(lex);

	}

	public static void main(String[] args){

		ILexicon lex = new SimpleLexicon();   
		ILexicon lex1 = new BinarySearchLexicon();



		InputStream is = lex.getClass().getResourceAsStream("/ospd3.txt");   
		ProgressMonitorInputStream pmis = StoppableReader.getMonitorableStream(is, "reading..."); 
		Scanner s = new Scanner(pmis);
		ArrayList<String> list = new ArrayList<String>();
		while (s.hasNext()){
			list.add(s.next().toLowerCase());
		}

		BoggleStats bs = new BoggleStats();
//		bs.runTests(lex,list);
//		bs.runTests(lex1,list);
		//		System.out.println("This is the index of the highest scoring Boggle Board: "+ index);
//		System.out.println("This is the highest scoring Boggle Board: ");
//		System.out.println(maxBoard);
		lex = new TrieLexicon();
		bs.runTests(lex,list);
	}
}
