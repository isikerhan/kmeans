package com.isikerhan.ml.math.distance;

public class EuclideanDistance extends MinkowskiDistance{

	@Override
	protected double getNorm() {
		return 2.0;
	}
}
