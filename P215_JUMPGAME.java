package P215_JUMPGAME;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class P215_JUMPGAME {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int len = in.nextInt();
			int[][] arr = new int[len][len];
			for(int j=0; j<arr.length; j++){
				for(int k=0; k<arr[j].length; k++){
					arr[j][k] = in.nextInt();
				}
			}
			
			JUMPGAME algospot = new JUMPGAME(arr);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class JUMPGAME {
	
	int[][] arr;
	int[][] cache;
	
	public JUMPGAME(int[][] arr){
		this.arr = arr;
		this.cache = new int[arr.length][arr.length];
		for(int i=0; i<cache.length; i++){
			Arrays.fill(cache[i], -1);
		}
	}
	
	public String solve(){
		
		if(jump(0, 0))
			return "YES";
		
		return "NO";
	}
	
	private boolean jump(int y, int x){
		
		if(y > arr.length-1 || x > arr.length-1)
			return false;
		
		if(y == arr.length-1 && x == arr.length-1)
			return true;
		
		if(cache[y][x] != -1){
			if(cache[y][x] == 1)
				return true;
			return false;
		}

		int jumpSize = arr[y][x];
		boolean ret = (jump(y+jumpSize, x) || jump(y, x+jumpSize));
		if(ret)
			cache[y][x] = 1;
		else
			cache[y][x] = 0;
		
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