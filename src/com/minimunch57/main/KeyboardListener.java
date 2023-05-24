package com.minimunch57.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Timer;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.minimunch57.listeners.FadeListener;
import com.minimunch57.listeners.InputListener;

/**
 * 
 * @author Minimunch57
 *
 */
public class KeyboardListener implements NativeKeyListener, FadeListener, InputListener {
	
	/**
	 *	An <code>int</code> for the set of quick chats <b>currently</b> being displayed to pick from.
	 *	This number will be a number in range 0-4, with 1-4 being representative of set numbers, and 0 indicating no current selection.
	 */
	private int currentSetSelection = 0;
	
	/** A <tt>Timer</tt> responsible for fading the quick chat options menu if no quick chat is selected. */
	private Timer setSelectionExpirationTimer = new Timer(1500, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent ae) {
			currentSetSelection = 0;
			fadeOut(1000);
		}
	});
	
	/**
	 * <ul>
	 * <p>	<b><i>KeyboardListener</i></b>
	 * <p>	<code>public KeyboardListener()</code>
	 * <p>	Creates a new <tt>KeyboardListener</tt> for listening to keyboard inputs.
	 * @throws NativeHookException
	 * </ul>
	 */
	public KeyboardListener() throws NativeHookException {
		final Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		GlobalScreen.registerNativeHook();
		registerKeyListener();
		
		setSelectionExpirationTimer.setRepeats(false);
		if(Settings.isEnabled()) {
		}
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>registerKeyListener</i></b>
	 * <p>	<code>public void registerKeyListener()</code>
	 * <p>	Registers this <tt>KeyboardListener</tt> as a global key listener.
	 * 		This method registers the key listener, and it is used by the <tt>KeyboardListener</tt> constructor.
	 * </ul>
	 */
	public void registerKeyListener() {
		GlobalScreen.addNativeKeyListener(this);
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>deregisterKeyListener</i></b>
	 * <p>	<code>public void deregisterKeyListener()</code>
	 * <p>	Deregisters this <tt>KeyboardListener</tt> as a global key listener.
	 * </ul>
	 */
	public void deregisterKeyListener() {
		GlobalScreen.removeNativeKeyListener(this);
	}
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent nke) {
		//	Do Nothing
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent nke) {
		/*
		 *	Checks if the released key is one of the quick chat keybinds. If so, it then checks whether to use it as the set selection or not.
		 *	If so, it sets it in preparation for the next quick chat keybind.
		 *	If not, then it sends a quick chat using the current set selection and the keybind that was just pressed.
		 */
		if(nke.getKeyCode()==Settings.getKeyOne()) {
			if(currentSetSelection==0) {
				currentSetSelection = 1;
				setSelectionExpirationTimer.restart();
				fadeIn(1);
			}
			else {
				sendQuickChat(currentSetSelection, 1);
				setSelectionExpirationTimer.stop();
				currentSetSelection = 0;
				fadeOut(1);
			}
		}
		else if(nke.getKeyCode()==Settings.getKeyTwo()) {
			if(currentSetSelection==0) {
				currentSetSelection = 2;
				setSelectionExpirationTimer.restart();
				fadeIn(1);
			}
			else {
				sendQuickChat(currentSetSelection, 2);
				setSelectionExpirationTimer.stop();
				currentSetSelection = 0;
				fadeOut(1);
			}
		}
		else if(nke.getKeyCode()==Settings.getKeyThree()) {
			if(currentSetSelection==0) {
				currentSetSelection = 3;
				setSelectionExpirationTimer.restart();
				fadeIn(1);
			}
			else {
				sendQuickChat(currentSetSelection, 3);
				setSelectionExpirationTimer.stop();
				currentSetSelection = 0;
				fadeOut(1);
			}
		}
		else if(nke.getKeyCode()==Settings.getKeyFour()) {
			if(currentSetSelection==0) {
				currentSetSelection = 4;
				setSelectionExpirationTimer.restart();
				fadeIn(1);
			}
			else {
				sendQuickChat(currentSetSelection, 4);
				setSelectionExpirationTimer.stop();
				currentSetSelection = 0;
				fadeOut(1);
			}
		}
	} 

	@Override
	public void nativeKeyTyped(NativeKeyEvent nke) {
		//Do Nothing
	}
	
	@Override
	public void fadeIn(int milliseconds) {
		//	Nothing unless overriden.
	}

	@Override
	public void fadeOut(int milliseconds) {
		//	Nothing unless overriden.
	}

	@Override
	public void sendQuickChat(int sel1, int sel2) {
		//	Nothing unless overriden.
	}
}
