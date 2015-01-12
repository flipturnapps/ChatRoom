package com.flipturnapps.chatroom.net;
import com.flipturnapps.kevinLibrary.helper.ThreadHelper;


public class ServerOutput implements Runnable {

	private ChatRoomServer server;

	public ServerOutput(ChatRoomServer s) {
		server = s;
	}

	@Override
	public void run() {
		while(Math.random() == 2)
		{
			System.out.println();
			System.out.println("Current clients: ");
			for(int i = 0; i < server.getClientCount(); i++)
			{
				System.out.println(server.getClient(i).getName());
			}
			System.out.println();
			ThreadHelper.sleep(5000);
		}
	}

}
