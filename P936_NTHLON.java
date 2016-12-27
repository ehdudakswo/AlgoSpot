package P936_NTHLON;

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

public class P936_NTHLON {
	
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int numOfEvent = in.nextInt();
			int[] A = new int[numOfEvent];
			int[] B = new int[numOfEvent];
			for(int j=0; j<numOfEvent; j++){
				A[j] = in.nextInt();
				B[j] = in.nextInt();
			}
			
			NTHLON algospot = new NTHLON(numOfEvent, A, B);
			int answer = algospot.solve();
			if(answer == -1)
				out.println("IMPOSSIBLE");
			else
				out.println(answer);
		}
		
		out.close();
	}
}

class NTHLON {
	
	final int INF = 987654321;
	final int minTime = 1;
	final int maxTime = 200;
	
	int numOfEvent;
	int[] A;
	int[] B;
	
	public NTHLON(int numOfEvent, int[] A, int[] B){
		this.numOfEvent = numOfEvent;
		this.A = A;
		this.B = B;
	}
	
	public int solve(){
		
		int delta = maxTime - minTime;
		int numOfVertex = delta*2 + 2;
		Graph g = new Graph(numOfVertex);
		g.setEdge(A, B, delta);
		
		int start = numOfVertex-1;
		int[] distance = g.dijkstra(start);
		int answer = distance[delta];
		if(answer == INF)
			return -1;
		
		return answer;
	}
}

class Graph {
	
	int numOfVertex;
	List<List<Edge>> adj;
	
	public Graph(int numOfVertex){
		
		this.numOfVertex = numOfVertex;
		this.adj = new ArrayList<List<Edge>>(numOfVertex);
		
		for(int i=0; i<numOfVertex; i++){
			adj.add(new ArrayList<Edge>());
		}
	}
	
	public void setEdge(int[] A, int[] B, int delta){
		
		int lenOfEvent = A.length;
		for(int from=0; from<numOfVertex; from++){
			for(int j=0; j<lenOfEvent; j++){
				int diff = A[j] - B[j];
				int to = from + diff;
				if(from == numOfVertex-1)
					to -= (delta+1);
				if(to < 0 || to > numOfVertex-2)
					continue;
				
				adj.get(from).add(new Edge(from, to, A[j]));
			}
		}
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