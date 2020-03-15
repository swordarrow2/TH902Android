package cn.s3bit.th902;

import cn.s3bit.th902.gamecontents.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;

/**
 * @author Obsidianss
 * <p>
 * Game class.
 * </p>
 */
public class GameMain extends Game {
	public SpriteBatch spriteBatch;
	public static final String GAME_TITLE = "TH902";
	public static GameMain instance = null;
	public static int width = 386;//540;//386;
    public static int height = 600;//720;//450;

	//public Stage activeStage = null;

	@Override
	public void create() {
		instance = this;
		spriteBatch = new SpriteBatch();
		Gdx.graphics.setVSync(false);
		//Gdx.graphics.setContinuousRendering(false);
		ResourceManager.Load();
		//setScreen(new MainMenuScreen());
		setScreen(new FightScreen());
		//setScreen(new DifficultySelectScreen());
		//setScreen(new CharacterSelectScreen());
	}

	@Override
	public void setScreen(Screen screen) {
		if (getScreen() != screen) {
			/*if (activeStage != null) {
			 activeStage.dispose();
			 }
			 activeStage = new Stage();*/
			Entity.Reset();
		}
		super.setScreen(screen);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
		//activeStage.draw();
		spriteBatch.begin();
		FightScreen.bf.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 850, 30);
		spriteBatch.end();
	}
}
