package P930_ROUTING;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class P930_ROUTING {
	
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
				double cost = in.nextDouble();
				edgeList.add(new Edge(from, to, cost));
			}
			
			ROUTING algospot = new ROUTING(numOfVertex, edgeList);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class ROUTING {
	
	int numOfVertex;
	List<Edge> edgeList;
	
	public ROUTING(int numOfVertex, List<Edge> edgeList){
		this.numOfVertex = numOfVertex;
		this.edgeList = edgeList;
	}
	
	public double solve(){
		
		Graph g = new Graph(numOfVertex, edgeList);
		double[] distance = g.dijkstra(0);
		double answer = distance[numOfVertex-1];
		
		return answer;
	}
	
}

class Graph {
	
	int numOfVertex;
	List<List<Edge>> adj;
	
	public Graph(int numOfVertex, List<Edge> edgeList){
		
		this.numOfVertex = numOfVertex;
		this.adj = new ArrayList<List<Edge>>(numOfVertex);
		
		for(int i=0; i<numOfVertex; i++){
			adj.add(new ArrayList<Edge>());
		}
		
		for(int i=0; i<edgeList.size(); i++){
			Edge edge= edgeList.get(i);
			adj.get(edge.from).add(edge);
			adj.get(edge.to).add(new Edge(edge.to, edge.from, edge.weight));
		}
	}

	public double[] dijkstra(int src){
		
		double INF = Double.MAX_VALUE;
		double[] distance = new double[numOfVertex];
		Arrays.fill(distance, INF);
		distance[src] = 1;
		
		PriorityQueue<DistanceInfo> pq = new PriorityQueue<DistanceInfo>();
		pq.add(new DistanceInfo(src, distance[src]));
		
		while(!pq.isEmpty()){
			DistanceInfo disInfo = pq.poll();
			int pqVertex = disInfo.vertex;
			double pqDistance = disInfo.distance;
			if(pqDistance > distance[pqVertex])
				continue;
			
			for(int i=0; i<adj.get(pqVertex).size(); i++){
				Edge edge = adj.get(pqVertex).get(i);
				int to = edge.to;
				double nextDistance = pqDistance * edge.weight;
				if(distance[to] > nextDistance){
					distance[to] = nextDistance;
					pq.add(new DistanceInfo(to, distance[to]));
				}
			}
		}
		
		return distance;
	}
	
	class DistanceInfo implements Comparable<DistanceInfo>{
		
		int vertex;
		double distance;
		
		public DistanceInfo(int vertex, double distance){
			this.vertex = vertex;
			this.distance = distance;
		}
		
		public int compareTo(DistanceInfo disInfo){
			
			if(distance == disInfo.distance)
				return 0;
			else{
				if(distance - disInfo.distance > 0)
					return 1;
				else
					return -1;
			}
		}
	}
}

class Edge {
	
	int from;
	int to;
	double weight;
	
	public Edge(int from, int to, double weight){
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