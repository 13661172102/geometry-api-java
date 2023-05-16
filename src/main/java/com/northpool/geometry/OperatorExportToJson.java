
package com.northpool.geometry;

import java.util.Map;

/**
 * Export to JSON format.
 */
public abstract class OperatorExportToJson extends Operator {

    @Override
    public Type getType() {
        return Type.ExportToJson;
    }

    /**
     * Performs the ExportToJson operation
     *
     * @return Returns a JsonCursor.
     */
    public abstract JsonCursor execute(SpatialReference spatialReference,
            GeometryCursor geometryCursor);

    /**
     * Performs the ExportToJson operation
     *
     * @return Returns a String.
     */
    public abstract String execute(SpatialReference spatialReference,
            Geometry geometry);

    /**
     * Performs the ExportToJson operation
     *
     * @return Returns a String.
     */
    public abstract String execute(SpatialReference spatialReference,
            Geometry geometry, Map<String, Object> exportProperties);

    public static OperatorExportToJson local() {
        return (OperatorExportToJson) OperatorFactoryLocal.getInstance()
                .getOperator(Type.ExportToJson);
    }
}
