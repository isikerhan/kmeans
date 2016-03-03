package com.isikerhan.ml.kmeans;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.isikerhan.ml.math.Vector;
import com.isikerhan.ml.math.distance.DistanceFunction;
import com.isikerhan.ml.math.distance.EuclideanDistance;

public class KMeans {

	private Cluster[] clusters;
	private HashMap<Vector<?>, Integer> clusterIndexes;
	private int k;
	private List<Vector<?>> dataSource;
	protected DistanceFunction distanceFunction = new EuclideanDistance();

	public KMeans(int k, List<Vector<?>> dataSource) {

		this.k = k;
		this.dataSource = dataSource;
		this.setClusters(new Cluster[k]);
		this.clusterIndexes = new HashMap<>();
	}

	public void initCentroids() {

		Random rand = new Random();
		int max = dataSource.size();

		for (int i = 0; i < k; i++) {

			Vector<?> v = dataSource.get(rand.nextInt(max));
			getClusters()[i] = new Cluster(v.toDoubleVector(), i);
		}
	}

	public boolean assignToCluster(Vector<?> vector, Cluster c) {
		Integer currentClusterIndex = clusterIndexes.get(vector);
		if(currentClusterIndex != null) {
			if(currentClusterIndex.intValue() == c.getClusterNo())
				return false;
			getClusters()[currentClusterIndex.intValue()].getElements().remove(vector);
		}
		clusterIndexes.put(vector, c.getClusterNo());
		c.getElements().add(vector);
		return true;
	}

	public boolean iterate() {

		boolean changed = false;

		for (Vector<?> v : dataSource) {
			double min = Double.MAX_VALUE;
			Cluster minCluster = null;

			for (Cluster c : getClusters()) {
				double dist = distanceFunction.distance(c.getCentroid(), v);
				if (dist < min) {
					min = dist;
					minCluster = c;
				}
			}
			changed |= assignToCluster(v, minCluster);
		}
		if (changed)
			recalculateClusters();
		return changed;
	}

	private void recalculateClusters() {
		
		for(Cluster c : getClusters()) {
			int numOfDims;
			try {
				numOfDims = c.getElements().get(0).getNumberOfDimensions();
			} catch (IndexOutOfBoundsException e) {
				return;
			}
			Vector<Double> centroid = Vector.zero(numOfDims);
			for(Vector<?> v : c.getElements())
				centroid = centroid.add(v);
			centroid = centroid.divide(c.getElements().size());
			c.setCentroid(centroid);
		}
	}
	
	public double sumOfSquaredErrors(){
		
		double sum = 0.0f;
		
		for(Cluster c : getClusters()) {
			Vector<Double> centroid = c.getCentroid();
			for(Vector<?> v : c.getElements())
				sum += Math.pow(distanceFunction.distance(centroid, v), 2.0);
		}
		
		return sum;
	}
	
	public Cluster[] getClusters() {
		return clusters;
	}

	public void setClusters(Cluster[] clusters) {
		this.clusters = clusters;
	}

	@Override
	public String toString() {
		String nl = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < getClusters().length; i++) {
			sb.append(String.format("********** Cluster %d **********" + nl, i));
			Cluster c = getClusters()[i];
			for(Vector<?> v : c.getElements())
				sb.append(v.toString() + nl);
			sb.append(nl);
		}
		
		sb.append(String.format("Sum of squared errors: %.2f.", sumOfSquaredErrors()));
		return sb.toString();
	}
}
