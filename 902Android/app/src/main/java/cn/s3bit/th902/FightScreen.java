package cn.s3bit.th902;

import cn.s3bit.th902.gamecontents.*;
import cn.s3bit.th902.gamecontents.components.*;
import cn.s3bit.th902.gamecontents.components.player.*;
import cn.s3bit.th902.utils.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.*;
import java.lang.reflect.*;

public class FightScreen extends ScreenAdapter {
	public static FightScreen instance;
	public static BitmapFont bf = new BitmapFont(Gdx.files.external("resources/font/mainfont.fnt"));
	public static int _difficulty = 2;
	private String mDifficulty[] = { "Easy", "Normal", "Hard", "Lunatic" };
	private ImageRenderer mBombs[] = new ImageRenderer[8];
	private ImageRenderer mHearts[] = new ImageRenderer[8];
	public static int gameTime = 0;
	public static int playerCount = 2;
	public static int bombCount = 3;
	public static int powerCount = 0;
	public static int pointCount = 0;
	public static final float TOP = 750;
	public static final float LEFT = -30;
	public static final float RIGHT = 610;
	public static final float BOTTOM = -30;
	public static final int PlayerTypeReimu = 1;
	public static final int PlayerTypeMarisa = 2;
	public static final int PlayerTypeA = 1;
	public static final int PlayerTypeB = 2;
	public static int PlayerType = 1;
	public static int PlayerChara = 1;
	public static SceneSystem sceneSystem;

	private FitViewport fitViewport;
	public Stage stage;
	public Group groupNormal;
	public Group groupHighLight;
	public Group groupGameUI;

	@Override
	public void show() {
		super.show();
		instance = this;
		fitViewport = new FitViewport(960, 720);//(GameMain.width, GameMain.height);
        stage = new Stage(fitViewport, GameMain.instance.spriteBatch); 
		groupNormal = new Group();
        groupHighLight = new Group();
		groupGameUI = new Group();
		groupNormal.addActor(new Actor() {
				public void draw(Batch batch, float parentAlpha) {
					GameMain.instance.spriteBatch.end();
					GameMain.instance.spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
					GameMain.instance.spriteBatch.begin();
				}
			});
		groupHighLight.addActor(new Actor() {
				public void draw(Batch batch, float parentAlpha) {
					GameMain.instance.spriteBatch.end();
					GameMain.instance.spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
					GameMain.instance.spriteBatch.begin();
				}
			});
		stage.addActor(groupHighLight);
		stage.addActor(groupNormal);
		stage.addActor(groupGameUI);
		groupGameUI.toFront();

		for (int i=1; i <= 5; i++) {
			long seed = MathUtils.random(4611686018427387904L);
			RandomPool.get(i).random.setSeed(seed);
		}
		MusicManager.StopBGM();
		Entity player = Entity.Create();
		Entity fightScreen = Entity.Create();
		Entity difficulty = Entity.Create();
		Entity score = Entity.Create();
		Entity highScore = Entity.Create();
		Entity graze = Entity.Create();
		Entity point = Entity.Create();
		Entity power = Entity.Create();
		Entity players = Entity.Create();
		Entity spellCard = Entity.Create();
		Entity bombs[] = new Entity[8];
		Entity hearts[] = new Entity[8];

		fightScreen.AddComponent(new Transform(new Vector2(480, 360)));
		player.AddComponent(new Transform(new Vector2(280, 100), new Vector2(0.4f, 0.4f)));
		difficulty.AddComponent(new Transform(new Vector2(760, 670)));
		score.AddComponent(new Transform(new Vector2(810, 570)));
		highScore.AddComponent(new Transform(new Vector2(635, 620)));
		graze.AddComponent(new Transform(new Vector2(810, 300)));
		point.AddComponent(new Transform(new Vector2(810, 250)));
		power.AddComponent(new Transform(new Vector2(810, 350)));
		players.AddComponent(new Transform(new Vector2(810, 500)));
		spellCard.AddComponent(new Transform(new Vector2(615, 450)));

		if (_difficulty > 0 && _difficulty < 5)
			difficulty.AddComponent(new ImageRenderer(ResourceManager.textures.get(mDifficulty[_difficulty - 1]), 1).attachToGroup(groupGameUI));
		score.AddComponent(new ImageRenderer(ResourceManager.textures.get("Score"), 1).attachToGroup(groupGameUI));
		highScore.AddComponent(new ImageRenderer(ResourceManager.textures.get("HighScore"), 1).attachToGroup(groupGameUI));
		players.AddComponent(new ImageRenderer(ResourceManager.textures.get("Players"), 1).attachToGroup(groupGameUI));
		spellCard.AddComponent(new ImageRenderer(ResourceManager.textures.get("SpellCard"), 1).attachToGroup(groupGameUI));
		power.AddComponent(new ImageRenderer(ResourceManager.textures.get("Power"), 1).attachToGroup(groupGameUI));
		graze.AddComponent(new ImageRenderer(ResourceManager.textures.get("Graze"), 1).attachToGroup(groupGameUI));
		point.AddComponent(new ImageRenderer(ResourceManager.textures.get("Point"), 1).attachToGroup(groupGameUI));

		for (int i = 0; i < 8; i++) {
			bombs[i] = Entity.Create();
			hearts[i] = Entity.Create();
			bombs[i].AddComponent(new Transform(new Vector2(720.5f + 30.0f * i, 450.5f)));
			hearts[i].AddComponent(new Transform(new Vector2(720.5f + 30.0f * i, 500.5f)));
			bombs[i].AddComponent(new ImageRenderer(ResourceManager.textures.get("Bomb"), 1));
			hearts[i].AddComponent(new ImageRenderer(ResourceManager.textures.get("Heart"), 1));
			mBombs[i] = bombs[i].GetComponent(ImageRenderer.class);
			mHearts[i] = hearts[i].GetComponent(ImageRenderer.class);
			mBombs[i].attachToGroup(groupGameUI);
			mBombs[i].image.setColor(1, 1, 1, 0);
			mHearts[i].attachToGroup(groupGameUI);
			mHearts[i].image.setColor(1, 1, 1, 0);
		}
		fightScreen.AddComponent(new ImageRenderer(ResourceManager.textures.get("FightScreen"), 0).attachToGroup(groupGameUI));
		if (PlayerChara == PlayerTypeReimu) {
			player.AddComponent(new PlayerReimu(PlayerType));
		} else if (PlayerChara == PlayerTypeMarisa) {

		}
		sceneSystem = SceneSystem.Create(_difficulty, 0);
		InputMultiplexer inputManager = new InputMultiplexer();
        inputManager.addProcessor(new PlayerInputProcessor());
        Gdx.input.setInputProcessor(inputManager);
	}

	@Override
    public void resize(int width, int height) {
        super.resize(width, height);
        fitViewport.update(width, height);
    }

	@Override
	public void render(float delta) {
		stage.draw();
		if (powerCount < 0) {
			powerCount = 0;
		}
		sceneSystem.PreUpdate();
		Entity.UpdateAll();
		JudgingSystem.judgeEnemyHurt();
		sceneSystem.PostUpdate();
		super.render(delta);
		gameTime++;
		GameMain.instance.spriteBatch.begin();
		bf.draw(GameMain.instance.spriteBatch, "difficulty:" + _difficulty
				+ "\nplayer:" + playerCount + "\nbomb:" + bombCount + "\npower:" + powerCount + "\npoint:" + pointCount,
				20, 705);
		GameMain.instance.spriteBatch.end();

		for (int i = 0; i < 8; i++)
			if (i < playerCount)
				mHearts[i].image.setColor(1, 1, 1, 1);
			else
				mHearts[i].image.setColor(1, 1, 1, 0);
		for (int i = 0; i < 8; i++)
			if (i < bombCount)
				mBombs[i].image.setColor(1, 1, 1, 1);
			else
				mBombs[i].image.setColor(1, 1, 1, 0);
	}

	public static boolean isOutOfScreen(Vector2 v) {
		return !(v.x >= LEFT && v.x <= RIGHT && v.y <= TOP && v.y >= BOTTOM);
	}
}
