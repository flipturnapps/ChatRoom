package com.flipturnapps.chatroom.game;

public class AlreadyInGameException extends GameException {

	@Override
	public String getMessage() {
		return "you are already in a game.";
	}

}
