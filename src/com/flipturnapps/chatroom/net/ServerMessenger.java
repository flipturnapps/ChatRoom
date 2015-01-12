package com.flipturnapps.chatroom.net;
import java.awt.Color;
import java.util.ArrayList;

import com.flipturnapps.chatroom.command.DownCommandClear;
import com.flipturnapps.chatroom.command.DownCommandSetFieldText;
import com.flipturnapps.chatroom.command.DownCommandShowClientsTypingInfo;
import com.flipturnapps.kevinLibrary.command.CharacterBrokenCommandParser;
import com.flipturnapps.kevinLibrary.command.Command;
import com.flipturnapps.kevinLibrary.net.ClientData;


public class ServerMessenger extends CharacterBrokenCommandParser
{
	/*
	 * ServerMessenger:
	 *  -Provides message presets for the server via static methods
	 *  -Is the CommandParser for the client to parse DownCommands
	 */
	//constants
	private static final String SPLITTING_CHARACTER = "~";
	private static final String STARTING_CHARACTER = "";
	public ServerMessenger(ArrayList<Command> commands) {
		super(commands);
		
	}
	//static methods
	public static String getDisplayTextLine(String textLine, int red, int green, int blue)
	{
		return "display" + SPLITTING_CHARACTER + textLine + SPLITTING_CHARACTER + red + SPLITTING_CHARACTER + green + SPLITTING_CHARACTER + blue;
	}
	public static String getDisplayTextLine(String textLine, ClientInfo info)
	{
		return "display" + SPLITTING_CHARACTER + "<" + info.getName() + "> " + textLine + SPLITTING_CHARACTER + info.getColor().getRed() + SPLITTING_CHARACTER + info.getColor().getGreen() + SPLITTING_CHARACTER + info.getColor().getBlue();
	}
	public static String stopping()
	{
		return "stop";
		
	}
	@Override
	public String getSplittingCharacters()
	{
		return SPLITTING_CHARACTER;
	}

	@Override
	public String getStartingCharacters() 
	{
		return STARTING_CHARACTER;
	}

	public static String addPerm(String string)
	{
		return "addperm" + SPLITTING_CHARACTER + string;
	}
	public static String refreshClientList(String totalString) 
	{
		return "refreshcl" + SPLITTING_CHARACTER + totalString;
	}
	
	public static String kick() {
		return "kick";
	}
	public static String getDisplayTextLine(String textLine, Color color) {
		return getDisplayTextLine(textLine,color.getRed(),color.getGreen(),color.getBlue());
	}
	public static String sendGameModify(String string) {
		return "gamemodify" + SPLITTING_CHARACTER + string;
	}
	public static String clear() 
	{
		return DownCommandClear.NAME;
	}
	public static String sendClientTypingInfo(String typingClientsString, String enteredClientsString, String name) {
	 //System.out.println(DownCommandShowClientsTypingInfo.NAME + SPLITTING_CHARACTER + typingClientsString + SPLITTING_CHARACTER + enteredClientsString);
		return DownCommandShowClientsTypingInfo.NAME + SPLITTING_CHARACTER + typingClientsString + SPLITTING_CHARACTER + enteredClientsString + SPLITTING_CHARACTER+ name;
	}
	public static String setClientFieldText(String fieldText)
	{
		return DownCommandSetFieldText.NAME + SPLITTING_CHARACTER + fieldText;
	}

	

}
