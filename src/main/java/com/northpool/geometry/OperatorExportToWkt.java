
package com.northpool.geometry;

public abstract class OperatorExportToWkt extends Operator {
	@Override
	public Type getType() {
		return Type.ExportToWkt;
	}

	public abstract String execute(int exportFlags, Geometry geometry,
			ProgressTracker progress_tracker);

	public static OperatorExportToWkt local() {
		return (OperatorExportToWkt) OperatorFactoryLocal.getInstance()
				.getOperator(Type.ExportToWkt);
	}

}
