package nl.avans.essperience.controllers;

import javax.swing.JFrame;
import javax.swing.JPanel;

import nl.avans.essperience.events.MicroGameFinishedEventListener;
import nl.avans.essperience.main.Main;
import nl.avans.essperience.models.FlappyBirdModel;
import nl.avans.essperience.models.GameModel;
import nl.avans.essperience.models.IndianaJantjeModel;
import nl.avans.essperience.models.MenuModel;
import nl.avans.essperience.views.FlappyBirdScreen;
import nl.avans.essperience.views.GameScreen;
import nl.avans.essperience.views.IndianaJantjeScreen;
import nl.avans.essperience.views.MenuScreen;

public class GameHandler extends JFrame
{
	private static final long serialVersionUID = -4608768969398477748L;

	private int _difficulty = 1;
	private final int _NUMBEROFGAMES = 1;

	private int _lives = GameHandler.MAX_LIVES;

	public static final int MAX_LIVES = 3;
	private GameScreen _gameScreen;
	private GameController _gameController;
	private GameModel _gameModel;

	public GameHandler()
	{
		super("Essperience");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		init(true);

		setContentPane(_gameScreen);

		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);  
		setSize(800, 800);
		setUndecorated(true);  
		setSize(1280, 800);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	public int getLivesLeft()
	{
		return _lives;
	}
	public int getDifficulty()
	{
		return _difficulty;
	}
	
	public void init(boolean firstRun)
	{
		this._gameScreen = new MenuScreen();
		this._gameModel = new MenuModel();
		this._gameController = new MenuController((MenuScreen)this._gameScreen, (MenuModel)_gameModel);
		this._gameController.addMicroGameFinishedEventListener(new MicroGameFinishedEventListener()
		{
			/**
			 * this is the finshed listener for the menu screen. All screens except the menuscreen will do other stuff in this event. (will be added in start method).
			 */
			@Override
			public void microGameFinishedEvent(boolean succeed) 
			{
				start(); // calls the start method to start the series of minigames. 
			}
		});
		
		if(!firstRun)
			changeScreen();
		
	}

	public void changeScreen()
	{
		Main.GAME.setContentPane(_gameScreen); // updating the game screen.
		Main.GAME.validate();
		Main.GAME.repaint();
		System.out.println("CHANGING SCREEN");
	}
	
	public void start()
	{
		nextGame(true); // for now.
	}

	public void stop()
	{
		reset();
	}

	/**
	 * resets the game back to the menuscreen
	 */
	public void reset()
	{
		_lives = MAX_LIVES;
		init(false);
	}



	public void nextGame(boolean succeed)
	{
		System.out.println("GOING TO CHANGE THE SCREEN");
		if(!succeed)
		{
			if(_lives == 1)
			{
				reset();
				return;
			}	
			else
				_lives--;
		}
		setContentPane(new JPanel(null));
		// do logic for next game screen hier.
		int rand = (int) (Math.random() * _NUMBEROFGAMES) + 2;
		switch (rand) {
		case 1: 
			_gameModel = new IndianaJantjeModel();
			_gameScreen = new IndianaJantjeScreen((IndianaJantjeModel) _gameModel);
			_gameController = new IndianaJantjeController((IndianaJantjeScreen)_gameScreen, (IndianaJantjeModel)_gameModel);
			break;
		case 2:
			this._gameModel = new FlappyBirdModel();
			this._gameScreen = new FlappyBirdScreen(this._gameModel);
			this._gameController = new FlappyBirdController((FlappyBirdModel)_gameModel, (FlappyBirdScreen)_gameScreen);
			break;
		default:
			reset();
			break;
		}
		_gameController.addMicroGameFinishedEventListener(new MicroGameFinishedEventListener() {

			@Override
			public void microGameFinishedEvent(boolean succeed) 
			{
				nextGame(succeed);
			}
		});

		changeScreen();
	}

}
