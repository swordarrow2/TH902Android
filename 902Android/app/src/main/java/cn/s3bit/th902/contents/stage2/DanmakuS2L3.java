package cn.s3bit.th902.contents.stage2;

import cn.s3bit.th902.*;
import cn.s3bit.th902.gamecontents.*;
import cn.s3bit.th902.gamecontents.components.*;
import cn.s3bit.th902.gamecontents.components.ai.*;
import cn.s3bit.th902.gamecontents.components.enemy.*;
import cn.s3bit.th902.utils.*;
import com.badlogic.gdx.math.*;
import com.th902.android.*;

public class DanmakuS2L3 extends DanmakuScene {

	int danmakuTime = 427;
	float cache = 30f;

	@Override
	public void Initialize(Entity entity) {
		enemy(0);
		waitEnemy(1000);
		enemy(1);
		waitEnemy(1000);
		enemy(2);
		waitEnemy(1000);
		enemy(3);
		waitEnemy(1000);
	}

	private void enemy(final int color) {
		yield.append(new Runnable(){

				@Override
				public void run() {
					final BulletType bt;
					Entity sprite = BaseSprite.Create(new Vector2(290, FightScreen.TOP), color, 1400);
					final Transform transform = sprite.GetComponent(Transform.class);
					sprite.AddComponent(new MoveFunction(MoveFunctionTarget.VELOCITY, MoveFunctionType.ASSIGNMENT, new IMoveFunction(){

												@Override
												public Vector2 getTargetVal(int time) {
													if (time < 60) {
														switch (color) {
															case 0:
																return IMoveFunction.vct2_tmp1.set(0, -2);
															case 1:
																return IMoveFunction.vct2_tmp1.set(0, -3);
															case 2:
																return IMoveFunction.vct2_tmp1.set(0, -4);
															case 3:
																return IMoveFunction.vct2_tmp1.set(0, -5);
															default:
																return IMoveFunction.vct2_tmp1.set(0, 0);
														}
													} else {
														return IMoveFunction.vct2_tmp1.set(0, 0);
													}
												}
											}));
					switch (color) {
						case 0:
							bt = BulletType.ColorBlue;
							break;
						case 1:
							bt = BulletType.ColorRed;
							break;
						case 2:
							bt = BulletType.ColorGreen;
							break;
						case 3:
							bt = BulletType.ColorYellow;
							break;
						default:
							bt = BulletType.ColorBlue;
							break;
					}
					sprite.AddComponent(new LambdaComponent(new Runnable(){

												@Override
												public void run() {
													if (RandomPool.get(1).random(32767) % 4 + 1 <= FightScreen._difficulty) {
														Vector2 vector2 = new Vector2(2 + 1.2f * FightScreen._difficulty, 0);
														ProjectileFactory.Create(transform.position.cpy(), BulletType.FormCircleS, bt, new MoveBasic(vector2.cpy().rotate(danmakuTime * danmakuTime / cache)), new EnemyJudgeCircle(6));
														ProjectileFactory.Create(transform.position.cpy(), BulletType.FormCircleS, bt, new MoveBasic(vector2.cpy().rotate(danmakuTime * danmakuTime / cache + 180)), new EnemyJudgeCircle(6));
														ProjectileFactory.Create(transform.position.cpy(), BulletType.FormCircleS, bt, new MoveBasic(vector2.cpy().rotate(-(danmakuTime * danmakuTime / cache))), new EnemyJudgeCircle(6));
														ProjectileFactory.Create(transform.position.cpy(), BulletType.FormCircleS, bt, new MoveBasic(vector2.cpy().rotate(-(danmakuTime * danmakuTime / cache + 180))), new EnemyJudgeCircle(6));
													}
													danmakuTime++;
													if (danmakuTime > 1200) {
														danmakuTime = 420;
													}
												}
											}, 60, 1));
					sprite.AddComponent(new ExtraDrop() {
							@Override
							public void LootLogic() {
								DropItem.CreateDropItem(transform.position.cpy(), 241);
							}
						});
				}
			});
	}

	private void waitEnemy(int frames) {
		yield.append(new EmptyRunnable(), frames * 2 / 3);
	}
}
