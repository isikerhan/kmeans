package com.isikerhan.ml.kmeans;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.isikerhan.ml.math.Vector;

public class Cluster {

	@SerializedName("elements")
	private List<Vector<?>> elements;
	@SerializedName("centroid")
	private Vector<Double> centroid;
	private int clusterNo;

	public Cluster(Vector<Double> initialCentroid, int clusterNo) {

		centroid = initialCentroid;
		elements = new ArrayList<>();
		this.setClusterNo(clusterNo);
	}

	public List<Vector<?>> getElements() {
		return elements;
	}

	public void addElement(Vector<?> element) {
		elements.add(element);
	}

	public Vector<Double> getCentroid() {
		return centroid;
	}

	public void setCentroid(Vector<Double> centroid) {
		this.centroid = centroid;
	}

	public int getClusterNo() {
		return clusterNo;
	}

	public void setClusterNo(int clusterNo) {
		this.clusterNo = clusterNo;
	}
}