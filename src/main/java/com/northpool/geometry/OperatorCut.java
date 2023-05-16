

package com.northpool.geometry;

/**
 * Splits the target polyline or polygon where it is crossed by the cutter polyline.
 */
public abstract class OperatorCut extends Operator {
  @Override
  public Type getType() {
    return Type.Cut;
  }

  /**
   * Performs the Cut operation on a geometry. 
   * @param bConsiderTouch Indicates whether we consider a touch event a cut. 
   * This only applies to polylines, but it's recommended to set this variable to True. 
   * @param cuttee The input geometry to be cut. 
   * @param cutter The polyline that will be used to divide the cuttee into 
   * pieces where it crosses the cutter. 
   * @return Returns a GeometryCursor of cut geometries. 
   * All left cuts will be grouped together in the first geometry. Right cuts and 
   * coincident cuts are grouped in the second geometry, and each undefined cut along
   * with any uncut parts are output as separate geometries. If there were no cuts
   * the cursor will return no geometry. If the left or right cut does not
   * exist, the returned geometry will be empty for this type of cut. An
   * undefined cut will only be produced if a left cut or right cut was
   * produced and there was a part left over after cutting or a cut is
   * bounded to the left and right of the cutter.
   */
  public abstract GeometryCursor execute(boolean bConsiderTouch,
          Geometry cuttee, Polyline cutter, SpatialReference spatialReference,
          ProgressTracker progressTracker);

  public static OperatorCut local() {
    return (OperatorCut) OperatorFactoryLocal.getInstance().getOperator(Type.Cut);
  }
}
