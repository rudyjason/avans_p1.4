package nl.avans.essperience.models;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.World;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.strategies.QuadSpaceStrategy;
import nl.avans.essperience.entities.simon.FruitPiece;
import nl.avans.essperience.main.Main;
import nl.avans.essperience.utils.AssetManager;

public class SimonGameModel extends GameModel
{
	public static final int BANANA = 0;
	public static final int ORANGE = 1;
	public static final int APPLE = 2;
	public static final int PEAR = 3;
	
	private int _patternLength;
	private World _myWorld;
	private Body _floor;
	private List<Body> _fruitPieces = new ArrayList<Body>();
	private Iterator<Body> _fruitPiecesIterator;
	private List<Body> _bodyList = new ArrayList<Body>();
	private boolean _debug = true;
	private Image[] _fruitImages = new Image[4];
	
	private int _stepsPerUpdate;
	private int _updateCounter;
	private int _totalUpdatesNeeded;
	
	private double _updateProgress;
	private double _actualProgress;
	
	public SimonGameModel()
	{		
		_fruitImages[BANANA] = AssetManager.Instance().getImage("Simon/banana.png");
		_fruitImages[ORANGE] = AssetManager.Instance().getImage("Simon/orange.png");
		_fruitImages[APPLE] = AssetManager.Instance().getImage("Simon/apple.png");
		_fruitImages[PEAR] = AssetManager.Instance().getImage("Simon/pear.png");
		
		init();
	}

	private void init()
	{
		int _difficulty = Main.GAME.getDifficulty();
		_patternLength = (_difficulty /4) +3;
		int stepsPerPiece = 100/ ((_difficulty/4) +5);
		_totalUpdatesNeeded = _patternLength * stepsPerPiece;
		_stepsPerUpdate = (_difficulty /4) +4;
		
		_myWorld = new World(new Vector2f(0.0f, 10.0f), 10, new QuadSpaceStrategy(20,5));
		
		_myWorld.clear();
		_myWorld.setGravity(0, 30);
		
		_floor = new StaticBody("Floor", new Box(Main.GAME.getWidth(), 1f));
		_floor.setPosition(Main.GAME.getWidth()/2, Main.GAME.getHeight() - 200);
		_myWorld.add(_floor);
		
		for(int i = 0; i < _patternLength; i++)					//add pieces of fruit
		{
			FruitPiece fp = new FruitPiece();					//create new pieces of fruit
			_fruitPieces.add(fp.getBody());						//get the Bodies and store them
		}
		
		_fruitPiecesIterator = _fruitPieces.iterator();
		
		if(_debug)
			System.out.println("INIT SIMON GAME COMPLETE");
	}
	
	@Override
	public void update()
	{
		_updateCounter++;

		if(_debug)
			System.out.println("position: " + _fruitPieces.get(0).getPosition().toString());
		
		//update and step the world
		super.update();
		for(int i = 0; i < _stepsPerUpdate; i ++)
			_myWorld.step();
		
		// track progress
		_updateProgress = (double)_updateCounter/(double)_totalUpdatesNeeded;
		_actualProgress = (double)_bodyList.size() / (double)_fruitPieces.size();
		
		//add bodies when the actualProgress is behind on the updateProgress
		if(_updateProgress >= _actualProgress)
		{
			Body body = null;
			
			if(_fruitPiecesIterator.hasNext())
			{
				body = _fruitPiecesIterator.next();
				_bodyList.add(body);
				_myWorld.add(body);
			}
		}
		
	}
	
	public void draw(Graphics g1)
	{
		Graphics2D g = (Graphics2D) g1;;
		
		for(Body body : _bodyList)
		{			
			float rotation = body.getRotation();
			int x = (int)body.getPosition().getX();
			int y = (int)body.getPosition().getY();
			
			g.translate(x, y);
			g.rotate(rotation);
			
			g.drawImage(getFruitImage(body), 0 -32, 0 -32, 64, 64, null);
			
			g.rotate(-rotation);
			g.translate(-x, -y);
		}
		

		if(_debug)
		{
			//display updateCounter
			int drawStringX = Main.GAME.getWidth() - 150;
			g.drawString("Update no.: " + _updateCounter, drawStringX, 20);
			
			//display _bodyList size
			g.drawString("_updateProgress: " + _updateProgress, drawStringX, 40);
			g.drawString("_actualProgress: " + _actualProgress, drawStringX, 60);
			g.drawString("_bodyList.Size: " + _bodyList.size(), drawStringX, 80);
			
			//debug display Floor
			int x = (int) _floor.getPosition().getX() - Main.GAME.getWidth()/2;
			int y = (int) _floor.getPosition().getY();
			int w = (int)_floor.getShape().getBounds().getWidth();
			int h = (int)_floor.getShape().getBounds().getHeight();
			g.drawRect(x, y, w, h);
			
			//debug display bodyBoundingBoxes
			for(Body body : _fruitPieces)
			{
				int bw = (int) body.getShape().getBounds().getWidth();
				int bh = (int) body.getShape().getBounds().getHeight();
				int bx = (int) body.getPosition().getX() - bw/2;
				int by = (int) body.getPosition().getY() - bh/2;
				g.drawRect(bx, by, bw, bh);
			}
		}
	}
	
	private Image getFruitImage(Body body)
	{
		String name = (String)body.getUserData();
//		System.out.println(name);
		
		switch(name)
		{
		default: // case "banana"
			return _fruitImages[BANANA];
		case "orange":
			return _fruitImages[ORANGE];
		case "apple":
			return _fruitImages[APPLE];
		case "pear":
			return _fruitImages[PEAR];
		}
	}
	
}


