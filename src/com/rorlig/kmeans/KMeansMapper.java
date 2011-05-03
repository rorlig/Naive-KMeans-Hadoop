package com.rorlig.kmeans;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
//import java.util.StringTokenizer;

import org.apache.hadoop.mapreduce.*;
//import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;

import com.rorlig.common.Point;
import com.rorlig.common.LibUtils;

public class KMeansMapper extends Mapper<Object, Text, Text, Text> {
	
	private static HashMap<Integer, Point> kMeansCenters = new HashMap<Integer,Point>();
//	private int currIteration =0;
	@Override
	protected void setup(Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Enter KMeansMapper::setup()");
		Configuration conf = context.getConfiguration();
		String kMeansFile = conf.get("kMeansFile");
//		currIteration = conf.getInt("Iteration",0);
		System.out.println("kMeansFile:" + kMeansFile);
		LoadKCenters(conf,kMeansFile);
//		super.setup(context);
	}
	
	private void LoadKCenters(Configuration conf, String kMeansFile ) throws IOException {
		//Load the KMeansCenters <id, x, y>
		//get the kmeancenters;
//		Path kmeansPath=null;
//		FileSystem fs = FileSystem.get(conf);
////		if (iteration==0){
//			kmeansPath= new Path(kMeansFile);
////		} else {
////			kmeansPath= new Path(conf.get("kcenter.txt"));
////		}
//		 System.out.println("Reading the kcenters");
		 
//		 BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(kmeansPath)));
		 kMeansCenters=LibUtils.readKMeansCenter(kMeansFile);
//         String line;
//         line=br.readLine();
//         System.out.println("Line at kcenter"+ line);
//         while (line != null){
//        	 	System.out.println("Line at kcenter"+ line);
//                StringTokenizer tokens = new StringTokenizer(line,"\t");
//                Integer key = Integer.parseInt(tokens.nextToken());
////                StringTokenizer commaTokens = new StringTokenizer(tokens.nextToken(),",");
//                Point pt = LibUtils.parseStringToPoint(tokens.nextToken());
////                Double x = Double.parseDouble(commaTokens.nextToken());
////                Double y = Double.parseDouble(commaTokens.nextToken());
////                Point pt = new Point(x,y);
//                kMeansCenters.put(key, pt);
//                line=br.readLine();
//                
//         }
		//read the file
		
		
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//for each point 
		// calculate distance from each centroid
		// for the closest centroid add the x, y to the centroid 
		// and the count i.e. number of elements going to the center also.
		// Output is <key, [count, sumx, sumy]
		// Reducer will sum this up and emit the new centroid, x, y ,
		// List of Points associated with the centroid.<x,y>?
		System.out.println("Enter KMeansMapper::map()");
		String data = value.toString();
		System.out.println("Enter KMeansMapper::map():" + data);
		String items[] = data.split(",");
		Double x = Double.parseDouble(items[1]);
		Double y = Double.parseDouble(items[2]);
		Point currPt = new Point(x,y);
		Iterator<Integer> iterator = kMeansCenters.keySet().iterator();
		double minDistance = Double.MAX_VALUE;
		Integer minId=0;
//		Point minPt=currPt;
		
		while (iterator.hasNext()) {
			Integer id = iterator.next();
			Point centerPt = kMeansCenters.get(id);
			double currDistance = LibUtils.eucleadianDistance(centerPt,currPt);
			if (currDistance<minDistance){
				minDistance = currDistance;
				minId = id;
			}
		}
//		Point minPt = kMeansCenters.get(minId);

		//output clusterId,x,y
		context.write(new Text(minId.toString()), new Text(currPt.toString()));
		
	}

	
	
}
