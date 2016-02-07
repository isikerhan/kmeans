package com.isikerhan.ml.kmeans.runner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.isikerhan.ml.kmeans.Cluster;
import com.isikerhan.ml.kmeans.KMeans;
import com.isikerhan.ml.math.Vector;

public class KMeansRunner {
	
	private static final int MAX_NUMBER_OF_ITERATIONS = 15;
	private static final int K = 7;
	
	public static void main(String[] args) {

		Random rand = new Random();
		List<Vector<?>> dataSet = new ArrayList<>();
		for(int i = 0; i < 500; i++) {
			double x, y;
			x = rand.nextDouble() * 100;
			y = rand.nextDouble() * 100;
			dataSet.add(new Vector<>(new Double[]{x, y}));
		}
				
		KMeans kmeans = new KMeans(K, dataSet);
		
		long t = System.currentTimeMillis();
		kmeans.initCentroids();
		
		int numberOfIterations;
		for(numberOfIterations = 0; kmeans.iterate() && numberOfIterations < MAX_NUMBER_OF_ITERATIONS; numberOfIterations++);
		
		for(Cluster c : kmeans.getClusters())
			System.out.println(c.getElements().contains(c.getCentroid()));
		
		System.out.println(String.format("Execution time: %d ms.", System.currentTimeMillis() - t));
		System.out.println(String.format("Num of iterations: %d.", numberOfIterations));
		System.out.println(kmeans.toString());
		System.out.println(new Gson().toJson(kmeans, KMeans.class));
	}
}
