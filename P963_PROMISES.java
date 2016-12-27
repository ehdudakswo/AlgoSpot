package P963_PROMISES;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class P963_PROMISES {
	
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int numOfVertex = in.nextInt();
			int numOfExist = in.nextInt();
			int numOfPlan = in.nextInt();
			
			List<Edge> existList = new ArrayList<Edge>(numOfExist);
			for(int j=0; j<numOfExist; j++){
				existList.add(new Edge(in.nextInt(), in.nextInt(), in.nextInt()));
			}
			
			List<Edge> planList = new ArrayList<Edge>(numOfPlan);
			for(int j=0; j<numOfPlan; j++){
				planList.add(new Edge(in.nextInt(), in.nextInt(), in.nextInt()));
			}
			
			PROMISES algospot = new PROMISES(numOfVertex, existList, planList);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class PROMISES {

	int numOfVertex;
	List<Edge> existList;
	List<Edge> planList;
	
	public PROMISES(int numOfVertex, List<Edge> existList, List<Edge> planList){
		this.numOfVertex = numOfVertex;
		this.existList = existList;
		this.planList = planList;
	}
	
	public int solve(){
		
		int answer = 0;
		
		Graph g = new Graph(numOfVertex, existList);
		for(int i=0; i<planList.size(); i++){
			Edge edge = planList.get(i);
			if(!g.update(edge.from, edge.to, edge.weight))
				answer++;
		}
		
		return answer;
	}
}

class Graph {
	
	final int INF = 987654321;
	
	int numOfVertex;
	int[][] adj;
	
	public Graph(int numOfVertex, List<Edge> existList){
		this.numOfVertex = numOfVertex;
		this.adj = new int[numOfVertex][numOfVertex];
		
		for(int i=0; i<adj.length; i++){
			for(int j=0; j<adj[i].length; j++){
				if(i == j)
					adj[i][j] = 0;
				else
					adj[i][j] = INF;
			}
		}
		
		for(int i=0; i<existList.size(); i++){
			Edge edge = existList.get(i);
			adj[edge.from][edge.to] = edge.weight;
			adj[edge.to][edge.from] = edge.weight;
		}
	}
	
	public boolean update(int from, int to, int weight){
		
		if(adj[from][to] <= weight)
			return false;
		
		for(int i=0; i<numOfVertex; i++){
			for(int j=0; j<numOfVertex; j++){
				int addWeight = Math.min(adj[i][from] + weight + adj[to][j], adj[i][to] + weight + adj[from][j]);
				adj[i][j] = Math.min(adj[i][j], addWeight);
			}
		}
		
		return true;
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