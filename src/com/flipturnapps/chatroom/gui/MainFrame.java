package com.flipturnapps.chatroom.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.BreakIterator;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.flipturnapps.chatroom.net.ClientMessenger;
import com.flipturnapps.chatroom.net.ClientTypingManager;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.chatroom.net.Start;
import com.flipturnapps.kevinLibrary.command.CommandOutput;
import com.flipturnapps.kevinLibrary.helper.KevinColor;
import com.flipturnapps.kevinLibrary.newgui.LabelArea;
import com.flipturnapps.kevinLibrary.newgui.LabelAreaWrapper;

//import com.apple.eawt.Application; //for mac


public class MainFrame extends JFrame implements WindowListener, CommandOutput{

	/*
	 * MainFrame:
	 *  -Main Gui for the program
	 *  -Has a LabelArea to display colored text and text field to take user input
	 */
	private JPanel contentPane;
	private LabelArea area;
	private JTextField textField;
	private boolean userFocus;
	private ArrayList<String> history;
	private ClientTypingManager typingManager;
	private int tabBookmark = -1;
	private String originalText;
	private int upBookmark = -1;
	private JPanel typingInfoPanel;
	private JPanel typingPanel;
	private JPanel enteredTextPanel;
	private JScrollPane typingScrollPane;
	private JScrollPane enteredScrollPane;
	private JPanel typingLabelPanel;
	private JPanel enteredLabelPanel;
	private int charCount;
	public MainFrame() 
	{
		setSize(new Dimension(485, 300));
		this.setIconImage(ResourceGetter.getBlueIcon());
		//Application.getApplication().setDockIconImage(ResourceGetter.getBlueIcon()); //for mac
		this.setTitle("ChatRoom - FlipTurnApps");
		this.setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		area = new LabelArea();
		LabelAreaWrapper wrapper = new LabelAreaWrapper(area);
		wrapper.setBackground(Color.LIGHT_GRAY);
		contentPane.add(wrapper);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {


			

			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == e.VK_ENTER)
				{
					if(history == null)
						history = new ArrayList<String>();
					if(textField.getText().startsWith("/"))
						history.add(textField.getText());
					Start.getClient().useUserInput((textField.getText()));
					textField.setText("");
				}
				else if(textField.getText().startsWith("/") || e.getKeyCode() == e.VK_SLASH)
				{}
				else if(charCount != textField.getText().length())
				{
					typingManager.processType(e);
				}
				processKey(e);
				charCount = textField.getText().length();
			}


		});
		textField.setForeground(Color.WHITE);
		textField.setBackground(Color.DARK_GRAY);
		wrapper.add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		
		typingInfoPanel = new JPanel();
		contentPane.add(typingInfoPanel, BorderLayout.EAST);
		typingInfoPanel.setLayout(new BoxLayout(typingInfoPanel, BoxLayout.Y_AXIS));
		
		typingPanel = new JPanel();
		typingPanel.setPreferredSize(new Dimension(125, 20));
		typingPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255)), "Currently Typing:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		typingInfoPanel.add(typingPanel);
		typingPanel.setLayout(new BorderLayout(0, 0));
		
		typingScrollPane = new JScrollPane();
		typingPanel.add(typingScrollPane, BorderLayout.CENTER);
		
		typingLabelPanel = new JPanel();
		typingScrollPane.setViewportView(typingLabelPanel);
		typingLabelPanel.setLayout(new BoxLayout(typingLabelPanel, BoxLayout.Y_AXIS));
		
		enteredTextPanel = new JPanel();
		enteredTextPanel.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0)), "Entered Text:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		enteredTextPanel.setPreferredSize(new Dimension(125, 20));
		typingInfoPanel.add(enteredTextPanel);
		enteredTextPanel.setLayout(new BorderLayout(0, 0));
		
		enteredScrollPane = new JScrollPane();
		enteredTextPanel.add(enteredScrollPane, BorderLayout.CENTER);
		
		enteredLabelPanel = new JPanel();
		enteredScrollPane.setViewportView(enteredLabelPanel);
		enteredLabelPanel.setLayout(new BoxLayout(enteredLabelPanel, BoxLayout.Y_AXIS));
		area.autoScrollDown();
		this.addWindowListener(this);
		
	}

	
	private void processKey(KeyEvent e) 
	{
		if(history != null && e.getKeyCode() == e.VK_UP && history.size() > 0)
		{
			if(upBookmark < 0)
				upBookmark = history.size() -1;
			textField.setText(history.get(upBookmark%history.size()));
			upBookmark++;
		}
		else
		{
			upBookmark = -1;
			if(e.getKeyCode() == e.VK_ESCAPE || e.getKeyCode() == e.VK_TAB)
			{
				if(tabBookmark == -1)
				{
					originalText = textField.getText();
					tabBookmark = 0;
					System.out.println("1!");
				}
				else
				{
					tabBookmark++;
				}
				String add = "";
				try
				{
					add = Start.getClient().getNameList()[tabBookmark % (Start.getClient().getNameList().length + 1)];
				}
				catch(Exception ex){};
				textField.setText(originalText + add );
			}
			else if(e.getKeyCode() == e.VK_BACK_SPACE)
			{
				if(tabBookmark != -1)
				{
					tabBookmark = -1;
					textField.setText(originalText);
				}
			}
			else
			{
				tabBookmark = -1;
			}
		}

	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		userFocus = true;
		this.setIconImage(ResourceGetter.getBlueIcon());
		//Application.getApplication().setDockIconImage(ResourceGetter.getBlueIcon()); //for mac
	}


	@Override
	public void windowClosed(WindowEvent arg0) {


	}


	@Override
	public void windowClosing(WindowEvent arg0) {
		if(Start.getServer() != null)
		{
			Start.getServer().sendAll(ServerMessenger.stopping());
		}
		else
			Start.getClient().sendMessage(ClientMessenger.getLeaving());

	}


	@Override
	public void windowDeactivated(WindowEvent arg0) {
		userFocus = false;

	}


	@Override
	public void windowDeiconified(WindowEvent arg0) {


	}


	@Override
	public void windowIconified(WindowEvent arg0) {


	}


	@Override
	public void windowOpened(WindowEvent arg0) {


	}


	public void addTextLine(String line, Color color) 
	{
		JLabel label = area.println(line);
		label.setForeground(color);
		label.setText(label.getText().replace("<", "&lt;"));
		label.setText(label.getText().replace(">", "&gt;"));
		wrapLabelText(label,label.getText());
	}

	private void wrapLabelText(JLabel label, String text) {
		FontMetrics fm = label.getFontMetrics(label.getFont());
		java.awt.Container container = label.getParent();
		int containerWidth = container.getWidth();

		BreakIterator boundary = BreakIterator.getWordInstance();
		boundary.setText(text);

		StringBuffer trial = new StringBuffer();
		StringBuffer real = new StringBuffer("<html>");

		int start = boundary.first();
		for (int end = boundary.next(); end != BreakIterator.DONE;
				start = end, end = boundary.next()) {
			String word = text.substring(start,end);
			trial.append(word);
			int trialWidth = SwingUtilities.computeStringWidth(fm,
					trial.toString());
			if (trialWidth > containerWidth) {
				trial = new StringBuffer(word);
				real.append("<br>");
			}
			real.append(word);
		}

		real.append("</html>");

		label.setText(real.toString());
	}

	public void printlnBlack(String string) 
	{
		this.addTextLine(string, KevinColor.kblack);

	}


	@Override
	public void println(String s) {
		this.printlnBlack(s);

	}


	@Override
	public void print(String s) {
		this.area.print(s);

	}


	@Override
	public void println() {
		this.printlnBlack("");

	}


	public boolean hasUserFocus() 
	{
		return userFocus;
	}


	public void changeIconAlert() 
	{
		this.setIconImage(ResourceGetter.getRedIcon());
		//Application.getApplication().setDockIconImage(ResourceGetter.getRedIcon()); //for mac
	}

	public LabelArea getLabelArea() {
		return area;
	}

	public JTextField getInputTextField() 
	{
		return this.textField;
	}

	public void setTypingManager(ClientTypingManager typingManager2) {
		this.typingManager = typingManager2;
	}

	private boolean done = false;
	public void setTypingStatus(String[] typingClients, String[] enteredClients) 
	{
		this.loadInfoPanel(typingLabelPanel,typingClients);
		this.loadInfoPanel(enteredLabelPanel, enteredClients);
		if(!done)
		{
		this.repaint();
		this.setSize(this.getWidth()-1, this.getHeight());
		this.setSize(this.getWidth()+1, this.getHeight());
		done = true;
		}
	}


	private void loadInfoPanel(JPanel panel, String[] names) 
	{
		for(int i = 0; i < names.length; i++)
		{
			getLabel(i,panel).setText(names[i]);
		}
		if(names.length < panel.getComponentCount())
		{
			for(int i = names.length; i < panel.getComponentCount(); i++)
			{
				JLabel label = (JLabel) panel.getComponent(i);
				label.setText("");
			}
		}
		panel.repaint();
		
	}
	private JLabel getLabel(int i, JPanel panel)
	{
		if(i < panel.getComponentCount())
			return (JLabel) panel.getComponent(i);
		else
			return newLabel(panel);
	}
	private JLabel newLabel(JPanel panel)
	{
		JLabel label = new JLabel();
		label.setAlignmentX(LEFT_ALIGNMENT);
		panel.add(label);
		return label;
	}

}
