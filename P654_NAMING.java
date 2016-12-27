package P654_NAMING;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class P654_NAMING {
	
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		String name1 = in.next();
		String name2 = in.next();
		
		NAMING algospot = new NAMING(name1, name2);
		List<Integer> answer = algospot.solve();
		for(int e : answer)
			System.out.print(e + " ");
		System.out.println();
		
		out.close();
	}
}

class NAMING {
	
	String str;
	List<Integer> ret;
	
	public NAMING(String name1, String name2){
		this.str = name1 + name2;
		this.ret = new ArrayList<Integer>();
	}
	
	public List<Integer> solve(){
		
		int[] partial = getPartialMatch(str);
		
		int len = str.length();
		while(true){
			ret.add(len);
			len = partial[len-1];
			if(len == 0)
				break;
		}
		
		Collections.reverse(ret);
		return ret;
	}
	
	private static int[] getPartialMatch(String str) {
		
		int[] pArr = new int[str.length()];
		int m = str.length();
		int begin = 1, matched = 0;
		while(begin + matched < m){
			if(str.charAt(begin + matched) == str.charAt(matched)){
				++matched;
				pArr[begin+matched-1] = matched;
			}
			else{
				if(matched == 0)
					++begin;
				else{
					begin += (matched - pArr[matched-1]);
					matched = pArr[matched-1];
				}
			}
		}
		
		return pArr;
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