package com.flipturnapps.chatroom.command;
import com.flipturnapps.chatroom.net.ChatRoomClient;
import com.flipturnapps.kevinLibrary.helper.ThreadHelper;
import com.flipturnapps.kevinLibrary.newgui.KDialog;


public class DownCommandBeKicked extends DownCommandTemplate {

	@Override
	public String getName() {
		return "kick";
	}

	@Override
	public int getMaximumParams() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinimumParams() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void downExecute(String[] params, ChatRoomClient data) {
		
		data.getGui().setVisible(false);
		data.getGui().dispose();
		data.getGui().setVisible(false);
		KDialog.showMessage("You have been kicked.");
		ThreadHelper.sleep(10000);
		System.exit(-1);
		

	}

}
