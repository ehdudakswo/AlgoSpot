package P891_CHILDRENDAY;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class P891_CHILDRENDAY {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			String listOfDigit = in.next();
			int numOfChildren = in.nextInt();
			int numOfGreedy = in.nextInt();
			
			CHILDRENDAY algospot = new CHILDRENDAY(listOfDigit, numOfChildren, numOfGreedy);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class CHILDRENDAY{
	
	String listOfDigit;
	int numOfChildren;
	int numOfGreedy;
	
	public CHILDRENDAY(String listOfDigit, int numOfChildren, int numOfGreedy){
		this.listOfDigit = listOfDigit;
		this.numOfChildren = numOfChildren;
		this.numOfGreedy = numOfGreedy;
	}
	
	public String solve(){
		
		char[] sortDigitArr = listOfDigit.toCharArray();
		Arrays.sort(sortDigitArr);
		listOfDigit = new String(sortDigitArr);
		
		int[] choice = new int[numOfChildren*2];
		Arrays.fill(choice, -1);
		
		int[] parent = new int[numOfChildren*2];
		Arrays.fill(parent, -1);
		parent[0] = 0;
		
		Queue<Integer> queue = new LinkedList<Integer>();	
		queue.add(0);
		
		while(!queue.isEmpty()){
			int popVertex = queue.poll();
			for(int i=0; i<listOfDigit.length(); i++){
				int digit = listOfDigit.charAt(i) - '0';
				int to = append(popVertex, digit, numOfChildren);
				if(parent[to] != -1)
					continue;
				
				parent[to] = popVertex;
				choice[to] = digit;
				queue.add(to);
			}
		}
		
		int vertex = numOfChildren + numOfGreedy;
		if(parent[vertex] == -1)
			return "IMPOSSIBLE";
		
		StringBuffer sb = new StringBuffer();
		while(parent[vertex] != vertex){
			sb.append(choice[vertex]);
			vertex = parent[vertex];
		}
		
		return sb.reverse().toString();
	}
	
	private int append(int vertex, int digit, int mod){
		
		int to = vertex*10 + digit;
		if(to < mod)
			return to;
		
		return (to % mod) + mod;
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