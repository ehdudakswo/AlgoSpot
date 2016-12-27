package P912_HANOI4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class P912_HANOI4 {
	
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		final int numOfPole = 4;
		final int MAX_DISK = 12;
		int[] cnt = new int[1 << (MAX_DISK*2)];
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int numOfDisk = in.nextInt();
			int[][] pole = new int[numOfPole][];
			for(int j=0; j<pole.length; j++){
				int disks = in.nextInt();
				pole[j] = new int[disks];
				for(int k=0; k<pole[j].length; k++){
					pole[j][k] = in.nextInt();
				}
			}

			HANOI4 algospot = new HANOI4(pole, numOfDisk, cnt);
			int answer = algospot.solve();
			out.println(answer);
		}
		
		out.close();
	}
}

class HANOI4 {
	
	int[][] pole;
	int numOfDisk;
	int[] cnt;
	
	public HANOI4(int[][] pole, int numOfDisk, int[] cnt) {
		this.pole = pole;
		this.numOfDisk = numOfDisk;
		this.cnt = cnt;
		Arrays.fill(cnt, 0);
	}
	
	public int solve() {
		
		int begin = getBeginState();
		int end = getEndState();
		
		int answer = bfs(begin, end);
		return answer;
	}
	
	private int bfs(int begin, int end){
		
		if(begin == end)
			return 0;
		
		Queue<Integer> queue = new LinkedList<Integer>();
		
		queue.add(begin);
		cnt[begin] = 1;
		
		queue.add(end);
		cnt[end] = -1;
		
		while(!queue.isEmpty()){
			
			int from = queue.poll();
			int[] top = {-1, -1, -1, -1};
			for(int i=numOfDisk-1; i>=0; i--){
				top[get(from, i)] = i;
			}
			
			for(int i=0; i<pole.length; i++){
				if(top[i] != -1){
					for(int j=0; j<pole.length; j++){
						if(i != j && top[j] == -1 || top[j] > top[i]){
							int to = set(from, top[i], j);
							if(cnt[to] == 0){
								queue.add(to);
								cnt[to] = increase(cnt[from]);
							}
							else if(sign(cnt[from]) != sign(cnt[to])){
								int ret = Math.abs(cnt[from]) + Math.abs(cnt[to]) - 1;
								return ret;
							}
						}
					}
				}
			}
		}
		
		return -1;
	}
	
	private int increase(int num){
		
		if(num < 0)
			return num-1;
		
		return num+1;
	}
	
	private int sign(int num) {
		
		if(num < 0)
			return -1;
		else if(num > 0)
			return 1;
		
		return 0;
	}

	private int getBeginState() {
		
		int begin = 0;
		
		for(int i=0; i<pole.length; i++){
			for(int j=0; j<pole[i].length; j++){
				begin = set(begin, pole[i][j]-1, i);
			}
		}
		
		return begin;
	}
	
	private int getEndState() {
		
		int end = 0;
		
		for(int i=0; i<pole.length; i++){
			for(int j=0; j<pole[i].length; j++){
				end = set(end, pole[i][j]-1, 3);
			}
		}
		
		return end;
	}

	private int set(int state, int idx, int value) {
		
		int ret = (state & ~(3 << idx*2)) | (value << idx*2);
		return ret;
	}
	
	private int get(int state, int idx){
		
		int ret = (state >> idx*2) & 3;
		return ret;
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