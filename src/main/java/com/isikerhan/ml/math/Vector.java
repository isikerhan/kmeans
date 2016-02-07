package com.isikerhan.ml.math;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Vector<N extends Number> {
	
	public static Vector<Double> zero(int size){
		Double[] values = new Double[size];
		for(int i = 0; i < size; i++)
			values[i] = new Double(0);
		return new Vector<>(values);
	}
	
	@SerializedName("values")
	private N[] values;
	
	public Vector(N[] values) {
		this.values = values;
	}
	
	public int getNumberOfDimensions() {
		return values.length;
	}
	
	public double getValueAt(int dimensionIndex) {
		try {
			return values[dimensionIndex].doubleValue();
		} catch(NullPointerException | ArrayIndexOutOfBoundsException e){
			throw new IndexOutOfBoundsException();
		}
	}
	
	public Vector<Double> toDoubleVector() {
		if(values.length > 0 && values[0] instanceof Double)
			return (Vector<Double>) this;
		Double newValues[] = new Double[values.length];
		for(int i = 0; i < values.length; i++)
			newValues[i] = values[i].doubleValue();
		return new Vector<Double>(newValues);
	}
	
	public Vector<Double> add(Vector<?> other) {
		Double newValues[] = new Double[values.length];
		for(int i = 0; i < values.length; i++)
			newValues[i] = values[i].doubleValue() + other.values[i].doubleValue();
		return new Vector<Double>(newValues);
	}
	
	public Vector<Double> divide(double d) {
		Double newValues[] = new Double[values.length];
		for(int i = 0; i < values.length; i++)
			newValues[i] = (double) values[i].doubleValue() / d;
		return new Vector<Double>(newValues);
	}
	
	@Override
	public int hashCode() {

		int hashCode = 0;
		for(N value : values)
			hashCode ^= value.intValue();
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof Vector<?>))
			return false;
		int numberOfDimensions;
		
		Vector other = (Vector) obj;
		
		if((numberOfDimensions = getNumberOfDimensions()) != other.getNumberOfDimensions())
			return false;
		
		for(int i = 0; i < numberOfDimensions; i++)
			if(getValueAt(i) != other.getValueAt(i))
				return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < values.length; i++) {
			sb.append(Double.toString(values[i].doubleValue()) + ", ");
		}
		sb.replace(sb.length() - 3, sb.length() - 1, "");
		return sb.toString();
	}
}
