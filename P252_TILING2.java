package P252_TILING2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class P252_TILING2 {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);

		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int width = in.nextInt();
			
			TILING2 algospot = new TILING2(width);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class TILING2 {
	
	final int MOD = 1000000007;
	
	int width;
	int[] cache;
	
	public TILING2(int width){
		this.width = width;
		this.cache = new int[width+1];
		Arrays.fill(cache, -1);
	}
	
	public int solve(){
		
		int answer = tiling(width);
		return answer;
	}
	
	private int tiling(int width){
		
		if(width <= 1)
			return 1;
		
		if(cache[width] != -1)
			return cache[width];
		
		int ret = (tiling(width-2) + tiling(width-1)) % MOD;
		cache[width] = ret;
		
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
