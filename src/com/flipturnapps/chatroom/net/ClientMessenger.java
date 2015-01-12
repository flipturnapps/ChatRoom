package com.flipturnapps.chatroom.net;
import java.util.ArrayList;

import com.flipturnapps.chatroom.command.UpCommandTypingProperties;
import com.flipturnapps.kevinLibrary.command.CharacterBrokenCommandParser;
import com.flipturnapps.kevinLibrary.command.Command;


public class ClientMessenger extends CharacterBrokenCommandParser{

	/*
	 * ClientMessenger:
	 *  -Makes messages in string form for the client to send to the server
	 *  -Holds constants for the server to use as a CommandParser
	 */
	
	private static final String SPLITTING_CHARACTER = "~";
	private static final String STARTING_CHARACTER = "";
	public ClientMessenger(ArrayList<Command> commands) {
		super(commands);
	}

	public static String getSetOtherName(String  oldName, String newName)
	{
		return "setothername" + SPLITTING_CHARACTER + oldName + SPLITTING_CHARACTER + newName;
	}
	public static String getSetName(String name)
	{
		return "setname" + SPLITTING_CHARACTER + name;
	}
	public static String getSay(String text) 
	{
		return "say" + SPLITTING_CHARACTER  + text;
	}
	public static String getLeaving()
	{
		return "leaving";
	}
	@Override
	public String getSplittingCharacters() {
		return SPLITTING_CHARACTER;
	}
	@Override
	public String getStartingCharacters() {
		return STARTING_CHARACTER;
	}

	public static String addPerm(String client, String perm) 
	{
		return "addperm" + SPLITTING_CHARACTER + client + SPLITTING_CHARACTER + perm;
	}

	public static String parseCommand(String string)
	{
		return "parse" + SPLITTING_CHARACTER + string;
	}
	public static String setServerClient(boolean sc)
	{
		return "setsc" + SPLITTING_CHARACTER + sc;
	}

	public static String sendGameModify(String gameString) 
	{
		return "gamemodify" + SPLITTING_CHARACTER + gameString;
	}
	public static String typingProperties(long typeGap, boolean boxEmpty)
	{
		return UpCommandTypingProperties.NAME +  SPLITTING_CHARACTER + typeGap +  SPLITTING_CHARACTER + boxEmpty;
	}

}
