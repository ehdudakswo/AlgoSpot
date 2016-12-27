package P948_TIMETRIP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class P948_TIMETRIP {
	
	public static void main(String[] args){
		
		final int UNREACHABLE = 987654321;
		final int INF = 123456789;
		
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
			
			TIMETRIP algospot = new TIMETRIP(UNREACHABLE, INF, numOfVertex, edgeList);
			int[] answer = algospot.solve();
			
			for(int j=0; j<answer.length; j++){
				if(answer[j] == UNREACHABLE){
					out.print("UNREACHABLE");
					break;
				}
				else if(answer[j] == -INF || answer[j] == INF)
					out.print("INFINITY" + " ");
				else
					out.print(answer[j] + " ");
			}
			out.println();
		}
		
		out.close();
	}
}

class TIMETRIP {

	final int UNREACHABLE;
	final int INF;
	
	int numOfVertex;
	List<List<Edge>> adj;
	
	public TIMETRIP(int UNREACHABLE, int INF, int numOfVertex, List<Edge> edgeList){
		
		this.UNREACHABLE = UNREACHABLE;
		this.INF = INF;
		this.numOfVertex = numOfVertex;
		this.adj = new ArrayList<List<Edge>>();
		
		for(int i=0; i<numOfVertex; i++){
			adj.add(new ArrayList<Edge>());
		}
		
		for(int i=0; i<edgeList.size(); i++){
			Edge edge = edgeList.get(i);
			adj.get(edge.from).add(edge);
		}
	}
	
	public int[] solve(){
		
		int minTime = bellman2(0, 1);
		reverseWeightSign();
		int maxTime = bellman2(0, 1);
		maxTime *= -1;
		
		int[] answer = new int[2];
		answer[0] = minTime;
		answer[1] = maxTime;
		
		return answer;
	}
	
	private int bellman2(int start, int end){
		
		int[] upper = new int[numOfVertex];
		Arrays.fill(upper, INF);
		upper[start] = 0;
		
		for(int iter=0; iter<numOfVertex-1; iter++){
			for(int from=0; from<adj.size(); from++){
				for(int i=0; i<adj.get(from).size(); i++){
					Edge edge = adj.get(from).get(i);
					int to = edge.to;
					int weight = edge.weight;
					upper[to] = Math.min(upper[to], upper[from] + weight);
				}
			}
		}
		
		if(!reachable(start, end))
			return UNREACHABLE;
		
		for(int from=0; from<adj.size(); from++){
			for(int i=0; i<adj.get(from).size(); i++){
				Edge edge = adj.get(from).get(i);
				int to = edge.to;
				int weight = edge.weight;
				if(weight + upper[from] < upper[to]){
					if(reachable(start, from) && reachable(from, end))
						return -INF;
				}
			}
		}
		
		return upper[end];
	}
	
	private boolean reachable(int start, int end){
		
		boolean[] visit = new boolean[numOfVertex];
		visit[start] = true;
		
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(start);
		
		while(!queue.isEmpty()){
			int from = queue.poll();
			for(int i=0; i<adj.get(from).size(); i++){
				Edge edge = adj.get(from).get(i);
				int to = edge.to;
				if(to == end)
					return true;
				if(!visit[to]){
					visit[to] = true;
					queue.add(to);
				}
			}
		}
		
		return false;
	}
	
	private void reverseWeightSign(){
		
		for(int i=0; i<adj.size(); i++){
			for(int j=0; j<adj.get(i).size(); j++){
				Edge edge = adj.get(i).get(j);
				edge.weight *= -1;
			}
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