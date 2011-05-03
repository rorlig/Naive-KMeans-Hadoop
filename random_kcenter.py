#!/usr/bin/env python

import sys
import random

def generate_kcenters(k):
	i=0
	#read the input file
	random_pts={}
	count = 0;
	for line in sys.stdin:
		line = line.strip()
		_id, value = line.split(',',1)
		#print _id
		random_pts[_id] = value;
		count=count+1;
	while i<k:
		random_center = random.randint(0,count)		
		value_center = random_pts[str(random_center)]
		print "{0}\t{1}".format(random_center,value_center)
		i=i+1;
		

if __name__=="__main__":
	generate_kcenters(10)
