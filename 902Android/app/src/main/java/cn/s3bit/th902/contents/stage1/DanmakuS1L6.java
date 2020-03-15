package cn.s3bit.th902.contents.stage1;

import cn.s3bit.th902.gamecontents.*;
import cn.s3bit.th902.gamecontents.components.*;
import cn.s3bit.th902.gamecontents.components.ai.*;
import cn.s3bit.th902.gamecontents.components.enemy.*;
import cn.s3bit.th902.utils.*;
import com.badlogic.gdx.math.*;
import java.util.concurrent.*;

public class DanmakuS1L6 extends DanmakuScene {
	static Entity[] sprites = {null, null};
	@Override
	public void Initialize(Entity entity) {
		Callable<Object> pause = new Callable<Object>(){

			@Override
			public Object call() throws Exception {
				return null;
			}
		};
		yield.append(pause, 120);
		yield.append(new Runnable(){

				@Override
				public void run() {
					for (int i=0; i <= 1; i++) {
						Entity sprite = BaseSprite.Create(new Vector2(135 + i * 300, 730), i, 300 + i * 300);
						sprites[i] = sprite;
						final Transform tr = sprite.GetComponent(Transform.class);
						tr.scale.set(0.5f, 0.5f);
						sprite.AddComponent(new LambdaComponent(new Runnable(){

													@Override
													public void run() {
														for (int angle=RandomPool.get(1).random(360), k=0; k < 24; angle += 15, k++)
															ProjectileFactory.Create(tr.position.cpy(),
																					 BulletType.FormCircleLightM,
																					 BulletType.ColorYellow,
																					 new EnemyJudgeCircle(13),
																					 new MoveBasic(new Vector2(7.2f, 0).rotate(angle)));
													}
												}, 40, 30));
						sprite.AddComponent(new MoveFunction(MoveFunctionTarget.VELOCITY, MoveFunctionType.ASSIGNMENT, new IMoveFunction(){

													@Override
													public Vector2 getTargetVal(int time) {
														return IMoveFunction.vct2_tmp1.set(0, time < 30 ? -8 : 0);
													}
												}));
					}
				}
			});
		yield.append(pause, 900);
	}
}
