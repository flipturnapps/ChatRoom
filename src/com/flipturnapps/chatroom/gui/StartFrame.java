package com.flipturnapps.chatroom.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.flipturnapps.chatroom.net.PortTester;
import com.flipturnapps.chatroom.net.Start;
import com.flipturnapps.chatroom.net.StartupData;
//import com.apple.eawt.Application; //for mac
import com.flipturnapps.kevinLibrary.helper.ThreadHelper;


public class StartFrame extends JFrame {


	private JPanel contentPane;
	private JButton btnHost;
	private JButton btnJoin;
	private JPanel panelSouth;
	private JLabel lblName;
	private JTextField fieldScreenName;
	private JPanel panelCenter;
	private JList<SearchRoom> list;
	private JPanel panelBtn;
	private JButton btnRescan;
	private JButton btnAddIp;
	private JButton btnHistory;


	private ArrayList<ArrayList<String>> roomsFound;
	private JScrollPane scrollPane;

	private StartupData startupData;
	private static final int ROOM_NAME_LENGTH = 25;
	public StartFrame(StartupData startupData, String versionId) 
	{
		this.startupData = startupData;
		roomsFound = new ArrayList<ArrayList<String>>();
		roomsFound.add(null);
		this.setIconImage(ResourceGetter.getBlueIcon());
		//Application.getApplication().setDockIconImage(ResourceGetter.getBlueIcon()); //for mac
		setTitle("ChatRoom Starter (v " + versionId + ")");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panelSouth = new JPanel();
		panelSouth.setBorder(null);
		contentPane.add(panelSouth, BorderLayout.SOUTH);

		lblName = new JLabel("Screen Name: ");
		panelSouth.add(lblName);

		fieldScreenName = new JTextField(10);
		panelSouth.add(fieldScreenName);		
		fieldScreenName.addKeyListener(new Listener());
		fieldScreenName.setText(startupData.name);

		btnJoin = new JButton("Join Selected");
		btnJoin.addActionListener(new Listener());
		btnJoin.setEnabled(false);
		panelSouth.add(btnJoin);

		btnHost = new JButton("Host Room");
		btnHost.addActionListener(new Listener());
		btnHost.setEnabled(false);
		panelSouth.add(btnHost);

		panelCenter = new JPanel();
		contentPane.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout());
		panelCenter.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 2), "Find A Room", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		panelBtn = new JPanel();
		panelBtn.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 1, true), "Finding Room Tools", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCenter.add(panelBtn, BorderLayout.SOUTH);

		btnRescan = new JButton("Re-scan");
		panelBtn.add(btnRescan);
		btnRescan.addActionListener(new Listener());

		btnAddIp = new JButton("Try Specific Ip");
		panelBtn.add(btnAddIp);
		btnAddIp.addActionListener(new Listener());

		btnHistory = new JButton("Try From History");
		panelBtn.add(btnHistory);
		btnHistory.addActionListener(new Listener());

		scrollPane = new JScrollPane();
		panelCenter.add(scrollPane, BorderLayout.CENTER);

		list = new JList<SearchRoom>();
		list.setBackground(SystemColor.control);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 1, true), "Rooms Found", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBackground(SystemColor.control);
		scrollPane.setViewportView(list);

		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList)evt.getSource();
				if (evt.getClickCount() >= 2) 
				{
					int index = list.locationToIndex(evt.getPoint());
					if(list.isSelectedIndex(index) && btnJoin.isEnabled())
					{
						join();
					}
				} 
			}
		});

		refreshLanSearch();		
		fixButtons();
		new Thread( new Listener()).start();
	}
	private void refreshLanSearch()
	{
		roomsFound.set(0,PortTester.autoSearchInLAN(Start.PORT));
	}
	private class SearchRoom
	{

		private String ip;
		private String name;
		private int port;
		public SearchRoom(String input)
		{
			String[] s1 = input.split("~");
			name = s1[1];
			String[] s2 = s1[0].replace(':', 'a').split("a");
			ip = s2[0];
			port = Integer.parseInt(s2[1]);
		}
		public String getIp()
		{
			return ip;
		}
		public String toString()
		{
			String shortName;
			try
			{
				shortName = name.substring(0,ROOM_NAME_LENGTH);
			}
			catch(Exception ex)
			{
				shortName = name;
			}
			while(shortName.length() < ROOM_NAME_LENGTH + 2)
			{
				shortName = shortName + " ";
			}

			String ret = shortName + "   " + "IP: " + ip;
			return ret;
		}
	}

	private void fixButtons()
	{
		boolean haveName = fieldScreenName.getText() != null && fieldScreenName.getText().length() > 0;
		boolean selectedIp = list.getSelectedValue() != null;
		if(haveName && !fieldScreenName.getText().contains(" "))
		{
			btnHost.setEnabled(true);
			if(selectedIp)
				btnJoin.setEnabled(true);
			else
				btnJoin.setEnabled(false);
		}
		else
			btnHost.setEnabled(false);
	}
	private void join()
	{
		startupData.name = fieldScreenName.getText();
		startupData.ip = list.getSelectedValue().getIp();
		Start.setStartupData(startupData);
		Start.join(startupData.ip);
		dispose();
		setVisible(false);
	}
	private class Listener implements KeyListener, ActionListener, Runnable
	{

		@Override
		public void keyPressed(KeyEvent e) {
			if(KeyEvent.VK_ENTER == e.getKeyCode())
			{
				//go();
			}

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void actionPerformed(ActionEvent e)
		{		
			if(e.getSource() == btnHost)
			{
				Start.host(fieldScreenName.getText());
				dispose();
				setVisible(false);
			}
			else if (e.getSource() == btnJoin)
			{
				join();
			}
			else if (e.getSource() == btnRescan)
			{
				refreshLanSearch();
			}
			else if (e.getSource() == btnHistory && !startupData.ip.equals(""))
			{

				PortTester scanner = new PortTester(startupData.ip + Start.PORT);
				roomsFound.add(scanner.startTest());			

			}
			else if (e.getSource() == btnAddIp)
			{
				new AddIpDialog(getStartFrame()).setVisible(true);
			}
		}

		@Override
		public void run() 
		{
			ArrayList<String> lastRoomList = null;
			while(true)
			{
				ThreadHelper.sleep(50);
				fixButtons();
				ArrayList<String> roomList = new ArrayList<String>();
				for(int x = 0; x < roomsFound.size(); x++)
				{
					try
					{
						for(int y = 0; y < roomsFound.get(x).size(); y++)
						{
							roomList.add(roomsFound.get(x).get(y));
						}
					}
					catch (Exception ex)
					{

					}
				}
				if(!roomList.equals(lastRoomList))
				{
					lastRoomList = roomList;
					SearchRoom room = list.getSelectedValue();
					DefaultListModel<SearchRoom> model = new DefaultListModel<SearchRoom>();
					for(int i = 0; i < roomList.size(); i++)
					{
						model.addElement(new SearchRoom(roomList.get(i)));
					}
					list.setModel(model);
					list.setSelectedValue(room, false);
				}

			}

		}

	}

	public void addIp(String text) 
	{
		PortTester tester = new PortTester(text);
		roomsFound.add(tester.startTest());

	}
	private StartFrame getStartFrame()
	{
		return this;
	}

}
