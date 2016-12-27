package P869_MEETINGROOM;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class P869_MEETINGROOM {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			
			int numOfTeam = in.nextInt();
			List<MeetingTime> listOfTime = new ArrayList<MeetingTime>(numOfTeam*2);
			for(int j=0; j<numOfTeam; j++){
				listOfTime.add(new MeetingTime(in.nextInt(), in.nextInt()));
				listOfTime.add(new MeetingTime(in.nextInt(), in.nextInt()));
			}
			
			MEETINGROOM algospot = new MEETINGROOM(listOfTime);
			List<MeetingTime> answer = algospot.solve();
			if(answer.size() == 0){
				out.println("IMPOSSIBLE");
			}
			else{
				out.println("POSSIBLE");
				for(int j=0; j<answer.size(); j++){
					out.println(answer.get(j));
				}
			}
		}
		
		out.close();
	}
}

class MeetingTime{
	
	int start;
	int end;
	
	public MeetingTime(int start, int end){
		this.start = start;
		this.end = end;
	}
	
	public String toString(){
		String ret = start + " " + end;
		return ret;
	}
}

class MEETINGROOM{
	
	List<MeetingTime> listOfTime;
	
	public MEETINGROOM(List<MeetingTime> listOfTime){
		this.listOfTime = listOfTime;
	}
	
	public List<MeetingTime> solve(){

		Graph g = new Graph(listOfTime);
		SCC scc = new SCC(g);
		int[] sccId = scc.tarjan();
		
		int size = listOfTime.size();
		for(int i=0; i<size*2; i+=2){
			if(sccId[i] == sccId[i+1])
				return new ArrayList<MeetingTime>();
		}
		
		List<MeetingTime> answer = new ArrayList<MeetingTime>(size);
		for(int i=0; i<size*2; i+=4){
			int idx = i/2;
			if(sccId[i] > sccId[i+1])
				idx += 1;
			
			answer.add(listOfTime.get(idx));
		}
		
		return answer;
	}
}

class Graph{
	
	List<Integer>[] adj;
	
	public Graph(List<MeetingTime> listOfMeetingTime){
		
		int size = listOfMeetingTime.size();
		
		this.adj = new List[size*2];
		for(int i=0; i<adj.length; i++){
			adj[i] = new ArrayList<Integer>();
		}
		
		for(int i=0; i<size; i+=2){
			int from = (i*2) + 1;
			int to = from + 1;
			adj[from].add(to);
			adj[to+1].add(from-1);
		}
		
		for(int i=0; i<size; i++){
			for(int j=i+1; j<size; j++){
				MeetingTime time1 = listOfMeetingTime.get(i);
				MeetingTime time2 = listOfMeetingTime.get(j);
				if(!disjoint(time1, time2)){
					int from = (i*2);
					int to = (j*2) + 1;
					adj[from].add(to);
					adj[to-1].add(from+1);
				}
			}
		}
	}
	
	private boolean disjoint(MeetingTime time1, MeetingTime time2){
		return ((time1.end <= time2.start) || (time2.end <= time1.start));
	}
	
	public List<Integer>[] getAdj(){
		return adj;
	}
}

class SCC{
	
	List<Integer>[] adj;
	int numOfVertex;
	
	int vertexCnt;
	int sccCnt;
	int[] sccId;
	int[] discovered;
	Stack<Integer> stack;
	
	public SCC(Graph g){
		this.adj = g.getAdj();
		this.numOfVertex = adj.length;
		
		this.sccId = new int[numOfVertex];
		Arrays.fill(sccId, -1);
		
		this.discovered = new int[numOfVertex];
		Arrays.fill(discovered, -1);

		this.stack = new Stack<Integer>();
	}
	
	public int[] tarjan(){
		
		for(int i=0; i<numOfVertex; i++){
			if(discovered[i] == -1)
				scc(i);
		}
		
		return sccId;
	}
	
	private int scc(int from){
		
		int ret = discovered[from] = vertexCnt++;
		stack.push(from);
		
		for(int i=0; i<adj[from].size(); i++){
			int to = adj[from].get(i);
			
			if(discovered[to] == -1)
				ret = Math.min(ret, scc(to));
			else if(sccId[to] == -1)
				ret = Math.min(ret, discovered[to]);
		}
		
		if(ret == discovered[from]){
			while(true){
				int popVertex = stack.pop();
				sccId[popVertex] = sccCnt;
				if(popVertex == from){
					break;
				}
			}
			sccCnt++;
		}
		
		return ret;
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