package P933_FIRETRUCKS;

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

public class P933_FIRETRUCKS {
	
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int numOfVertex = in.nextInt();
			numOfVertex += 1;
			int numOfEdge= in.nextInt();
			int numOfFireLocation = in.nextInt();
			int numOfFireStation = in.nextInt();
			
			List<Edge> edgeList = new ArrayList<Edge>(numOfEdge);
			for(int j=0; j<numOfEdge; j++){
				int from = in.nextInt();
				int to = in.nextInt();
				int weight = in.nextInt();
				edgeList.add(new Edge(from, to, weight));
			}
			
			int[] fireLocation = new int[numOfFireLocation];
			for(int j=0; j<fireLocation.length; j++){
				fireLocation[j] = in.nextInt();
			}
			
			int[] fireStation = new int[numOfFireStation];
			for(int j=0; j<fireStation.length; j++){
				fireStation[j] = in.nextInt();
			}
			
			FIRETRUCKS algospot = new FIRETRUCKS(numOfVertex, edgeList, fireLocation, fireStation);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class FIRETRUCKS {
	
	int numOfVertex;
	List<Edge> edgeList;
	int[] fireLocation;
	int[] fireStation;
	
	public FIRETRUCKS(int numOfVertex, List<Edge> edgeList, int[] fireLocation, int[] fireStation){
		this.numOfVertex = numOfVertex;
		this.edgeList = edgeList;
		this.fireLocation = fireLocation;
		this.fireStation = fireStation;
	}
	
	public int solve(){
		
		Graph g = new Graph(numOfVertex, edgeList);
		for(int i=0; i<fireStation.length; i++){
			int to = fireStation[i];
			g.addEdge(new Edge(0, to, 0));
		}
		
		int sumOfDistance = 0;
		int[] distance = g.dijkstra(0);
		for(int i=0; i<fireLocation.length; i++){
			int to = fireLocation[i];
			sumOfDistance += distance[to];
		}
		
		return sumOfDistance;
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
	
	public void addEdge(Edge edge){
		
		adj.get(edge.from).add(edge);
		adj.get(edge.to).add(new Edge(edge.to, edge.from, edge.weight));
	}

	public int[] dijkstra(int src){
		
		final int INF = 987654321;
		int[] distance = new int[numOfVertex];
		Arrays.fill(distance, INF);
		distance[src] = 0;
		
		PriorityQueue<DistanceInfo> pq = new PriorityQueue<DistanceInfo>();
		pq.add(new DistanceInfo(src, distance[src]));
		
		while(!pq.isEmpty()){
			DistanceInfo disInfo = pq.poll();
			int pqVertex = disInfo.vertex;
			int pqDistance = disInfo.distance;
			if(pqDistance > distance[pqVertex])
				continue;
			
			for(int i=0; i<adj.get(pqVertex).size(); i++){
				Edge edge = adj.get(pqVertex).get(i);
				int to = edge.to;
				int nextDistance = pqDistance + edge.weight;
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
		int distance;
		
		public DistanceInfo(int vertex, int distance){
			this.vertex = vertex;
			this.distance = distance;
		}
		
		public int compareTo(DistanceInfo disInfo){
			return (distance - disInfo.distance);
		}
	}
	
//	public int[] dijkstra2(int src){
//		
//		int INF = 987654321;
//		int[] distance = new int[numOfVertex];
//		Arrays.fill(distance, INF);
//		distance[src] = 0;
//		
//		boolean[] visited = new boolean[numOfVertex];
//		
//		while(true){
//			
//			int from = -1;
//			double closest = INF;
//			for(int i=0; i<distance.length; i++){
//				if(visited[i] || distance[i] == INF)
//					continue;
//				
//				if(closest > distance[i]){
//    				closest = distance[i];
//    				from = i;
//				}
//			}
//			if(closest == INF)
//				break;
//			
//			visited[from] = true;
//			List<Edge> edgeList = adj.get(from);
//			for(int i=0; i<edgeList.size(); i++){
//				int to = edgeList.get(i).to;
//				if(visited[to])
//					continue;
//				
//				int weight =  edgeList.get(i).weight;
//				if(distance[to] > distance[from] + weight){
//					distance[to] = distance[from] + weight;
//				}
//			}
//		}
//		
//		return distance;
//	}
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