package P718_INSERTION;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class P718_INSERTION {
	
	public static void main(String[] args){
		
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int len = in.nextInt();
			int[] shift = new int[len];
			for(int j=0; j<shift.length; j++){
				shift[j] = in.nextInt();
			}
			
			INSERTION algospot = new INSERTION(len, shift);
			int[] answer = algospot.solve();
			for(int e : answer)
				out.print(e + " ");
			out.println();
		}
		
		out.close();
	}
}

class INSERTION {
	
	int len;
	int[] shift;
	
	public INSERTION(int len, int[] shift){
		this.len = len;
		this.shift = shift;
	}
	
	public int[] solve(){
		
		int[] answer = new int[len];
		
		TreeHeap treeHeap = new TreeHeap();
		for(int i=1; i<=len; i++){
			treeHeap.add(i);
		}
		
		for(int i=len-1; i>=0; i--){
			int order = treeHeap.size - shift[i];
			int element = treeHeap.getElement(order);
			answer[i] = element;
			treeHeap.remove(element);
		}
		
		return answer;
	}
}

class TreeHeap {
	
	Node root;
	int size;
	
	public TreeHeap(){
		this.size = 0;
		this.root = null;
	}
	
	public void add(int e){
		this.root = insert(root, new Node(e));
		this.size++;
	}
	
	private Node insert(Node root, Node newNode){
		
		if(root == null)
			return newNode;
			
		if(root.priority < newNode.priority){
			NodePair nodePair = split(root, newNode);
			newNode.setLeft(nodePair.left);
			newNode.setRight(nodePair.right);
			return newNode;
		}
		else if(newNode.elemenet < root.elemenet){
			root.setLeft(insert(root.left, newNode));
		}
		else{
			root.setRight(insert(root.right, newNode));
		}
		
		return root;
	}
	
	private NodePair split(Node root, Node newNode){
		
		if(root == null)
			return (new NodePair(null, null));
		
		if(root.elemenet < newNode.elemenet){
			NodePair nodePair = split(root.right, newNode);
			root.setRight(nodePair.left);
			return (new NodePair(root, nodePair.right));
		}
		else{
			NodePair nodePair = split(root.left, newNode);
			root.setLeft(nodePair.right);
			return (new NodePair(nodePair.left, root));
		}
	}
	
	public void remove(int element){
		root = erase(root, element);
		this.size--;
	}
	
	public Node erase(Node root, int element){
		
		if(root == null)
			return root;
		
		
		if(root.elemenet == element){
			return merge(root.left, root.right);
		}
		else if(root.elemenet > element){
			root.setLeft(erase(root.left, element));
			
		}
		else{
			root.setRight(erase(root.right, element));
		}
		
		return root;
		
	}
	
	public Node merge(Node left, Node right){
		
		if(left == null)
			return right;
		
		if(right == null)
			return left;
		
	
		if(left.priority < right.priority){
			right.setLeft(merge(left, right.left));
			return right;
		}
		else{
			left.setRight(merge(left.right, right));
			return left;
		}
		
	}
	
	public int getElement(int order){
		
		Node getNode = findByOrder(root, order);
		int retElement = getNode.elemenet;
		return retElement;
		
	}
	
	public Node findByOrder(Node root, int order){
		
		int leftSize = 0;
		if(root.left != null)
			leftSize = root.left.size();
		
		if(order == leftSize + 1){
			return root;
		}
		else if(order <= leftSize){
			return findByOrder(root.left, order);
		}
		else{
			return findByOrder(root.right, order - leftSize - 1);
		}
	}
	
	public int size(){
		return size;
	}
}

class Node {
	
	int elemenet;
	Node left, right;
	int priority;
	int size;
	
	public Node(int e){
		this.elemenet = e;
		this.left = null;
		this.right = null;
		this.priority = (int) (Math.random() * 10000000);
		this.size = 1;
	}
	
	public void setLeft(Node node){
		this.left = node;
		calcSize();
	}
	
	public void setRight(Node node){
		this.right = node;
		calcSize();
	}
	
	public void calcSize(){
		this.size = 1;
		if(left != null)
			size += left.size;
		if(right != null)
			size += right.size;
	}
	
	public int size(){
		return size;
	}
}

class NodePair {
	
	Node left, right;
	
	public NodePair(Node left, Node right){
		this.left = left;
		this.right = right;
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