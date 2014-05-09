package nl.avans.essperience.controllers;

import java.util.ArrayList;

import javax.swing.JFrame;

import nl.avans.essperience.events.MicroGameFinishedEventListener;
import nl.avans.essperience.models.GameModel;
import nl.avans.essperience.models.IndianaJantjeModel;
import nl.avans.essperience.models.MenuModel;
import nl.avans.essperience.views.GameScreen;
import nl.avans.essperience.views.MenuScreen;

public class GameHandler extends JFrame
{
	private static final long serialVersionUID = -4608768969398477748L;

	private int _difficulty = 1;

	private int _lives = GameHandler.MAX_LIVES;

	private final int _NUMBEROFGAMES = 1;

	public static final int MAX_LIVES = 3;
	private GameScreen _gameScreen;
	private GameController _gameController;
	private GameModel _gameModel;

	public GameHandler()
	{
		super("Essperience");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		init();

		setContentPane(_gameScreen);

		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);  
		setSize(800, 800);
		setVisible(true);
	}

	public void init()
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
		start();
	}

	public void start()
	{
		int rand = (int) (Math.random() * _NUMBEROFGAMES) + 1;
		switch (rand) {
		case 1: 
			_gameModel = new IndianaJantjeModel(_difficulty);
			break;
		default:
			System.out.println("Game choice error");
			break;
		}
		_gameModel.run();
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
		init();
	}



	public void nextGame(boolean succeed)
	{
		if(!succeed)
		{
			if(_lives == 1)
			{
				reset();
				return;
			}	
			else
				_lives--;
		} else {
			_difficulty++;
		}

		// do logic for next game screen hier.
	}

}


