package com.isikerhan.ml.math.distance;

import com.isikerhan.ml.math.Vector;
import static com.isikerhan.ml.math.Math.root;
import static java.lang.Math.pow;
import static java.lang.Math.abs;

abstract class MinkowskiDistance implements DistanceFunction {

	@Override
	public final double distance(Vector<?> p1, Vector<?> p2) {
		int numOfDimensions;
		double sum = 0.0;
		if((numOfDimensions = p1.getNumberOfDimensions()) != p2.getNumberOfDimensions())
			throw new IllegalArgumentException();
		
		for(int i = 0; i < numOfDimensions; i++)
			sum += pow(abs(p1.getValueAt(i) - p2.getValueAt(i)), getNorm());
		
		return root(sum, getNorm());
	}

	protected abstract double getNorm();
}
