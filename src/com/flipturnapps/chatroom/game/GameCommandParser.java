package com.flipturnapps.chatroom.game;

import java.util.ArrayList;

import com.flipturnapps.kevinLibrary.command.CharacterBrokenCommandParser;
import com.flipturnapps.kevinLibrary.command.Command;

public class GameCommandParser extends CharacterBrokenCommandParser
{

	
	public GameCommandParser(ArrayList<Command> commands) 
	{
		super(commands);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getSplittingCharacters() 
	{
		return "`";
	}

	@Override
	public String getStartingCharacters() {
		return "";
	}

	

}
