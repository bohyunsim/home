package Contest2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

class UserSolution
{
	
	int[]  pointAarr;
	HashMap< Integer, ArrayList<Integer>> typePointMap = new HashMap< Integer, ArrayList<Integer>>();
	
 	void init(int N, int M, int mType[], int mTime[])
	{
 		pointAarr = new int[N];
 		typePointMap.clear();
 		
 		for(int i=0; i <N -1; i++) {
 			ArrayList<Integer> pointList = typePointMap.get(mType[i]) ;
 			if (pointList == null) {
 				pointList = new ArrayList<Integer>();
 			}
 			
 			pointList.add(i);
 			typePointMap.put(mType[i], pointList);
 			pointAarr[i+1] = mTime[i];
 		}
 		
 		
	}

	void destroy()
	{

	}

	void update(int mID, int mNewTime)
	{
		pointAarr[mID+1] = mNewTime;
	}

	int updateByType(int mTypeID, int mRatio256)
	{
		ArrayList<Integer> pointList = typePointMap.get(mTypeID) ;
		
	    int timeSum =0;
	    for( Integer point : pointList ) {
	    	pointAarr[point+1] = pointAarr[point+1] * mRatio256/256 ;
	    	timeSum += pointAarr[point+1];
	    }
		
		return timeSum;
	}

	int calculate(int mA, int mB)
	{
		int timeSum =0;
		
		int start =mA;
		int end =mB;
		
		if ( mA > mB) {
			start  = mB;
			end = mA;
		}
		
		for( int i=(start+1); i<=end; i++ ) {
			timeSum += pointAarr[i];
		}
		return timeSum;
	}
}