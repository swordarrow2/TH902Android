package cn.s3bit.th902.contents.stage1;

import cn.s3bit.th902.*;
import cn.s3bit.th902.gamecontents.*;
import cn.s3bit.th902.gamecontents.components.*;
import cn.s3bit.th902.gamecontents.components.ai.*;
import cn.s3bit.th902.gamecontents.components.enemy.*;
import com.badlogic.gdx.math.*;
import com.th902.android.*;
import java.util.concurrent.*;

public class DanmakuS1L4 extends DanmakuScene {

	@Override
	public void Initialize(Entity entity) {
		yield.append(new EmptyRunnable(), 300);
		yield.append(new Runnable(){

				@Override
				public void run() {
					Entity boss = Entity.Create();
					final Transform transform = new Transform(new Vector2(285, 730), new Vector2(2, 2));
					boss.AddComponent(transform);
					boss.AddComponent(new ImageRenderer(ResourceManager.barrages.get(230), 0).attachToGroup(FightScreen.instance.groupNormal));
					BossHP hp = new BossHP(
						new int[]{1800, 1800, 3600},
						new int[]{1200, 1200, 2400},
						new float[] {1f, 1f, 1.6f},
						new String[]{null, null, "动符「弹跳」"},
						new Runnable[]{null, null, null},
						new Runnable[]{null, null, new Runnable(){

								@Override
								public void run() {
									DropItem.CreateDropItem(transform.position.cpy(), 241);
								}
							} });
					boss.AddComponent(hp);
					boss.AddComponent(new EnemyJudgeCircle(54, hp));
					boss.AddComponent(new EnemyChaseable(hp));
					boss.AddComponent(new MoveFunction(MoveFunctionTarget.VELOCITY, MoveFunctionType.ASSIGNMENT, new IMoveFunction(){

											  @Override
											  public Vector2 getTargetVal(int time) {
												  return IMoveFunction.vct2_tmp1.set(0, time < 40 ? -4f : 0);
											  }
										  }));
					boss.AddComponent(new AIS1L4Boss());
				}
			});
	}

	int countdown = 240;
	@Override
	public void Update() {
		yield.yield();
		if (yield.isFinished()) {
			yield.append(new Callable(){

					@Override
					public Object call() throws Exception {
						return null;
					}
				}, 1048576);
			countdown--;
		}
		if (countdown < 240 && !EnemySpellInfoSystem.isActive()) {
			if (countdown > 0) {
				countdown--;
			} else {
				Kill();
				FightScreen.sceneSystem.nextScene();
			}
		}
	}
}
