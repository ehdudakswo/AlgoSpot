package P702_NERD2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class P702_NERD2 {
	public static void main(String[] args) throws IOException{
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int numOfCase = in.nextInt();
		for(int i=0; i<numOfCase; i++){
			int numOfParticipant = in.nextInt();
			List<Participant> participantList = new ArrayList<Participant>(numOfParticipant);
			for(int j=0; j<numOfParticipant; j++){
				int numOfProblem = in.nextInt();
				int numOfRamen = in.nextInt();
				participantList.add(new Participant(numOfProblem, numOfRamen));
			}
			
			NERD2 algospot = new NERD2(participantList);
			out.println(algospot.solve());
		}
		out.close();
	}
}

class Participant{
	
	int numOfProblem;
	int numOfRamen;
	
	public Participant(int numOfProblem, int numOfRamen){
		this.numOfProblem = numOfProblem;
		this.numOfRamen = numOfRamen;
	}
}

class ProblemComparator implements Comparator<Participant>{
	public int compare(Participant o1, Participant o2){
		return o1.numOfProblem - o2.numOfProblem;
	}
}

class NERD2{
	
	List<Participant> participantList;
	
	public NERD2(List<Participant> participantList){
		this.participantList = participantList;
	}
	
	public int solve(){
		
		int sumOfParticipant = 0;
		TreeSet<Participant> participantSet = new TreeSet<Participant>(new ProblemComparator());
		
		for(int i=0; i<participantList.size(); i++){
			Participant participant = participantList.get(i);
			addParticipant(participantSet, participant);
			sumOfParticipant += participantSet.size();
		}
		
		return sumOfParticipant;
	}
	
	private boolean addParticipant(TreeSet<Participant> participantSet, Participant participant){
		
		Participant nextParticipant = participantSet.ceiling(participant);
		if(nextParticipant != null && nextParticipant.numOfRamen > participant.numOfRamen)
			return false;
		
		while(true){
			Participant preParticipant = participantSet.floor(participant);
			if(preParticipant == null){
				participantSet.add(participant);
				break;
			}
			else{
				if(preParticipant.numOfRamen > participant.numOfRamen){
					participantSet.add(participant);
					break;
				}
				else{
					participantSet.remove(preParticipant);
				}
			}
		}
		
		return true;
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