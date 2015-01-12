package com.flipturnapps.chatroom.gui;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class ResourceGetter
{
	public static Image getBlueIcon()
	{
		try {
			return ImageIO.read(new ResourceGetter().getClass().getResource("/res/blueIcon"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Image getRedIcon()
	{
		try {
			return ImageIO.read(new ResourceGetter().getClass().getResource("/res/redIcon"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void notifyClient()
	{
		try
		{
			URL url = new ResourceGetter().getClass().getResource("/res/alert");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		}
		catch(Exception ex){};

	}

}
