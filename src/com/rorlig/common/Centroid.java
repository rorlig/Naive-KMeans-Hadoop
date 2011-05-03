package com.rorlig.common;

public class Centroid {
	Point centroid;
	int id;
	
	Centroid(int _id, Point _centroid) {
		centroid = _centroid;
		id = _id;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer();
		buffer.append("id").append(centroid.toString());
		return buffer.toString();
	}
	
	
}
