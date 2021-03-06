package nl.avans.essperience.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import nl.avans.essperience.entities.Score;
import nl.avans.essperience.main.Main;
import nl.avans.essperience.utils.Enums.GameKeys;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Utils 
{
	private static boolean _debug = false;
	public static GameKeys getFromKeyboardCode(int code)
	{
		if (_debug)
			System.out.println(code);
		if(code == 65)
			return GameKeys.KeyA;
		else if(code == 66)
			return GameKeys.KeyB;
		else if(code == 87)
			return GameKeys.KeyW;
		else if(code == 68)
			return GameKeys.KeyD;
		else if(code == 85)
			return GameKeys.KeyU;
		else if(code == 73)
			return GameKeys.KeyI;
		else if(code == 79)
			return GameKeys.KeyO;
		else if(code == 80)
			return GameKeys.KeyP;
		else if(code == 10)
			return GameKeys.KeyEnter;
		else if(code == 38)
			return GameKeys.KeyUp;
		else if(code == 40)
			return GameKeys.KeyDown;
		else if(code == 32)
			return GameKeys.KeySpacebar;
		else if(code == 49)
			return GameKeys.Key1;
		else if(code == 50)
			return GameKeys.Key2;
		else if(code == 51)
			return GameKeys.Key3;
		else if(code == 52)
			return GameKeys.Key4;
		else if(code == 53)
			return GameKeys.Key5;
		else if(code == 54)
			return GameKeys.Key6;
		else if(code == 55)
			return GameKeys.Key7;
		else if(code == 56)
			return GameKeys.Key8;
		else if(code == 57)
			return GameKeys.Key9;
		else
			return GameKeys.None;
		
	}
	
	public static int percentOf(int percent, int maxValue)
	{
		return (maxValue / 100) * percent;
	}
	
	/**
	 * measures the width of a string
	 * @param s string to be measured
	 * @return width of the string in pixels
	 * @author jack
	 */
	public static int getWidth(String s, Font font)
	{
		int width;

//		width = s.length() * 3;

		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
//		Font font = new Font("Tahoma", Font.PLAIN, 12);
		width = (int)(font.getStringBounds(s, frc).getWidth());

		return width;
	}
	
	/**
	 * measures the width of a string
	 * @param s string to be measured
	 * @return width of the string in pixels
	 * @author jack
	 */
	public static int getWidth(String s)
	{  
		Font font = new Font("Tahoma", Font.PLAIN, 12);
		return getWidth(s, font);
	}
	
	/**
	 * Crops a string to fit the maxWidth that has been passed in
	 * @param s string to be cropped
	 * @param maxWidth: max width(in pixels) the returned string has to fit in.
	 * @return a cropped string that fits the specified maxWidth
	 * @author jack
	 */
	public static String cropString(String s, int maxWidth)
	{
//		System.out.println("StringWidth and maxWidth"); //DEBUGGING PURPOSES
//		System.out.println(getWidth(s));
//		System.out.println(maxWidth);

		String string = s;

		if(getWidth(string) < maxWidth)
			return string;

		while(getWidth(string + "...") > maxWidth)
			string = string.substring(0, string.length() - 1);

		string += "...";

		return string;
	}
	
	public static boolean isUnix() 
	{	 
		String OS = System.getProperty("os.name").toLowerCase();
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}
	
	public static String parseName(String name)
	{
		String parsedName = "";
		
		if(name.length() == 0)
		{
			return "Unknown%20Soldier";
		}
		
		for(int i = 0; i < name.length(); i++)
		{
			if(name.charAt(i) == ' ')
			{
				parsedName += "%20";
			}
			else
			{
				parsedName += name.charAt(i);
			}
		}
		
		return parsedName;
	}
	
	public static void drawString(Graphics g, String string, int x, int y)
	{
		drawString(g, string, 1, x, y);
	}
	
	public static void drawString(Graphics g, String string, int outlineThickness, int x, int y)
	{
		drawString(g, string, Color.black, Color.white, outlineThickness, x, y);
	}
	
	public static void drawString(Graphics g, String string, Color stringColor, Color outlineColor, int outlineThickness, int x, int y)
	{
		g.setColor(outlineColor);
		g.drawString(string, x - outlineThickness, y);
		g.drawString(string, x + outlineThickness, y);
		g.drawString(string, x, y - outlineThickness);
		g.drawString(string, x, y + outlineThickness);
		g.setColor(stringColor);
		g.drawString(string, x, y);
		g.setColor(outlineColor);
	}

	public static void addHighScore(final String name, final int score)
	{	
		final String parsedName = parseName(name);
		
		new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					String url = "http://essperience.tostring.nl/highscore_add/" + parsedName + "/" + score;
					 
					URL obj = new URL(url);
					HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			 
					con.setRequestMethod("GET");
					con.setRequestProperty("User-Agent", "Mozilla/5.0");
			 
					int responseCode = con.getResponseCode();
					if (_debug)
					{
					System.out.println("\nSending 'GET' request to URL : " + url);
					System.out.println("Response Code : " + responseCode);
					}
					BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();
			 
					while ((inputLine = in.readLine()) != null) 
					{
						response.append(inputLine);
					}
					in.close();
			 
					if (_debug)
					{
						//print result
						System.out.println(response.toString());
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}).start();
				
	}
	
	public static void disableAutoPress()
	{
		if(Utils.isUnix())
		{
			try
			{
				Process proc = Runtime.getRuntime().exec("xset r off");
				if (_debug)
				{
					System.out.println("Repeat is off");
				}
				@SuppressWarnings("unused")
				BufferedReader read = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				proc.waitFor();
			}
			catch(Exception e) 
			{
				if (_debug)
				{
				System.out.println(e.getMessage());
				}
			}
		}
	}
	
	public static void enableAutoPress()
	{
		if(Utils.isUnix())
		{
			try
			{
				Process proc = Runtime.getRuntime().exec("xset r on");

				if (_debug)
				{
					System.out.println("Rpeat is on");
				}
				@SuppressWarnings("unused")
				BufferedReader read = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				proc.waitFor();
			}
			catch(Exception e) 
			{
				if (_debug)
				{
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	public static List<Score> getTopScores(int amount)
	{
		List<Score> result = new ArrayList<Score>();
		try
		{
			String url = "http://essperience.tostring.nl/top_highscore/" + amount;
			 
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
	 
			int responseCode = con.getResponseCode();
			//System.out.println("\nSending 'GET' request to URL : " + url);
			//System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) 
			{
				response.append(inputLine);
			}
			in.close();
			//JsonObject job = new Gson().fromJson(response.toString(), JsonObject.class);
			
			Type listType = new TypeToken<List<Score>>() {}.getType();
			result = new Gson().fromJson(response.toString(), listType);
			
			//result = (new Gson().fromJson(response.toString(), new ArrayList<Score>().getClass()));
			//print result
			//System.out.println(response.toString());
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void startBackgroundWorder()
	{
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run() 
			{
				while(true)
				{
					Main.GAME.setScores(Utils.getTopScores(5));
					try
					{
						Thread.sleep(5000);
					}catch(Exception e){}
				}	
			}
			
		});
		t.start();
	}
}
