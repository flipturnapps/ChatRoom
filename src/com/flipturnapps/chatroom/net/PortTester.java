package com.flipturnapps.chatroom.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class PortTester 
{
	private ArrayList<String> list;
	private ArrayList<String> currList;
	private static final int THREADS = 75;
	private boolean modeComputerFinder;
	private static final long START_TIMEOUT = 2000;
	private long timeout = START_TIMEOUT;
	public PortTester(String ips)
	{

		list = new ArrayList<String>();
		String ip = ips.split(":")[0];
		String port = ips.split(":")[1];
		String[] ipar = ip.replace('.','a').split("a");
		String[][] ipsf = new String[5][2];
		String[] ports = new String[2];
		for (int i = 0; i < ipar.length; i++) 
		{
			//System.out.println(ipar[i]);
			try
			{
				ipsf[i][0] = ipar[i].split("-")[0];
				ipsf[i][1] = ipar[i].split("-")[1];
			}
			catch(Exception ex)
			{
				ipsf[i][0] = ipar[i];
				ipsf[i][1] = ipar[i];
			}
		}
		try
		{
			ports[0] = port.split("-")[0];		
			ports[1] = port.split("-")[1];
		}
		catch(Exception ex)
		{
			ports[0] = port;
			ports[1] = port;
		}
		int[][] intsf = new int[5][2];
		for(int x = 0; x < 4; x++)
		{
			for(int y = 0; y < 2; y++)
			{
				intsf[x][y] = Integer.parseInt(ipsf[x][y]);
			}
		}
		intsf[4][0] = Integer.parseInt(ports[0]);
		intsf[4][1] = Integer.parseInt(ports[1]);
		for(int a = intsf[0][0]; a <= intsf[0][1]; a++)
		{
			for(int b = intsf[1][0]; b <= intsf[1][1]; b++)
			{
				for(int c = intsf[2][0]; c <= intsf[2][1]; c++)
				{
					for(int d = intsf[3][0]; d <= intsf[3][1]; d++)
					{
						for(int e = intsf[4][0]; e <= intsf[4][1]; e++)
						{
							String add = a+"."+b+"."+c+"."+d+":"+e;
							///System.out.println(add);
							list.add(add);
						}	
					}	
				}	
			}
		}
	}
	public ArrayList<String> startTest()
	{
		currList = new ArrayList<String>();

		new Thread(new Tester()).start();

		return currList;
	}
	private class Tester implements Runnable
	{

		@Override
		public void run() 
		{
			ArrayList<TestData> tests = new ArrayList<TestData>();
			while(list.size()>0)
			{
				for(int i = 0; i < tests.size();i++)
				{
					if(System.currentTimeMillis() - tests.get(i).startTime > getTimeout())
					{
						TestData data = tests.get(i);
						data.thread.interrupt();
						tests.remove(data);
					}
				}
				while(tests.size() < THREADS)
				{
					TestPort test = new TestPort();
					Thread thread = new Thread(test);
					TestData data = new TestData();
					data.test = test;
					data.thread = thread;
					data.startTime = System.currentTimeMillis();
					thread.start();
					tests.add(data);
				}
			}

		}

	}
	private class TestData
	{
		public Thread thread;
		public TestPort test;
		public long startTime;
	}
	private class TestPort implements Runnable
	{

		@Override
		public void run() 
		{
			try
			{
				String ip = list.remove(0);
				String name = isPortOpen(ip);
				if(name != null)
				{
					currList.add(ip + "~" + name);
				}

			}
			catch(Exception ex)
			{

			}

		}

	}
	public ArrayList<String> getIpList()
	{
		return list;
	}
	public void setModeComputerFinder(boolean b)
	{
		this.modeComputerFinder = b;
	}
	public boolean getModeComputerFinder()
	{
		return this.modeComputerFinder;
	}
	public  String isPortOpen(String ip)
	{
		String IPAdr = ip.split(":")[0];

		int port = Integer.parseInt( ip.split(":")[1]);
		Socket socket = null;
		try {
			socket = new Socket(IPAdr, port);   
			String name = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
			socket.close();
			return name;
		} catch (Exception e) {
			if(modeComputerFinder && e.getMessage().contains("refused"))
				return "Refused";
			return null;
		}
	}
	public long getTimeout() {
		return timeout;
	}
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	public static ArrayList<String> autoSearchInLAN(int port)
	{
		String[] ipstring = null;
		int[] ips = null;
		try {
			ipstring = Inet4Address.getLocalHost().getHostAddress().toString().replace('.','a').split("a");
			ips = new int[ipstring.length];
			for (int i = 0; i < ips.length; i++) 
			{
				ips[i] = Integer.parseInt(ipstring[i]);

			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> ret = null;
		try
		{
			PortTester tester = new PortTester(ips[0]+"."+ips[1]+"."+ips[2]+"."+"0-255:" + port);
			ret = tester.startTest();
		}
		catch(Exception ex)
		{

		}
		return ret;
	}
}
