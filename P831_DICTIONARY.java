package P831_DICTIONARY;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class P831_DICTIONARY {
	public static void main(String[] args){
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			
			int numOfWord = in.nextInt();
			List<String> wordList = new ArrayList<String>(numOfWord);
			for(int j=0; j<numOfWord; j++){
				String word = in.next();
				wordList.add(word);
			}
			
			DICTIONARY algospot = new DICTIONARY(wordList);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class DICTIONARY{
	
	final String WRONG = "INVALID HYPOTHESIS";
	List<String> wordList;
	
	public DICTIONARY(List<String> wordList){
		this.wordList = wordList;
	}
	
	public String solve(){
		
		Graph g = new Graph(wordList);
		
		String answer = g.getOrder();
		if(answer == null)
			return WRONG;
		
		return answer;
	}
}

class Graph{
	
	final int numOfVertex = 26;
	int[][] adj;
	
	boolean[] visited;
	StringBuffer order;
	
	public Graph(List<String> wordList){
		
		this.adj = new int[numOfVertex][numOfVertex];
		this.visited = new boolean[numOfVertex];
		this.order = new StringBuffer(numOfVertex);
		
		for(int i=0; i<wordList.size()-1; i++){
			String word1 = wordList.get(i);
			String word2 = wordList.get(i+1);
			
			int minLen = Math.min(word1.length(), word2.length());
			for(int j=0; j<minLen; j++){
				char ch1 = word1.charAt(j);
				char ch2 = word2.charAt(j);
				if(ch1 != ch2){
					int from = ch1 - 'a';
					int to = ch2 - 'a';
					adj[from][to] = 1;
					break;
				}
			}
		}
	}
	
	public String getOrder(){
		
		for(int i=0; i<numOfVertex; i++){
			if(!visited[i]){
				dfs(i);
			}
		}
				
		order.reverse();
		for(int i=1; i<order.length(); i++){
			int from = order.charAt(i);
			for(int j=i-1; j>=0; j--){
				int to = order.charAt(j);
				if(adj[from][to] == 1)
					return null;
			}
		}
		
		for(int i=0; i<order.length(); i++){
			char ch = order.charAt(i);
			order.setCharAt(i, (char)(ch + 'a'));
		}
		
		return order.toString();
	}
	
	private void dfs(int from){
		
		visited[from] = true;
		
		for(int to=0; to<numOfVertex; to++){
			if(!visited[to] && adj[from][to] == 1)
				dfs(to);
		}
		
		order.append((char)from);
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