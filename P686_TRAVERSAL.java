package P686_TRAVERSAL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class P686_TRAVERSAL {
	public static void main(String[] args){
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			
			int numOfNode = in.nextInt();
			int[] preorder = new int[numOfNode];
			int[] inorder = new int[numOfNode];
			
			for(int j=0; j<preorder.length; j++)
				preorder[j] = in.nextInt();
			
			for(int j=0; j<inorder.length; j++)
				inorder[j] = in.nextInt();
			
			
			TRAVERSAL algospot = new TRAVERSAL(preorder, inorder);
			int[] answer = algospot.solve();
			
			for(int e : answer)
				out.print(e + " ");
			out.println();
			
		}
		out.close();
	}
}

class TRAVERSAL{
	
	int[] preorder;
	int[] inorder;
	List<Integer> postList;
	
	public TRAVERSAL(int[] preorder, int[] inorder){
		this.preorder = preorder;
		this.inorder = inorder;
		this.postList = new ArrayList<Integer>();
	}
	
	public int[] solve(){
		
		getPostorder(preorder, inorder);
		
		int[] postorder = new int[postList.size()];
		for(int i=0; i<postList.size(); i++)
			postorder[i] = postList.get(i);
		
		return postorder;
	}
	
	private void getPostorder(int[] preorder, int[] inorder){
		
		if(preorder == null || inorder == null)
			return;
		
		int root = preorder[0];
		int idx = getIdx(inorder, root);
		
		int[] leftPreorder = split(preorder, 1, idx+1);
		int[] rightPreorder = split(preorder, idx+1, preorder.length);
		
		int[] leftInorder = split(inorder, 0, idx);
		int[] rightInorder = split(inorder, idx+1, inorder.length);
		
		getPostorder(leftPreorder, leftInorder);
		getPostorder(rightPreorder, rightInorder);
		postList.add(root);
	}
	
	private int getIdx(int[] inorder, int root){
		
		for(int i=0; i<inorder.length; i++){
			if(inorder[i] == root)
				return i;
		}
		
		return -1;
	}
	
	private int[] split(int[] arr, int start, int end){
		
		int len = end - start;
		if(len <= 0)
			return null;
		
		int[] splitArr = new int[len];
		for(int i=0, j=start; i<len; i++, j++){
			splitArr[i] = arr[j];
		}
		
		return splitArr;
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