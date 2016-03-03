package com.isikerhan.ml.kmeans.runner;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import com.isikerhan.ml.kmeans.KMeans;
import com.isikerhan.ml.math.Vector;

public class KMeansRunner {

	private static final int MAX_NUMBER_OF_ITERATIONS = 15;
	private static final int K = 7;

	public static void main(String[] args) throws IOException {

		if (args.length < 1) {
			System.err.println("File path must be specified.");
			System.exit(1);
		}
		File f = new File(args[0]);
		List<Vector<? extends Number>> dataSet = null;
		try {
			dataSet = Vector.fromCsv(f);
		} catch (IOException e) {

			String msg = String.format("%s: %s", e.getClass().getName(), e.getMessage());
			System.err.println(msg);
			System.exit(1);
		}

		File out = new File(f.getParent().toString() + "/out.txt");
		out.createNewFile();
		
		PrintStream[] writers = new PrintStream[] {
				System.out,
				new PrintStream(out)
		};

		KMeans kmeans = new KMeans(K, dataSet);

		long t = System.currentTimeMillis();
		kmeans.initCentroids();

		int numberOfIterations;
		for (numberOfIterations = 0; kmeans.iterate()
				&& numberOfIterations < MAX_NUMBER_OF_ITERATIONS; numberOfIterations++)
			;

		for(PrintStream writer : writers){
			
			writer.println(String.format("Execution time: %d ms.", System.currentTimeMillis() - t));
			writer.println(String.format("Num of iterations: %d.", numberOfIterations));
			writer.println(kmeans.toString());
		}

		System.exit(0);
	}
}
