package P259_ASYMTILING;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class P259_ASYMTILING {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int width = in.nextInt();
			
			ASYMTILING algospot = new ASYMTILING(width);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class ASYMTILING {
	
	final int MOD = 1000000007;
	
	int width;
	int[] cache;
	
	public ASYMTILING(int width){
		this.width = width;
		this.cache = new int[width+1];
		Arrays.fill(cache, -1);
	}
	
	public int solve(){
		
		int answer = asymmetric(width);
		return answer;
	}
	
	private int asymmetric(int width){
		
		if(width % 2 == 1)
			return (tiling(width) - tiling((width-1)/2) + MOD) % MOD;
		
		int ret = tiling(width);
		ret = (ret - tiling((width-2)/2) + MOD) % MOD;
		ret = (ret - tiling(width/2) + MOD) % MOD;
		
		return ret;
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