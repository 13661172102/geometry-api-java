

package com.northpool.geometry;

/**
 *Import from JSON format.
 */
public abstract class OperatorImportFromJson extends Operator {
	@Override
	public Type getType() {
		return Type.ImportFromJson;
	}

	/**
	 * Performs the ImportFromJson operation on a number of Json Strings
	 * 
	 * @return Returns a MapGeometryCursor.
	 */
	abstract MapGeometryCursor execute(Geometry.Type type,
			JsonReaderCursor jsonReaderCursor);

	/**
	 *Performs the ImportFromJson operation on a single Json string
	 *@return Returns a MapGeometry.
	 */
	public abstract MapGeometry execute(Geometry.Type type,
			JsonReader jsonReader);

	/**
	 *Performs the ImportFromJson operation on a single Json string
	 *@return Returns a MapGeometry.
	 */
	public abstract MapGeometry execute(Geometry.Type type, String string);
	
	public static OperatorImportFromJson local() {
		return (OperatorImportFromJson) OperatorFactoryLocal.getInstance()
				.getOperator(Type.ImportFromJson);
	}

}
