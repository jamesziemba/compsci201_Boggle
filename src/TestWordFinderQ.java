
import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

public class TestWordFinderQ {
    
    private IBoardMaker myMaker;
    private IWordOnBoardFinder myFinder;
    
    public class LocalBoardMaker implements IBoardMaker {

        private String[] good = {
          "atru",
          "sqan",
          "niot",
          "qdqn"
        };
        public BoggleBoard makeBoard(int rows) {
           String[] faces = new String[good.length*good.length];
           int count = 0;
           for(String s : good){
               for(int k=0; k < s.length(); k++)
               {
                   faces[count] = ""+s.charAt(k);
                   if (faces[count].charAt(0) == 'q')
                	   faces[count] = "qu";
                   count++;
               }
           }
           return new BoggleBoard(faces);
        }
    }
    
    
    @Before
    public void setUp(){
        myMaker = new LocalBoardMaker(); 
        myFinder = new GoodWordOnBoardFinder();
    }
    
    private String getWord(BoggleBoard board, List<BoardCell> list){
        String word = "";
        for(BoardCell cell : list){
            word += board.getFace(cell.row, cell.col);
            if (word.charAt(word.length()-1) == 'q')
               word += "u";
        }
        return word;
    }
    
    
    @Test
    public void testGoodCorners(){
        String[] cornerWords = {
                "not", 
                "urn", "quid","qat"
        };
        BoggleBoard board = myMaker.makeBoard(4);
        for(String s : cornerWords){
            List<BoardCell> list = myFinder.cellsForWord(board, s);
            String word = getWord(board,list);
            assertEquals("fail for "+s,s,word);
        }
    }
    @Test
    public void testBadCorners(){
        String[] cornerWords = {
            "notary", "urine", "need", "diners", "astride", "nosier",
            "kumquat", "queer"
        };
        BoggleBoard board = myMaker.makeBoard(4);
        for(String s : cornerWords){
            List<BoardCell> list = myFinder.cellsForWord(board, s);
            String word = getWord(board,list);
            assertNotSame("fail for "+s,s,word);
        }
    }
    
    @Test
    public void testNonCorners(){
        String[] words = {
            "iquot", "insert", "quiet", "train"
        };
        BoggleBoard board = myMaker.makeBoard(4);
        for(String s : words){
            List<BoardCell> list = myFinder.cellsForWord(board, s);
            String word = getWord(board, list);
            assertEquals("fail for "+s,s,word);
        }
    }
}
