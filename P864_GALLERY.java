package P864_GALLERY;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class P864_GALLERY {
	public static void main(String[] args){
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int numOfVertex = in.nextInt();
			int numOfEdge = in.nextInt();
			
			List<Edge> edgeList = new ArrayList<Edge>(numOfEdge);
			for(int j=0; j<numOfEdge; j++){
				int from = in.nextInt();
				int to = in.nextInt();
				edgeList.add(new Edge(from, to));
			}
			
			GALLERY algospot = new GALLERY(numOfVertex, edgeList);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class GALLERY{
	
	int numOfVertex;
	List<Edge> edgeList;
	
	public GALLERY(int numOfVertex, List<Edge> edgeList){
		this.numOfVertex = numOfVertex;
		this.edgeList = edgeList;
	}
	
	public int solve(){
		Graph g = new Graph(numOfVertex, edgeList);
		return g.getMinInstall();
	}
}

class Graph{
	
	int numOfVertex;
	List<List<Edge>> adj;
	
	final int UNWATCHED = 0;
	final int WATCHED = 1;
	final int INSTALLED = 2;
	int numOfInstall = 0;
	boolean[] visited;
	
	public Graph(int numOfVertex, List<Edge> edgeList){
		this.numOfVertex = numOfVertex;
		this.adj = new ArrayList<List<Edge>>(numOfVertex);
		this.visited = new boolean[numOfVertex];
		
		for(int i=0; i<numOfVertex; i++){
			adj.add(new ArrayList<Edge>());
		}
		
		for(int i=0; i<edgeList.size(); i++){
			Edge edge = edgeList.get(i);
			int from = edge.from;
			int to = edge.to;
			
			adj.get(from).add(edge);
			adj.get(to).add(new Edge(to, from));
		}
	}
	
	public int getMinInstall(){
		
		for(int i=0; i<numOfVertex; i++){
			if(!visited[i] && dfs(i) == UNWATCHED){
				numOfInstall++;
			}
		}
		
		return numOfInstall;
	}
	
	private int dfs(int from){
		
		visited[from] = true;
		
		int[] children = {0, 0, 0};
		
		List<Edge> vertex = adj.get(from);
		for(int i=0; i<vertex.size(); i++){
			int to = vertex.get(i).to;
			if(!visited[to]){
				int state = dfs(to);
				children[state]++;
			}
		}
		
		if(children[UNWATCHED] > 0){
			numOfInstall++;
			return INSTALLED;
		}
		
		if(children[INSTALLED] > 0)
			return WATCHED;
		
		return UNWATCHED;
	}
	
}

class Edge{
	int from;
	int to;
	
	public Edge(int from, int to){
		this.from = from;
		this.to = to;
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