package com.brandmaker.cs.skyhigh.tdb.utils;

/**
 * Class for parsing message
 *
 * @param <T> type of returning value
 */
public class ParseObjectMessage<T> {
	private boolean isValid;
	private T value;

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public ParseObjectMessage(boolean isValid, T value) {
		this.setValid(isValid);
		this.setValue(value);
	}

}
