package com.rorlig.common;

public class Point {
	public double x, y;
	
	public Point(double _x, double _y) {
		x= _x;
		y=_y;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer();
		buffer.append(x).append(",").append(y);
		return buffer.toString();
	}
	
	
}
