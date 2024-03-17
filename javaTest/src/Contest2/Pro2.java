package Contest2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class UserSolution2
{
	public void playGame(int N)
	{
		int[][] diffIdx = new int[N][4];
		
		int cardCnt = N*2;
		
		for( int i=1; i<cardCnt; i++) {
			
			int diffFindVal = binSearch(N,i);
			for( int j=0; j<4;j++) {
				if( diffIdx[diffFindVal][j]==0) {
					 diffIdx[diffFindVal][j] = i;
					 break;
				}
			}
		}
		
		for( int i= 1; i<diffIdx.length; i++) {
			for( int j= 0; j<diffIdx[i].length; j++) {
				for( int k= (j+1); k<diffIdx[i].length; k++) {
					if (diffIdx[i][k] >0) {
						Pro2.checkCards(diffIdx[i][j], diffIdx[i][k], 0);
					}
					
				}
				
			}
		}
		
		Pro2.checkCards(0, diffIdx[0][0], 0);
		
	}
	
	public int binSearch(int N, int nCol) {
		int start =0;
		int end = N-1;
		
		while( start < end) {
			int mid = (start+end)/2 ;
			
			if( Pro2.checkCards(0, nCol, mid)) {
				end = mid;
			}else {
				start = mid +1;
			}
		}
		
		return start;
	}
}

class Pro2
{
	private final static UserSolution2 usersolution = new UserSolution2();
	public static BufferedReader br = null ;
	
	private final static int MAX_N = 50000; 
	
	private static int N; 
	private static int[] cards = new int[MAX_N * 2]; 
	private static boolean[] found = new boolean[MAX_N + 1]; 
	private static int foundCnt; 
	private static boolean isCorrect; 
	
	public static boolean checkCards(int indexA, int indexB, int diff) 
	{
		if(!isCorrect || indexA < 0 || indexA >= N * 2 || indexB < 0 || indexB >= N * 2) 
		{
			isCorrect = false;
			return false; 
		}
		if(Math.abs(cards[indexA] - cards[indexB]) > diff)
		{
			return false;
		}
		int val = cards[indexA];
		if(diff == 0 && indexA != indexB && !found[val]) {
			foundCnt++;
			found[val] = true; 
		}
		return true;
	}
	
	public static void init()
	{
		foundCnt = 0;
		isCorrect = true; 
		for(int i = 1; i <= N; i++)
			found[i] = false; 
	}
	
	private static boolean run() throws Exception
	{
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N * 2; i++)
			cards[i] = Integer.parseInt(st.nextToken());
	
		init();
		usersolution.playGame(N); 
		return isCorrect && foundCnt == N; 
	}

	public static void main(String[] args) throws Exception
	{
		int TC, MARK;

		System.setIn(new java.io.FileInputStream("data/input2.txt"));
		 br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		TC = Integer.parseInt(st.nextToken());
		MARK = Integer.parseInt(st.nextToken());

		for (int testcase = 1; testcase <= TC; ++testcase)
		{
			int score = run() ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}
		br.close();
	}
}