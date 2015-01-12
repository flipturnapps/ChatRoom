package com.flipturnapps.chatroom.game;

import com.flipturnapps.chatroom.gui.TextGameFrame;
import com.flipturnapps.chatroom.net.ChatRoomClient;
import com.flipturnapps.chatroom.net.Start;

public class TextGameDisplayMessageDownCommand extends DownGameCommandTemplate
{
	public static final String name = "down_text_game_display";
	@Override
	public String getName() 
	{
		return name;
	}

	@Override
	public int getMaximumParams() 
	{
		return 1;
	}

	@Override
	public int getMinimumParams() 
	{
		return 1;
	}

	@Override
	protected void run(String[] params, ChatRoomClient client) 
	{
		if(client.getAlternateFrame() == null || !(client.getAlternateFrame() instanceof TextGameFrame))
		{
			TextGameFrame frame = new TextGameFrame(client);
			frame.setVisible(true);
			client.setAlternateFrame(frame);
		}
		if(client.getAlternateFrame() == null || !(client.getAlternateFrame() instanceof TextGameFrame))
		{
			Start.forceExit("The client's alternateframe is not an instanceof TextGameFrame");
		}
		TextGameFrame frame = (TextGameFrame) client.getAlternateFrame();
		frame.showMessage(params[0]);
	}
	
}
