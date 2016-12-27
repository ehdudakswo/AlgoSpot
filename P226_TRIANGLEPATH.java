package P226_TRIANGLEPATH;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class P226_TRIANGLEPATH {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int numOfLine = in.nextInt();
			int[][] triangle = new int[numOfLine][numOfLine];
			
			for(int j=0; j<triangle.length; j++){
				for(int k=0; k<triangle[j].length; k++){
					if(j >= k){
						triangle[j][k] = in.nextInt();
					}
				}
			}
			
			TRIANGLEPATH algospot = new TRIANGLEPATH(triangle);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class TRIANGLEPATH {
	
	int[][] triangle;
	int[][] cache;
	
	public TRIANGLEPATH(int[][] triangle){
		this.triangle = triangle;
		this.cache = new int[triangle.length][triangle.length];
	}
	
	public int solve(){
		
		int answer = maxCost(0, 0);
		return answer;
	}
	
	private int maxCost(int y, int x){
		
		if(cache[y][x] != 0)
			return cache[y][x];
		
		if(y >= triangle.length-1)
			return triangle[y][x];
		
		int ret = Math.max(maxCost(y+1, x), maxCost(y+1, x+1)) + triangle[y][x];
		cache[y][x] = ret;
		
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