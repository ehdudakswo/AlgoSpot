package P620_JOSEPHUS;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class P620_JOSEPHUS {
	public static void main(String[] args){
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        
        int numOfCase = in.nextInt();
        for(int i=0; i<numOfCase; i++){
        	int numOfSoldier = in.nextInt();
        	int orderOfDeath = in.nextInt();
        	
        	JOSEPHUS algospot = new JOSEPHUS(numOfSoldier, orderOfDeath);
        	int[] answerArr = algospot.solve();
        	
        	for(int j=0; j<answerArr.length; j++){
        		out.print(answerArr[j] + " ");
        	}
        	out.println();
        }
        out.close();
	}
}

class JOSEPHUS{
	int numOfSoldier;
	int orderOfDeath;
	
	public JOSEPHUS(int numOfSoldier, int orderOfDeath){
		this.numOfSoldier = numOfSoldier;
		this.orderOfDeath = orderOfDeath;
	}
	
	public int[] solve(){
		
		List<Integer> liveList = new LinkedList<Integer>();
		for(int i=1; i<=numOfSoldier; i++){
			liveList.add(i);
		}
		
		Iterator<Integer> iter = liveList.iterator();
		iter.next();
		
		while(true){
			iter.remove();
			if(liveList.size() <= 2)
				break;
			
			for(int i=0; i<orderOfDeath; i++){
				if(!iter.hasNext())
					iter = liveList.iterator();
				iter.next();
			}
		}
		
		int[] answerArr = new int[2];
		for(int i=0; i<answerArr.length; i++){
			answerArr[i] = liveList.get(i);
		}
		return answerArr;
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