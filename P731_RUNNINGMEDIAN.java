package P731_RUNNINGMEDIAN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class P731_RUNNINGMEDIAN {
	
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int len = in.nextInt();
			int a = in.nextInt();
			int b = in.nextInt();
			
			RUNNINGMEDIAN algospot = new RUNNINGMEDIAN(len, a, b);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class RUNNINGMEDIAN {
	
	final int MOD = 20090711;
	
	int len;
	RNG rng;
	
	public RUNNINGMEDIAN(int len, int a, int b){
		this.len = len;
		this.rng = new RNG(a, b);
	}
	
	public int solve(){
		
		int answer = 0;
		
		PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(new DescendingCompare());
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
		
		for(int i=0; i<len; i++){
			if(maxHeap.size() == minHeap.size())
				maxHeap.add(rng.next());
			else
				minHeap.add(rng.next());
			
			if(!maxHeap.isEmpty() && !minHeap.isEmpty() && maxHeap.peek() > minHeap.peek()){
				int num1 = maxHeap.poll();
				int num2 = minHeap.poll();
				maxHeap.add(num2);
				minHeap.add(num1);
			}
			
			answer = (maxHeap.peek() + answer) % MOD;
		}
		
		return answer;
	}
}

class DescendingCompare implements Comparator<Integer> {
	public int compare(Integer obj1, Integer obj2){
		return obj2.intValue() - obj1.intValue();
	}
}

class RNG {
	
	final int MOD = 20090711;
	
	long num;
	int a;
	int b;
	
	public RNG(int a, int b){
		this.num = 1983;
		this.a = a;
		this.b = b;
	}
	
	public int next(){
		int ret = (int)num;
		num = (num*a + b) % MOD;
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