
package com.northpool.geometry;

/**
 *A callback to provide progress and cancel tracking mechanism for lengthy operation.
 */
public abstract class ProgressTracker {
	/**
	 *Periodically called by a lengthy operation to check if the caller requested to cancel.
	 *@param step The current step of the operation.
	 *@param totalExpectedSteps is the number of steps the operation is expects to complete its task.
	 *@return true, if the operation can continue. Returns False, when the operation has to terminate due to a user cancelation.
	 */
	public abstract boolean progress(int step, int totalExpectedSteps);
	
	/**
	 * Checks the tracker and throws UserCancelException if tracker is not null and progress returns false
	 * @param tracker can be null, then the method does nothing.
	 */
	public static void checkAndThrow(ProgressTracker tracker) {
		if (tracker != null && !tracker.progress(-1, -1))
			throw new UserCancelException();
	}
}
