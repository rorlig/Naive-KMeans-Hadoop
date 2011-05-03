#!/usr/bin/env python

import sys
import random

def generate_random(n,lower,upper):
	i = 0
	while i<n:
		print "{0},{1},{2}".format(i, random.uniform(lower, upper), random.uniform(lower,upper))
		i=i+1
	

if __name__=="__main__":
	generate_random(10000,-200,200)
