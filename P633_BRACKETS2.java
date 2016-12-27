package P633_BRACKETS2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.StringTokenizer;


public class P633_BRACKETS2 {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			String str = in.next();
			
			BRACKETS2 algospot = new BRACKETS2(str);
			out.println(algospot.solve());
			
		}
		out.close();
	}
}

class BRACKETS2{
	
	String str;
	
	BRACKETS2(String str){
		this.str = str;
	}
	
	public String solve(){
		
		final String ANSWER = "YES";
		final String WRONG = "NO";
		
		String open  = "({[";
		String close = ")}]";
		
		Stack<Character> stack = new Stack<Character>();
		for(int i=0; i<str.length(); i++){
			char ch = str.charAt(i);
			if(open.indexOf(ch) != -1){
				stack.push(ch);
			}
			else{
				if(stack.isEmpty())
					return WRONG;
				
				char openCh = stack.pop();
				if(open.indexOf(openCh) != close.indexOf(ch))
					return WRONG;
			}
		}
		
		if(!stack.isEmpty())
			return WRONG;
		
		return ANSWER;
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