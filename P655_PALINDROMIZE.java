package P655_PALINDROMIZE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class P655_PALINDROMIZE {
	
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			String str = in.next();
			
			PALINDROMIZE algospot = new PALINDROMIZE();
			out.println(algospot.solve(str));
		}
		
		out.close();
	}
}

class PALINDROMIZE {
	
	public int solve(String str){
		
		String rev = new StringBuffer(str).reverse().toString();
		int overlap = maxOverlap(str, rev);
		int answer = str.length()*2 - overlap;
		
		return answer;
	}
	
	private int maxOverlap(String str1, String str2){
		
		int[] partial = getPartialMatch(str2);
		
		int begin = 0;
		int match = 0;
		
		while(begin < str1.length()){
			if(match < str2.length() && str1.charAt(begin+match) == str2.charAt(match)){
				match++;
				if(begin+match >= str1.length())
					return match;
			}
			else{
				if(match == 0)
					begin++;
				else{
					begin += (match - partial[match-1]);
					match = partial[match-1];
				}
			}
		}
		
		return 0;
	}
	
	private int[] getPartialMatch(String str) {
		
		int[] partial = new int[str.length()];
		
		int begin = 1;
		int match = 0;
		
		while(begin+match < str.length()) {
			if(str.charAt(begin+match) == str.charAt(match)) {
				match++;
				partial[begin+match-1] = match;
			}
			else {
				if(match == 0)
					begin++;
				else {
					begin += (match - partial[match-1]);
					match = partial[match-1];
				}
			}
		}
		
		return partial;
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