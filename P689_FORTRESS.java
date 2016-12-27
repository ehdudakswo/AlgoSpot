package P689_FORTRESS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class P689_FORTRESS {
	
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int numOfWall = in.nextInt();
			int[] x = new int[numOfWall];
			int[] y = new int[numOfWall];
			int[] radius = new int[numOfWall];
			
			for(int j=0; j<numOfWall; j++){
				x[j] = in.nextInt();
				y[j] = in.nextInt();
				radius[j] = in.nextInt();
			}
			
			FORTRESS algospot = new FORTRESS(numOfWall, x, y, radius);
			int answer = algospot.solve();
			out.println(answer);
		}
		
		out.close();
	}
}

class FORTRESS {
	
	int numOfWall;
	int[] x;
	int[] y;
	int[] radius;
	
	Node[] nodes;
	int maxMove;
	
	public FORTRESS(int numOfWall, int[] x, int[] y, int[] radius){
		
		this.numOfWall = numOfWall;
		this.x = x;
		this.y = y;
		this.radius = radius;
		
		this.nodes = new Node[numOfWall];
		for(int i=0; i<nodes.length; i++){
			nodes[i] = new Node(i);
		}
		
		this.maxMove = 0;
	}
	
	public int solve(){
		
		for(int i=0; i<nodes.length; i++){
			setChild(i);
		}
		
		getHeight(nodes[0]);
		return maxMove;
	}
	
	private void setChild(int root){
		
		for(int i=0; i<numOfWall; i++){
			if(isChild(root, i)){
				nodes[root].childs.add(nodes[i]);
			}
		}
	}
	
	private boolean isChild(int a, int b){
		
		if(!encloses(a, b))
			return false;
		
		for(int i=0; i<numOfWall; i++){
			if(i == a || i == b)
				continue;
			
			if(encloses(a, i) && encloses(i, b))
				return false;
		}
		
		return true;
	}
	
	private boolean encloses(int a, int b){
		
		if(radius[a] <= radius[b])
			return false;
		
		double distance = getDistance(x[a], y[a], x[b], y[b]);
		int radiusDiff = radius[a] - radius[b];
		if(distance > radiusDiff)
			return false;
		
		return true;
	}
	
	private double getDistance(int x1, int y1, int x2, int y2){
		
		double ret = Math.sqrt(Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2));
		return ret;
	}
	
	private int getHeight(Node node){
		
		if(node.childs.size() == 0)
			return 0;
		
		int[] heights = new int[node.childs.size()];
		for(int i=0; i<node.childs.size(); i++){
			heights[i] = getHeight(node.childs.get(i));
		}
		
		Arrays.sort(heights);
		int lastIdx = heights.length-1;
		int ret = heights[lastIdx] + 1;
		
		if(node.childs.size() == 1){
			maxMove = Math.max(maxMove, ret);
		}
		else{
			maxMove = Math.max(maxMove, heights[lastIdx] + heights[lastIdx-1] + 2);
		}
		
		return ret;
	}
}

class Node {
	
	int idx;
	List<Node> childs;
	
	public Node(int idx){
		this.idx = idx;
		this.childs = new ArrayList<Node>();
	}
	
	public void setChild(Node node){
		childs.add(node);
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