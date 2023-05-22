package com.minimunch57.listeners;

/**
 * 
 * @author Minimunch57
 *
 */
public interface FadeListener {
	
	/**
	 * <ul>
	 * <p>	<b><i>fadeIn</i></b>
	 * <p>	<code>public void fadeIn(int milliseconds)</code>
	 * <p>	Called when a fade in animation is to play on the respective component.
	 * @param milliseconds - the amount of time it should take to fade.
	 * </ul>
	 */
	public void fadeIn(int milliseconds);
	
	/**
	 * <ul>
	 * <p>	<b><i>fadeOut</i></b>
	 * <p>	<code>public void fadeOut(int milliseconds)</code>
	 * <p>	Called when a fade out animation is to play on the respective component.
	 * @param milliseconds - the amount of time it should take to fade.
	 * </ul>
	 */
	public void fadeOut(int milliseconds);
}
