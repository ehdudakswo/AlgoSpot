package P501_PASS486;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class P501_PASS486 {
	
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		PASS486 algospot = new PASS486();
//		algospot.setMinFactor();
		algospot.setFactorCnt();
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int numOfFactor = in.nextInt();
			int low = in.nextInt();
			int high = in.nextInt();
			
			out.println(algospot.solve(numOfFactor, low, high));
		}
		
		out.close();
	}
}

class PASS486 {
	
	final int MAX = 10*1000*1000;

//	int[] minFactor;
//	int[] minFactorPow;
	int[] factorCnt;
	
	public PASS486(){		
//		this.minFactor = new int[MAX+1];
//		this.minFactorPow = new int[MAX+1];
		this.factorCnt = new int[MAX+1];
	}
	
	public int solve(int numOfFactor, int low, int high){
		
		int cnt = 0;
		for(int i=low; i<=high; i++){
			if(factorCnt[i] == numOfFactor)
				cnt++;
		}
		
		return cnt;
	}
	
//	public void setMinFactor(){
//		
//		minFactor[0] = minFactor[1] = -1;
//		for(int i=2; i<=MAX; i++)
//			minFactor[i] = i;
//		
//		int sqrt = (int)Math.sqrt(MAX);
//		for(int i=2; i<=sqrt; i++){
//			if(i == minFactor[i]){
//				for(int j=i*i; j<=MAX; j+=i){
//					if(j == minFactor[j])
//						minFactor[j] = i;
//				}
//			}
//		}
//	}
//	
//	public void setFactorCnt(){
//		
//		factorCnt[1] = 1;
//		
//		for(int i=2; i<=MAX; i++){
//			if(i == minFactor[i]){
//				minFactorPow[i] = 1;
//				factorCnt[i] = 2;
//			}
//			else{
//				int pre = i / minFactor[i];
//				if(minFactor[pre] == minFactor[i]){
//					minFactorPow[i] = minFactorPow[pre] + 1;
//					factorCnt[i] = factorCnt[pre] / (minFactorPow[pre] + 1) * (minFactorPow[i] + 1);
//				}
//				else{
//					minFactorPow[i] = 1;
//					factorCnt[i] = factorCnt[pre] * 2;
//				}
//			}
//		}
//	}
	
	public void setFactorCnt(){
		
		for(int i=1 ; i<=MAX ; i++){
			for(int j=i; j<=MAX; j+=i){
				factorCnt[j]++;
			}
		}
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