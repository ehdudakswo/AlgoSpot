package P979_LAN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class P979_LAN {
	
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int numOfVertex = in.nextInt();
			int numOfInstalled = in.nextInt();
			
			int[] xArr = new int[numOfVertex];
			for(int j=0; j<xArr.length; j++){
				xArr[j] = in.nextInt();
			}
			
			int[] yArr = new int[numOfVertex];
			for(int j=0; j<yArr.length; j++){
				yArr[j] = in.nextInt();
			}
			
			List<Edge> installedList = new ArrayList<Edge>(numOfInstalled);
			for(int j=0; j<numOfInstalled; j++){
				int from = in.nextInt();
				int to = in.nextInt();
				int weight = 0;
				installedList.add(new Edge(from, to, weight));
			}
			
			LAN algospot = new LAN(numOfVertex, xArr, yArr, installedList);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class LAN {
	
	int numOfVertex;
	int[] xArr;
	int[] yArr;
	List<Edge> installedList;
	
	public LAN(int numOfVertex, int[] xArr, int[] yArr, List<Edge> installedList){
		this.numOfVertex = numOfVertex;
		this.xArr = xArr;
		this.yArr = yArr;
		this.installedList = installedList;
	}
	
	public double solve(){
		
		Graph g = new Graph(numOfVertex);
		g.setAdj(xArr, yArr, installedList);
		
		List<Edge> mstEdgeList = new ArrayList<Edge>();
		double cost = g.prim(mstEdgeList);
		
		return cost;
	}
}

class Graph {
	
	final int INF = 987654321;
	
	int numOfVertex;
	double[][] adj;
	
	public Graph(int numOfVertex){
		this.numOfVertex = numOfVertex;
		this.adj = new double[numOfVertex][numOfVertex];
	}
	
	public void setAdj(int[] xArr, int[] yArr, List<Edge> installedList){
		
		for(int i=0; i<numOfVertex; i++){
			for(int j=0; j<numOfVertex; j++){
				if(i == j){
					adj[i][j] = 0;
				}
				else{
					adj[i][j] = getDistance(xArr[i], yArr[i], xArr[j], yArr[j]);
				}
			}
		}
		
		for(int i=0; i<installedList.size(); i++){
			Edge edge = installedList.get(i);
			int from = edge.from;
			int to = edge.to;
			double weight = edge.weight;
			
			adj[from][to] = weight;
			adj[to][from] = weight;
		}
	}
	
	private double getDistance(int x1, int y1, int x2, int y2){
		
		double xDiffSq = Math.pow(x1-x2, 2);
		double yDiffSq = Math.pow(y1-y2, 2);
		double distance = Math.sqrt(xDiffSq + yDiffSq);
		
		return distance;
	}
	
	public double prim(List<Edge> mstEdgeList){
		
		double sumOfCost = 0;
		
		boolean[] visit = new boolean[numOfVertex];
		double[] minWeight = new double[numOfVertex];
		int[] parent = new int[numOfVertex];
		
		Arrays.fill(minWeight, INF);
		Arrays.fill(parent, -1);
		
		minWeight[0] = 0;
		parent[0] = 0;
		
		for(int i=0; i<numOfVertex; i++){
			
			int minVertex = -1;
			for(int j=0; j<numOfVertex; j++){
				if(!visit[j] && (minVertex == -1 || minWeight[minVertex] > minWeight[j]) ){
					minVertex = j;
				}
			}
			
			if(minVertex != parent[minVertex])
				mstEdgeList.add(new Edge(parent[minVertex], minVertex, minWeight[minVertex]));
			
			visit[minVertex] = true;
			sumOfCost += minWeight[minVertex];
			
			
			for(int j=0; j<numOfVertex; j++){
				double cost = adj[minVertex][j];
				if(visit[j] || cost == INF || minVertex == j)
					continue;
				
				if(minWeight[j] > cost){
					minWeight[j] = cost;
					parent[j] = minVertex;
				}
			}
		}
		
		return sumOfCost;
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