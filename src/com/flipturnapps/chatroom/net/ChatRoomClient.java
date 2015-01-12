package com.flipturnapps.chatroom.net;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.flipturnapps.chatroom.gui.MainFrame;
import com.flipturnapps.chatroom.gui.ResourceGetter;
import com.flipturnapps.kevinLibrary.command.CommandParseException;
import com.flipturnapps.kevinLibrary.command.CommandParser;
import com.flipturnapps.kevinLibrary.command.CommandSpeaker;
import com.flipturnapps.kevinLibrary.command.IncorrectDataException;
import com.flipturnapps.kevinLibrary.command.NonExistentCommandException;
import com.flipturnapps.kevinLibrary.helper.Numbers;
import com.flipturnapps.kevinLibrary.helper.ObjectWR;
import com.flipturnapps.kevinLibrary.helper.ThreadHelper;
import com.flipturnapps.kevinLibrary.net.KClient;
import com.flipturnapps.kevinLibrary.newgui.KDialog;

public class ChatRoomClient extends KClient implements CommandSpeaker{
	
	/*
	 * ChatRoomClient:
	 *  -Receive messages from the server
	 *  -Parse those messages as DownCommands
	 *  -Send messages to the server via  ClientMessenger
	 *  -Deal with user input
	 */
	
	public static final long TYPE_DATA_WAIT_TIME = 100;
	private CommandParser dParser;
	private ArrayList<String> opPerms;
	private MainFrame frame;
	private long lastTimeBeeped;
	private String[] nameList;
	private String ip;
	private String saveName;
	private JFrame alternateFrame;
	private ClientTypingManager typingManager;
	private String name;
	
	public ChatRoomClient(String ip, int port, MainFrame frame, String name) throws UnknownHostException,
			IOException {
		super(ip, port);
		this.ip = ip;
		dParser = new ParserFactory().getDownCommandParser();
		opPerms = new ArrayList<String>();
		opPerms.add("op");
		this.name = name;
		this.frame = frame;
		this.sendMessage(ClientMessenger.setServerClient(Start.getServer() != null));
		new Thread(new SetNameThread()).start();
		
	}

	private class SetNameThread implements Runnable
	{

		@Override
		public void run() {
			
			sendMessage(ClientMessenger.getSetName(name));
			while(isConnected())
			{
				ThreadHelper.sleep(TYPE_DATA_WAIT_TIME);
				sendServerTypeData();
			}
			
		}

		
		
	}
	@Override
	protected void disconnectedFromServer() 
	{
		KDialog.showMessage("You have been disconnected from the server.");
		ThreadHelper.sleep(10000);
		System.exit(-1);

	}
	private void sendServerTypeData() 
	{
		if(typingManager == null)
		{
			typingManager = new ClientTypingManager(this);
			this.getGui().setTypingManager(typingManager);
		}
		long typeLength = this.typingManager.getTypeGap();
		boolean boxNotEmpty = !this.getGui().getInputTextField().getText().equals("");
		if(this.getGui().getInputTextField().getText().startsWith("/"))
			boxNotEmpty = false;
		this.sendMessage(ClientMessenger.typingProperties(typeLength, boxNotEmpty));
		
	}
	@Override
	protected void readMessage(String read) 
	{
		try {
			if(dParser != null)
			dParser.runCommand(read, this, this);
		} catch (IncorrectDataException e) {
			System.out.println("Datatype " + e.getErrorTypeText() + " rejected when trying to run dc "+ e.getCommandName());
		} catch (CommandParseException e) {
			System.out.println("The dc could not be parsed. " + read);
		} catch (NonExistentCommandException e) {
			System.out.println("The client tried to call a nonexistent dc " + e.getCommandName());
		}

	}

	@Override
	public ArrayList<String> getPermsOwned() 
	{
		return opPerms;
	}
	public MainFrame getGui()
	{
		return frame;
	}
	
	public void useUserInput(String string) 
	{
		if(!string.startsWith("/"))
			this.sendMessage(ClientMessenger.getSay(string));
		else
		{
			this.sendMessage(ClientMessenger.parseCommand(string));
		}
	}

	

	public void alert() 
	{
		if(!frame.hasUserFocus())
		{
			frame.changeIconAlert();			
			if((System.currentTimeMillis() - this.lastTimeBeeped) > 0)
			{
				this.lastTimeBeeped = System.currentTimeMillis();
					ResourceGetter.notifyClient();
				
			}
		}
	}

	public void turnAlertCountOff() 
	{
		this.lastTimeBeeped = 0;
		
	}

	public void setClientNameList(String[] list) 
	{
		this.nameList = list;
		
	}

	public String[] getNameList() {
		return nameList;
	}
	

	public void setSaveName(String string) 
	{
		this.saveName = string;
	}

	public JFrame getAlternateFrame() {
		return alternateFrame;
	}

	public void setAlternateFrame(JFrame alternateFrame) {
		this.alternateFrame = alternateFrame;
	}

	public void closeWindows() 
	{
		if (frame != null)
		{
			frame.setVisible(false);
			frame.dispose();
		}
		if (alternateFrame != null)
		{
			alternateFrame.setVisible(false);
			alternateFrame.dispose();
		}
		
	}

	public void nullAlternateFrame() 
	{
		alternateFrame = null;
		
	}

	public String getName() 
	{
		return saveName;
	}
}
