package com.flipturnapps.chatroom.command;

import java.util.ArrayList;

import com.flipturnapps.chatroom.game.GameCommandParser;
import com.flipturnapps.chatroom.net.ChatRoomClient;
import com.flipturnapps.chatroom.net.ParserFactory;
import com.flipturnapps.kevinLibrary.command.CommandParseException;
import com.flipturnapps.kevinLibrary.command.CommandSpeaker;
import com.flipturnapps.kevinLibrary.command.IncorrectDataException;
import com.flipturnapps.kevinLibrary.command.NonExistentCommandException;

public class DownCommandGameModify extends DownCommandTemplate implements CommandSpeaker{

	private GameCommandParser parser;
	@Override
	public String getName() {
		return "gamemodify";
	}

	@Override
	public int getMaximumParams() {
		return 1;
	}

	@Override
	public int getMinimumParams() {
		return 1;
	}

	@Override
	protected void downExecute(String[] params, ChatRoomClient data) 
	{
		if(parser == null)
			parser = ParserFactory.getDownGameCommandParser();
		try {
			parser.runCommand(params[0], this, data);
		} catch (IncorrectDataException e) {
			System.out.println("Down Game Command Parse: Datatype " + e.getErrorTypeText() + " rejected when trying to run uc "+ e.getCommandName());
		} catch (CommandParseException e) {
			System.out.println("Down Game Command Parse: The uc could not be parsed. " + params[0]);
		} catch (NonExistentCommandException e) {
			System.out.println("Down Game Command Parse: The client tried to call a nonexistent uc " + e.getCommandName());
		}
	}

	@Override
	public ArrayList<String> getPermsOwned()
	{
		return null;
	}

}
