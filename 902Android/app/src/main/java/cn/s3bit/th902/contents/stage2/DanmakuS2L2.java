package cn.s3bit.th902.contents.stage2;

import cn.s3bit.th902.*;
import cn.s3bit.th902.gamecontents.*;
import cn.s3bit.th902.gamecontents.components.*;
import cn.s3bit.th902.gamecontents.components.ai.*;
import cn.s3bit.th902.gamecontents.components.enemy.*;
import com.badlogic.gdx.math.*;
import com.th902.android.*;

public class DanmakuS2L2 extends DanmakuScene {
	int x1 = 167;
	int x2 = 424;

	@Override
	public void Initialize(Entity entity) {
		enemyA(x1);
		enemyA(x2);
		waitEnemy(180);
		enemyA(x1);
		enemyA(x2);
		waitEnemy(180);
		enemyA(x1);
		enemyA(x2);
		waitEnemy(180);
		enemyA(x1);
		enemyA(x2);
		waitEnemy(600);
	}

	private void enemyA(final int x) {
		yield.append(new Runnable(){

				@Override
				public void run() {
					Entity sprite = BaseSprite.Create(new Vector2(x, FightScreen.TOP), 1, 20);
					final Transform transform = sprite.GetComponent(Transform.class);
					final Vector2 playerPosition = JudgingSystem.playerJudge.cpy().sub(transform.position).nor().scl(3f);
					sprite.AddComponent(new MoveFunction(MoveFunctionTarget.VELOCITY, MoveFunctionType.ASSIGNMENT, new IMoveFunction(){

												@Override
												public Vector2 getTargetVal(int time) {
													if (time < 60) {
														return IMoveFunction.vct2_tmp1.set(0, -3);
													} else if (time < 90) {
														return IMoveFunction.vct2_tmp1.set(0, 0);
													} else {
														return IMoveFunction.vct2_tmp1.set(playerPosition);
													}
												}
											}));
					sprite.AddComponent(new LambdaComponent(new Runnable(){

												@Override
												public void run() {
													ProjectileFactory.Create(transform.position.cpy(), BulletType.FormCircleS, BulletType.ColorRed, new MoveSnipe(3f), new EnemyJudgeCircle(6));
												}
											}, 20));
					sprite.AddComponent(new ExtraDrop() {
							@Override
							public void LootLogic() {
								DropItem.CreateDropItem(transform.position.cpy(), 241);
								Entity proj = ProjectileFactory.Create(transform.position.cpy(), BulletType.FormCircleS, BulletType.ColorRed);
								proj.AddComponent(new MoveSnipe(9f));
								proj.AddComponent(new EnemyJudgeCircle(6));
							}
						});
				}
			});
	}

	private void waitEnemy(int frames) {
		yield.append(new EmptyRunnable(), frames * 2 / 3);
	}
}
