
package com.northpool.geometry;

/**
 * An abstract reader for Json.
 * 
 * See JsonParserReader for a concrete implementation around JsonParser.
 */
abstract public interface JsonReader {
	public static enum Token {
		END_ARRAY,
		END_OBJECT,
		FIELD_NAME,
		START_ARRAY,
		START_OBJECT,
		VALUE_FALSE,
		VALUE_NULL,
		VALUE_NUMBER_FLOAT,
		VALUE_NUMBER_INT,
		VALUE_STRING,
		VALUE_TRUE
	}

	abstract public Token nextToken() throws JsonGeometryException;

	abstract public Token currentToken() throws JsonGeometryException;

	abstract public void skipChildren() throws JsonGeometryException;

	abstract public String currentString() throws JsonGeometryException;

	abstract public double currentDoubleValue() throws JsonGeometryException;

	abstract public int currentIntValue() throws JsonGeometryException;
	
	abstract public boolean currentBooleanValue() throws JsonGeometryException;
}

