package cn.s3bit.th902.danmaku.mbg;

import cn.s3bit.mbgparser.*;
import cn.s3bit.mbgparser.item.*;
import cn.s3bit.th902.*;
import cn.s3bit.th902.contents.*;
import cn.s3bit.th902.gamecontents.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.*;
import java.io.*;
import java.nio.charset.*;
import java.util.concurrent.*;
/**
 * CrazyStorm的解析实现，这样就可以直接放到游戏里来了
 */
public class MBGScene extends BossSpell {
	int maxLife;
	int maxTime;
	float bombResist;
	Texture texture;
	public MBGData mbgData;
	LinkedBlockingQueue<Entity> entities;
	IntMap<MBGBulletEmitter> bulletEmitters;
	IntMap<MBGReflexBoard> reflexBoards;

	int additionalBefore, additionalAfter;

	boolean isFirst, isLast;
	public int globalTime;

	public String name;
	public MBGScene(int maxLife, int maxTime, float bombResist, Texture texture, String name, boolean isFirst, boolean isLast, FileHandle file) {
		this(maxLife, maxTime, bombResist, texture, name, isFirst, isLast, new String(file.readBytes(), Charset.forName("UTF-8")), 0, 0);
	}

	public MBGScene(int maxLife, int maxTime, float bombResist, Texture texture, String name, boolean isFirst, boolean isLast, FileHandle file, int additionalBefore, int additionalAfter) {
		this(maxLife, maxTime, bombResist, texture, name, isFirst, isLast, new String(file.readBytes(), Charset.forName("UTF-8")), additionalBefore, additionalAfter);
	}

	public MBGScene(int maxLife, int maxTime, float bombResist, Texture texture, String name, boolean isFirst, boolean isLast, String mbg, int additionalBefore, int additionalAfter) {
		this.maxLife = maxLife;
		this.maxTime = maxTime;
		this.bombResist = bombResist;
		this.texture = texture;
		this.isFirst = isFirst;
		this.isLast = isLast;
		this.additionalBefore = additionalBefore;
		this.additionalAfter = additionalAfter;
		this.name = name;
		try {
			mbgData = MBGData.parseFrom(mbg);
			bulletEmitters = new IntMap<>();
			reflexBoards = new IntMap<>();
			mAddContent();
		} catch (IOException e) {
			throw new AssertionError(e);
		}
	}

	private void mAddContent() {
		if (mbgData.layer1 != null) {
			for (BulletEmitter bulletEmitter : mbgData.layer1.BulletEmitters)
				bulletEmitters.put(bulletEmitter.ID, new MBGBulletEmitter(bulletEmitter, this, FightScreen.instance.groupNormal));
			for (ReflexBoard reflexBoard : mbgData.layer1.ReflexBoards)
				reflexBoards.put(reflexBoard.ID, new MBGReflexBoard(reflexBoard, this, FightScreen.instance.groupNormal));
		}
		if (mbgData.layer2 != null) {
			for (BulletEmitter bulletEmitter : mbgData.layer2.BulletEmitters)
				bulletEmitters.put(bulletEmitter.ID, new MBGBulletEmitter(bulletEmitter, this, FightScreen.instance.groupNormal));
			for (ReflexBoard reflexBoard : mbgData.layer2.ReflexBoards)
				reflexBoards.put(reflexBoard.ID, new MBGReflexBoard(reflexBoard, this, FightScreen.instance.groupNormal));
		}
		if (mbgData.layer3 != null) {
			for (BulletEmitter bulletEmitter : mbgData.layer3.BulletEmitters)
				bulletEmitters.put(bulletEmitter.ID, new MBGBulletEmitter(bulletEmitter, this, FightScreen.instance.groupNormal));
			for (ReflexBoard reflexBoard : mbgData.layer3.ReflexBoards)
				reflexBoards.put(reflexBoard.ID, new MBGReflexBoard(reflexBoard, this, FightScreen.instance.groupNormal));
		}
		if (mbgData.layer4 != null) {
			for (BulletEmitter bulletEmitter : mbgData.layer4.BulletEmitters)
				bulletEmitters.put(bulletEmitter.ID, new MBGBulletEmitter(bulletEmitter, this, FightScreen.instance.groupNormal));
			for (ReflexBoard reflexBoard : mbgData.layer4.ReflexBoards)
				reflexBoards.put(reflexBoard.ID, new MBGReflexBoard(reflexBoard, this, FightScreen.instance.groupNormal));
		}
	}

	@Override
	public int getMaxLife() {
		return maxLife;
	}

	@Override
	public int getMaxTime() {
		return maxTime;
	}

	@Override
	public float getBombResist() {
		return bombResist;
	}

	@Override
	public Texture getTexture() {
		return texture;
	}

	@Override
	public boolean isFirst() {
		return isFirst;
	}

	@Override
	public boolean isLast() {
		return isLast;
	}

	@Override
	public void start() {
		globalTime = 0;
		entities = new LinkedBlockingQueue<>();

		for (IntMap.Entry<MBGBulletEmitter> emitter:bulletEmitters) {
			Entity em = Entity.Create();
			em.AddComponent(emitter.value);
			entities.add(em);
		}
		
		for (IntMap.Entry<MBGReflexBoard> board:reflexBoards) {
			Entity em = Entity.Create();
			em.AddComponent(board.value);
			entities.add(em);
		}
	}

	@Override
	public void act() {
		globalTime++;
		if (globalTime > mbgData.totalFrame) {
			end();
			mAddContent();
			start();
		}
	}

	@Override
	public void end() {
		while (!entities.isEmpty()) {
			entities.remove().Destroy();
		}
		bulletEmitters.clear();
		reflexBoards.clear();
	}

	@Override
	public int sleepTimeBeforeSpell() {
		return super.sleepTimeBeforeSpell() + additionalBefore;
	}

	@Override
	public int sleepTimeAfterSpell() {
		return super.sleepTimeAfterSpell() + additionalAfter;
	}

	@Override
	public String getName() {
		return name == null ? super.getName() : name;
	}
}
