package P602_CHRISTMAS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class P602_CHRISTMAS {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int numOfBox = in.nextInt();
			int numOfChildren = in.nextInt();
			int[] doll = new int[numOfBox];
			for(int j=0; j<doll.length; j++){
				doll[j] = in.nextInt();
			}
			
			CHRISTMAS algospot = new CHRISTMAS(numOfBox, numOfChildren, doll);
			int[] answer = algospot.solve();
			for(int e : answer)
				out.print(e + " ");
			out.println();
		}
		
		out.close();
	}
}

class CHRISTMAS {
	
	final int MOD = 20091101;
	
	int numOfBox;
	int numOfChildren;
	int[] doll;
	int[] pSum;
	
	public CHRISTMAS(int numOfBox, int numOfChildren, int[] doll){
		this.numOfBox = numOfBox;
		this.numOfChildren = numOfChildren;
		this.doll = doll;
		this.pSum = new int[numOfBox+1];
		
		pSum[0] = 0;
		for(int i=1; i<pSum.length; i++){
			pSum[i] = (pSum[i-1] + doll[i-1]) % numOfChildren;
		}
	}
	
	public int[] solve(){
		
		int[] answer = new int[2];
		
		answer[0] = waysToBuy();
		answer[1] = maxBuys();
		
		return answer;
	}
	
	private int waysToBuy(){
		
		long[] count = new long[numOfChildren];
		for(int i=0; i<pSum.length; i++){
			count[pSum[i]]++;
		}
		
		long ret = 0;
		for(int i=0; i<count.length; i++){
			if(count[i] >= 2){
				ret = (ret + (count[i]*(count[i]-1) / 2)) % MOD;
			}
		}
		
		return (int)ret;
	}
	
	private int maxBuys() {
	
		int[] ret = new int[pSum.length];
		int[] pre = new int[numOfChildren];
		Arrays.fill(pre, -1);
		
		for(int i=0; i<ret.length; i++){
			if(i == 0){
				ret[i] = 0;
			}
			else{
				ret[i] = ret[i-1];
			}
			
			int loc = pre[pSum[i]];
			if(loc != -1){
				ret[i] = Math.max(ret[i], ret[loc] + 1);
			}
			
			pre[pSum[i]] = i;
		}
		
		return ret[ret.length-1];
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