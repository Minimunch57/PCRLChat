package com.minimunch57.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JWindow;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.minimunch57.main.Settings;

/**
 * 
 * @author Minimunch57
 *
 */
public class Chat {
	
	/** The <tt>JWindow</tt> container of this chat window. */
	private JWindow window;
	/** The content pane of this chat window. */
	private JPanel contentPane;
	/** The <tt>JTextPane</tt> containing the chat text.*/
	private JTextPane chatPane;
	/** The <tt>JTextPane</tt> containing the drop shadow for the chat text. */
	private JTextPane chatPaneShadow;
	
	/** The x and y values for the location of this chat window. */
	private int x, y;
	/** The width and height values of this chat window. */
	private int width, height;
	
	/** A <tt>Timer</tt> responsible for fading the quick chat if no more quick chats are sent within the time. */
	private Timer fadeExpirationTimer = new Timer(2500, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent ae) {
			fadeOut(2000);
		}
	});
	
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
				fadeExpirationTimer.restart();
			}
			else if(window.getOpacity()>0) {
				window.setVisible(true);
			}
		}
	});
	

	/**
	 * <ul>
	 * <p>	<b><i>Chat</i></b>
	 * <p>	<code>public Chat(int frameX, int frameY)</code>
	 * <p>	Creates a new <tt>Chat</tt> at the passed coordinates.
	 * @param frameX - the x-coordinate
	 * @param frameY - the y-coordinate
	 * </ul>
	 */
	public Chat(int frameX, int frameY) {
		/*	Set the dimensions and frame positions for the chat window. */
		width = 400;
		height = 240;
		x = frameX;
		y = frameY;
		
		/*	Setup the effect's containers and label. */
		setupWindow();
		setupContentPane();
		setupChatPane();
		
		/*	Sets the fade expiration Timer to not repeat. */
		fadeExpirationTimer.setRepeats(false);
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>setupWindow</i></b>
	 * <p>	<code>private void setupWindow()</code>
	 * <p>	Sets up the <tt>JWindow</tt> of this chat window.
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
	 * <p>	Sets up the content pane of this chat window.
	 * </ul>
	 */
	private void setupContentPane() {
		contentPane = new JPanel();
		contentPane.setBounds(0, 0, width, height);
		contentPane.setFocusable(false);
		contentPane.setOpaque(false);
		contentPane.setLayout(null);
		window.setContentPane(contentPane);
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>setupChatPane</i></b>
	 * <p>	<code>private void setupChatPane()</code>
	 * <p>	Sets up the <tt>JTextPane</tt> of this chat window.
	 * </ul>
	 */
	private void setupChatPane() {
		chatPane = new JTextPane() {
			/** This <tt>JTextPane</tt>'s unique serial. */
			private static final long serialVersionUID = 3747290327413958822L;
			
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
		chatPane.setBounds(0, 0, width, height-50);
		chatPane.setFocusable(false);
		chatPane.setEditable(false);
		chatPane.setFont(new Font("Arial Narrow", Font.PLAIN, 20));
		chatPane.setHighlighter(null);
		chatPane.setForeground(new Color(180, 180, 180));
		chatPane.setOpaque(false);
		chatPane.setBorder(null);
		chatPane.setVisible(true);
		
		//	Force auto-scroll to bottom when new text is appended.
		final DefaultCaret caret = (DefaultCaret) chatPane.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		final JScrollPane scrollPane = new JScrollPane(chatPane);
		scrollPane.setBounds(chatPane.getBounds());
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);
		
		//	Not implemented yet
		chatPaneShadow = new JTextPane();
		chatPaneShadow.setBounds(2, 2, width, height);
		chatPaneShadow.setFocusable(false);
		chatPaneShadow.setFont(new Font("Arial Narrow", Font.TYPE1_FONT, 20));
		chatPaneShadow.setHighlighter(null);
//		chatPaneShadow.setBackground(new Color(0, 0, 0, 0));
		chatPaneShadow.setForeground(new Color(0, 0, 0));
		chatPaneShadow.setVisible(true);
//		contentPane.add(chatPaneShadow);
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>appendTextToPane</i></b>
	 * <p>	<code>private void appendTextToPane(String text, Color color)</code>
	 * <p>	Appends the passed, colored <code>text</code> to the chat pane at the end of the line.
	 * @param text - the text to append.
	 * @param color - the color of the text.
	 * </ul>
	 */
	private void appendTextToPane(String text, Color color) {
		SwingUtilities.invokeLater(() -> {
			final StyledDocument styledDocument = chatPane.getStyledDocument();
			final Style changingStyle = styledDocument.addStyle("text", StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE));
			if(text.contains("YOU: ")&&color.equals(Color.WHITE)) {
				StyleConstants.setBold(changingStyle, true);
			}
			changingStyle.addAttribute(StyleConstants.Foreground, color);
			
			try {
				styledDocument.insertString(styledDocument.getLength(), text, styledDocument.getStyle("text"));
			} catch(Exception e) {}
		});
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>addTextToPane</i></b>
	 * <p>	<code>private void addTextToPane(String text, Color color)</code>
	 * <p>	Adds the passed, colored <code>text</code> to the chat pane in a new line.
	 * @param text - the text to add.
	 * @param color - the color of the text.
	 * </ul>
	 */
	private void addTextToPane(String text, Color color) {
		if(chatPane.getText().length()>0) {
			text = "\n" + text;
		}
		appendTextToPane(text, color);
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>sendQuickChat</i></b>
	 * <p>	<code>public void sendQuickChat(int chatCode1, int chatCode2)</code>
	 * <p>	Sends a quick chat to this chat window.
	 * 		The message sent is dependent upon the passed chat codes and the current settings.
	 * 		Chat codes are numbers ranging from 1 to 4 inclusively.
	 * @param chatCode1 - the first chat code.
	 * @param chatCode2 - the second chat code.
	 * </ul>
	 */
	public void sendQuickChat(int chatCode1, int chatCode2) {
		//Send a chat message based on what is saved in the Settings.
		addTextToPane("YOU: ", Color.WHITE);
		appendTextToPane(Settings.getQuickChats(chatCode1)[chatCode2-1], new Color(180, 180, 180));
		fadeIn(1);
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>refresh</i></b>
	 * <p>	<code>private void refresh()</code>
	 * <p>	Refreshes the chat window, repainting the <tt>JWindow</tt> and all of its components.
	 * </ul>
	 */
	@SuppressWarnings("unused")
	private void refresh() {
		SwingUtilities.invokeLater(() -> {
			window.repaint();
			contentPane.repaint();
			chatPane.repaint();
			chatPaneShadow.repaint();
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
		if(fadeExpirationTimer.isRunning()) {
			fadeExpirationTimer.restart();
		}
		else {
			fadeStartTime = System.nanoTime();
			fadeTimeMilli = milliseconds;
			fadeTimer.restart();
		}
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
	 * <p>	Closes this <tt>Chat</tt> window, disposing of its <tt>JWindow</tt> and all of its contents.
	 * </ul>
	 */
	public void close() {
		window.dispose();
		window = null;
		contentPane = null;
		chatPane = null;
		chatPaneShadow = null;
		try {
			finalize();
		} catch (Throwable t) {
			JOptionPane.showMessageDialog(null, "A minor, internal error has occurred.\nPlease notify the developer and provide error code: ChaCloCat1", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>getX</i></b>
	 * <p>	<code>public float getX()</code>
	 * <p>	Gets this <tt>Chat</tt> container's current x-coordinate.
	 * @return the x-coordinate of this <tt>Chat</tt> container.
	 * </ul>
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>getY</i></b>
	 * <p>	<code>public float getY()</code>
	 * <p>	Gets this <tt>Chat</tt> container's current y-coordinate.
	 * @return the y-coordinate of this <tt>Chat</tt>'s container.
	 * </ul>
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>getWidth</i></b>
	 * <p>	<code>public int getWidth()</code>
	 * <p>	Gets this <tt>Chat</tt> container's width.
	 * @return the width of this <tt>Chat</tt>'s container.
	 * </ul>
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>getHeight</i></b>
	 * <p>	<code>public int getHeight()</code>
	 * <p>	Gets this <tt>Chat</tt> container's height.
	 * @return the height of this <tt>Chat</tt>'s container.
	 * </ul>
	 */
	public int getHeight() {
		return height;
	}
}
