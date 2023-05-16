
package com.northpool.geometry;

/**
 * Generalizes geometries using Douglas-Peucker algorithm.
 */
public abstract class OperatorGeneralize extends Operator {
  @Override
  public Type getType() {
    return Type.Generalize;
  }

  /**
   * Performs the Generalize operation on a geometry set. Point and
   * multipoint geometries are left unchanged. An envelope is converted to a
   * polygon.
   * 
   */
  public abstract GeometryCursor execute(GeometryCursor geoms,
          double maxDeviation, boolean bRemoveDegenerateParts,
          ProgressTracker progressTracker);

  /**
   * Performs the Generalize operation on a single geometry. Point and
   * multipoint geometries are left unchanged. An envelope is converted to a
   * polygon.
   */
  public abstract Geometry execute(Geometry geom, double maxDeviation,
          boolean bRemoveDegenerateParts, ProgressTracker progressTracker);

  public static OperatorGeneralize local() {
    return (OperatorGeneralize) OperatorFactoryLocal.getInstance().getOperator(Type.Generalize);
  }

}
