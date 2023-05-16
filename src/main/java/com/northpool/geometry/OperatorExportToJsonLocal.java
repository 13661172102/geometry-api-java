
package com.northpool.geometry;

import java.util.Map;

class OperatorExportToJsonLocal extends OperatorExportToJson {

    @Override
    public JsonCursor execute(SpatialReference spatialReference,
            GeometryCursor geometryCursor) {
        return new OperatorExportToJsonCursor(spatialReference, geometryCursor);
    }

    @Override
    public String execute(SpatialReference spatialReference, Geometry geometry) {
        SimpleGeometryCursor gc = new SimpleGeometryCursor(geometry);
        JsonCursor cursor = new OperatorExportToJsonCursor(spatialReference, gc);
        return cursor.next();
    }

    @Override
    public String execute(SpatialReference spatialReference,
            Geometry geometry, Map<String, Object> exportProperties) {
        return OperatorExportToJsonCursor.exportToString(geometry, spatialReference, exportProperties);
    }
}
