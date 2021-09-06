package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.MainMenuScreen;
import com.mygdx.game.utils.Art;
import com.mygdx.game.utils.Sounds;

public class MainScreen extends Game {

	@Override
	public void create() {
		Art.load();
		Sounds.load();
		setScreen(new MainMenuScreen(this,"Space Shooter Game",-1));
	}
	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
	}


}
