package com.flipturnapps.chatroom.game;

import java.util.ArrayList;

public class TextGameInfo extends GameInfo
{
	private ArrayList<String> messagesFrom;
	public TextGameInfo()
	{
		messagesFrom = new ArrayList<String>();
	}
	public ArrayList<String> getMessagesFrom() 
	{
		return messagesFrom;
	}

}
