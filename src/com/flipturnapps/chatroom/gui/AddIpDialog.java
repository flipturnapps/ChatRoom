package com.flipturnapps.chatroom.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.flipturnapps.chatroom.net.Start;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddIpDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JButton okButton;
	private JButton cancelButton;
	private StartFrame startFrame;

	
	public AddIpDialog(StartFrame frame) 
	{
		super(frame);
		this.startFrame = frame;
		setTitle("IP Search");
		setBounds(100, 100, 380,150);
		this.setIconImage(ResourceGetter.getBlueIcon());
		//Application.getApplication().setDockIconImage(ResourceGetter.getBlueIcon()); //for mac
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblSearchForRoom = new JLabel("Search for room at this IP: ");
			lblSearchForRoom.setFont(new Font("Arial", Font.PLAIN, 14));
			contentPanel.add(lblSearchForRoom);
		}
		{
			textField = new JTextField();
			contentPanel.add(textField);
			textField.setColumns(15);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new Listener());
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new Listener());
			}
		}
	}
	private class Listener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource() == okButton)
			{
				String[] arr = null;
				try
				{
				arr = textField.getText().replace('.','a').split("a");
				}
				catch(Exception ex)
				{
					
				}
				boolean good = true;
				if(arr != null)
				{
					for (int i = 0; i < arr.length; i++) 
					{
						int length = arr[i].length();
						if(length < 1 || length > 3)
							good = false;
					}
				}
				if(good)
				startFrame.addIp(textField.getText() +":"+Start.PORT);
				setVisible(false);
				dispose();
			}
			else
			{
				setVisible(false);
				dispose();
			}
		}
		
	}

}
