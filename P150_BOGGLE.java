package P150_BOGGLE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class P150_BOGGLE {
	public static void main(String[] args) {
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		final int sizeOfBoard = 5;
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++) {
			char[][] board = new char[sizeOfBoard][sizeOfBoard];
			for(int j=0; j<board.length; j++) {
				String str = in.next();
				for(int k=0; k<str.length(); k++) {
					board[j][k] = str.charAt(k);
				}
			}
			
			int numOfWord = in.nextInt();
			List<String> wordList = new ArrayList<String>(numOfWord);
			for(int j=0; j<numOfWord; j++) {
				wordList.add(in.next());
			}
			
			BOGGLE algospot = new BOGGLE(board, wordList);
			List<String> answer = algospot.solve();
			for(int j=0; j<answer.size(); j++){
				out.println(answer.get(j));
			}
		}
		
		out.close();
	}
}

class BOGGLE {
	
	char[][] board;
	List<String> wordList;
	
	String target;
	boolean find;
	boolean[][][] cache;
	
	public BOGGLE(char[][] board, List<String> wordList) {
		this.board = board;
		this.wordList = wordList;
	}
	
	public List<String> solve() {
		
		List<String> answer = new ArrayList<String>();
		
		for(int i=0; i<wordList.size(); i++) {
			target = wordList.get(i);
			find = false;
			cache = new boolean[5][5][target.length()];
			
			boggle();
			if(find)
				answer.add(target + " " + "YES");
			else
				answer.add(target + " " + "NO");
			
		}
		
		return answer;
	}
	
	private void boggle(){
		
		for(int y=0; y<board.length; y++){
			for(int x=0; x<board[y].length; x++){
				if(!find)
					search(y, x, 0);
			}
		}
	}
	
	private void search(int y, int x, int step){
		
		if(find)
			return;
		
		if(x < 0 || y < 0 || x > 4 || y > 4)
			return;
		
		if(board[y][x] != target.charAt(step))
			return;
		
		if(step == target.length()-1) {
			find = true;
			return;
		}
		
		if(cache[y][x][step] == true)
			return;
		
		search(y-1, x-1, step+1);
		search(y-1, x, step+1);
		search(y-1, x+1, step+1);
		search(y, x-1, step+1);
		search(y, x+1, step+1);
		search(y+1, x-1, step+1);
		search(y+1, x, step+1);
		search(y+1, x+1, step+1);
		
		cache[y][x][step] = true;
	}
}

class InputReader {
	
	private BufferedReader reader;
	private StringTokenizer tokenizer;

    public InputReader(InputStream stream) {
        reader = new BufferedReader(new InputStreamReader(stream));
        tokenizer = null;
    }

    public String next() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }
    
    public long nextLong() {
        return Long.parseLong(next());
    }
    
    public double nextDouble() {
    	return Double.parseDouble(next());
    }
}