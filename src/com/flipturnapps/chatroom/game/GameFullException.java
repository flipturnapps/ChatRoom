package com.flipturnapps.chatroom.game;

public class GameFullException extends GameException 
{
	public String getMessage()
	{
		return "that game cannot take any more players.";
	}

}
