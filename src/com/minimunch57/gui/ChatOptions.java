package com.minimunch57.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * 
 * @author Minimunch57
 *
 */
public class ChatOptions {

	/** The <tt>JWindow</tt> container of this chat options window. */
	private JWindow window;
	/** The content pane of this chat options window. */
	private JPanel contentPane;
	/** The <tt>JTextPane</tt> containing the chat text.*/
	private JTextPane chatPane;
	
	/** The x and y values for the location of this chat options window. */
	private int x, y;
	/** The width and height values of this chat options window. */
	private int width, height;
	
	/** A <code>long</code> value for the last fade start time in nanoseconds. */
	private long fadeStartTime = 0L;
	/** A <code>int</code> for the amount of time it should take to fade in the window. */
	private int fadeTimeMilli = 1;
	/** A <tt>Timer</tt> responsible for handling the fading of the <tt>JWindow</tt>. */
	private Timer fadeTimer = new Timer(10, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent ae) {
			//	Sets opacity, constraining it within the bounds of 0.0f to 1.0f.
			final long timeElapsedMilli = (int) ((System.nanoTime() - fadeStartTime)/1000000L);
			final float adjustedFadeAmnt = Math.abs(1.0f/fadeTimeMilli)*timeElapsedMilli;
			if(fadeTimeMilli<0) {
				window.setOpacity(Math.min(Math.max(0, 1.0f-adjustedFadeAmnt), 1));
			}
			else {
				window.setOpacity(Math.min(Math.max(0, adjustedFadeAmnt), 1));
				
			}
			
			if(window.getOpacity()<=0) {
				window.setVisible(false);
				fadeTimer.stop();
			}
			else if(window.getOpacity()>=1.0) {
				window.setVisible(true);
				fadeTimer.stop();
			}
			else if(window.getOpacity()>0) {
				window.setVisible(true);
			}
		}
	});
	
	/**
	 * <ul>
	 * <p>	<b><i>ChatOptions</i></b>
	 * <p>	<code>public ChatOptions(int frameX, int frameY)</code>
	 * <p>	Creates a new <tt>ChatOptions</tt> window at the passed coordinates.
	 * @param frameX - the x-coordinate
	 * @param frameY - the y-coordinate
	 * </ul>
	 */
	public ChatOptions(int frameX, int frameY) {
		/*	Set the dimensions and frame positions for the chat window. */
		width = 270;
		height = 200;
		x = frameX;
		y = frameY;
		
		/*	Setup the effect's containers and label. */
		setupWindow();
		setupContentPane();
		setupChatPane();
	}

	/**
	 * <ul>
	 * <p>	<b><i>setupWindow</i></b>
	 * <p>	<code>private void setupWindow()</code>
	 * <p>	Sets up the <tt>JWindow</tt> of this chat options window.
	 * </ul>
	 */
	private void setupWindow() {
		window = new JWindow();
		window.setBounds(x, y, width, height); 
		window.setFocusable(false);
		window.setAutoRequestFocus(false);
		window.setBackground(new Color(0, 0, 0, 0));
		window.setAlwaysOnTop(true);
		window.setOpacity(0);
		window.setVisible(false);
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>setupContentPane</i></b>
	 * <p>	<code>private void setupContentPane()</code>
	 * <p>	Sets up the content pane of this chat options window.
	 * </ul>
	 */
	private void setupContentPane() {
		contentPane = new JPanel() {

			/** This <tt>JPanel</tt>'s unique serial. */
			private static final long serialVersionUID = 5866103135430141534L;
			
			@Override
			public void paintComponent(Graphics g) {
				//	Set rendering hints for better quality and readability of text.
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
				
				//	Draw a partially transparent background.
				g2d.setComposite(AlphaComposite.Src.derive(.50f));
				g2d.setColor(getBackground());
				g2d.fillRect(0, 0, width, height);
				
				super.paintComponent(g2d);
			}
		};
		contentPane.setBounds(0, 0, width, height);
		contentPane.setFocusable(false);
		contentPane.setBackground(Color.BLACK);
		contentPane.setOpaque(false);
		contentPane.setLayout(null);
		window.setContentPane(contentPane);
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>setupChatPane</i></b>
	 * <p>	<code>private void setupChatPane()</code>
	 * <p>	Sets up the <tt>JTextPane</tt> of this chat options window.
	 * </ul>
	 */
	private void setupChatPane() {
		chatPane = new JTextPane() {
			/** This <tt>JTextPane</tt>'s unique serial. */
			private static final long serialVersionUID = 4574578130091175758L;
			
			@Override
			public void paintComponent(Graphics g) {
				//	Set rendering hints for better quality and readability of text.
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
				
				super.paintComponent(g2d);
			}
		};
		chatPane.setBounds(0, 0, width, height);
		chatPane.setFocusable(false);
		chatPane.setEditable(false);
		chatPane.setFont(new Font("Arial Narrow", Font.PLAIN, 20));
		chatPane.setHighlighter(null);
		chatPane.setForeground(new Color(180, 180, 180));
		chatPane.setOpaque(false);
		chatPane.setVisible(true);
		contentPane.add(chatPane);
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>refresh</i></b>
	 * <p>	<code>private void refresh()</code>
	 * <p>	Refreshes the chat options window, repainting the <tt>JWindow</tt> and all of its components.
	 * </ul>
	 */
	@SuppressWarnings("unused")
	private void refresh() {
		SwingUtilities.invokeLater(() -> {
			window.repaint();
			contentPane.repaint();
			chatPane.repaint();
		});
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>fadeIn</i></b>
	 * <p>	<code>public void fadeIn(int milliseconds)</code>
	 * <p>	Fades in the <tt>Chat</tt> window.
	 * 		The amount of time it takes to fade in depends on the amount of milliseconds passed.
	 * @param milliseconds - the amount of time it should take to fade in the window.
	 * </ul>
	 */
	public void fadeIn(int milliseconds) {
		fadeStartTime = System.nanoTime();
		fadeTimeMilli = milliseconds;
		fadeTimer.restart();
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>fadeOut</i></b>
	 * <p>	<code>public void fadeOut(int milliseconds)</code>
	 * <p>	Fades out the <tt>Chat</tt> window.
	 * 		The amount of time it takes to fade out depends on the amount of milliseconds passed.
	 * @param milliseconds - the amount of time it should take to fade out the window.
	 * </ul>
	 */
	public void fadeOut(int milliseconds) {
		fadeStartTime = System.nanoTime();
		fadeTimeMilli = -milliseconds;
		fadeTimer.restart();
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>close</i></b>
	 * <p>	<code>public void close()</code>
	 * <p>	Closes this <tt>ChatOptions</tt> window, disposing of its <tt>JWindow</tt> and all of its contents.
	 * </ul>
	 */
	public void close() {
		window.dispose();
		window = null;
		contentPane = null;
		chatPane = null;
		try {
			finalize();
		} catch (Throwable e) {
			JOptionPane.showMessageDialog(null, "A minor, internal error has occurred.\nPlease notify the developer and provide error code: ChaOptCloCat1", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>getX</i></b>
	 * <p>	<code>public float getX()</code>
	 * <p>	Gets this <tt>ChatOptions</tt> window's current x-coordinate.
	 * @return the x-coordinate of this <tt>ChatOptions</tt> window.
	 * </ul>
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>getY</i></b>
	 * <p>	<code>public float getY()</code>
	 * <p>	Gets this <tt>ChatOptions</tt> window's current y-coordinate.
	 * @return the y-coordinate of this <tt>ChatOptions</tt> window.
	 * </ul>
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>getWidth</i></b>
	 * <p>	<code>public int getWidth()</code>
	 * <p>	Gets this <tt>ChatOptions</tt> window's width.
	 * @return the width of this <tt>ChatOptions</tt> window.
	 * </ul>
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>getHeight</i></b>
	 * <p>	<code>public int getHeight()</code>
	 * <p>	Gets this <tt>ChatOptions</tt> window's height.
	 * @return the height of this <tt>ChatOptions</tt> window.
	 * </ul>
	 */
	public int getHeight() {
		return height;
	}
}
