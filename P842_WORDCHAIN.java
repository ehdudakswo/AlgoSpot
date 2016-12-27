package P842_WORDCHAIN;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class P842_WORDCHAIN {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);

		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int numOfWord = in.nextInt();
			List<String> wordList = new ArrayList<String>(numOfWord);
			for(int j=0; j<numOfWord; j++){
				wordList.add(in.next());
			}
			
			WORDCHAIN algospot = new WORDCHAIN(wordList);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class WORDCHAIN{
	
	final String WRONG = "IMPOSSIBLE";
	final int numOfVertex = 26;
	
	List<String> wordList;
	int[][] adj;
	List<String>[][] graph;
	
	int[] indegree;
	int[] outdegree;
	List<Integer> chainVertexList;
	
	@SuppressWarnings("unchecked")
	public WORDCHAIN(List<String> wordList){
		
		this.wordList = wordList;
		this.adj = new int[numOfVertex][numOfVertex];
		this.graph = new List[numOfVertex][numOfVertex];
		for(int i=0; i<graph.length; i++){
			for(int j=0; j<graph[i].length; j++){
				graph[i][j] = new ArrayList<String>();
			}
		}
		
		this.indegree = new int[numOfVertex];
		this.outdegree = new int[numOfVertex];
		chainVertexList = new ArrayList<Integer>(wordList.size() + 1);
		
		for(int i=0; i<wordList.size(); i++){
			String word = wordList.get(i);
			char first = word.charAt(0);
			char last = word.charAt(word.length()-1);
			
			int from = (first - 'a');
			int to = (last - 'a');
			
			adj[from][to]++;
			graph[from][to].add(word);
			outdegree[from]++;
			indegree[to]++;
		}
	}
	
	public String solve(){
		
		if(!checkEuler())
			return WRONG;
		
		int startVertex = getStartVertex();
		getChainVertexList(startVertex);
		
		if(chainVertexList.size() != (wordList.size() + 1))
			return WRONG;
		
		String chainWords = getChainWords();
		return chainWords;
	}
	
	private boolean checkEuler(){
		
		int plus = 0;
		int minus = 0;
		for(int i=0; i<numOfVertex; i++){
			int diff = outdegree[i] - indegree[i];
			if(diff > 1 || diff < -1)
				return false;
			else if(diff == 1)
				plus++;
			else if(diff == -1)
				minus++;
		}
		
		if( (plus==0 && minus==0) || (plus==1 && minus==1) )
			return true;
		
		return false;
	}
	
	private int getStartVertex(){
		
		for(int i=0; i<numOfVertex; i++){
			int diff = outdegree[i] - indegree[i];
			if(diff == 1){
				return i;
			}
		}
		
		for(int i=0; i<numOfVertex; i++){
			if(outdegree[i] > 0 || indegree[i] > 0){
				return i;
			}
		}
		
		return -1;
	}
	
	private void getChainVertexList(int from){
		
		for(int to=0; to<numOfVertex; to++){
			if(adj[from][to] > 0){
				adj[from][to]--;
				getChainVertexList(to);
			}
		}
		chainVertexList.add(from);
	}
	
	private String getChainWords(){
		
		Collections.reverse(chainVertexList);
		StringBuffer wordList = new StringBuffer();
		for(int i=0; i<chainVertexList.size()-1; i++){
			int from = chainVertexList.get(i);
			int to = chainVertexList.get(i+1);
			String word = graph[from][to].remove(0);
			if(wordList.length() > 0)
				wordList.append(" ");
			wordList.append(word);
		}
		
		return wordList.toString();
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