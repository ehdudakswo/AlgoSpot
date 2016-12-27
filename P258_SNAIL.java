package P258_SNAIL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class P258_SNAIL {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int[] arr = new int[2];
			arr[0] = in.nextInt();
			arr[1] = in.nextInt();
			
			SNAIL algospot = new SNAIL(arr);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class SNAIL {
		
	int depthOfWell;
	int daysOfRainySeason;
	
	double[][] cache;
	
	public SNAIL(int[] arr){
		this.depthOfWell = arr[0];
		this.daysOfRainySeason = arr[1];
		this.cache = new double[daysOfRainySeason+1][depthOfWell*2+1];
		for(int i=0; i<cache.length; i++){
			Arrays.fill(cache[i], -1);
		}
	}
	
	public double solve(){
		
		double answer = climb(0, 0);
		return answer;
	}
	
	private double climb(int days, int climbed){
		
		if(cache[days][climbed] != -1)
			return cache[days][climbed];
		
		if(days >= daysOfRainySeason){
			if(climbed >= depthOfWell)
				return 1;
			else
				return 0;
		}
		
		double ret = 0;
		ret += climb(days+1, climbed+1) * 0.25;
		ret += climb(days+1, climbed+2) * 0.75;
		
		cache[days][climbed] = ret;
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