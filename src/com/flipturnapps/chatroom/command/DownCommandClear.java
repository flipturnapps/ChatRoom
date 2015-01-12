package com.flipturnapps.chatroom.command;

import com.flipturnapps.chatroom.command.DownCommandTemplate;
import com.flipturnapps.chatroom.net.ChatRoomClient;

public class DownCommandClear extends DownCommandTemplate {

	public static final String NAME = "clear";

	public DownCommandClear() {
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getMaximumParams() {
		return 0;
	}

	@Override
	public int getMinimumParams() {
		return 0;
	}

	@Override
	protected void downExecute(String[] params, ChatRoomClient data) 
	{
		data.getGui().getLabelArea().clear();
		data.getGui().repaint();
	}

}
