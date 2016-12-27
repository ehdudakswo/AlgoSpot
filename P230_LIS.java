package P230_LIS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class P230_LIS {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int len = in.nextInt();
			int[] arr = new int[len];
			for(int j=0; j<arr.length; j++){
				arr[j] = in.nextInt();
			}
			
			LIS algospot = new LIS(arr);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class LIS {
	
	int[] arr;
	int[] cache;
	
	public LIS(int[] arr){
		this.arr = arr;
		this.cache = new int[arr.length + 1];
	}
	
	public int solve(){
		
		int answer = lis(-1) - 1;
		return answer;
	}
	
	private int lis(int idx){
		
		if(cache[idx+1] != 0)
			return cache[idx+1];
		
		int ret = 1;
		
		for(int i=idx+1; i<arr.length; i++){
			if(idx == -1 || arr[i] > arr[idx]){
				ret = Math.max(ret, lis(i) + 1);
				cache[idx+1] = ret;
			}
		}
		
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
