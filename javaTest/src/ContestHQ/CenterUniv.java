package ContestHQ;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

import java.io.InputStreamReader;

class UserSolution {
	
	static LinkedList<Integer> linkList = new LinkedList<Integer>();
	
	public void init() 
	{
		linkList.clear();
		linkList.add(500);
	}
	
	public void enroll(int A, int B) 
    {
        addSort(A);
        addSort(B);
	}

	private void addSort(int findNum) {
		
		int start = 0;
		int end = linkList.size() -1;
		
		int insIdx = 0;
		
		while( start < end) {
			int mid = (start + end) /2;
			
			if ( linkList.get(mid) <= findNum  ) {
				start = mid +1;
			}else {
				end = mid -1;
			}
		}
		
		insIdx = start;
		
		insIdx  = insIdx +  (linkList.get(insIdx) < findNum ? 1: 0) ;
		
		linkList.add(insIdx, findNum); 		
	}

	public int getMidValue()
	{
		return linkList.get(  linkList.size()/2 ); 
	}
	
	
	
}

public class CenterUniv {

	private static boolean run(BufferedReader br) throws IOException {

		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		int Q = Integer.parseInt(st.nextToken());

		usersolution.init();

		boolean isCorrect = true; 
		
		for (int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			usersolution.enroll(A, B); 
			int userAns = usersolution.getMidValue(); 
			int ans = Integer.parseInt(st.nextToken());
			if(userAns != ans)
				isCorrect = false; 
		}
		return isCorrect;
	}

	private final static UserSolution usersolution = new UserSolution();

	public static void main(String[] args) throws Exception {

		 System.setIn(new java.io.FileInputStream("src/ContestHQ/CenterUniv.txt"));

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int TC = Integer.parseInt(st.nextToken());
		int MARK = Integer.parseInt(st.nextToken());

		for (int testcase = 1; testcase <= TC; testcase++) {
			int score = run(br) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}
		br.close();
	}
}