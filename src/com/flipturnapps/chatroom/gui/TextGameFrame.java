package com.flipturnapps.chatroom.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.flipturnapps.chatroom.game.TextGameSendStringUpCommand;
import com.flipturnapps.chatroom.game.UpGameCommandAlternateFrameClosed;
import com.flipturnapps.chatroom.net.ChatRoomClient;
import com.flipturnapps.chatroom.net.ClientMessenger;
import com.flipturnapps.chatroom.net.Start;
import com.flipturnapps.kevinLibrary.newgui.KJTextArea;

public class TextGameFrame extends JFrame implements WindowListener{

	private JPanel contentPane;
	private JTextField textField;
	private KJTextArea textArea;
	private ChatRoomClient client;

	public TextGameFrame(ChatRoomClient c) 
	{
		this.client = c;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		this.addWindowListener(this);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new KJTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(textArea);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(KeyEvent.VK_ENTER == e.getKeyCode())
				{
					String gameString = TextGameSendStringUpCommand.name + "`" + textField.getText();
					Start.getClient().sendMessage(ClientMessenger.sendGameModify(gameString));
					textField.setText("");
				}
			}
		});
		contentPane.add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		this.repaint();
	}

	public void repaint()
	{
		super.repaint();
		this.setTitle(client.getName());
	}
	public void showMessage(String string) 
	{
		textArea.println(string);
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) 
	{
		client.sendMessage(ClientMessenger.sendGameModify(UpGameCommandAlternateFrameClosed.NAME));
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
