package com.flipturnapps.chatroom.net;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Random;

import javax.swing.JDialog;

import com.flipturnapps.chatroom.gui.MainFrame;
import com.flipturnapps.chatroom.gui.StartFrame;
import com.flipturnapps.kevinLibrary.helper.FlushWriter;
import com.flipturnapps.kevinLibrary.helper.ObjectWR;
import com.flipturnapps.kevinLibrary.helper.ThreadHelper;
import com.flipturnapps.kevinLibrary.newgui.KDialog;


public class Start {

	/*
	 * ChatRoom:
	 *  -Colorfully show each members' messages to each other
	 *  -Tell all clients when server is shut down
	 *  -Tell all clients when a client leaves
	 *  -Provide basic commands protected by command permissions
	 *  
	 * Start Class:
	 * 	-Main (driver) class
	 *  -Start up the client/server
	 *  -Provide other classes with a backup reference to the server and client (mostly for MainFrame class)
	 */

	//static variables
	private static ChatRoomServer server = null;
	private static ChatRoomClient client;
	private static MainFrame frame;
	private static StartupData startupData;
	
	public static final String VERSION_ID = "3.0";
	public static final int PORT = 23452;
	private static final String DEV_NAME = "FlipTurnApps";
	private static final String APP_NAME = "ChatRoom";
	
	public static void main(String[] args) 
	{
		ClientInfo.setAutoDisconnectWaitTime(4000);
		getStartupData();
		new StartFrame(startupData,VERSION_ID).setVisible(true);

	}

	

	private static void getStartupData() 
	{
		if(new File("nosave.dev").exists())
			makeNewStartupData();
		else
		{
			try 
			{
				startupData = (StartupData) ObjectWR.read(getSaveLocation() + "startup.dat");
			} 
			catch (Exception ex)
			{
				ex.printStackTrace();
				makeNewStartupData();
			}
		}

	}
	
	private static void makeNewStartupData()
	{
		startupData = new StartupData();
		startupData.ip = "";
		startupData.name = "";
	}

	public static void host(String screenName) 
	{
		startupData.name = screenName;
		try {
			server = new ChatRoomServer(PORT,screenName + "'s Room");
		} catch (IOException e) {
			e.printStackTrace();
			KDialog.showMessage("There was a problem hosting. (IOE)");
			frame.dispose();
			frame.setVisible(false);
			ThreadHelper.sleep(10000);
			System.exit(-1);
		}
		System.out.println("Hosted successfully");
		join("127.0.0.1");
		new Thread(new ClientConstantUpdater(server)).start();
	}

	public static void join(String text) 
	{
		writeStartupData();
		ThreadHelper.sleep(250);
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
		try {
			client = new ChatRoomClient(text, PORT, frame,startupData.name);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			KDialog.showMessage("There was a problem joining. (UHE)");
			ThreadHelper.sleep(3000);
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			KDialog.showMessage("There was a problem joining. (IOE)");
			ThreadHelper.sleep(3000);
			System.exit(-1);
		}
		
		System.out.println("Joined successfully");

	}

	private static void writeStartupData() 
	{
		new File(getSaveLocation()).mkdirs();
		try {
			ObjectWR.write(startupData,getSaveLocation() + "startup.dat");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static String getSaveLocation()
	{
		String os = System.getProperty("os.name","generic").toLowerCase(Locale.ENGLISH);
		String home = System.getProperty("user.home");
		if ((os.indexOf("mac") >= 0) || (os.indexOf("darwin") >= 0)) {
			return home + "/Library/Application Support/"+ DEV_NAME +"/"+APP_NAME+"/" + VERSION_ID + "/";
		} else if (os.indexOf("win") >= 0) {
			return home + "/AppData/Roaming/"+ DEV_NAME +"/"+APP_NAME+"/" + VERSION_ID + "/";
		} else if (os.indexOf("nux") >= 0) {
			return home + "/."+ DEV_NAME +"/"+APP_NAME+"/"  + VERSION_ID + "/";
		} else {
			return "/"+ DEV_NAME +"/"+APP_NAME+"/"  + VERSION_ID + "/";
		}
	}
	public static ChatRoomClient getClient() {
		return client;

	}

	public static ChatRoomServer getServer() {
		return server;
	}

	public static void forceExit(String string) 
	{
		File file = new File("forceclose.log");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			FlushWriter writer = new FlushWriter(new FileWriter(file));
			writer.println(string);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(client != null)
			client.closeWindows();
		KDialog.showMessage("ChatRoom has force closed because of an error. Check forceclose.log.", JDialog.ERROR);
		ThreadHelper.sleep(5000);
		System.exit(-1);
	}



	public static void setStartupData(StartupData startupData2) 
	{
		startupData = startupData2;
	}

}
