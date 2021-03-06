package nl.avans.essperience.models;

import nl.avans.essperience.main.Main;


public class RedButtonModel extends GameModel
{
	private int _colorChange;
	private int _difficulty;
	private boolean _dont;
	
	public RedButtonModel()
	{
		_maxTime = 1000 + (4000/(int)Math.sqrt(Main.GAME.getDifficulty()));;
		_difficulty = Main.GAME.getDifficulty();
		_colorChange = 0;
		if (Math.random() > 0.5f)
		{
			_dont = true;
		}
		else
		{
			_dont = false;
		}
	}
	
	@Override
	public void update()
	{
		if (getTimeRemaining() == 0)
		{
			if(_modelToControllerListener != null)
				_modelToControllerListener.timesUpEvent();
		}
		_colorChange += _difficulty;
	}
	
	public boolean getColorChange() {
		if (_colorChange > 15) {
			_colorChange -= 15;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getDont()
	{
		return _dont;
	}
}
