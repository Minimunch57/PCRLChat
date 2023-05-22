package com.minimunch57.main;

import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * 
 * @author Minimunch57
 *
 */
public class Settings {
	
	/** A <code>boolean</code> for whether or not PCRLChat is enabled. */
	private static boolean enabled = true;
	
	/** An <code>int</code> for the key code of the <b>first</b> quick chat key. */
	private static int keyOne = NativeKeyEvent.VC_1;
	/** An <code>int</code> for the key code of the <b>second</b> quick chat key. */
	private static int keyTwo = NativeKeyEvent.VC_2;
	/** An <code>int</code> for the key code of the <b>third</b> quick chat key. */
	private static int keyThree = NativeKeyEvent.VC_3;
	/** An <code>int</code> for the key code of the <b>fourth</b> quick chat key. */
	private static int keyFour = NativeKeyEvent.VC_4;
	
	/**	A <tt>String</tt> array for all of the quick chat <b>options</b>. */
	final public static String[] chatOptions = {"$#@%!", "All yours.", "Calculated.", "Centering!", "Close one!",
	"Defending...", "Go for it!", "Great clear!", "Great pass!", "Holy cow!", "I got it!", "In position.", "Incoming!",
	"My bad...", "My fault.", "Need boost!", "Nice block!", "Nice one!", "Nice shot!", "No problem.", "No way!", "Noooo!",
	"OMG!", "Okay.", "Oops!", "Savage!", "Siiiick!", "Sorry!", "Take the shot!", "Thanks!", "What a play!", "What a save!",
	"Whew.", "Whoops...", "Wow!", "gg", "Well played.", "That was fun!", "Rematch!", "One. More. Game.", "What a game!",
	"Nice moves.", "Everybody dance!"};
	
	/**	A <tt>String</tt> array for the first set of quick chat <b>selections</b>. */
	private static String[] firstSelections = {"I got it!", "Need boost!", "Take the shot!", "Defending..."};
	/**	A <tt>String</tt> array for the second set of quick chat <b>selections</b>. */
	private static String[] secondSelections = {"Nice shot!", "Great pass!", "Thanks!", "What a save!"};
	/**	A <tt>String</tt> array for the third set of quick chat <b>selections</b>. */
	private static String[] thirdSelections = {"OMG!", "Noooo!", "Wow!", "Close one!"};
	/**	A <tt>String</tt> array for the fourth set of quick chat <b>selections</b>. */
	private static String[] fourthSelections = {"$#@%!", "No problem.", "Whoops...", "Sorry!"};
	
	/* -------------------- HELPER METHODS -------------------- */
	
	
	/* -------------------- GET/IS METHODS -------------------- */	
	/**
	 * <ul>
	 * <p>	<b><i>isEnabled</i></b>
	 * <p>	<code>public boolean isEnabled()</code>
	 * <p>	Returns a <code>boolean</code> for whether or not PCRLChat is enabled.
	 * @return Returns <code>true</code> if PCRLChat is enabled; <code>false</code> otherwise.
	 * </ul>
	 */
	public static boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>getKeyOne</i></b>
	 * <p>	<code>public static int getKeyOne()</code>
	 * <p>	Gets the current key code of the first quick chat keybind.
	 * @return an <code>int</code> representing the current key code of the first quick chat keybind.
	 * </ul>
	 */
	public static int getKeyOne() {
		return keyOne;
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>getKeyTwo</i></b>
	 * <p>	<code>public static int getKeyTwo()</code>
	 * <p>	Gets the current key code of the second quick chat keybind.
	 * @return an <code>int</code> representing the current key code of the second quick chat keybind.
	 * </ul>
	 */
	public static int getKeyTwo() {
		return keyTwo;
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>getKeyThree</i></b>
	 * <p>	<code>public static int getKeyThree()</code>
	 * <p>	Gets the current key code of the third quick chat keybind.
	 * @return an <code>int</code> representing the current key code of the third quick chat keybind.
	 * </ul>
	 */
	public static int getKeyThree() {
		return keyThree;
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>getKeyFour</i></b>
	 * <p>	<code>public static int getKeyFour()</code>
	 * <p>	Gets the current key code of the fourth quick chat keybind.
	 * @return an <code>int</code> representing the current key code of the fourth quick chat keybind.
	 * </ul>
	 */
	public static int getKeyFour() {
		return keyFour;
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>getQuickChats</i></b>
	 * <p>	<code>public static String[] getQuickChats(int set)</code>
	 * <p>	Returns the set of quick chats specified by the passed <code>int</code>.
	 * 		There are only four lists of quick chat messages.
	 * 		Therefore, the passed <code>int</code> must at a minimum of 1 and a maximum of 4.
	 * @param set - the <code>int</code> in range 1-4 (inclusive) used to select which list of quick chats to get.
	 * @return the specified set of quick chats in a <tt>String</tt> array. Returns <code>null</code> if the passed <code>int</code> is <1 or >4.
	 * </ul>
	 */
	public static String[] getQuickChats(int set) {
		final String[][] quickChatSetsList = new String[][] {firstSelections, secondSelections, thirdSelections, fourthSelections};
		if(set<1 || set>4) {
			return null;
		}
		return quickChatSetsList[set-1];
	}
	
	/* -------------------- SET METHODS -------------------- */
	/**
	 * <ul>
	 * <p>	<b><i>setEnabled</i></b>
	 * <p>	<code>public void setEnabled(boolean enabled)</code>
	 * <p>	Enables or disables PCRLfChat based on the passed <code>boolean</code>.
	 * </ul>
	 */
	public static void setEnabled(boolean enabled) {
		Settings.enabled = enabled;
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>setKeyOne</i></b>
	 * <p>	<code>public static void setKeyOne(int keyCode)</code>
	 * <p>	Sets the first quick chat keybind to a key based on the passed <code>int</code>.
	 * @param keyCode - an <code>int</code> representing the key code to set the first quick chat keybind to.
	 * </ul>
	 */
	public static void setKeyOne(int keyCode) {
		keyOne = keyCode;
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>setKeyTwo</i></b>
	 * <p>	<code>public static void setKeyTwo(int keyCode)</code>
	 * <p>	Sets the second quick chat keybind to a key based on the passed <code>int</code>.
	 * @param keyCode - an <code>int</code> representing the key code to set the second quick chat keybind to.
	 * </ul>
	 */
	public static void setKeyTwo(int keyCode) {
		keyTwo = keyCode;
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>setKeyThree</i></b>
	 * <p>	<code>public static void setKeyThree(int keyCode)</code>
	 * <p>	Sets the third quick chat keybind to a key based on the passed <code>int</code>.
	 * @param keyCode - an <code>int</code> representing the key code to set the third quick chat keybind to.
	 * </ul>
	 */
	public static void setKeyThree(int keyCode) {
		keyThree = keyCode;
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>setKeyFour</i></b>
	 * <p>	<code>public static void setKeyFour(int keyCode)</code>
	 * <p>	Sets the fourth quick chat keybind to a key based on the passed <code>int</code>.
	 * @param keyCode - an <code>int</code> representing the key code to set the fourth quick chat keybind to.
	 * </ul>
	 */
	public static void setKeyFour(int keyCode) {
		keyFour = keyCode;
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>setQuickChat</i></b>
	 * <p>	<code>public static void setQuickChat(int set, int selection, String text)</code>
	 * <p>	Sets the quick chat specified by the <code>int</code>s <code>set</code> and <code>selection</code> to the passed <code>text</code>.
	 * 		For instance, if you wanted to set, 'gg' to the second set of quick chats in position four, then you would pass it like this:
	 * <p>	<code>setQuickChat(2, 4, "gg");</code>
	 * @param set - an <code>int</code> for specifying which set of quick chats to modify.
	 * @param selection an <code>int</code> for specifying which quick chat in the set to modify.
	 * @param text - the <tt>String</tt> to be applied at the passed quick chat set and position.
	 * </ul>
	 */
	public static void setQuickChat(int set, int selection, String text) {
		final String[][] quickChatSetsList = new String[][] {firstSelections, secondSelections, thirdSelections, fourthSelections};
		quickChatSetsList[set-1][selection-1] = text;
	}
}
