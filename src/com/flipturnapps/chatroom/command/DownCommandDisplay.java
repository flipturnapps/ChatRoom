package com.flipturnapps.chatroom.command;
import java.awt.Color;

import com.flipturnapps.chatroom.net.ChatRoomClient;


public class DownCommandDisplay extends DownCommandTemplate {

	/*
	 * DownCommandDisplay:
	 *  -Farthest down the command inheritance chain
	 *  -Provides instructions on how to display text of a certain color (rgb values)
	 */
	
	@Override
	public String getName() {
		return "display";
	}

	@Override
	public int getMaximumParams() 
	{
		return 4;
	}

	@Override
	public int getMinimumParams() 
	{
		
		return 4;
	}

	

	@Override
	protected void downExecute(String[] params, ChatRoomClient data) 
	{
		String line = params[0];
		int red = Integer.parseInt(params[1]);
		int green = Integer.parseInt(params[2]);
		int blue = Integer.parseInt(params[3]);
		data.getGui().addTextLine(line, new Color(red,green,blue));
		data.alert();
	}



}
