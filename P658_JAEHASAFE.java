package P658_JAEHASAFE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class P658_JAEHASAFE {
	
	public static void main(String[] args) {
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		while(numOfCase-- > 0) {
			int numOfShift = in.nextInt();
			
			List<String> dialList = new ArrayList<String>(numOfShift+1);
			for(int i=0; i<numOfShift+1; i++) {
				dialList.add(in.next());
			}
			
			JAEHASAFE algospot = new JAEHASAFE();
			out.println(algospot.solve(dialList));
		}
		
		out.close();
	}
}

class JAEHASAFE {
	
	public int solve(List<String> dialList) {
		
		int ret = 0;
		for(int i=0; i<dialList.size()-1; i++) {
			String dial1 = dialList.get(i);
			String dial2 = dialList.get(i+1);
			
			if(i % 2 == 0)
				ret += kmpSearch(dial2+dial2, dial1).get(0);
			else
				ret += kmpSearch(dial1+dial1, dial2).get(0);
		}
		
		return ret;
	}
	
	private List<Integer> kmpSearch(String source, String target) {
		
		List<Integer> ret = new ArrayList<Integer>();
		int[] partial = getPartialMatch(target);

		int begin = 0;
		int match = 0;
		
		while(begin <= (source.length() - target.length())) {
			if(match < target.length() && source.charAt(begin+match) == target.charAt(match)) {
				match++;
				if(match == target.length())
					ret.add(begin);
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
		
		return ret;
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