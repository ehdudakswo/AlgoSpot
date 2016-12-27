package P886_SORTGAME;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

public class P886_SORTGAME {
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		ReverseCount revCnt = new ReverseCount(8);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int sizeOfList = in.nextInt();
			List<Integer> list = new ArrayList<Integer>(sizeOfList);
			for(int j=0; j<sizeOfList; j++)
				list.add(in.nextInt());
			
			SORTGAME algospot = new SORTGAME(list, revCnt);
			out.println(algospot.solve());
		}
		
		out.close();
	}
}

class SORTGAME{
	
	List<Integer> list;
	ReverseCount revCnt;
	
	public SORTGAME(List<Integer> list, ReverseCount revCnt){
		this.list = list;
		this.revCnt = revCnt;
	}
	
	public int solve(){
		
		List<Integer> perm = getNormalPerm();
		Map<List<Integer>, Integer> revCntMap = revCnt.getRevCntMap(perm.size());
		return revCntMap.get(perm);
	}
	
	private List<Integer> getNormalPerm(){
		
		List<Integer> perm = new ArrayList<Integer>(list.size());
		for(int i=0; i<list.size(); i++){
			int smaller = 0;
			for(int j=0; j<list.size(); j++){
				if(list.get(i) > list.get(j))
					smaller++;
			}
			perm.add(i, smaller);
		}
		
		return perm;
	}
}

class ReverseCount{
	
	Map<List<Integer>, Integer> revCntMap;
	
	public ReverseCount(int len){
		this.revCntMap = new HashMap<List<Integer>, Integer>();
		for(int i=1; i<=len; i++){
			calcReverseCnt(i);
		}
	}
	
	private void calcReverseCnt(int n){

		List<Integer> list = new ArrayList<Integer>(n);
		for(int i=0; i<n; i++){
			list.add(i);
		}

		revCntMap.put(list, 0);
		
		Queue<List<Integer>> queue = new LinkedList<List<Integer>>();
		queue.add(list);
		
		while(!queue.isEmpty()){
			
			List<Integer> popList = queue.poll();
			int cnt = revCntMap.get(popList);
			
			for(int i=0; i<popList.size(); i++){
				for(int j=i+2; j<=popList.size(); j++){
					reverse(popList, i, j);
					if(!revCntMap.containsKey(popList)){
						List<Integer> newList = new ArrayList<Integer>(popList);
						revCntMap.put(newList, cnt + 1);
						queue.add(newList);
					}
					reverse(popList, i, j);
				}
			}
			
		}
	}
	
	private void reverse(List<Integer> list, int start, int end){
		
		for(int i=start, j=end-1; i<=j; i++, j--){
			int temp = list.get(i);
			list.set(i, list.get(j));
			list.set(j, temp);
		}
	}
	
	public Map<List<Integer>, Integer> getRevCntMap(int n){
		return revCntMap;
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