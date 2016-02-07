package com.isikerhan.ml.math.distance;

import com.isikerhan.ml.math.Vector;

public interface DistanceFunction {

	double distance(Vector<?> p1, Vector<?> p2);
}
