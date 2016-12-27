package P244_QUANTIZE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class P244_QUANTIZE {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int[] arr = new int[in.nextInt()];
			int numOfNumber = in.nextInt();
			
			for(int j=0; j<arr.length; j++){
				arr[j] = in.nextInt();
			}
			
			QUANTIZE algospot = new QUANTIZE(arr, numOfNumber);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class QUANTIZE {
	
	final int INF = 987654321;
	
	int[] arr;
	int numOfNumber;
	
	int[] pSum;
	int[] pSqSum;
	
	int[][] cache;
	
	public QUANTIZE(int[] arr, int numOfNumber){
		
		this.arr = arr;
		this.numOfNumber = numOfNumber;
		
		this.pSum = new int[arr.length];
		this.pSqSum = new int[arr.length];
		
		this.cache = new int[arr.length+1][numOfNumber+1];
		for(int i=0; i<cache.length; i++){
			Arrays.fill(cache[i], -1);
		}
	}
	
	public int solve(){
		
		Arrays.sort(arr);
		setPartSum();
		
		int answer = quantize(0, numOfNumber);
		return answer;
	}
	
	private void setPartSum(){
		
		pSum[0] = arr[0];
		pSqSum[0] = arr[0] * arr[0];
		
		for(int i=1; i<arr.length; i++){
			pSum[i] = pSum[i-1] + arr[i];
			pSqSum[i] = pSqSum[i-1] + (arr[i]*arr[i]);
		}
	}
	
	private int quantize(int from, int numOfPart){
		
		if(cache[from][numOfPart] != -1)
			return cache[from][numOfPart];
		
		if(from == arr.length)
			return 0;
		
		if(numOfPart == 0)
			return INF;
		
		int ret = INF;
		for(int partSize=1; from+partSize <= arr.length; partSize++){
			ret = Math.min(ret, minError(from, from+partSize-1) + quantize(from+partSize, numOfPart-1));
		}
		cache[from][numOfPart] = ret;
		
		return ret;
	}
	
	private int minError(int low, int high){

		int sum = pSum[high];
		int sqSum = pSqSum[high];
		
		if(low > 0){
			sum -= pSum[low-1];
			sqSum -= pSqSum[low-1];
		}
			
		int len = high - low + 1;
		int avg = (int)((double)sum / len + 0.5);
		int ret = len*avg*avg - 2*avg*sum + sqSum;
		
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