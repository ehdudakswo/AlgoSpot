package P507_POTION;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class P507_POTION {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int numOfIngredient = in.nextInt();
			
			int[] recipe = new int[numOfIngredient];
			for(int j=0; j<recipe.length; j++){
				recipe[j] = in.nextInt();
			}
			
			int[] put = new int[numOfIngredient];
			for(int j=0; j<put.length; j++){
				put[j] = in.nextInt();
			}
			
			POTION algospot = new POTION(numOfIngredient, recipe, put);
			int[] answer = algospot.solve();
			for(int e : answer)
				out.print(e + " ");
			out.println();
		}
		
		out.close();
	}
}

class POTION {
	
	int numOfIngredient;
	int[] recipe;
	int[] put;
	
	public POTION(int numOfIngredient, int[] recipe, int[] put){
		this.numOfIngredient = numOfIngredient;
		this.recipe = recipe;
		this.put = put;
	}
	
	public int[] solve(){
		
		int[] answer = new int[numOfIngredient];
		
		int gcd = recipe[0];
		for(int i=1; i<numOfIngredient; i++){
			gcd = gcd(recipe[i], gcd);
		}
		
		int max = gcd;
		for(int i=0; i<numOfIngredient; i++){
			max = Math.max(max, ceil(put[i], recipe[i]/gcd));
		}
		
		for(int i=0; i<numOfIngredient; i++){
			answer[i] = (recipe[i]/gcd * max) - put[i];
		}
		
		return answer;
	}
	
	private int gcd(int a, int b){

		if(b == 0)
			return a;
		
		return gcd(b, a % b);
	}
	
	private int ceil(int a, int b){
		
		int ret = a / b;
		if(a % b != 0)
			ret += 1;
		
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