package com.rorlig.kmeans;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.rorlig.common.LibUtils;

//import com.rorlig.WordCountJob;
//import com.rorlig.WordCountJob.IntSumReducer;
//import com.rorlig.WordCountJob.TokenizerMapper;

public class KMeansDriver {
	
	
	private static final float MIN_THRESHOLD =  0.5f;

	public static void main(String args[]) throws IOException, InterruptedException, ClassNotFoundException {
		Configuration conf = new Configuration();
	    
	    if (args.length != 4) {
	      System.err.println("Usage: wordcount <in> <out> <max-iterations> <error threshold>");
	      System.exit(1);
	    }
	    float error_threshold = new Float(args[3]);
	    if (error_threshold<MIN_THRESHOLD) error_threshold = MIN_THRESHOLD;
	    int maxIterations = new Integer(args[2]);
	    Path inPath = new Path(args[0]);
	    Path outPath = null;
	    long start = System.currentTimeMillis();
	    int i = 0;
	    String prevKMeansFile = "kcenter.txt";
	    String currKMeansFile = "";
	    conf.set("kMeansFile", prevKMeansFile);
	    System.out.println("KMeasnDriver calling the mapreduce with iterations = " + maxIterations);
//	    double bigdistance = 0.0;
	    while (i<maxIterations){
	    	System.out.println("KMeasnDriver currentIteration" + i);
	       	outPath = new Path(args[1]+i);
	    	Job job = new Job(conf, "KMeans");
	        job.setJarByClass(KMeansDriver.class);
	        job.setMapperClass(KMeansMapper.class);
//	        job.setCombinerClass(KMeansReducer.class);
	        job.setReducerClass(KMeansReducer.class);
	        job.setOutputKeyClass(Text.class);
	        job.setOutputValueClass(Text.class);
	        FileInputFormat.addInputPath(job, inPath);
	        FileOutputFormat.setOutputPath(job, outPath);
	        job.waitForCompletion(true);
//	        inPath = outPath;
	        currKMeansFile = args[1]+i+"/part-r-00000";
	        conf.set("kMeansFile", currKMeansFile);
	        double currDistance = LibUtils.compareKCenters(prevKMeansFile,currKMeansFile);
	        System.out.println("Iteration:"+ i + ":Error Value:" + currDistance);
	        prevKMeansFile=currKMeansFile;
	        if (currDistance <error_threshold) {
//	        	System.out.println("Iteration : "+ i + "Error Value" + currDistance);
	        	break;
	        }
	        ++i;
	        
	    }
	    long end = System.currentTimeMillis();
	    long totalTime = end-start;
	    System.out.println("Total Time for KMeans MapReduce "
	    					+ totalTime);
		
	}

}
