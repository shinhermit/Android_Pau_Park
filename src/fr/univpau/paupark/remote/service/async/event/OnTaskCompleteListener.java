package fr.univpau.paupark.remote.service.async.event;

/**
 * Listens for the completion of a task and realises some actions.
 * 
 * @author Josuah Aron
 *
 */
public interface OnTaskCompleteListener
{
	/**
	 * Realises some action when a task is over.
	 */
	public void onTaskComplete();
}
