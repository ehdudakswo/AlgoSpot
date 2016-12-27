package P254_TRIPATHCNT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class P254_TRIPATHCNT {
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
			
			TRIPATHCNT algospot = new TRIPATHCNT(triangle);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class TRIPATHCNT{
	
	int[][] triangle;
	int[][] countCache;
	int[][] costCache;
	
	public TRIPATHCNT(int[][] triangle){
		this.triangle = triangle;
		this.countCache = new int[triangle.length][triangle.length];
		this.costCache = new int[triangle.length][triangle.length];
	}
	
	public int solve(){
		
		int answer = count(0, 0);
		return answer;
	}
	
	private int count(int y, int x){
		
		if(countCache[y][x] != 0)
			return countCache[y][x];
		
		if(y >= triangle.length-1)
			return 1;
		
		int ret = 0;
		if(maxCost(y+1, x) >= maxCost(y+1, x+1))
			ret += count(y+1, x);
		if(maxCost(y+1, x) <= maxCost(y+1, x+1))
			ret += count(y+1, x+1);
		
		countCache[y][x] = ret;
		return ret;	
		
	}
	
	private int maxCost(int y, int x){
		
		if(costCache[y][x] != 0)
			return costCache[y][x];
		
		if(y >= triangle.length-1)
			return triangle[y][x];
		
		int ret = Math.max(maxCost(y+1, x), maxCost(y+1, x+1)) + triangle[y][x];
		costCache[y][x] = ret;
		
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