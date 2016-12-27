package P239_PI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class P239_PI {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			String num = in.next();
			PI algospot = new PI(num);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class PI {
	
	final int INF = 100000000;
	
	String num;
	int[] cache;
	
	public PI(String num){
		this.num = num;
		this.cache = new int[num.length()];
	}
	
	public int solve(){
		
		int answer = memorize(0);
		return answer;
	}
	
	private int memorize(int begin){
		
		if(begin == num.length())
			return 0;
		
		if(cache[begin] != 0)
			return cache[begin];
		
		int ret = INF;
		for(int L=3; L<=5; L++){
			int nextIdx = begin + L;
			if(nextIdx <= num.length()){
				ret = Math.min(ret, memorize(nextIdx) + classify(begin, nextIdx - 1));
				cache[begin] = ret;
			}
		}
		
		return ret;
	}
	
	private int classify(int a, int b){
		
		int diff = num.charAt(a) - num.charAt(a+1);
		boolean progressive = true;
		for(int i=a; i<b; i++){
			if(num.charAt(i) - num.charAt(i+1) != diff){
				progressive = false;
				break;
			}
		}
		
		if(progressive && diff == 0)
			return 1;
		
		if(progressive && (diff == 1 || diff == -1))
			return 2;
		
		boolean alternating = true;
		for(int i=a; i<=b; i+=2){
			if(num.charAt(i) != num.charAt(a)){
				alternating = false;
				break;
			}
		}
		for(int i=a+1; i<=b; i+=2){
			if(num.charAt(i) != num.charAt(a+1)){
				alternating = false;
				break;
			}
		}
		
		if(alternating)
			return 4;
		
		if(progressive)
			return 5;
		
		return 10;
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