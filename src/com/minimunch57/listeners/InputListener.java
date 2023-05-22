package com.minimunch57.listeners;

/**
 * 
 * @author Minimunch57
 *
 */
public interface InputListener {

	/**
	 * <ul>
	 * <p>	<b><i>sendQuickChat</i></b>
	 * <p>	<code>public void sendQuickChat(int sel1, int sel2)</code>
	 * <p>	Called when a quick chat under the selections <code>sel1</code> and <code>sel2</code> is to be sent.
	 * @param sel1 - the first numerical selection in range 1-4.
	 * @param sel2 - the second numerical selection in range 1-4.
	 * </ul>
	 */
	public void sendQuickChat(int sel1, int sel2);
}
