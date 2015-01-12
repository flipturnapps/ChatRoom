package com.flipturnapps.chatroom.command;

import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.kevinLibrary.command.Command;
import com.flipturnapps.kevinLibrary.command.CommandOutput;
import com.flipturnapps.kevinLibrary.command.OutputSimpleCommand;


public abstract class UserCommandTemplate extends OutputSimpleCommand {

	

	public UserCommandTemplate(CommandOutput output) 
	{
		super(output);
	}

	
	public String getHelpText()
	{
		return null;
	}
	@Override
	public boolean willInterceptCommand(Command command, String[] params) {
		return false;
	}

	@Override
	public void interceptCommand(Command command, String[] params) {

	}

	@Override
	public int getInterceptPriority() {
		return 0;
	}

	

	@Override
	public Object execute(String commandName, String[] params, Object data) 
	{
		UpCommandWrapper wrapper = (UpCommandWrapper) data;
		userExecute(params, wrapper.server, wrapper.client);
		return null;
	}

	

	protected abstract void userExecute(String[] params, ChatRoomServer server, ClientInfo client);

	public ArrayList<String> getPermissionsCanHave()
	{
		ArrayList<String> perms = new ArrayList<String>();
		perms.add(this.getName());
		addPerms(perms);
		return perms;
		
	}

	protected abstract void addPerms(ArrayList<String> perms);



	@Override
	public boolean objectDataOK(Object data) {
		return data instanceof UpCommandWrapper;
	}

	@Override
	public boolean permProtected() {
		return true;
	}

}
