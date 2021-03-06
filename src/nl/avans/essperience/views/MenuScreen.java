package nl.avans.essperience.views;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import nl.avans.essperience.entities.Score;
import nl.avans.essperience.main.Main;
import nl.avans.essperience.models.MenuModel;
import nl.avans.essperience.utils.AssetManager;
import nl.avans.essperience.utils.Utils;

public class MenuScreen extends GameScreen
{
	private static final long serialVersionUID = 4178628810705405806L;
	
	private boolean _debug = false;
	
	private Image _checkedLeft;
	private Image _checkedRight;
	private Image _unCheckedLeft; 
	private Image _unCheckedRight;
	private Font _font = Main.GAME.getFont(60);
	private Font _fontH = Main.GAME.getFont(50);
	private Font _fontURL = Main.GAME.getFont(100);
	public MenuScreen(MenuModel model) 
	{
		super(model);
		
		BufferedImage checkedFull = (BufferedImage)AssetManager.Instance().getImage("Essperience/footsteps_green.png");
		_checkedLeft = checkedFull.getSubimage(0, 0, 75, 175);
		_checkedRight = checkedFull.getSubimage(75, 0, 75, 175);
		
		BufferedImage uncheckedFull = (BufferedImage)AssetManager.Instance().getImage("Essperience/footsteps.png");
		_unCheckedLeft = uncheckedFull.getSubimage(0, 0, 75, 175);
		_unCheckedRight = uncheckedFull.getSubimage(75, 0, 75, 175);
	}

	@Override
	public void update() 
	{
		if(_debug)
			System.out.println("Update called.");
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//drawing code.
		if(_debug)
			System.out.println("Drawing menu screen");
		
		if(Main.GAME != null)
		{
			g.drawImage(AssetManager.Instance().getImage("Essperience/menuscreen.png"), 0, 0, Main.GAME.getWidth(), Main.GAME.getHeight(), null);
			int xCenter = Main.GAME.getWidth() / 2;
			int yCenter = Main.GAME.getHeight() / 2;
			List<Score> scores = Main.GAME.getScores();
			int count = 0;
		
			for(int i = 0; i < scores.size(); i++)
			{
				int xLoc = xCenter - 150;
				int yLoc = 50 +(count * 55);
				g.setFont(_fontH);
				String place = (i+1+"");
				
				if(i ==0)
					place = " " + place;
				
				Utils.drawString(g, place + ": " +scores.get(i).getName() + " - " + scores.get(i).getScore(), xLoc, yLoc);
				//g.drawString((i+1) + ": " + scores.get(i).getScore() + " : " + scores.get(i).getName() , xLoc, yLoc);
				count++;
			}
			
			//Font font = new Font("Arial", Font.PLAIN, 60) ;
			
			BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			FontMetrics fm = img.getGraphics().getFontMetrics(_font);
			String startGame = "Stand on the pressure plates";
			String nLine = "to start the game!";
			
			g.drawImage(AssetManager.Instance().getImage("Essperience/essteling_logo.png"), 0, 0, 400,142,null);
			g.drawImage(AssetManager.Instance().getImage("Essperience/essperience_logo.png"), Main.DIMENSION.width - 400,0, 400, 142, null);
			int stringWidth = fm.stringWidth(startGame);
			int nLineWidth = fm.stringWidth(nLine);
			
			g.setFont(_font);
			Utils.drawString(g, startGame, xCenter - (stringWidth / 2), yCenter + 200 + 30);
			Utils.drawString(g, nLine, xCenter - (nLineWidth / 2),  yCenter + 200 + 85);
			
			String url = "Essperience.tostring.nl";
			int urlWidth = Utils.getWidth(url, _fontURL);
			g.setFont(_fontURL);
			Utils.drawString(g, url, xCenter - (urlWidth / 2),  yCenter + 200 + 250);
			
			int leftLocX = xCenter - (20 + 150);
			int rightLocX = xCenter + 20;
			
			if(((MenuModel)_gameModel).getLeftFoot() == true)
				g.drawImage(_checkedLeft, leftLocX, yCenter, 75, 175, null);
			else
				g.drawImage(_unCheckedLeft, leftLocX, yCenter, 75, 175, null);
			
			if(((MenuModel)_gameModel).getRightFoot() == true)
				g.drawImage(_checkedRight, rightLocX, yCenter, 75, 175, null);
			else
				g.drawImage(_unCheckedRight, rightLocX, yCenter, 75, 175, null);
				
		}
	}
	

}
