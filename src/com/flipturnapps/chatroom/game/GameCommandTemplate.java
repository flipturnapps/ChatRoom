package com.flipturnapps.chatroom.game;

import java.util.ArrayList;

import com.flipturnapps.kevinLibrary.command.Command;
import com.flipturnapps.kevinLibrary.command.SimpleCommand;

public abstract class GameCommandTemplate extends SimpleCommand {

	

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
	public boolean permProtected() {
		// TODO Auto-generated method stub
		return false;
	}

}
