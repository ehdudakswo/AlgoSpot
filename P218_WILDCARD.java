package P218_WILDCARD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class P218_WILDCARD {
	
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			String wildcardStr = in.next();
			int numOfFileName = in.nextInt();
			
			String[] fileNameArr = new String[numOfFileName];
			for(int j=0; j<numOfFileName; j++){
				fileNameArr[j] = in.next();
			}
			
			WILDCARD algospot = new WILDCARD(wildcardStr, fileNameArr);
			List<String> answer = algospot.solve();
			Collections.sort(answer);
			for(String e : answer)
				out.println(e);
		}
		
		out.close();
	}
}

class WILDCARD {
	
	String wildcardStr;
	String[] fileNameArr;

	String fileName;
	int[][] cache;
	
	public WILDCARD(String wildcardStr, String[] fileNameArr){
		this.wildcardStr = wildcardStr;
		this.fileNameArr = fileNameArr;
		this.cache = new int[101][101];
	}
	
	public List<String> solve(){
		
		List<String> answer = new ArrayList<String>();
		
		for(int i=0; i<fileNameArr.length; i++){
			initCache();
			fileName = fileNameArr[i];
			if(match(0, 0) == 1)
				answer.add(fileName);
		}
		
		return answer;
	}
	
	private void initCache() {
		
		for(int i=0; i<cache.length; i++){
			Arrays.fill(cache[i], -1);
		}
	}
	
	private int match(int w, int s){
		
		if(cache[w][s] != -1)
			return cache[w][s];			
		
		while(w < wildcardStr.length() && s < fileName.length() && 
				(wildcardStr.charAt(w) == fileName.charAt(s) || wildcardStr.charAt(w) == '?')){
			w++;
			s++;
		}
		
		if(w == wildcardStr.length()){
			if(s == fileName.length()){
				return cache[w][s] = 1;
			}
			
			return cache[w][s] = 0;
		}
		
		if(wildcardStr.charAt(w) == '*'){
			for(int skip=0; (s+skip)<=fileName.length(); skip++){
				if(match(w+1, s+skip) == 1){
					return cache[w][s] = 1;
				}
			}
		}
		
		return cache[w][s] = 0;
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