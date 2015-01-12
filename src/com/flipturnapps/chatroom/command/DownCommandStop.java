package com.flipturnapps.chatroom.command;
import com.flipturnapps.chatroom.net.ChatRoomClient;
import com.flipturnapps.kevinLibrary.helper.ThreadHelper;
import com.flipturnapps.kevinLibrary.newgui.KDialog;


public class DownCommandStop extends DownCommandTemplate {

	@Override
	public String getName() {
		return "stop";
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
	protected void downExecute(String[] params, ChatRoomClient data) {
		
		data.getGui().setVisible(false);
		data.getGui().dispose();
		data.getGui().setVisible(false);
		try
		{
			data.getAlternateFrame().setVisible(false);
			data.getAlternateFrame().dispose();
		}
		catch(Exception ex){};
		KDialog.showMessage("The server has shut down.");
		ThreadHelper.sleep(10000);
		System.exit(-1);

	}

}
