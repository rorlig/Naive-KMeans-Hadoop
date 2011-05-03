package com.rorlig.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class LibUtils {
	
	public static double eucleadianDistance (Point p1, Point p2) {
		return Math.sqrt((p1.x-p2.x)*(p1.x-p2.x) +(p1.y-p2.y)*(p1.y-p2.y));
	}

	public static double compareKCenters(String prevKMeansFile, String currKMeansFile) {
		// TODO Auto-generated method stub
//	 	System.out.println("LibUtils::compareKCenters");
//	 	System.out.println("LibUtils::compareKCenters prevFile" + prevKMeansFile);
//	 	System.out.println("LibUtils::compareKCenters current File" + currKMeansFile);
		HashMap<Integer, Point> oldKMeansCenters = new HashMap<Integer,Point>();
		HashMap<Integer, Point> newKMeansCenters = new HashMap<Integer,Point>();
		
		try {
			oldKMeansCenters=readKMeansCenter(prevKMeansFile);
			newKMeansCenters=readKMeansCenter(currKMeansFile);
			Iterator<Integer> iterator = oldKMeansCenters.keySet().iterator();
			double bigDistance = 0.0;
			while (iterator.hasNext()) {
				Integer id = iterator.next();
				Point oldPt = oldKMeansCenters.get(id);
				Point newPt = newKMeansCenters.get(id);
				double currDistance = eucleadianDistance(oldPt, newPt);
				if (currDistance>bigDistance) {
					bigDistance = currDistance;
				}
			}
			return bigDistance;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}

	public static Point parseStringToPoint(String data) {
		StringTokenizer commaTokens = new StringTokenizer(data,",");
		Double x = Double.parseDouble(commaTokens.nextToken());
        Double y = Double.parseDouble(commaTokens.nextToken());
        Point pt = new Point(x,y);
		return pt;
		// TODO Auto-generated method stub
		
	}

	public static HashMap<Integer, Point>  readKMeansCenter(String kMeansFile) throws IOException {
		// TODO Auto-generated method stub
//		System.out.println("LibUtils::readKMeansCenter for File:" + kMeansFile);
		Configuration conf = new Configuration();
		Path kmeansPath=null;
		FileSystem fs = FileSystem.get(conf);
		kmeansPath= new Path(kMeansFile);
		
		HashMap<Integer, Point> kMeansCenters = new HashMap<Integer,Point>();
		BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(kmeansPath)));
		String line;
        line=br.readLine();
        System.out.println("Line at kcenter"+ line);
        while (line != null){
//       	 	System.out.println("Line at kcenter"+ line);
               StringTokenizer tokens = new StringTokenizer(line,"\t");
               Integer key = Integer.parseInt(tokens.nextToken());
               Point pt = parseStringToPoint(tokens.nextToken());
               kMeansCenters.put(key, pt);
               line=br.readLine();
               
        }
		return kMeansCenters;
	}
}
