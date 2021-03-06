package cn.s3bit.th902.contents.stage1;

import cn.s3bit.th902.*;
import cn.s3bit.th902.gamecontents.*;
import cn.s3bit.th902.gamecontents.components.*;
import cn.s3bit.th902.gamecontents.components.ai.*;
import cn.s3bit.th902.gamecontents.components.enemy.*;
import cn.s3bit.th902.utils.*;
import com.badlogic.gdx.math.*;

public class DanmakuS1L2 extends DanmakuScene {
	static int count = 0, counter1 = 0;
	@Override
	public void Initialize(Entity entity) {
		count = 0;
		yield.append(new Runnable(){

				@Override
				public void run() {
					Entity sprite = BaseSprite.Create(new Vector2(285, 730), 1, 480);
					final Transform transform = sprite.GetComponent(Transform.class);
					sprite.AddComponent(new MoveFunction(MoveFunctionTarget.VELOCITY, MoveFunctionType.ASSIGNMENT, new IMoveFunction(){

												@Override
												public Vector2 getTargetVal(int time) {
													return IMoveFunction.vct2_tmp1.set(0, -20f / ((time + 1) / 2f));
												}
											}));
					sprite.AddComponent(new LambdaComponent(new Runnable() {
												@Override
												public void run() {
													counter1++;
													if (counter1 % 12 < 9) {
														Vector2 speed = new Vector2();
														GameHelper.snipeVct(transform.position, null, 15, speed);
														speed.nor().scl(5);
														ProjectileFactory.Create(transform.position.cpy(), BulletType.FormBulletM, BulletType.ColorPink,
																				 new EnemyJudgeCircle(5),
																				 new MoveBasic(speed));
														GameHelper.snipeVct(transform.position, null, -15, speed);
														speed.nor().scl(5);
														ProjectileFactory.Create(transform.position.cpy(), BulletType.FormBulletM, BulletType.ColorPink,
																				 new EnemyJudgeCircle(5),
																				 new MoveBasic(speed));
													}
												}
											}, 0, 7));
				}
			});
		for (int i=0; i < 16; i++) {
			//center 285, 368
			yield.append(new Runnable(){

					@Override
					public void run() {

					}
				}, 38);
			yield.append(new Runnable(){

					@Override
					public void run() {
						Entity sprite = BaseSprite.Create(new Vector2(100, 730), 3, 20);
						final Transform transform = sprite.GetComponent(Transform.class);
						sprite.AddComponent(new MoveBasic(0.1f, -2f, RandomPool.get(1).random(0f, 0.04f), RandomPool.get(1).random(-0.001f, 0)));
						sprite.AddComponent(new ExtraDrop() {
								@Override
								public void LootLogic() {
									count++;
									if (count % 4 == 0)
										DropItem.CreateDropItem(transform.position.cpy(), 241);
									else if (count % 3 == 0)
										DropItem.CreateDropItem(transform.position.cpy(), 242);
								}
							});
						sprite.AddComponent(new LambdaComponent(new Runnable(){

													@Override
													public void run() {
														ProjectileFactory.Create(transform.position.cpy(), BulletType.FormCircleS2, BulletType.ColorRed, new MoveSnipe(8f), new EnemyJudgeCircle(6));
													}	
												}, 45));
					}
				});
			yield.append(new Runnable(){

					@Override
					public void run() {
						Entity sprite = BaseSprite.Create(new Vector2(480, 730), 3, 20);
						final Transform transform = sprite.GetComponent(Transform.class);
						sprite.AddComponent(new MoveBasic(0.1f, -2f, RandomPool.get(1).random(-0.04f, 0), RandomPool.get(1).random(-0.001f, 0)));
						sprite.AddComponent(new ExtraDrop() {
								@Override
								public void LootLogic() {
									count++;
									if (count % 4 == 0)
										DropItem.CreateDropItem(transform.position.cpy(), 241);
									else if (count % 3 == 0)
										DropItem.CreateDropItem(transform.position.cpy(), 242);
								}
							});
						sprite.AddComponent(new LambdaComponent(new Runnable(){

													@Override
													public void run() {
														ProjectileFactory.Create(transform.position.cpy(), BulletType.FormCircleS2, BulletType.ColorRed, new MoveSnipe(8f), new EnemyJudgeCircle(6));
													}
												}, 45));
						
					}
				});
		}
		yield.append(new Runnable(){

				@Override
				public void run() {
				}
			}, 180);
	}
}
