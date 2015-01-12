package com.flipturnapps.chatroom.command;

import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.kevinLibrary.command.Command;
import com.flipturnapps.kevinLibrary.command.SimpleCommand;
import com.flipturnapps.kevinLibrary.net.ClientData;


public abstract class UpCommandTemplate extends SimpleCommand {
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
	public ArrayList<String> getPermissionsCanHave() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean objectDataOK(Object data) {
		if(data != null && data instanceof UpCommandWrapper)
			return true;
		return false;
	}

	@Override
	public boolean permProtected() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Object execute(String commandName, String[] params, Object data) 
	{
		UpCommandWrapper wrapper = (UpCommandWrapper) data;
		upExecute(params, wrapper.server, wrapper.client);
		return null;
		
	}


	protected abstract void upExecute(String[] params, ChatRoomServer server, ClientInfo client);

}
