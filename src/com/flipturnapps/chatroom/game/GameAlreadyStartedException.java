package com.flipturnapps.chatroom.game;

public class GameAlreadyStartedException extends GameException {

	@Override
	public String getMessage() 
	{
		return "the game has already started.";
	}

}
