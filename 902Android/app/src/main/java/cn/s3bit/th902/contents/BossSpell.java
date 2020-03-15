package cn.s3bit.th902.contents;

import cn.s3bit.th902.*;
import cn.s3bit.th902.gamecontents.*;
import cn.s3bit.th902.gamecontents.components.*;
import cn.s3bit.th902.gamecontents.components.ai.*;
import cn.s3bit.th902.gamecontents.components.enemy.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;
import java.util.concurrent.*;

public abstract class BossSpell extends DanmakuScene {
	public int existTime;
	protected Entity boss;
	protected Transform transform;
	protected SpellHP hp;
	@Override
	public void Initialize(Entity entity) {
		yield.append(new Callable(){

				@Override
				public Object call() throws Exception {
					return null;
				}
			});
		existTime = 0;
		//mCountDownBeforeSpell = sleepTimeBeforeSpell();
		mCountDownAfterSpell = sleepTimeAfterSpell();
		mIsEnded = false;
		if (isFirst()) {
			boss = Entity.Create();
			transform = new Transform(getInitialPosition(), new Vector2(2, 2));
			boss.AddComponent(transform);
			boss.AddComponent(new ImageRenderer(getTexture(), 0).attachToGroup(FightScreen.instance.groupNormal));
			hp = new SpellHP(getMaxLife(), getMaxTime(), getBombResist(), getName());
			boss.AddComponent(hp);
			boss.AddComponent(new EnemyJudgeCircle(Math.min(getTexture().getHeight(), getTexture().getWidth()) * 0.9f, hp));
			boss.AddComponent(new EnemyChaseable(hp));
			if (isDefaultEntering())
				boss.AddComponent(new MoveFunction(MoveFunctionTarget.VELOCITY, MoveFunctionType.ASSIGNMENT, new IMoveFunction(){

										  @Override
										  public Vector2 getTargetVal(int time) {
											  return IMoveFunction.vct2_tmp1.set(0, time < 40 ? -4f : 0);
										  }
									  }));
			EnemySpellInfoSystem.activate(boss);
		} else {
			boss = EnemySpellInfoSystem.central;
			transform = boss.GetComponent(Transform.class);
			transform = new Transform(transform.position, transform.rotation, transform.scale);
			boss.Destroy();

			boss = Entity.Create();
			boss.AddComponent(transform);
			boss.AddComponent(new ImageRenderer(getTexture(), 0).attachToGroup(FightScreen.instance.groupNormal));
			hp = new SpellHP(getMaxLife(), getMaxTime(), getBombResist(), getName());
			boss.AddComponent(hp);
			boss.AddComponent(new EnemyJudgeCircle(Math.min(getTexture().getHeight(), getTexture().getWidth()) * 0.9f, hp));
			boss.AddComponent(new EnemyChaseable(hp));
			EnemySpellInfoSystem.activate(boss);
		}
		THSoundEffects.Cat.sound.play();
		start();
	}

	//private int mCountDownBeforeSpell;
	private int mCountDownAfterSpell;
	private boolean mIsEnded;
	@Override
	public void Update() {
		/*if (mCountDownBeforeSpell > 0) {
		 mCountDownBeforeSpell--;
		 }
		 else */
		if (mIsEnded) {
			if (mCountDownAfterSpell > 0)
				mCountDownAfterSpell--;
			else
				Kill();
		} else {
			act();
			existTime++;
		}
		if (!mIsEnded && (existTime >= getMaxTime() || hp.hp <= 0)) {
			mIsEnded = true;
			end();
			if (existTime < getMaxTime())
				shootdown();
			hp.hp = 0;
			hp.Kill();
			EnemySpellInfoSystem.deactivate();
			GameHelper.clearEnemyBullets();
		}
		if (isDefaultEntering() && existTime == 40 && isFirst()) {
			boss.GetComponent(MoveFunction.class).Kill();
		}
	}

	@Override
	public void Kill() {
		if (isLast()) {
			EnemySpellInfoSystem.deactivate();
			boss.Destroy();
			THSoundEffects.Enep1.sound.play();
		}
		yield.yield();
		super.Kill();
	}

	public boolean isFirst() {
		return false;
	}
	public boolean isDefaultEntering() {
		return true;
	}
	public Vector2 getInitialPosition() {
		return new Vector2(285, 730);
	}
	public boolean isLast() {
		return false;
	}
	public String getName() {
		return "";
	}

	public abstract int getMaxLife();
	public abstract int getMaxTime();
	public abstract float getBombResist();
	public abstract Texture getTexture();

	public int sleepTimeBeforeSpell() {
		return isFirst() ? 300 : 0;
	}
	public int sleepTimeAfterSpell() {
		return isLast() ? 240 : 0;
	}

	public void start() {

	}
	public void act() {

	}
	public void end() {

	}
	public void shootdown() {

	}
}
