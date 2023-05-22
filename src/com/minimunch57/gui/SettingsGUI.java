package com.minimunch57.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jnativehook.NativeHookException;

import com.minimunch57.main.KeyboardListener;
import com.minimunch57.main.Main;
import com.minimunch57.main.Settings;

/**
 * 
 * @author Minimunch57
 *
 */
public class SettingsGUI extends JFrame {

	/**	The serial version UID for the GUI. */
	private static final long serialVersionUID = 8079826760765710089L;
	/** The content pane for the GUI. */
	private JPanel contentPane;
	
	/** The file location for the properties file which contains the settings for PCRLChat. */
	private static String propsFileLocation = System.getProperty("user.home") + "/AppData/Roaming/Minimunch57/PCRLChat/PCRLChat Settings.properties";
	/** The <tt>Properties</tt> object that handles the settings. */
	private Properties props = new Properties();
	/** The <tt>FileWriter</tt> object that is responsible for writing the settings to the properties file. */
	private FileWriter writer = null;
	/** The <tt>Chat</tt> window used for displaying quick chats. */
	private Chat chat = null;
	/** The <tt>ChatOptions</tt> window used for displaying quick chat options. */
	private ChatOptions chatOptions = null;
	/** The <tt>KeyboardListener</tt> responsible for handling keyboard inputs. */
	private KeyboardListener kl = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//	Create the SettingsGUI and Start the Application
					new SettingsGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * <ul>
	 * <p>	<b><i>SettingsGUI</i></b>
	 * <p>	<code>public SettingsGUI()</code>
	 * <p>	Creates and shows the settings panel for PCRLChat.
	 * </ul>
	 */
	public SettingsGUI() {
		/*	Create or Load Settings */
		if (!new File(propsFileLocation).exists()) {
			writeSettings(false);
		} else {
			loadSettings();
		}
		
		/*	Final Setup	*/
		setupSwingComponents();
		setupChat();
		setupTray();
		setupKeyboardListener();
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>setupSwingComponents</i></b>
	 * <p>	<code>private void setupSwingComponents()</code>
	 * <p>	Sets up the components for the settings menu.
	 * </ul>
	 */
	private void setupSwingComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>setupTray</i></b>
	 * <p>	<code>private void setupTray()</code>
	 * <p>	Sets up the tray icon, including its menu, items, and behavior.
	 * </ul>
	 */
	private void setupTray() {
		// The Pop-up Menu that appears upon a right-click on the Tray Icon.
		final PopupMenu popMenu = new PopupMenu("PCRLChat");
		// The icon for the application in the Tray.
		final TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/com/minimunch57/images/logo16.png")), "PCRLChat", popMenu);
		// The system tray.
		final SystemTray tray = SystemTray.getSystemTray();
		
		/*	Setup Menu Items */
		final MenuItem aboutItem = new MenuItem("About");
		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(null, "PCRLChat v1.0\n\nCreated by Minimunch57", "PCRLC",
						JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(SettingsGUI.class.getResource("/com/minimunch57/images/logo64.png")));
			}
		});
		final MenuItem enableDisableItem = new MenuItem("Disable");
		enableDisableItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				//	Enable or Disable the Application
				if (Settings.isEnabled()) {
					Settings.setEnabled(false);
					kl.deregisterKeyListener();
					enableDisableItem.setLabel("Enable");
				} else {
					Settings.setEnabled(true);
					kl.registerKeyListener();
					enableDisableItem.setLabel("Disable");
				}
			}
		});
		final MenuItem settingsItem = new MenuItem("Settings");
		settingsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				//	Make the Settings GUI Visible
				setVisible(true);
			}
		});
		final MenuItem exitItem = new MenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				//	Exit the Application
				System.exit(0);
			}
		});
		popMenu.add(aboutItem);
		popMenu.add(enableDisableItem);
		popMenu.add(settingsItem);
		popMenu.add(exitItem);
		try {
			tray.add(trayIcon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>setupChat</i></b>
	 * <p>	<code>private void setupChat()</code>
	 * <p>	Sets up the chat display and selection windows.
	 * </ul>
	 */
	private void setupChat() {
		/*	Create the Chat Window */
		chat = new Chat(40, 40);
		
		/*	Create the Chat Options Window */
		chatOptions = new ChatOptions(40, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2-100);
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>setupChat</i></b>
	 * <p>	<code>private void setupKeyboardListener()</code>
	 * <p>	Sets up the keyboard listener.
	 * </ul>
	 */
	private void setupKeyboardListener() {
		/*	Start the Keyboard Listener */
		try {
			kl = new KeyboardListener() {
				@Override
				public void fadeIn(int milliseconds) {
					chatOptions.fadeIn(milliseconds);
				}

				@Override
				public void fadeOut(int milliseconds) {
					chatOptions.fadeOut(milliseconds);
				}
				
				@Override
				public void sendQuickChat(int sel1, int sel2) {
					chat.sendQuickChat(sel1, sel2);
				}
			};
		} catch (NativeHookException nhe) {
			nhe.printStackTrace();
		}
	}

	/**
	 * <ul>
	 * <p>	<b><i>writeSettings</i></b>
	 * <p>	<code>public void writeSettings(boolean fileExists)</code>
	 * <p>	Writes the current settings to the properties file.
	 * @param fileExists - pass <code>true</code> if the settings file exists; pass <code>false</code> otherwise.
	 * </ul>
	 */
	private void writeSettings(boolean fileExists) {
		try {
			if(!fileExists) {
				File file = new File(propsFileLocation.substring(0, propsFileLocation.lastIndexOf("/")+1));
				file.mkdirs();
				file = new File(propsFileLocation);
				file.createNewFile();
			}
			writer = new FileWriter(propsFileLocation);
			props.setProperty("EnabledDisabled", Boolean.toString(Settings.isEnabled()));
			props.setProperty("KeybindOne", String.valueOf(Settings.getKeyOne()));
			props.setProperty("KeybindTwo", String.valueOf(Settings.getKeyTwo()));
			props.setProperty("KeybindThree", String.valueOf(Settings.getKeyThree()));
			props.setProperty("KeybindFour", String.valueOf(Settings.getKeyFour()));
			props.setProperty("Chat-OneOne", Settings.getQuickChats(1)[0]);
			props.setProperty("Chat-OneTwo", Settings.getQuickChats(1)[1]);
			props.setProperty("Chat-OneThree", Settings.getQuickChats(1)[2]);
			props.setProperty("Chat-OneFour", Settings.getQuickChats(1)[3]);
			props.setProperty("Chat-TwoOne", Settings.getQuickChats(2)[0]);
			props.setProperty("Chat-TwoTwo", Settings.getQuickChats(2)[1]);
			props.setProperty("Chat-TwoThree", Settings.getQuickChats(2)[2]);
			props.setProperty("Chat-TwoFour", Settings.getQuickChats(2)[3]);
			props.setProperty("Chat-ThreeOne", Settings.getQuickChats(3)[0]);
			props.setProperty("Chat-ThreeTwo", Settings.getQuickChats(3)[1]);
			props.setProperty("Chat-ThreeThree", Settings.getQuickChats(3)[2]);
			props.setProperty("Chat-ThreeFour", Settings.getQuickChats(3)[3]);
			props.setProperty("Chat-FourOne", Settings.getQuickChats(4)[0]);
			props.setProperty("Chat-FourTwo", Settings.getQuickChats(4)[1]);
			props.setProperty("Chat-FourThree", Settings.getQuickChats(4)[2]);
			props.setProperty("Chat-FourFour", Settings.getQuickChats(4)[3]);
			props.store(writer, "PCRLChat Settings\nChanging these settings to invalid values may result in a reset of PCRLChat.");
			writer.close();
		} catch(Exception ex) {}
	}
	
	/**
	 * <ul>
	 * <p>	<b><i>loadSettings</i></b>
	 * <p>	<code>private void loadSettings()</code>
	 * <p>	Loads the settings from the properties file.
	 * </ul>
	 */
	private void loadSettings() {
		try {
			FileReader reader = new FileReader(propsFileLocation);
			props.load(reader);
			Settings.setEnabled(Boolean.parseBoolean(props.getProperty("EnabledDisabled")));
			Settings.setKeyOne(Integer.parseInt(props.getProperty("KeybindOne")));
			Settings.setKeyTwo(Integer.parseInt(props.getProperty("KeybindTwo")));
			Settings.setKeyThree(Integer.parseInt(props.getProperty("KeybindThree")));
			Settings.setKeyFour(Integer.parseInt(props.getProperty("KeybindFour")));
			Settings.setQuickChat(1, 1, props.getProperty("Chat-OneOne"));
			Settings.setQuickChat(1, 2, props.getProperty("Chat-OneTwo"));
			Settings.setQuickChat(1, 3, props.getProperty("Chat-OneThree"));
			Settings.setQuickChat(1, 4, props.getProperty("Chat-OneFour"));
			Settings.setQuickChat(2, 1, props.getProperty("Chat-TwoOne"));
			Settings.setQuickChat(2, 2, props.getProperty("Chat-TwoTwo"));
			Settings.setQuickChat(2, 3, props.getProperty("Chat-TwoThree"));
			Settings.setQuickChat(2, 4, props.getProperty("Chat-TwoFour"));
			Settings.setQuickChat(3, 1, props.getProperty("Chat-ThreeOne"));
			Settings.setQuickChat(3, 2, props.getProperty("Chat-ThreeTwo"));
			Settings.setQuickChat(3, 3, props.getProperty("Chat-ThreeThree"));
			Settings.setQuickChat(3, 4, props.getProperty("Chat-ThreeFour"));
			Settings.setQuickChat(4, 1, props.getProperty("Chat-FourOne"));
			Settings.setQuickChat(4, 2, props.getProperty("Chat-FourTwo"));
			Settings.setQuickChat(4, 3, props.getProperty("Chat-FourThree"));
			Settings.setQuickChat(4, 4, props.getProperty("Chat-FourFour"));
			reader.close();
		} catch(Exception ex) {
			ex.printStackTrace();
			int confirm = JOptionPane.showConfirmDialog(null, "Error loading settings!\nThe settings file must be corrupt or contain invalid settings.\n\nReset PCRLChat?\n\t- Note: This may reset all settings and preferences!", "Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);;
			if(confirm==0) writeSettings(true);
			else System.exit(0);
		}
	}
}
