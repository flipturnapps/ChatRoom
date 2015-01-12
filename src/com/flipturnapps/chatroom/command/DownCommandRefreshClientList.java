package com.flipturnapps.chatroom.command;
import com.flipturnapps.chatroom.net.ChatRoomClient;


public class DownCommandRefreshClientList extends DownCommandTemplate {

	@Override
	public String getName() {
		return "refreshcl";
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
	protected void downExecute(String[] params, ChatRoomClient data) {
		data.setClientNameList(params[0].split(","));

	}

}
