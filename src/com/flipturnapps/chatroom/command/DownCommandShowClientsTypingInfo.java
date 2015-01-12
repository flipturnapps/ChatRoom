package com.flipturnapps.chatroom.command;

import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomClient;

public class DownCommandShowClientsTypingInfo extends DownCommandTemplate {

	public static final String NAME = "showclientstypinginfo";
	@Override
	public String getName() 
	{
		return NAME;
	}

	@Override
	public int getMaximumParams() 
	{
		return 3;
	}

	@Override
	public int getMinimumParams() 
	{
		return 3;
	}

	@Override
	protected void downExecute(String[] params, ChatRoomClient data) 
	{
		String[] typingClients = params[0].split(" ");
		String[] enteredClients = params[1].split(" ");
		typingClients = replaceInArrays(typingClients,params[2]);
		enteredClients = replaceInArrays(enteredClients,params[2]);
		if(typingClients.length == 1 && typingClients[0].equals("Nobody"))
			typingClients = new String[0];
		if(enteredClients.length == 1 && enteredClients[0].equals("Nobody"))
			enteredClients = new String[0];
		data.getGui().setTypingStatus(typingClients,enteredClients);
		
	}

	private String[] replaceInArrays(String[] names, String name) 
	{
		ArrayList<String> namesList = new ArrayList<String>();
		for(int i = 0; i < names.length; i++)
		{
			if(names[i].equals(name))
			{
				namesList.add("You");
			}
			else if(names[i] != null && !names[i].equals("null"))
			{
				namesList.add(names[i]);
			}
		}
		String[] ret = new String[namesList.size()];
		for (int i = 0; i < ret.length; i++)
		{
			ret[i] = namesList.get(i);
		}
		return ret;
	}

}
