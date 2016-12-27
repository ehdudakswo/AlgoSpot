package P959_DRUNKEN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class P959_DRUNKEN {
	
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfVertex = in.nextInt();
		int numOfEdge = in.nextInt();
		
		int[] delay = new int[numOfVertex];
		for(int i=0; i<delay.length; i++){
			delay[i] = in.nextInt();
		}
		
		List<Edge> edgeList = new ArrayList<Edge>(numOfEdge);
		for(int i=0; i<numOfEdge; i++){
			int from = in.nextInt()-1;
			int to = in.nextInt()-1;
			int weight = in.nextInt();
			edgeList.add(new Edge(from, to, weight));
		}
		
		DRUNKEN algospot = new DRUNKEN(numOfVertex, edgeList, delay);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int from = in.nextInt()-1;
			int to = in.nextInt()-1;
			int answer = algospot.solve(from, to);
			out.println(answer);
		}
		
		out.close();
	}
}

class DRUNKEN {
	
	Graph g;
	int[][] W;
	
	public DRUNKEN(int numOfVertex, List<Edge> edgeList, int[] delay){
		this.g = new Graph(numOfVertex, edgeList, delay);
		this.g.floyd();
		this.W = g.getW();
	}
	
	public int solve(int from, int to){
		return W[from][to];
	}
}

class Graph {
	
	final int INF = 987654321;
	
	int numOfVertex;
	List<Edge> edgeList;
	int[] delay;
	
	int[][] adj;
	int[][] W;
	
	public Graph(int numOfVertex, List<Edge> edgeList, int[] delay){
		this.numOfVertex = numOfVertex;
		this.edgeList = edgeList;
		this.delay = delay;
		
		this.adj = new int[numOfVertex][numOfVertex];
		this.W = new int[numOfVertex][numOfVertex];
		
		for(int i=0; i<adj.length; i++){
			for(int j=0; j<adj[i].length; j++){
				if(i == j){
					adj[i][j] = 0;
					W[i][j] = 0;
				}
				else{
					adj[i][j] = INF;
					W[i][j] = INF;
				}
			}
		}
		
		for(int i=0; i<edgeList.size(); i++){
			Edge edge = edgeList.get(i);
			int from = edge.from;
			int to = edge.to;
			int weight = edge.weight;
			
			adj[from][to] = weight;
			adj[to][from] = weight;
			W[from][to] = weight;
			W[to][from] = weight;
		}
	}
	
	public void floyd(){
		
		VertexDelay[] vertexDelay = new VertexDelay[numOfVertex];
		for(int i=0; i<vertexDelay.length; i++){
			vertexDelay[i] = new VertexDelay(i, delay[i]);
		}
		Arrays.sort(vertexDelay, new DelayCompare());
		
		for(int k=0; k<numOfVertex; k++){
			int v = vertexDelay[k].vertex;
			for(int i=0; i<numOfVertex; i++){
				for(int j=0; j<numOfVertex; j++){
					adj[i][j] = Math.min(adj[i][j], adj[i][v] + adj[v][j]);
					W[i][j] = Math.min(W[i][j], adj[i][v] + delay[v] + adj[v][j]);
				}
			}
		}
	}
	
	public int[][] getW(){
		return W;
	}
	
	class VertexDelay {
		int vertex;
		int delay;
		
		public VertexDelay(int v, int d){
			this.vertex = v;
			this.delay = d;
		}
	}
	
	class DelayCompare implements Comparator<VertexDelay>{
		public int compare(VertexDelay obj1, VertexDelay obj2){
			return obj1.delay - obj2.delay;
		}
	}
}

class Edge {
	
	int from;
	int to;
	int weight;
	
	public Edge(int from, int to, int weight){
		this.from = from;
		this.to = to;
		this.weight = weight;
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