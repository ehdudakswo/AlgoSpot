package P269_NUMB3RS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class P269_NUMB3RS {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int numOfTown = in.nextInt();
			int day = in.nextInt();
			int prison = in.nextInt();
			
			int[][] adj = new int[numOfTown][numOfTown];
			for(int j=0; j<adj.length; j++){
				for(int k=0; k<adj[j].length; k++){
					adj[j][k] = in.nextInt();
				}
			}
			
			int numOfCalcTown = in.nextInt();
			int[] calcTownArr = new int[numOfCalcTown];
			for(int j=0; j<calcTownArr.length; j++){
				calcTownArr[j] = in.nextInt();
			}
			
			NUMB3RS algospot = new NUMB3RS(adj, day, prison, calcTownArr);
			double[] answer = algospot.solve();
			for(double e : answer)
				out.print(e + " ");
			out.println();
		}
		
		out.close();
	}
}

class NUMB3RS {
	
	int[][] adj;
	int day;
	int prison;
	int[] calcTownArr;
	
	int[] degree;
	int end;
	double[][] cache;
	
	public NUMB3RS(int[][] adj, int day, int prison, int[] calcTownArr){
		this.adj = adj;
		this.day = day;
		this.prison = prison;
		this.calcTownArr = calcTownArr;

		this.degree = new int[adj.length];
		for(int i=0; i<adj.length; i++){
			int cnt = 0;
			for(int j=0; j<adj[i].length; j++){
				if(adj[i][j] > 0){
					cnt++;
				}
			}
			degree[i] = cnt;
		}
		
		this.cache = new double[adj.length+1][day+1];		
	}
	
	public double[] solve(){

		double[] answer = new double[calcTownArr.length];
		for(int i=0; i<answer.length; i++){
			initCache();
			end = calcTownArr[i];
			answer[i] = search(prison, day);
		}
		
		return answer;
	}
	
	private void initCache(){
		
		for(int i=0; i<cache.length; i++){
			Arrays.fill(cache[i], -1);
		}
	}
	
	private double search(int from, int day){
		
		if(cache[from][day] != -1)
			return cache[from][day];
		
		if(day == 0)
			return (from == end ? 1 : 0);

		double ret = 0;
		for(int to=0; to<adj[from].length; to++){
			if(adj[from][to] > 0){
				ret += (search(to, day-1) / degree[from]);
			}
		}
		
		cache[from][day] = ret;
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