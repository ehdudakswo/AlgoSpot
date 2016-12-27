package P236_JLIS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class P236_JLIS {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int len1 = in.nextInt();
			int len2 = in.nextInt();
			
			int[] arr1 = new int[len1];
			int[] arr2 = new int[len2];
			
			for(int j=0; j<arr1.length; j++){
				arr1[j] = in.nextInt();
			}
			for(int j=0; j<arr2.length; j++){
				arr2[j] = in.nextInt();
			}
			
			JLIS algospot = new JLIS(arr1, arr2);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class JLIS{
	
	int[] arr1;
	int[] arr2;
	int[][] cache;
	
	public JLIS(int[] arr1, int[] arr2){
		this.arr1 = arr1;
		this.arr2 = arr2;
		
		int maxLen = Math.max(arr1.length, arr2.length);
		this.cache = new int[maxLen+1][maxLen+1];
	}
	
	public int solve(){
		
		int answer = jlis(-1, -1) - 2;
		return answer;
	}
	
	private int jlis(int idx1, int idx2){
		
		if(cache[idx1+1][idx2+1] != 0)
			return cache[idx1+1][idx2+1];
		
		int ret = 2;
		cache[idx1+1][idx2+1] = ret;
		long max = getMax(idx1, idx2);
		
		for(int nextIdx=idx1+1; nextIdx<arr1.length; nextIdx++){
			if(arr1[nextIdx] > max){
				ret = Math.max(ret, jlis(nextIdx, idx2) + 1);
				cache[idx1+1][idx2+1] = ret;
			}
		}
		
		for(int nextIdx=idx2+1; nextIdx<arr2.length; nextIdx++){
			if(arr2[nextIdx] > max){
				ret = Math.max(ret, jlis(idx1, nextIdx) + 1);
				cache[idx1+1][idx2+1] = ret;
			}
		}
		
		return ret;
	}
	
	private long getMax(int idx1, int idx2){
		
		long max;
		
		if(idx1 == -1 && idx2 == -1)
			max = Long.MIN_VALUE;
		else if(idx1 == -1 && idx2 != -1)
			max = arr2[idx2];
		else if(idx1 != -1 && idx2 == -1)
			max = arr1[idx1];
		else
			max = Math.max(arr1[idx1], arr2[idx2]);
		
		return max;
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