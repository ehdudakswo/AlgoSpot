package P981_TPATH;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class P981_TPATH {
	
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
				int weight = in.nextInt();
				edgeList.add(new Edge(from, to, weight));
			}
			
			TPATH algospot = new TPATH(numOfVertex, edgeList);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class TPATH {
	
	final int INF = 987654321;
	
	int numOfVertex;
	List<Edge> edgeList;
	
	public TPATH(int numOfVertex, List<Edge> edgeList){
		this.numOfVertex = numOfVertex;
		this.edgeList = edgeList;
	}
	
	public int solve(){
		
		Collections.sort(edgeList, new WeightCompare());
		
		int ret = INF;
		for(int i=0; i<edgeList.size(); i++){
			Edge edge = edgeList.get(i);
			int weight = edge.weight;
			ret = Math.min(ret, kruskalMinUpperBound(weight, i) - weight);
		}
		
		return ret;
	}
	
	private int kruskalMinUpperBound(int minWeight, int idx){
		
		DisjointSet sets = new DisjointSet(numOfVertex);
		for(int i=idx; i<edgeList.size(); i++){
			Edge edge = edgeList.get(i);			
			sets.merge(edge.from, edge.to);
			if(sets.find(0) == sets.find(numOfVertex-1))
				return edge.weight;
		}
		
		return INF;
	}
	
	class WeightCompare implements Comparator<Edge>{
		public int compare(Edge obj1, Edge obj2){
			return obj1.weight - obj2.weight;
		}
	}
	
}

class DisjointSet{
	
	int[] parent;
	int[] rank;
	
	public DisjointSet(int num){
		this.parent = new int[num];
		this.rank = new int[num];
		
		for(int i=0; i<parent.length; i++){
			parent[i] = i;
		}
	}
	
	public void merge(int v1, int v2){
		
		int v1Top = find(v1);
		int v2Top = find(v2);
		
		if(v1Top == v2Top)
			return;

		if(rank[v1Top] < rank[v2Top]){
			parent[v1Top] = v2Top;
		}
		else if(rank[v1Top] > rank[v2Top]){
			parent[v2Top] = v1Top;
		}
		else{
			parent[v1Top] = v2Top;
			rank[v2Top] += 1;
		}
	}
	
	public int find(int v){
		
		if(v == parent[v])
			return v;
		
		return parent[v] = find(parent[v]);
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