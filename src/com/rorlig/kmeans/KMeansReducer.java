package com.rorlig.kmeans;

import java.io.IOException;

//import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class KMeansReducer extends Reducer<Text, Text, Text, Text> {

	
	
////	@Override
//	protected void reduce(Text arg0, Iterable<Text> arg1,
//			org.apache.hadoop.mapreduce.Reducer.Context arg2)
//			throws IOException, InterruptedException {
//		// TODO Auto-generated method stub
////		super.reduce(arg0, arg1, arg2);
//	}


//	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		 double sumX = 0, sumY=0;
		 int count = 0;
	     for (Text val : values) {
	    	  System.out.println("value:" + val.toString());
	    	  String [] data = val.toString().split(",");
	    	  Double x = Double.parseDouble(data[0]);
	    	  Double y = Double.parseDouble(data[1]);
	    	  sumX+=x;
	    	  sumY+=y;
	    	  ++count;
	     }
	     Double avgX = sumX/count;
	     Double avgY = sumY/count;
	     Text value = new Text(avgX.toString()+ "," + avgY.toString());
	     context.write(key, value); 
//		super.reduce(arg0, arg1, arg2);
	}

}
