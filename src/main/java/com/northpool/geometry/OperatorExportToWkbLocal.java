

package com.northpool.geometry;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class OperatorExportToWkbLocal extends OperatorExportToWkb {

    @Override
    public ByteBuffer execute(int exportFlags, Geometry geometry,
                              ProgressTracker progressTracker) {
        int size = exportToWKB(exportFlags, geometry, 0,null);
        ByteBuffer wkbBuffer = ByteBuffer.allocate(size).order(
                ByteOrder.nativeOrder());
        exportToWKB(exportFlags, geometry, 0, wkbBuffer);
        return wkbBuffer;
    }

    @Override
    public int execute(int exportFlags, Geometry geometry,
                       ByteBuffer wkbBuffer, ProgressTracker progressTracker) {
        return exportToWKB(exportFlags, geometry, 0, wkbBuffer);
    }

    public ByteBuffer execute(int exportFlags, Geometry geometry,
                              Integer srid) {
        int size = exportToWKB(exportFlags, geometry, srid, null);
        ByteBuffer wkbBuffer = ByteBuffer.allocate(size).order(
                ByteOrder.nativeOrder());
        exportToWKB(exportFlags, geometry, srid, wkbBuffer);
        return wkbBuffer;
    }

    private static void writeGeometryType(int offset, int geometryType, int srid, ByteBuffer wkbBuffer){
        int flag3D = 0;
        int typeInt = geometryType | flag3D;
        typeInt |= 0x20000000;
        wkbBuffer.putInt(offset, typeInt);
        wkbBuffer.putInt(offset + 4, srid);
    }


    private static int exportToWKB(int exportFlags, Geometry geometry, Integer srid,
                                   ByteBuffer wkbBuffer) {
        if (geometry == null)
            return 0;

        int type = geometry.getType().value();
        switch (type) {
            case Geometry.GeometryType.Polygon:
                if ((exportFlags & WkbExportFlags.wkbExportLineString) != 0
                        || (exportFlags & WkbExportFlags.wkbExportMultiLineString) != 0
                        || (exportFlags & WkbExportFlags.wkbExportPoint) != 0
                        || (exportFlags & WkbExportFlags.wkbExportMultiPoint) != 0)
                    throw new GeometryException("invalid argument");

                return exportPolygonToWKB(exportFlags, (Polygon) geometry, srid,
                        wkbBuffer);
            case Geometry.GeometryType.Polyline:
                if ((exportFlags & WkbExportFlags.wkbExportPolygon) != 0
                        || (exportFlags & WkbExportFlags.wkbExportMultiPolygon) != 0
                        || (exportFlags & WkbExportFlags.wkbExportPoint) != 0
                        || (exportFlags & WkbExportFlags.wkbExportMultiPoint) != 0)
                    throw new GeometryException("invalid argument");
                return exportPolylineToWKB(exportFlags, (Polyline) geometry, srid,
                        wkbBuffer);

            case Geometry.GeometryType.MultiPoint:
                if ((exportFlags & WkbExportFlags.wkbExportLineString) != 0
                        || (exportFlags & WkbExportFlags.wkbExportMultiLineString) != 0
                        || (exportFlags & WkbExportFlags.wkbExportPolygon) != 0
                        || (exportFlags & WkbExportFlags.wkbExportMultiPolygon) != 0)
                    throw new GeometryException("invalid argument");
                return exportMultiPointToWKB(exportFlags, (MultiPoint) geometry, srid,
                        wkbBuffer);

            case Geometry.GeometryType.Point:
                if ((exportFlags & WkbExportFlags.wkbExportLineString) != 0
                        || (exportFlags & WkbExportFlags.wkbExportMultiLineString) != 0
                        || (exportFlags & WkbExportFlags.wkbExportPolygon) != 0
                        || (exportFlags & WkbExportFlags.wkbExportMultiPolygon) != 0)
                    throw new GeometryException("invalid argument");
                return exportPointToWKB(exportFlags, (Point) geometry, srid, wkbBuffer);

            case Geometry.GeometryType.Envelope:
                if ((exportFlags & WkbExportFlags.wkbExportLineString) != 0
                        || (exportFlags & WkbExportFlags.wkbExportMultiLineString) != 0
                        || (exportFlags & WkbExportFlags.wkbExportPoint) != 0
                        || (exportFlags & WkbExportFlags.wkbExportMultiPoint) != 0)
                    throw new GeometryException("invalid argument");
                return exportEnvelopeToWKB(exportFlags, (Envelope) geometry, srid,
                        wkbBuffer);

            default: {
                throw GeometryException.GeometryInternalError();
                // return -1;
            }
        }
    }

    private static int exportPolygonToWKB(int exportFlags, Polygon _polygon, Integer srid,
                                          ByteBuffer wkbBuffer) {
        MultiPathImpl polygon = (MultiPathImpl) _polygon._getImpl();

        if ((exportFlags & (int) WkbExportFlags.wkbExportFailIfNotSimple) != 0) {
            int simple = polygon.getIsSimple(0.0);

            if (simple != MultiVertexGeometryImpl.GeometryXSimple.Strong)
                throw new GeometryException("non simple geometry");
        }

        boolean bExportZs = polygon.hasAttribute(VertexDescription.Semantics.Z)
                && (exportFlags & (int) WkbExportFlags.wkbExportStripZs) == 0;
        boolean bExportMs = polygon.hasAttribute(VertexDescription.Semantics.M)
                && (exportFlags & (int) WkbExportFlags.wkbExportStripMs) == 0;

        int polygonCount = polygon.getOGCPolygonCount();
        if ((exportFlags & (int) WkbExportFlags.wkbExportPolygon) != 0
                && polygonCount > 1)
            throw new IllegalArgumentException();

        int partCount = polygon.getPathCount();
        int point_count = polygon.getPointCount();
        point_count += partCount; // add 1 point per part

        if (point_count > 0 && polygonCount == 0)
            throw new GeometryException("corrupted geometry");

        // In the WKB_export_defaults case, polygons gets exported as a
        // WKB_multi_polygon.

        // get size for buffer
        int size = 0;
        if ((exportFlags & (int) WkbExportFlags.wkbExportPolygon) == 0
                || polygonCount == 0)
            size += 1 /* byte order */+ 4 /* wkbType */ + 4 /* srid */ + 4 /* numPolygons */;

        size += polygonCount
                * (1 /* byte order */+ 4 /* wkbType */ + 4 /* srid */+ 4/* numRings */)
                + partCount * (4 /* num_points */) + point_count * (2 * 8 /*
         * xy
         * coordinates
         */);

        if (bExportZs)
            size += (point_count * 8 /* zs */);
        if (bExportMs)
            size += (point_count * 8 /* ms */);

        if (size >= NumberUtils.intMax())
            throw new GeometryException("invalid call");

        if (wkbBuffer == null)
            return (int) size;
        else if (wkbBuffer.capacity() < size)
            throw new GeometryException("buffer is too small");

        int offset = 0;

        byte byteOrder = (byte) (wkbBuffer.order() == ByteOrder.LITTLE_ENDIAN ? WkbByteOrder.wkbNDR
                : WkbByteOrder.wkbXDR);

        // Determine the wkb type
        int type;
        if (!bExportZs && !bExportMs) {
            type = WkbGeometryType.wkbPolygon;

            if ((exportFlags & WkbExportFlags.wkbExportPolygon) == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiPolygon, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, polygonCount);
                offset += 4;
            } else if (polygonCount == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbPolygon, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, 0);
                offset += 4;
            }
        } else if (bExportZs && !bExportMs) {
            type = WkbGeometryType.wkbPolygonZ;

            if ((exportFlags & WkbExportFlags.wkbExportPolygon) == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiPolygonZ, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, polygonCount);
                offset += 4;
            } else if (polygonCount == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbPolygonZ, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, 0);
                offset += 4;
            }
        } else if (bExportMs && !bExportZs) {
            type = WkbGeometryType.wkbPolygonM;

            if ((exportFlags & WkbExportFlags.wkbExportPolygon) == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiPolygonM, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, (int) polygonCount);
                offset += 4;
            } else if (polygonCount == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbPolygonM, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, 0);
                offset += 4;
            }
        } else {
            type = WkbGeometryType.wkbPolygonZM;

            if ((exportFlags & WkbExportFlags.wkbExportPolygon) == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiPolygonZM, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, polygonCount);
                offset += 4;
            } else if (polygonCount == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbPolygonZM, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, 0);
                offset += 4;
            }
        }

        if (polygonCount == 0)
            return offset;

        AttributeStreamOfDbl position = (AttributeStreamOfDbl) (polygon
                .getAttributeStreamRef(VertexDescription.Semantics.POSITION));

        AttributeStreamOfDbl zs = null;
        if (bExportZs) {
            if (polygon
                    ._attributeStreamIsAllocated(VertexDescription.Semantics.Z))
                zs = (AttributeStreamOfDbl) (polygon
                        .getAttributeStreamRef(VertexDescription.Semantics.Z));
        }

        AttributeStreamOfDbl ms = null;
        if (bExportMs) {
            if (polygon
                    ._attributeStreamIsAllocated(VertexDescription.Semantics.M))
                ms = (AttributeStreamOfDbl) (polygon
                        .getAttributeStreamRef(VertexDescription.Semantics.M));
        }

        int ipartend = 0;
        int ipolygonend = 0;

        for (int ipolygon = 0; ipolygon < (int) polygonCount; ipolygon++) {
            // write byte order
            wkbBuffer.put(offset, byteOrder);
            offset += 1;

            // write type
            writeGeometryType(offset, type, srid, wkbBuffer);
            offset += 8;

            // get partcount for the ith polygon
            AttributeStreamOfInt8 pathFlags = polygon.getPathFlagsStreamRef();

            int ipolygonstart = ipolygonend;
            ipolygonend++;

            while (ipolygonend < partCount
                    && (pathFlags.read(ipolygonend) & PathFlags.enumOGCStartPolygon) == 0)
                ipolygonend++;

            // write numRings
            wkbBuffer.putInt(offset, ipolygonend - ipolygonstart);
            offset += 4;

            for (int ipart = ipolygonstart; ipart < ipolygonend; ipart++) {
                // get num_points
                int ipartstart = ipartend;
                ipartend = (int) polygon.getPathEnd(ipart);

                // write num_points
                wkbBuffer.putInt(offset, ipartend - ipartstart + 1);
                offset += 4;

                // duplicate the start point
                double x = position.read(2 * ipartstart);
                double y = position.read(2 * ipartstart + 1);

                wkbBuffer.putDouble(offset, x);
                offset += 8;
                wkbBuffer.putDouble(offset, y);
                offset += 8;

                if (bExportZs) {
                    double z;
                    if (zs != null)
                        z = zs.read(ipartstart);
                    else
                        z = VertexDescription
                                .getDefaultValue(VertexDescription.Semantics.Z);

                    wkbBuffer.putDouble(offset, z);
                    offset += 8;
                }

                if (bExportMs) {
                    double m;
                    if (ms != null)
                        m = ms.read(ipartstart);
                    else
                        m = VertexDescription
                                .getDefaultValue(VertexDescription.Semantics.M);

                    wkbBuffer.putDouble(offset, m);
                    offset += 8;
                }

                // We must write to the buffer backwards - ogc polygon format is
                // opposite of shapefile format
                for (int i = ipartend - 1; i >= ipartstart; i--) {
                    x = position.read(2 * i);
                    y = position.read(2 * i + 1);

                    wkbBuffer.putDouble(offset, x);
                    offset += 8;
                    wkbBuffer.putDouble(offset, y);
                    offset += 8;

                    if (bExportZs) {
                        double z;
                        if (zs != null)
                            z = zs.read(i);
                        else
                            z = VertexDescription
                                    .getDefaultValue(VertexDescription.Semantics.Z);

                        wkbBuffer.putDouble(offset, z);
                        offset += 8;
                    }

                    if (bExportMs) {
                        double m;
                        if (ms != null)
                            m = ms.read(i);
                        else
                            m = VertexDescription
                                    .getDefaultValue(VertexDescription.Semantics.M);

                        wkbBuffer.putDouble(offset, m);
                        offset += 8;
                    }
                }
            }
        }

        return offset;
    }

    private static int exportPolylineToWKB(int exportFlags, Polyline _polyline, Integer srid,
                                           ByteBuffer wkbBuffer) {
        MultiPathImpl polyline = (MultiPathImpl) _polyline._getImpl();

        if ((exportFlags & WkbExportFlags.wkbExportFailIfNotSimple) != 0) {
            int simple = polyline.getIsSimple(0.0);

            if (simple < 1)
                throw new GeometryException("corrupted geometry");
        }

        boolean bExportZs = polyline
                .hasAttribute(VertexDescription.Semantics.Z)
                && (exportFlags & WkbExportFlags.wkbExportStripZs) == 0;
        boolean bExportMs = polyline
                .hasAttribute(VertexDescription.Semantics.M)
                && (exportFlags & WkbExportFlags.wkbExportStripMs) == 0;

        int partCount = polyline.getPathCount();
        if ((exportFlags & WkbExportFlags.wkbExportLineString) != 0
                && partCount > 1)
            throw new IllegalArgumentException();

        int point_count = polyline.getPointCount();

        for (int ipart = 0; ipart < partCount; ipart++)
            if (polyline.isClosedPath(ipart))
                point_count++;

        // In the WKB_export_defaults case, polylines gets exported as a
        // WKB_multi_line_string

        // get size for buffer
        int size = 0;
        if ((exportFlags & WkbExportFlags.wkbExportLineString) == 0
                || partCount == 0)
            size += 1 /* byte order */+ 4 /* wkbType */ + 4 /* srid */+ 4 /* numLineStrings */;

        size += partCount
                * (1 /* byte order */+ 4 /* wkbType */ + 4 /* srid */+ 4/* num_points */)
                + point_count * (2 * 8 /* xy coordinates */);

        if (bExportZs)
            size += (point_count * 8 /* zs */);
        if (bExportMs)
            size += (point_count * 8 /* ms */);

        if (size >= NumberUtils.intMax())
            throw new GeometryException("invalid call");

        if (wkbBuffer == null)
            return (int) size;
        else if (wkbBuffer.capacity() < (int) size)
            throw new GeometryException("buffer is too small");

        int offset = 0;

        byte byteOrder = (byte) (wkbBuffer.order() == ByteOrder.LITTLE_ENDIAN ? WkbByteOrder.wkbNDR
                : WkbByteOrder.wkbXDR);

        // Determine the wkb type
        int type;
        if (!bExportZs && !bExportMs) {
            type = WkbGeometryType.wkbLineString;

            if ((exportFlags & WkbExportFlags.wkbExportLineString) == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiLineString, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, (int) partCount);
                offset += 4;
            } else if (partCount == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbLineString, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, 0);
                offset += 4;
            }
        } else if (bExportZs && !bExportMs) {
            type = WkbGeometryType.wkbLineStringZ;

            if ((exportFlags & WkbExportFlags.wkbExportLineString) == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiLineStringZ, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, (int) partCount);
                offset += 4;
            } else if (partCount == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbLineStringZ, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, 0);
                offset += 4;
            }
        } else if (bExportMs && !bExportZs) {
            type = WkbGeometryType.wkbLineStringM;

            if ((exportFlags & WkbExportFlags.wkbExportLineString) == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiLineStringM, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, (int) partCount);
                offset += 4;
            } else if (partCount == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbLineStringM, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, 0);
                offset += 4;
            }
        } else {
            type = WkbGeometryType.wkbLineStringZM;

            if ((exportFlags & WkbExportFlags.wkbExportLineString) == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiLineStringZM, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, partCount);
                offset += 4;
            } else if (partCount == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbLineStringZM, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, 0);
                offset += 4;
            }
        }

        if (partCount == 0)
            return offset;

        AttributeStreamOfDbl position = (AttributeStreamOfDbl) (polyline
                .getAttributeStreamRef(VertexDescription.Semantics.POSITION));

        AttributeStreamOfDbl zs = null;
        if (bExportZs) {
            if (polyline
                    ._attributeStreamIsAllocated(VertexDescription.Semantics.Z))
                zs = (AttributeStreamOfDbl) (polyline
                        .getAttributeStreamRef(VertexDescription.Semantics.Z));
        }

        AttributeStreamOfDbl ms = null;
        if (bExportMs) {
            if (polyline
                    ._attributeStreamIsAllocated(VertexDescription.Semantics.M))
                ms = (AttributeStreamOfDbl) (polyline
                        .getAttributeStreamRef(VertexDescription.Semantics.M));
        }

        int ipartend = 0;
        for (int ipart = 0; ipart < (int) partCount; ipart++) {
            // write byte order
            wkbBuffer.put(offset, byteOrder);
            offset += 1;

            // write type
            writeGeometryType(offset, type, srid, wkbBuffer);
            offset += 8;

            // get start and end indices
            int ipartstart = ipartend;
            ipartend = (int) polyline.getPathEnd(ipart);

            // write num_points
            int num_points = ipartend - ipartstart;
            if (polyline.isClosedPath(ipart))
                num_points++;

            wkbBuffer.putInt(offset, num_points);
            offset += 4;

            // write points
            for (int i = ipartstart; i < ipartend; i++) {
                double x = position.read(2 * i);
                double y = position.read(2 * i + 1);

                wkbBuffer.putDouble(offset, x);
                offset += 8;
                wkbBuffer.putDouble(offset, y);
                offset += 8;

                if (bExportZs) {
                    double z;
                    if (zs != null)
                        z = zs.read(i);
                    else
                        z = VertexDescription
                                .getDefaultValue(VertexDescription.Semantics.Z);

                    wkbBuffer.putDouble(offset, z);
                    offset += 8;
                }

                if (bExportMs) {
                    double m;
                    if (ms != null)
                        m = ms.read(i);
                    else
                        m = VertexDescription
                                .getDefaultValue(VertexDescription.Semantics.M);

                    wkbBuffer.putDouble(offset, m);
                    offset += 8;
                }
            }

            // duplicate the start point if the Polyline is closed
            if (polyline.isClosedPath(ipart)) {
                double x = position.read(2 * ipartstart);
                double y = position.read(2 * ipartstart + 1);

                wkbBuffer.putDouble(offset, x);
                offset += 8;
                wkbBuffer.putDouble(offset, y);
                offset += 8;

                if (bExportZs) {
                    double z;
                    if (zs != null)
                        z = zs.read(ipartstart);
                    else
                        z = VertexDescription
                                .getDefaultValue(VertexDescription.Semantics.Z);

                    wkbBuffer.putDouble(offset, z);
                    offset += 8;
                }

                if (bExportMs) {
                    double m;
                    if (ms != null)
                        m = ms.read(ipartstart);
                    else
                        m = VertexDescription
                                .getDefaultValue(VertexDescription.Semantics.M);

                    wkbBuffer.putDouble(offset, m);
                    offset += 8;
                }
            }
        }

        return offset;
    }

    private static int exportMultiPointToWKB(int exportFlags,
                                             MultiPoint _multipoint, Integer srid, ByteBuffer wkbBuffer) {
        MultiPointImpl multipoint = (MultiPointImpl) _multipoint._getImpl();

        boolean bExportZs = multipoint
                .hasAttribute(VertexDescription.Semantics.Z)
                && (exportFlags & WkbExportFlags.wkbExportStripZs) == 0;
        boolean bExportMs = multipoint
                .hasAttribute(VertexDescription.Semantics.M)
                && (exportFlags & WkbExportFlags.wkbExportStripMs) == 0;

        int point_count = multipoint.getPointCount();
        if ((exportFlags & WkbExportFlags.wkbExportPoint) != 0
                && point_count > 1)
            throw new IllegalArgumentException();

        // get size for buffer
        int size;
        if ((exportFlags & WkbExportFlags.wkbExportPoint) == 0) {
            size = 1 /* byte order */+ 4 /* wkbType */ + 4 /* srid */+ 4 /* num_points */
                    + point_count
                    * (1 /* byte order */+ 4 /* wkbType */ + 4 /* srid */+ 2 * 8 /*
             * xy
             * coordinates
             */);

            if (bExportZs)
                size += (point_count * 8 /* zs */);
            if (bExportMs)
                size += (point_count * 8 /* ms */);
        } else {
            size = 1 /* byte order */+ 4 /* wkbType */ + 4 /* srid */+ 2 * 8 /* xy coordinates */;

            if (bExportZs)
                size += 8 /* z */;
            if (bExportMs)
                size += 8 /* m */;
        }

        if (size >= NumberUtils.intMax())
            throw new GeometryException("invalid call");

        if (wkbBuffer == null)
            return (int) size;
        else if (wkbBuffer.capacity() < (int) size)
            throw new GeometryException("buffer is too small");

        int offset = 0;

        byte byteOrder = (byte) (wkbBuffer.order() == ByteOrder.LITTLE_ENDIAN ? WkbByteOrder.wkbNDR
                : WkbByteOrder.wkbXDR);

        // Determine the wkb type
        int type;
        if (!bExportZs && !bExportMs) {
            type = WkbGeometryType.wkbPoint;

            if ((exportFlags & WkbExportFlags.wkbExportPoint) == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiPoint, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, (int) point_count);
                offset += 4;
            } else if (point_count == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                wkbBuffer.putInt(offset, type);
                offset += 4;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
            }
        } else if (bExportZs && !bExportMs) {
            type = WkbGeometryType.wkbPointZ;

            if ((exportFlags & WkbExportFlags.wkbExportPoint) == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiPointZ, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, (int) point_count);
                offset += 4;
            } else if (point_count == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                wkbBuffer.putInt(offset, type);
                offset += 4;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
            }
        } else if (bExportMs && !bExportZs) {
            type = WkbGeometryType.wkbPointM;

            if ((exportFlags & WkbExportFlags.wkbExportPoint) == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiPointM, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, (int) point_count);
                offset += 4;
            } else if (point_count == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                wkbBuffer.putInt(offset, type);
                offset += 4;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
            }
        } else {
            type = WkbGeometryType.wkbPointZM;

            if ((exportFlags & WkbExportFlags.wkbExportPoint) == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiPointZM, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, point_count);
                offset += 4;
            } else if (point_count == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                wkbBuffer.putInt(offset, type);
                offset += 4;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
            }
        }

        if (point_count == 0)
            return offset;

        AttributeStreamOfDbl position = (AttributeStreamOfDbl) (multipoint
                .getAttributeStreamRef(VertexDescription.Semantics.POSITION));

        AttributeStreamOfDbl zs = null;
        if (bExportZs) {
            if (multipoint
                    ._attributeStreamIsAllocated(VertexDescription.Semantics.Z))
                zs = (AttributeStreamOfDbl) (multipoint
                        .getAttributeStreamRef(VertexDescription.Semantics.Z));
        }

        AttributeStreamOfDbl ms = null;
        if (bExportMs) {
            if (multipoint
                    ._attributeStreamIsAllocated(VertexDescription.Semantics.M))
                ms = (AttributeStreamOfDbl) (multipoint
                        .getAttributeStreamRef(VertexDescription.Semantics.M));
        }

        for (int i = 0; i < (int) point_count; i++) {
            // write byte order
            wkbBuffer.put(offset, byteOrder);
            offset += 1;

            // write type
            writeGeometryType(offset, type, srid, wkbBuffer);
            offset += 8;

            // write xy coordinates
            double x = position.read(2 * i);
            double y = position.read(2 * i + 1);

            wkbBuffer.putDouble(offset, x);
            offset += 8;
            wkbBuffer.putDouble(offset, y);
            offset += 8;

            // write Z
            if (bExportZs) {
                double z;
                if (zs != null)
                    z = zs.read(i);
                else
                    z = VertexDescription
                            .getDefaultValue(VertexDescription.Semantics.Z);

                wkbBuffer.putDouble(offset, z);
                offset += 8;
            }

            // write M
            if (bExportMs) {
                double m;
                if (ms != null)
                    m = ms.read(i);
                else
                    m = VertexDescription
                            .getDefaultValue(VertexDescription.Semantics.M);

                wkbBuffer.putDouble(offset, m);
                offset += 8;
            }
        }

        return offset;
    }

    private static int exportPointToWKB(int exportFlags, Point point, Integer srid,
                                        ByteBuffer wkbBuffer) {
        boolean bExportZs = point.hasAttribute(VertexDescription.Semantics.Z)
                && (exportFlags & WkbExportFlags.wkbExportStripZs) == 0;
        boolean bExportMs = point.hasAttribute(VertexDescription.Semantics.M)
                && (exportFlags & WkbExportFlags.wkbExportStripMs) == 0;

        boolean bEmpty = point.isEmpty();
        int point_count = bEmpty ? 0 : 1;

        // get size for buffer
        int size;
        if ((exportFlags & WkbExportFlags.wkbExportMultiPoint) != 0) {
            size = 1 /* byte order */+ 4 /* wkbType */ + 4 /* srid */+ 4 /* num_points */
                    + point_count
                    * (1 /* byte order */+ 4 /* wkbType */ + 4 /* srid */+ 2 * 8 /*
             * xy
             * coordinates
             */);

            if (bExportZs)
                size += (point_count * 8 /* zs */);
            if (bExportMs)
                size += (point_count * 8 /* ms */);
        } else {
            size = 1 /* byte order */+ 4 /* wkbType */ + 4 /* srid */+ 2 * 8 /* xy coordinates */;

            if (bExportZs)
                size += 8 /* z */;
            if (bExportMs)
                size += 8 /* m */;
        }

        if (size >= NumberUtils.intMax())
            throw new GeometryException("invalid call");

        if (wkbBuffer == null)
            return size;
        else if (wkbBuffer.capacity() < size)
            throw new GeometryException("buffer is too small");

        int offset = 0;

        byte byteOrder = (byte) (wkbBuffer.order() == ByteOrder.LITTLE_ENDIAN ? WkbByteOrder.wkbNDR
                : WkbByteOrder.wkbXDR);

        // Determine the wkb type
        int type;
        if (!bExportZs && !bExportMs) {
            type = WkbGeometryType.wkbPoint;

            if ((exportFlags & WkbExportFlags.wkbExportMultiPoint) != 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiPoint, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, (int) point_count);
                offset += 4;
            } else if (point_count == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                wkbBuffer.putInt(offset, type);
                offset += 4;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
            }
        } else if (bExportZs && !bExportMs) {
            type = WkbGeometryType.wkbPointZ;

            if ((exportFlags & WkbExportFlags.wkbExportMultiPoint) != 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiPointZ, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, (int) point_count);
                offset += 4;
            } else if (point_count == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                wkbBuffer.putInt(offset, type);
                offset += 4;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
            }
        } else if (bExportMs && !bExportZs) {
            type = WkbGeometryType.wkbPointM;

            if ((exportFlags & WkbExportFlags.wkbExportMultiPoint) != 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiPointM, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, (int) point_count);
                offset += 4;
            } else if (point_count == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                wkbBuffer.putInt(offset, type);
                offset += 4;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
            }
        } else {
            type = WkbGeometryType.wkbPointZM;

            if ((exportFlags & WkbExportFlags.wkbExportMultiPoint) != 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiPointZM, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, (int) point_count);
                offset += 4;
            } else if (point_count == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                wkbBuffer.putInt(offset, type);
                offset += 4;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
                wkbBuffer.putDouble(offset, NumberUtils.TheNaN);
                offset += 8;
            }
        }

        if (point_count == 0)
            return offset;

        // write byte order
        wkbBuffer.put(offset, byteOrder);
        offset += 1;

        // write type
        writeGeometryType(offset, type, srid, wkbBuffer);
        offset += 8;

        // write xy coordinate
        double x = point.getX();
        double y = point.getY();
        wkbBuffer.putDouble(offset, x);
        offset += 8;
        wkbBuffer.putDouble(offset, y);
        offset += 8;

        // write Z
        if (bExportZs) {
            double z = point.getZ();
            wkbBuffer.putDouble(offset, z);
            offset += 8;
        }

        // write M
        if (bExportMs) {
            double m = point.getM();
            wkbBuffer.putDouble(offset, m);
            offset += 8;
        }

        return offset;
    }

    private static int exportEnvelopeToWKB(int exportFlags, Envelope envelope, Integer srid,
                                           ByteBuffer wkbBuffer) {
        boolean bExportZs = envelope
                .hasAttribute(VertexDescription.Semantics.Z)
                && (exportFlags & WkbExportFlags.wkbExportStripZs) == 0;
        boolean bExportMs = envelope
                .hasAttribute(VertexDescription.Semantics.M)
                && (exportFlags & WkbExportFlags.wkbExportStripMs) == 0;
        boolean bEmpty = envelope.isEmpty();

        int partCount = bEmpty ? 0 : 1;
        int point_count = bEmpty ? 0 : 5;

        // Envelope by default is exported as a WKB_polygon

        // get size for buffer
        int size = 0;
        if ((exportFlags & WkbExportFlags.wkbExportMultiPolygon) != 0
                || partCount == 0)
            size += 1 /* byte order */+ 4 /* wkbType */ + 4 /* srid */+ 4 /* numPolygons */;

        size += partCount
                * (1 /* byte order */+ 4 /* wkbType */ + 4 /* srid */+ 4/* numRings */)
                + partCount * (4 /* num_points */) + point_count * (2 * 8 /*
         * xy
         * coordinates
         */);

        if (bExportZs)
            size += (point_count * 8 /* zs */);
        if (bExportMs)
            size += (point_count * 8 /* ms */);

        if (size >= NumberUtils.intMax())
            throw new GeometryException("invalid call");

        if (wkbBuffer == null)
            return size;
        else if (wkbBuffer.capacity() < size)
            throw new GeometryException("buffer is too small");

        int offset = 0;

        byte byteOrder = (byte) (wkbBuffer.order() == ByteOrder.LITTLE_ENDIAN ? WkbByteOrder.wkbNDR
                : WkbByteOrder.wkbXDR);

        // Determine the wkb type
        int type;
        if (!bExportZs && !bExportMs) {
            type = WkbGeometryType.wkbPolygon;

            if ((exportFlags & WkbExportFlags.wkbExportMultiPolygon) != 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiPolygon, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, (int) partCount);
                offset += 4;
            } else if (partCount == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbPolygon, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, 0);
                offset += 4;
            }
        } else if (bExportZs && !bExportMs) {
            type = WkbGeometryType.wkbPolygonZ;

            if ((exportFlags & WkbExportFlags.wkbExportPolygon) != 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiPolygonZ, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, partCount);
                offset += 4;
            } else if (partCount == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbPolygonZ, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, 0);
                offset += 4;
            }
        } else if (bExportMs && !bExportZs) {
            type = WkbGeometryType.wkbPolygonM;

            if ((exportFlags & WkbExportFlags.wkbExportMultiPolygon) != 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiPolygonM, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, partCount);
                offset += 4;
            } else if (partCount == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbPolygonM, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, 0);
                offset += 4;
            }
        } else {
            type = WkbGeometryType.wkbPolygonZM;

            if ((exportFlags & WkbExportFlags.wkbExportMultiPolygon) != 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbMultiPolygonZM, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, partCount);
                offset += 4;
            } else if (partCount == 0) {
                wkbBuffer.put(offset, byteOrder);
                offset += 1;
                writeGeometryType(offset, WkbGeometryType.wkbPolygonZM, srid, wkbBuffer);
                offset += 8;
                wkbBuffer.putInt(offset, 0);
                offset += 4;
            }
        }

        if (partCount == 0)
            return offset;

        // write byte order
        wkbBuffer.put(offset, byteOrder);
        offset += 1;

        // write type
        writeGeometryType(offset, type, srid, wkbBuffer);
        offset += 8;

        // write numRings
        wkbBuffer.putInt(offset, 1);
        offset += 4;

        // write num_points
        wkbBuffer.putInt(offset, 5);
        offset += 4;

        Envelope2D env = new Envelope2D();
        envelope.queryEnvelope2D(env);

        Envelope1D z_interval = null;
        if (bExportZs)
            z_interval = envelope.queryInterval(VertexDescription.Semantics.Z,
                    0);

        Envelope1D mInterval = null;
        if (bExportMs)
            mInterval = envelope
                    .queryInterval(VertexDescription.Semantics.M, 0);

        wkbBuffer.putDouble(offset, env.xmin);
        offset += 8;
        wkbBuffer.putDouble(offset, env.ymin);
        offset += 8;

        if (bExportZs) {
            wkbBuffer.putDouble(offset, z_interval.vmin);
            offset += 8;
        }

        if (bExportMs) {
            wkbBuffer.putDouble(offset, mInterval.vmin);
            offset += 8;
        }

        wkbBuffer.putDouble(offset, env.xmax);
        offset += 8;
        wkbBuffer.putDouble(offset, env.ymin);
        offset += 8;

        if (bExportZs) {
            wkbBuffer.putDouble(offset, z_interval.vmax);
            offset += 8;
        }

        if (bExportMs) {
            wkbBuffer.putDouble(offset, mInterval.vmax);
            offset += 8;
        }

        wkbBuffer.putDouble(offset, env.xmax);
        offset += 8;
        wkbBuffer.putDouble(offset, env.ymax);
        offset += 8;

        if (bExportZs) {
            wkbBuffer.putDouble(offset, z_interval.vmin);
            offset += 8;
        }

        if (bExportMs) {
            wkbBuffer.putDouble(offset, mInterval.vmin);
            offset += 8;
        }

        wkbBuffer.putDouble(offset, env.xmin);
        offset += 8;
        wkbBuffer.putDouble(offset, env.ymax);
        offset += 8;

        if (bExportZs) {
            wkbBuffer.putDouble(offset, z_interval.vmax);
            offset += 8;
        }

        if (bExportMs) {
            wkbBuffer.putDouble(offset, mInterval.vmax);
            offset += 8;
        }

        wkbBuffer.putDouble(offset, env.xmin);
        offset += 8;
        wkbBuffer.putDouble(offset, env.ymin);
        offset += 8;

        if (bExportZs) {
            wkbBuffer.putDouble(offset, z_interval.vmin);
            offset += 8;
        }

        if (bExportMs) {
            wkbBuffer.putDouble(offset, mInterval.vmin);
            offset += 8;
        }

        return offset;
    }

}
