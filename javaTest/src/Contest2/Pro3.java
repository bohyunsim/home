package Contest2;

import java.io.*;
import java.util.*;

class RoomInfoKey{
	int roominfoKey;
	int roomid;
	
	public RoomInfoKey(int roominfoKey, int roomid) {
		super();
		this.roominfoKey = roominfoKey;
		this.roomid = roomid;
	}
	
}

class RoomInfo{
	int roominfoKey;
	int roomid;
	int hotelid;
	int price;
	int[] checkInOut = new int[10000];
	
}

class UserSolution3 {
	
	List<RoomInfoKey> roomInfoKeyList = new ArrayList<RoomInfoKey>(); 
	
	HashMap<Integer, ArrayList> hotelIdRoomMap = new HashMap<Integer, ArrayList>();
	HashMap<Integer, RoomInfo> roomInfoMap = new HashMap<Integer, RoomInfo>();
	
    boolean isRoomInfoListSort = false;
	
	
	public void init(int N, int roomCnt[]) {
		roomInfoKeyList.clear();
		hotelIdRoomMap.clear();
		roomInfoMap.clear();
		
		isRoomInfoListSort = false;
	}

	public void addRoom(int hotelID, int roomID, int roomInfo[]) {
		
		ArrayList<Integer> roomIdList = hotelIdRoomMap.get(hotelID);
		if ( roomIdList ==null) {
			roomIdList = new ArrayList<Integer>() ;
		}
		roomIdList.add(roomID) ;
		hotelIdRoomMap.put(hotelID, roomIdList) ;
		
		RoomInfo roomData = new RoomInfo();
		roomData.roomid = roomID;
		roomData.hotelid = hotelID;
		
		roomData.price = roomInfo[4];
		roomData.roominfoKey = makeInfoKey(roomInfo[0], roomInfo[1],roomInfo[2],roomInfo[3]);
		
		roomInfoMap.put(roomID, roomData) ;
		
		RoomInfoKey roomKey = new  RoomInfoKey(roomData.roominfoKey,  roomID );
		roomInfoKeyList.add(roomKey);
	}
	
	public int makeInfoKey( int regoin, int bedCnt, int roomKind, int viewKind ) {
	    return  Integer.parseInt(regoin+""+bedCnt + "" + roomKind +"" + viewKind  ) ;
	}

	public int findRoom(int requirements[]) {
		
		if ( !isRoomInfoListSort ) {
			Collections.sort(roomInfoKeyList, new Comparator<RoomInfoKey>() {
				@Override
				public int compare(RoomInfoKey o1, RoomInfoKey o2) {
					// TODO Auto-generated method stub
					return o1.roominfoKey > o2.roominfoKey ? 1:o1.roominfoKey < o2.roominfoKey?-1:0 ;
				}
			});
			
			isRoomInfoListSort = true;
		}
		
		int findInfo = makeInfoKey( requirements[2],requirements[3],requirements[4],requirements[5] );
		
		int lowerBound = lowerBound(findInfo);
		
		int roomId = -1;
		int minPrice = -1;
		
		if ( lowerBound >=0 ) {
			
			for( int i = lowerBound ; i <roomInfoKeyList.size(); i++ ) {
				
				if ( findInfo ==  roomInfoKeyList.get(i).roominfoKey) {
					
					RoomInfoKey curRoomInfoKey =  roomInfoKeyList.get(i);
					RoomInfo roomInfo = roomInfoMap.get(curRoomInfoKey.roomid);
					
					if ( roomInfo.checkInOut[requirements[0]] ==0 && roomInfo.checkInOut[requirements[1]] ==0   ) {
						int curPrice = roomInfo.price;
						
						if ( minPrice <0 || curPrice < minPrice  ) {
							minPrice  = curPrice;
							roomId = curRoomInfoKey.roomid;
						}else if ( curPrice == minPrice) {
							roomId = Math.min( roomId, curRoomInfoKey.roomid );
						}
					}
				}else {
					break;
				}
			}
		}

		if ( roomId > 0) {
			RoomInfo roomInfo = roomInfoMap.get(roomId) ;
			for( int i=requirements[0] ; i < requirements[1]; i++  ) {
				roomInfo.checkInOut[i] = 1;
			}
		}
		
		return roomId;
	}
	
	public int lowerBound(int findInfo) {
		int start =0;
		int end = roomInfoKeyList.size()-1;
		
		while(start < end) {
		   int mid = (start + end )/2;
		   RoomInfoKey roomInfoKey = roomInfoKeyList.get(mid);
		   if ( findInfo <= roomInfoKey.roominfoKey ) {
			   end = mid;
		   }else {
			   start = mid +1;
		   }
		}
		
		return findInfo==roomInfoKeyList.get(start).roominfoKey ? start : -1;
	}
	
	
	

	public int risePrices(int hotelID) {
		ArrayList<Integer> roomIdList = hotelIdRoomMap.get(hotelID) ;
		
		int priceSum = 0;
		
		for( int roomId : roomIdList) {
			RoomInfo roomData = roomInfoMap.get(roomId);
			roomData.price = (int) (roomData.price + ( roomData.price *0.1)) ;
			priceSum +=roomData.price ;
		}
		
	    return priceSum;
	}
}

class Pro3 {
	private final static int ADDROOM = 100;
	private final static int FINDROOM = 200;
	private final static int RISEPRICES = 300;
	private final static int END = 400;
	
	private final static UserSolution3 usersolution = new UserSolution3();
	public static BufferedReader br = null;// new BufferedReader(new InputStreamReader(System.in));
	
	private static boolean run() throws Exception {
		
		StringTokenizer st;
		boolean isCorrect = true;
		int cmd, user_ans, correct_ans;
		int roomCnt[],
			hotelID = 0, 
			roomID = 0, 
			roomInfo[] 			= new int[5],
			requirementsInfo[] 	= new int[6];
		
		int n;
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		roomCnt = new int[n];
		for(int i = 0; i < n; i++)
			roomCnt[i] = Integer.parseInt(st.nextToken());
		usersolution.init(n, roomCnt);
		for (int q = 0; ; ++q) {
			st = new StringTokenizer(br.readLine());
			cmd = Integer.parseInt(st.nextToken());

			switch (cmd) {
	        case ADDROOM:
	        	hotelID = Integer.parseInt(st.nextToken());
	        	roomID = Integer.parseInt(st.nextToken());
	            for (int i = 0; i < 5; i++)
	                roomInfo[i] = Integer.parseInt(st.nextToken());
	            usersolution.addRoom(hotelID, roomID, roomInfo);
	            break;
	        case FINDROOM:
	            for (int i = 0; i < 6; i++)
	                requirementsInfo[i] = Integer.parseInt(st.nextToken());
	            user_ans = usersolution.findRoom(requirementsInfo);
	            correct_ans = Integer.parseInt(st.nextToken());
	            if (user_ans != correct_ans)
	            	isCorrect = false;
	            break;
	        case RISEPRICES:
	        	hotelID = Integer.parseInt(st.nextToken());
	            user_ans = usersolution.risePrices(hotelID);
	            correct_ans = Integer.parseInt(st.nextToken());
	            if (user_ans != correct_ans)
	            	isCorrect = false;
	            break;
	        case END:
	            return isCorrect;
	        default:
	        	isCorrect = false;
	            break;
	        }
		}
	}

	public static void main(String[] args) throws Exception {
		int TC, MARK;

		 System.setIn(new java.io.FileInputStream("data/input3.txt"));

		 br =  new BufferedReader(new InputStreamReader(System.in));
			
		 
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		TC = Integer.parseInt(st.nextToken());
		MARK = Integer.parseInt(st.nextToken());

		for (int testcase = 1; testcase <= TC; ++testcase) {
			int score = run() ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}

		br.close();
	}
}