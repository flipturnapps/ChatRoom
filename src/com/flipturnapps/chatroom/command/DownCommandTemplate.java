package com.flipturnapps.chatroom.command;
import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomClient;
import com.flipturnapps.kevinLibrary.command.Command;
import com.flipturnapps.kevinLibrary.command.SimpleCommand;


public abstract class DownCommandTemplate extends SimpleCommand {

	/*
	 * DownCommandTemplate:
	 *  -Second lowest on the command inheritance chain
	 *  -Overrides SimpleCommand methods that will be the same for all DownCommands
	 */
	
	@Override
	public String getHelpText() {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public boolean willInterceptCommand(Command command, String[] params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void interceptCommand(Command command, String[] params) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getInterceptPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] getAliases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object execute(String commandName, String[] params, Object data) {
		downExecute(params,(ChatRoomClient) data);
		return null;
	}
	@Override
	public boolean permProtected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> getPermissionsCanHave() {
		// TODO Auto-generated method stub
		return null;
	}

	
	//protected visibility modifier
	protected abstract void downExecute(String[] params, ChatRoomClient data);



	@Override
	public boolean objectDataOK(Object data) {
		return data instanceof ChatRoomClient;
	}

	

}
