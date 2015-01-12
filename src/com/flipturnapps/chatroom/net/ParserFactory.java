package com.flipturnapps.chatroom.net;

import java.util.ArrayList;

import com.flipturnapps.chatroom.command.DownCommandBeKicked;
import com.flipturnapps.chatroom.command.DownCommandClear;
import com.flipturnapps.chatroom.command.DownCommandDisplay;
import com.flipturnapps.chatroom.command.DownCommandGameModify;
import com.flipturnapps.chatroom.command.DownCommandRefreshClientList;
import com.flipturnapps.chatroom.command.DownCommandSetFieldText;
import com.flipturnapps.chatroom.command.DownCommandShowClientsTypingInfo;
import com.flipturnapps.chatroom.command.DownCommandStop;
import com.flipturnapps.chatroom.command.UpCommandGameModify;
import com.flipturnapps.chatroom.command.UpCommandLeaving;
import com.flipturnapps.chatroom.command.UpCommandParseUserCommand;
import com.flipturnapps.chatroom.command.UpCommandSay;
import com.flipturnapps.chatroom.command.UpCommandSetName;
import com.flipturnapps.chatroom.command.UpCommandSetOtherName;
import com.flipturnapps.chatroom.command.UpCommandSetServerClient;
import com.flipturnapps.chatroom.command.UpCommandTypingProperties;
import com.flipturnapps.chatroom.command.UserCommandAddPerm;
import com.flipturnapps.chatroom.command.UserCommandClearAll;
import com.flipturnapps.chatroom.command.UserCommandClearThis;
import com.flipturnapps.chatroom.command.UserCommandCreateGame;
import com.flipturnapps.chatroom.command.UserCommandDisplayTypingProperties;
import com.flipturnapps.chatroom.command.UserCommandIntercept;
import com.flipturnapps.chatroom.command.UserCommandJoinGame;
import com.flipturnapps.chatroom.command.UserCommandKick;
import com.flipturnapps.chatroom.command.UserCommandMessage;
import com.flipturnapps.chatroom.command.UserCommandPrintName;
import com.flipturnapps.chatroom.command.UserCommandRemPerm;
import com.flipturnapps.chatroom.command.UserCommandRename;
import com.flipturnapps.chatroom.command.UserCommandReply;
import com.flipturnapps.chatroom.command.UserCommandSetAutoUpdateWaitTime;
import com.flipturnapps.chatroom.command.UserCommandSetColor;
import com.flipturnapps.chatroom.command.UserCommandSetFieldText;
import com.flipturnapps.chatroom.game.GameCommandParser;
import com.flipturnapps.chatroom.game.TextGameCloseWindowDownCommand;
import com.flipturnapps.chatroom.game.TextGameDisplayMessageDownCommand;
import com.flipturnapps.chatroom.game.TextGameSendStringUpCommand;
import com.flipturnapps.chatroom.game.UpGameCommandAlternateFrameClosed;
import com.flipturnapps.kevinLibrary.command.Command;
import com.flipturnapps.kevinLibrary.command.CommandOutput;
import com.flipturnapps.kevinLibrary.command.CommandParser;
import com.flipturnapps.kevinLibrary.command.SlashCommandParser;


public class ParserFactory implements CommandOutput
{
	/*
	 * ParserFactory:
	 *  -Create CommandParsers for the Client, Server, and User Command catch
	 *  -Instantiates the command classes to add to the parsers
	 */
	public CommandParser getUpCommandParser()
	{
		ArrayList<Command> commands = new ArrayList<Command>();
		commands.add(new UpCommandSetName());
		commands.add(new UpCommandSay());
		commands.add(new UpCommandLeaving());
		commands.add(new UpCommandSetOtherName());
		commands.add(new UpCommandParseUserCommand());
		commands.add(new UpCommandSetServerClient());
		commands.add(new UpCommandGameModify());
		commands.add(new UpCommandTypingProperties());
		return new ClientMessenger(commands);
	}

	public CommandParser getDownCommandParser() 
	{
		ArrayList<Command> commands = new ArrayList<Command>();
		commands.add(new DownCommandDisplay());
		commands.add(new DownCommandStop());
		commands.add(new DownCommandRefreshClientList());
		commands.add(new DownCommandBeKicked());
		commands.add(new DownCommandGameModify());
		commands.add(new DownCommandClear());
		commands.add(new DownCommandSetFieldText());
		commands.add(new DownCommandShowClientsTypingInfo());
		return new ServerMessenger(commands);
		
	}

	public CommandParser getUserCommandParser() 
	{
		CommandOutput out = Start.getClient().getGui();
		ArrayList<Command> commands = new ArrayList<Command>();
		commands.add(new UserCommandRename(out));
		commands.add(new UserCommandAddPerm(out));
		commands.add(new UserCommandJoinGame(out));
		commands.add(new UserCommandRemPerm(out));
		commands.add(new UserCommandKick(out));
		commands.add(new UserCommandSetColor(out));
		commands.add(new UserCommandMessage(out));
		commands.add(new UserCommandReply(out));
		commands.add(new UserCommandIntercept(out));
		commands.add(new UserCommandCreateGame(out));
		commands.add(new UserCommandClearAll(out));
		commands.add(new UserCommandClearThis(out));
		commands.add(new UserCommandPrintName(out));
		commands.add(new UserCommandSetAutoUpdateWaitTime(out));
		commands.add(new UserCommandDisplayTypingProperties(out));
		commands.add(new UserCommandSetFieldText(out));
		return new SlashCommandParser(commands);
	}
	public static GameCommandParser getDownGameCommandParser() 
	{
		ArrayList<Command> commands = new ArrayList<Command>();
		commands.add(new TextGameDisplayMessageDownCommand());
		commands.add(new TextGameCloseWindowDownCommand());
		return new GameCommandParser(commands);
	}
	public static GameCommandParser getUpGameCommandParser() 
	{
		ArrayList<Command> commands = new ArrayList<Command>();
		commands.add(new TextGameSendStringUpCommand());
		commands.add(new UpGameCommandAlternateFrameClosed());
		return new GameCommandParser(commands);
	}

	@Override
	public void println(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void print(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void println() {
		// TODO Auto-generated method stub
		
	}

	

	
	
}
