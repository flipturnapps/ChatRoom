package com.flipturnapps.chatroom.command;

import com.flipturnapps.chatroom.net.ChatRoomClient;

public class DownCommandSetFieldText extends DownCommandTemplate {

	
	public static final String NAME = "setfieldtext";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getMaximumParams() 
	{
		return 1;
	}

	@Override
	public int getMinimumParams() {
		return 1;
	}

	@Override
	protected void downExecute(String[] params, ChatRoomClient data) 
	{
		data.getGui().getInputTextField().setText(params[0]);

	}

}
