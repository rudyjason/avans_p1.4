package nl.avans.essperience.models;

import java.awt.Image;
import java.awt.image.BufferedImage;

import nl.avans.essperience.entities.flappy.FlappyPipe;
import nl.avans.essperience.entities.flappy.FlappyPlayer;
import nl.avans.essperience.events.CollisionDetectedEventListener;
import nl.avans.essperience.main.Main;
import nl.avans.essperience.utils.AssetManager;
import nl.avans.essperience.utils.Enums.DockLocations;

public class FlappyBirdModel extends GameModel
{
	private Image _background;
	
	// pipes
	private FlappyPipe _pipeTop;
	private FlappyPipe _pipeBottom;
	
	//player 
	private FlappyPlayer _player;
	private CollisionDetectedEventListener _collision = null;
	
	public FlappyBirdModel()
	{
		
		_background = AssetManager.Instance().getImage("Flappy/background.png");
		init();
	}
	
	public Image getBackground()
	{
		return _background;
	}
	
	public FlappyPipe getPipeTop()
	{
		return _pipeTop;
	}
	
	public FlappyPipe getPipeBottom()
	{
		return _pipeBottom;
	}
	
	public FlappyPlayer getPlayer()
	{
		return _player;
	}
	
	@Override
	public void update() 
	{
		// TODO Auto-generated method stub
		_pipeTop.moveLeft();
		_pipeBottom.moveLeft();
		
		if (_pipeTop.collision(_player.getShape()) || _pipeBottom.collision(_player.getShape())) 
		{
			if(_collision != null)
				_collision.collisionDetected();
			
			
		}
		
		// if the bird is above the GAME.getHeight -20(height of birdimage) it can be moved up. else the game should be ended cause the bird hit the ground
		if(_player.getY() < Main.GAME.getHeight() - 20)
			_player.moveY(10);
		else
		{
			if(_collision != null)
				_collision.collisionDetected();
		}
		
	}
	public void addCollisionListener(CollisionDetectedEventListener e)
	{
		_collision = e;
	}
	
	public void init()
	{		
		/**
		 * TODO make dynamic..
		 */
		_player = new FlappyPlayer();
		
		_pipeTop = new FlappyPipe(DockLocations.Top, Main.GAME.getDifficulty() * 2);
		_pipeTop.setX(600);
		_pipeTop.setY(0);
		_pipeTop.setWidth(100);
		_pipeTop.setHeight(400);
		
		_pipeBottom = new FlappyPipe(DockLocations.Bottom,Main.GAME.getDifficulty() * 2);
		_pipeBottom.setX(600);
		_pipeBottom.setY(Main.GAME.getHeight() - 200);
		_pipeBottom.setWidth(100);
		_pipeBottom.setHeight(200);
	}
	
	public void flap()
	{
		//if position more than 10 it can move up. (0 is the top of the screen)
		if(_player.getY() > 10)
			_player.moveY(-6);
	}
}
