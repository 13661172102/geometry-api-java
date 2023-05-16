
package com.northpool.geometry;

class OperatorImportFromJsonLocal extends OperatorImportFromJson {

	@Override
	public MapGeometryCursor execute(Geometry.Type type,
			JsonReaderCursor jsonParserCursor) {
		return new OperatorImportFromJsonCursor(type.value(), jsonParserCursor);
	}

	@Override
	public MapGeometry execute(Geometry.Type type, JsonReader jsonParser) {
		return OperatorImportFromJsonCursor.importFromJsonParser(type.value(), jsonParser);
	}
	@Override
	public MapGeometry execute(Geometry.Type type, String string) {
		return execute(type, JsonParserReader.createFromString(string));
	}	
}
