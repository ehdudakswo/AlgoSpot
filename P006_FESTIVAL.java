package P006_FESTIVAL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class P006_FESTIVAL {
	
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int N = in.nextInt();
			int L = in.nextInt();
			int[] arr = new int[N];
			for(int j=0; j<arr.length; j++){
				arr[j] = in.nextInt();
			}
			
			FESTIVAL algospot = new FESTIVAL(arr, N, L);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class FESTIVAL {
	
	final int MAX = 101;
	
	int[] arr;
	int N;
	int L;
	int[] psum;
	
	public FESTIVAL(int[] arr, int N, int L){
		this.arr = arr;
		this.N = N;
		this.L = L;
		
		this.psum = new int[N];
		psum[0] = arr[0];
		for(int i=1; i<psum.length; i++){
			psum[i] = psum[i-1] + arr[i];
		}
	}
	
	public double solve(){
		
		double min = MAX;
		for(int i=L; i<=N; i++){
			for(int j=0; j+i<=N; j++){
				min = Math.min(min, getAvg(arr, j, j+i));
			}
		}
		
		return min;
	}
	
	private double getAvg(int[] arr, int a, int b){
		
		int sum = psum[b-1] - psum[a];
		sum += arr[a];
		
		double ret = (double)sum/(b-a);
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