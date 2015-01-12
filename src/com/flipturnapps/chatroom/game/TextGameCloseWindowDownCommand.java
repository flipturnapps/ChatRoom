package com.flipturnapps.chatroom.game;

import com.flipturnapps.chatroom.net.ChatRoomClient;

public class TextGameCloseWindowDownCommand extends DownGameCommandTemplate
{
	public static final String NAME = "closewindow";
	@Override
	public String getName() 
	{
		return NAME;
	}

	@Override
	public int getMaximumParams() 
	{
		return 0;
	}

	@Override
	public int getMinimumParams() 
	{
		return 0;
	}

	@Override
	protected void run(String[] params, ChatRoomClient client)
	{
		client.getAlternateFrame().dispose();
		client.getAlternateFrame().setVisible(false);
		client.nullAlternateFrame();
		
	}

}
