
package com.northpool.geometry;

public abstract class OperatorBoundary extends Operator {
  @Override
  public Type getType() {
    return Type.Boundary;
  }

  /**
   * Calculates the boundary geometry.
   * @param geoms The input geometry cursor.
   * @param progress_tracker The progress tracker, that allows to cancel the lengthy operation.
   * @return Returns a cursor over boundaries for each geometry.
   */
  abstract public GeometryCursor execute(GeometryCursor geoms,
          ProgressTracker progress_tracker);

  /**
   * Calculates the boundary.
   * @param geom The input geometry.
   * @param progress_tracker The progress tracker, that allows to cancel the lengthy operation.
   * @return Returns the boundary.
   *
   * For Point - returns an empty point.
   * For Multi_point - returns an empty point.
   * For Envelope - returns a polyline, that bounds the envelope.
   * For Polyline - returns a multipoint, using OGC specification (includes path endpoints, using mod 2 rule).
   * For Polygon - returns a polyline that bounds the polygon (adds all rings of the polygon to a polyline).
   */
  abstract public Geometry execute(Geometry geom,
          ProgressTracker progress_tracker);

  public static OperatorBoundary local() {
    return (OperatorBoundary) OperatorFactoryLocal.getInstance().getOperator(Type.Boundary);
  }
}
