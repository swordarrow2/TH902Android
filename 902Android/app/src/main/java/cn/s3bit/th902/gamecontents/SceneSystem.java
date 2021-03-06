package cn.s3bit.th902.gamecontents;

import cn.s3bit.th902.*;
import cn.s3bit.th902.contents.stage1.*;
import cn.s3bit.th902.contents.stage2.*;
import cn.s3bit.th902.contents.stage6.*;
import cn.s3bit.th902.danmaku.mbg.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.files.*;
import com.th902.android.*;
import java.io.*;
import java.util.*;

public class SceneSystem {
	public static SceneSystem Create(int difficulty, int stageid) {
		return Create(difficulty, stageid, null);
	}

	public static SceneSystem Create(int difficulty, int stageid, Runnable afterFinish) {
		// test
		SceneSystem system = new SceneSystem();
		// Stage 1
		system.mScenes.add(new DanmakuS1L1());
		system.mScenes.add(new DanmakuS1L2());
		system.mScenes.add(new DanmakuS1L3());
		system.mScenes.add(new DanmakuS1L4());
		system.mScenes.add(new DanmakuS1L5());
		system.mScenes.add(new DanmakuS1L6());
		system.mScenes.add(new DanmakuS1LLast());

		// Stage 2
		system.mScenes.add(new DanmakuS2L1());
		system.mScenes.add(new DanmakuS2L2());
		system.mScenes.add(new DanmakuS2L3());
		system.mScenes.add(new MBGScene(3500, 2400, 1.7f, ResourceManager.barrages.get(230), "", true, false, Gdx.files.external("resources/Danmaku/feifu1.mbg"), 180, 0));
		system.mScenes.add(new MBGScene(5000, 3000, 2.2f, ResourceManager.barrages.get(230), "「贰壹叁壹伍」", false, false, Gdx.files.external("resources/Danmaku/21315.mbg"), 0, 60));
		system.mScenes.add(new MBGScene(2000, 1500, 1f, ResourceManager.barrages.get(230), "", false, false, Gdx.files.external("resources/Danmaku/yifei.mbg"), 0, 60));
		system.mScenes.add(new MBGScene(1073741824, 2000, 0f, ResourceManager.barrages.get(230), "「极光与流星」", false, false, Gdx.files.external("resources/Danmaku/jiguangyuliuxing.mbg"), 0, 120));
		system.mScenes.add(new MBGScene(12000, 3000, 4f, ResourceManager.barrages.get(230), "终符「弹幕结界」", false, true, Gdx.files.external("resources/Danmaku/danmujiejie.mbg")));

		// Stage 5
		system.mScenes.add(new MBGScene(5000, 3000, 2.25f, ResourceManager.barrages.get(230), "算符「四重递归」", true, true, Gdx.files.external("resources/Danmaku/sichongdigui.mbg"), 360, 0));

		// Stage 6
		system.mScenes.add(new MBGScene(5000, 3000, 2.1f, ResourceManager.barrages.get(230), "", true, false, Gdx.files.external("resources/Danmaku/yaobai.mbg"), 360, 0));
		system.mScenes.add(new MBGScene(1073741824, 2200, 0f, ResourceManager.barrages.get(230), "里冬「天青色旋转木马」", false, false, Gdx.files.external("resources/Danmaku/lidong.mbg"), 0, 120));
		system.mScenes.add(new MBGScene(4000, 2000, 1.8f, ResourceManager.barrages.get(230), "", false, false, Gdx.files.external("resources/Danmaku/zuotu.mbg"), 0, 60));
		system.mScenes.add(new SpellWaveParticleChanged1());
		system.mScenes.add(new MBGScene(7700, 2400, 2f, ResourceManager.barrages.get(230), "里冬「绽放的烟花」", false, true, Gdx.files.external("resources/Danmaku/1233Y7Resave.mbg"), 0, 400));
		/*system.mScenes.add(new MBGScene(1000, 1000, 1, ResourceManager.barrages.get(230), true, true,
		 "Crazy Storm Data 1.01\n"+
		 "Center:315,240,0,0,0,0,\n"+
		 "Totalframe:200\n"+
		 "Layer1:新图层 ,1,200,2,0,0,0,0\n"+
		 "0,0,False,-1,False,,320,224,1,200,-99998,-99998,0,0,{X:0 Y:0},1,5,0,{X:0 Y:0},0,1,20,{X:0 Y:0},0,0,{X:0 Y:0},200,1,1,1,255,255,255,100,0,{X:0 Y:0},True,5,0,{X:0 Y:0},0,0,{X:0 Y:0},1,1,True,True,False,False,True,False,,,0,0,0,0,0,0,20,0,0,0,0,0,0,0,0,0,0,True,True,True,False\n"+
		 "1,0,False,-1,False,,256,128,1,200,-99998,-99998,0,0,{X:0 Y:0},1,5,0,{X:0 Y:0},360,0,0,{X:0 Y:0},0,0,{X:0 Y:0},200,1,1,1,255,255,255,100,0,{X:0 Y:0},True,5,0,{X:0 Y:0},0,0,{X:0 Y:0},1,1,True,True,False,False,True,False,,,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,True,True,True,False\n"+
		 "Layer2:empty\n"+
		 "Layer3:empty\n"+
		 "Layer4:empty\n"
		 ));*/
		//system.mScenes.add(new ExampleDanmakuScene());
		//system.mScenes.add(new ExampleDanmakuScene());
		system.afterFinish = afterFinish;
		if (system.afterFinish == null) {
			system.afterFinish = new EmptyRunnable();
		}
		return system;
	}

	private SceneSystem() {
		mScenes = new ArrayList<>();
		mCurrentIndex = 0;
		afterFinish = null;
		sceneManager = null;
	}

	private ArrayList<DanmakuScene> mScenes;
	private int mCurrentIndex;
	private Runnable afterFinish;
	public Entity sceneManager;

	public void PreUpdate() {
		if (mCurrentIndex >= mScenes.size()) {
			if (afterFinish != null) {
				afterFinish.run();
				afterFinish = null;
			}
			return;
		}
		DanmakuScene scene = mScenes.get(mCurrentIndex);
		if (sceneManager == null) {
			sceneManager = Entity.Create();
			sceneManager.AddComponent(scene);
		}
	}

	public void PostUpdate() {
		if (mCurrentIndex >= mScenes.size() || sceneManager == null)
			return;
		DanmakuScene scene = mScenes.get(mCurrentIndex);
		if (scene.yield.isFinished()) {
			nextScene();
		}
	}

	public void nextScene() {
		mCurrentIndex++;
		sceneManager = null;
	}
}
