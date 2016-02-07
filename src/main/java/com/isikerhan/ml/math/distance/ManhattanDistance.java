package com.isikerhan.ml.math.distance;

public class ManhattanDistance extends MinkowskiDistance{

	@Override
	protected double getNorm() {
		return 1.0;
	}

}
