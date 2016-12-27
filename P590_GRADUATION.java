package P590_GRADUATION;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class P590_GRADUATION {
	
	public static void main(String[] args) {
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		while(numOfCase-- > 0) {
			int numOfMajor = in.nextInt();
			int numOfRequired = in.nextInt();
			int numOfTerm = in.nextInt();
			int maxOfTaken = in.nextInt();
			
			int[][] precourse = new int[numOfMajor][];
			for(int i=0; i<precourse.length; i++) {
				int size = in.nextInt();
				precourse[i] = new int[size];
				for(int j=0; j<precourse[i].length; j++) {
					precourse[i][j] = in.nextInt();
				}
			}
	
			int[][] term = new int[numOfTerm][];
			for(int i=0; i<term.length; i++) {
				int size = in.nextInt();
				term[i] = new int[size];
				for(int j=0; j<term[i].length; j++) {
					term[i][j] = in.nextInt();
				}
			}
			
			GRADUATION algospot = new GRADUATION();
			int answer = algospot.solve(numOfMajor, precourse, term, numOfRequired, maxOfTaken);
			
			if(answer == -1)
				out.println("IMPOSSIBLE");
			else
				out.println(answer);
		}
		
		out.close();
	}
}

class GRADUATION {
	
	boolean[] isTaken;
	int[][] precourse;
	int maxOfTaken;
	int[][] term;
	int numOfRequired;
	
	public int solve(int numOfMajor, int[][] precourse, int[][] term, int numOfRequired, int maxOfTaken) {
		
		this.precourse = precourse;
		this.maxOfTaken = maxOfTaken;
		this.term = term;
		this.numOfRequired = numOfRequired;
		
		Combination comb = new Combination(term.length);
		for(int i=1; i<=term.length; i++) {
			List<List<Integer>> list = comb.get(i);
			for(int j=0; j<list.size(); j++) {
				isTaken = new boolean[numOfMajor];
				if(takens(list.get(j))) {
					return i;
				}
			}
		}
		
		return -1;
	}
	
//	public int solve(int numOfMajor, int[][] precourse, int[][] term, int numOfRequired, int maxOfTaken) {
//		
//		this.isTaken = new boolean[numOfMajor];
//		this.precourse = precourse;
//		this.maxOfTaken = maxOfTaken;
//		
//		int numOfTerm = 0;
//		int sumOfTaken = 0;
//		
//		for(int i=0; i<term.length; i++) {
//			int numOfTaken = taken(term[i]);
//			if(numOfTaken == 0)
//				continue;
//			
//			numOfTerm++;
//			sumOfTaken += numOfTaken;
//			if(sumOfTaken >= numOfRequired)
//				return numOfTerm;
//		}
//		
//		return -1;
//	}
	
	private boolean takens(List<Integer> list) {
		
		int sum = 0;
		for(int i=0; i<list.size(); i++) {
			int terms = list.get(i);
			sum += taken(term[terms]);
			if(sum >= numOfRequired)
				return true;
		}
		
		return false;
	}
	
	private int taken(int[] term) {
		
		List<Integer> newTaken = new ArrayList<Integer>();
		int numOfTaken = 0;
		
		for(int i=0; i<term.length; i++) {
			int course = term[i];
			if(canTaken(course, precourse[course])) {
				numOfTaken++;
				newTaken.add(course);
				if(numOfTaken >= maxOfTaken) 
					break;
			}
		}
		
		for(int i=0; i<newTaken.size(); i++) {
			int course = newTaken.get(i);
			isTaken[course] = true;
		}
		
		return numOfTaken;
	}
	
	private boolean canTaken(int course, int[] precourse) {
		
		if(isTaken[course])
			return false;
		
		for(int i=0; i<precourse.length; i++) {
			if(!isTaken[precourse[i]]){
				return false;
			}
		}
		
		return true;
	}
}

class Combination {
	
	List<Integer> list;
	boolean[] used;
	List<List<Integer>> combList;
	
	public Combination(List<Integer> list){
		this.list = list;
		this.used = new boolean[list.size()];
		this.combList = new ArrayList<List<Integer>>();
	}
	
	public Combination(int n){
		this.list = new ArrayList<Integer>(n);
		for(int i=0; i<n; i++) {
			list.add(i);
		}
		this.used = new boolean[list.size()];
		this.combList = new ArrayList<List<Integer>>();
	}
	
	public List<List<Integer>> get(int r){
		Arrays.fill(used, false);
		combList.clear();
		makeCombination(0, r);
		return combList;
	}
	
	private void makeCombination(int idx, int r){
		
		if(r == 0){	
			List<Integer> comb = new ArrayList<Integer>();
			for(int i=0; i<list.size(); i++){
				if(used[i])
					comb.add(list.get(i));
			}
			combList.add(comb);
			return;
		}
		
		for(int i=idx; i<=list.size()-r; i++){
			used[i] = true;
			makeCombination(i+1, r-1);
			used[i] = false;
		}
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