package com.flipturnapps.chatroom.command;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ParserFactory;
import com.flipturnapps.kevinLibrary.command.CommandParseException;
import com.flipturnapps.kevinLibrary.command.CommandParser;
import com.flipturnapps.kevinLibrary.command.IncorrectDataException;
import com.flipturnapps.kevinLibrary.command.NonExistentCommandException;
import com.flipturnapps.kevinLibrary.net.ClientData;


public class UpCommandParseUserCommand extends UpCommandTemplate {

	private CommandParser uParser;
	public UpCommandParseUserCommand()
	{

	}
	@Override
	public String getName() {
		return "parse";
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
	protected void upExecute(String[] params, ChatRoomServer server,
			ClientInfo client) {
		if(uParser == null)
			uParser = new ParserFactory().getUserCommandParser();
		String string = params[0];
		UpCommandWrapper wrapper = new UpCommandWrapper(server,client);
		try {
			uParser.runCommand(params[0], client, wrapper);
		} catch (IncorrectDataException e) {
			server.clientPrintInBlack("Datatype " + e.getErrorTypeText() + " rejected when trying to run "+ e.getCommandName(), client);
			System.out.println(client.getName() + " failed to run a usercommand beacuse the perms were bad \"" + params[0] + "\"");
		} catch (CommandParseException e) {
			server.clientPrintInBlack("The command could not be parsed. " + string,client);
		} catch (NonExistentCommandException e) {
			server.clientPrintInBlack("You tried to call a nonexistent command " + e.getCommandName(),client);
		}

	}

}
