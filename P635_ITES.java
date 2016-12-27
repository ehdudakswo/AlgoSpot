package P635_ITES;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class P635_ITES {
	public static void main(String[] args){
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int partialSum = in.nextInt();
			int numOfSignal = in.nextInt();
			
			ITES algospot = new ITES(partialSum, numOfSignal);
			out.println(algospot.solve());
		}
		out.close();
	}
}

class ITES{
	int partialSum;
	int numOfSignal;
	RandNumGenerator rng;
	
	public ITES(int partialSum, int numOfSignal){
		this.partialSum = partialSum;
		this.numOfSignal = numOfSignal;
		this.rng = new RandNumGenerator(1983);
	}
	
	public int solve(){
		
		int cnt = 0;
		int sumOfNum = 0;
		Queue<Integer> queue = new LinkedList<Integer>();
		
		for(int i=0; i<numOfSignal; i++){
			int newNum = rng.next();
			queue.add(newNum);
			sumOfNum += newNum;
			
			while(sumOfNum > partialSum){
				sumOfNum -= queue.poll();
			}
			if(sumOfNum == partialSum){
				cnt++;
			}
		}
		
		return cnt;
	}
	
	class RandNumGenerator{
		final long MOD = 1L<<32;
		long num;
				
		public RandNumGenerator(int init){
			this.num = init;
		}
		
		public int next(){
			int ret = (int)(num % 10000 + 1);
			num = (num * 214013 + 2531011) % MOD;
			return ret;
		}
	}
}

class InputReader {
    public BufferedReader reader;
    public StringTokenizer tokenizer;

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