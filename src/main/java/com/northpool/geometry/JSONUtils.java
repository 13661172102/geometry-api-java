
package com.northpool.geometry;

final class JSONUtils {

	static boolean isObjectStart(JsonReader parser) throws Exception {
		return parser.currentToken() == null ? parser.nextToken() == JsonReader.Token.START_OBJECT
				: parser.currentToken() == JsonReader.Token.START_OBJECT;
	}

	static double readDouble(JsonReader parser) {
		if (parser.currentToken() == JsonReader.Token.VALUE_NUMBER_FLOAT)
			return parser.currentDoubleValue();
		else if (parser.currentToken() == JsonReader.Token.VALUE_NUMBER_INT)
			return parser.currentIntValue();
		else if (parser.currentToken() == JsonReader.Token.VALUE_NULL)
			return NumberUtils.NaN();
		else if (parser.currentToken() == JsonReader.Token.VALUE_STRING)
			if (parser.currentString().equals("NaN"))
				return NumberUtils.NaN();

		throw new GeometryException("invalid parameter");
	}

}
