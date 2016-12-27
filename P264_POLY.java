package P264_POLY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class P264_POLY {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int numOfSquare = in.nextInt();
			
			POLY algospot = new POLY(numOfSquare);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class POLY {
	
	final int MOD = 10*1000*1000;
	
	int numOfSquare;
	int[][] cache;
	
	public POLY(int numOfSquare){
		this.numOfSquare = numOfSquare;
		this.cache = new int[numOfSquare+1][numOfSquare+1];
		for(int i=0; i<cache.length; i++){
			Arrays.fill(cache[i], -1);
		}
	}
	
	public int solve(){
		
		int answer = 0;
		for(int i=1; i<=numOfSquare; i++){
			answer += poly(numOfSquare, i);
			answer %= MOD;
		}
		
		return answer;
	}
	
	private int poly(int n, int first){
		
		if(n == first)
			return 1;
		
		if(cache[n][first] != -1)
			return cache[n][first];
		
		int ret = 0;
		for(int second=1; second<=(n-first); second++){
			int add = first + second - 1;
			add *= poly(n-first, second);
			add %= MOD;
			ret += add;
			ret %= MOD;
		}
		
		cache[n][first] = ret;
		return ret;
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