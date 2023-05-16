


package com.northpool.geometry;

/** 
 * Creates the convex hull of the input geometry.
 */
public abstract class OperatorConvexHull extends Operator {
  @Override
  public Operator.Type getType() {
    return Operator.Type.ConvexHull;
  }

  /** 
   * Calculates the convex hull.
   * @param geoms The input geometry cursor.
   * @param progress_tracker The progress tracker. Allows cancellation of a lengthy operation.
   * @param b_merge Put true if you want the convex hull of all the geometries in the cursor combined.
   * Put false if you want the convex hull of each geometry in the cursor individually.
   * @return Returns a cursor over result convex hulls.
   */
  abstract public GeometryCursor execute(GeometryCursor geoms, boolean b_merge,
          ProgressTracker progress_tracker);

  /** 
   * Calculates the convex hull geometry.
   * @param geom The input geometry.
   * @param progress_tracker The progress tracker. Allows cancellation of a lengthy operation.
   * @return Returns the convex hull.
   * 
   * Point - Returns the same point.
   * Envelope - returns the same envelope.
   * MultiPoint - If the point count is one, returns the same multipoint. If the point count is two, returns a polyline of the points. Otherwise, computes and returns the convex hull polygon.
   * Segment - Returns a polyline consisting of the segment.
   * Polyline - If consists of only one segment, returns the same polyline. Otherwise, computes and returns the convex hull polygon.
   * Polygon - If more than one path or if the path isn't already convex, computes and returns the convex hull polygon. Otherwise, returns the same polygon.
   */
  abstract public Geometry execute(Geometry geom,
          ProgressTracker progress_tracker);

  /** 
   * Checks whether a Geometry is convex.
   * @param geom The input geometry to test for convex.
   * @param progress_tracker The progress tracker.
   * @return Returns true if the geometry is convex.
   */
  abstract public boolean isConvex(Geometry geom,
          ProgressTracker progress_tracker);

  public static OperatorConvexHull local() {
    return (OperatorConvexHull) OperatorFactoryLocal.getInstance().getOperator(Type.ConvexHull);
  }
}
