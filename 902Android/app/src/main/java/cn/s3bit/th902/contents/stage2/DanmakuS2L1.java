package cn.s3bit.th902.contents.stage2;

import cn.s3bit.th902.*;
import cn.s3bit.th902.gamecontents.*;
import cn.s3bit.th902.gamecontents.components.*;
import cn.s3bit.th902.gamecontents.components.ai.*;
import cn.s3bit.th902.gamecontents.components.enemy.*;
import com.badlogic.gdx.math.*;
import com.th902.android.*;

public final class DanmakuS2L1 extends DanmakuScene {
	private final int n1 = 1;
	private final int n2 = -1;
	int x1 = 167;
	int x2 = 424;

	@Override
	public void Initialize(Entity entity) {
		enemyA(n1, x1);
		waitEnemy(30);
		enemyA(n1, x1);
		waitEnemy(30);
		enemyA(n1, x1);
		waitEnemy(30);
		enemyA(n1, x1);

		waitEnemy(15);
		enemyB(n1, x2);
		waitEnemy(15);
		enemyA(n1, x1);
		waitEnemy(15);
		enemyB(n1, x2);
		waitEnemy(15);
		enemyA(n1, x1);
		waitEnemy(15);

		enemyB(n1, x2);
		waitEnemy(30);
		enemyB(n1, x2);
		waitEnemy(30);
		enemyB(n1, x2);
		waitEnemy(30);
		enemyB(n1, x2);
		waitEnemy(90);

		enemyA(n2, x2);
		waitEnemy(30);
		enemyA(n2, x2);
		waitEnemy(30);
		enemyA(n2, x2);
		waitEnemy(30);
		enemyA(n2, x2);

		waitEnemy(15);
		enemyB(n2, x1);
		waitEnemy(15);
		enemyA(n2, x2);
		waitEnemy(15);
		enemyB(n2, x1);
		waitEnemy(15);
		enemyA(n2, x2);
		waitEnemy(15);

		waitEnemy(60);

		enemyB(n2, x1);
		waitEnemy(30);
		enemyB(n2, x1);
		waitEnemy(30);
		enemyB(n2, x1);
		waitEnemy(30);
		enemyB(n2, x1);
	}

	private void enemyA(int n, final int x) {
		yield.append(new Runnable(){

				@Override
				public void run() {
					Entity sprite = BaseSprite.Create(new Vector2(x, FightScreen.TOP), 1, 20);
					final Transform transform = sprite.GetComponent(Transform.class);
					sprite.AddComponent(new MoveBasic(0, -4f, 0, 0));
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

	private void enemyB(final int n, final int x) {
		yield.append(new Runnable(){

				@Override
				public void run() {
					Entity sprite = BaseSprite.Create(new Vector2(x, FightScreen.TOP), 0, 20);
					final Transform transform = sprite.GetComponent(Transform.class);
					sprite.AddComponent(new MoveFunction(MoveFunctionTarget.VELOCITY, MoveFunctionType.ASSIGNMENT, new IMoveFunction(){

												@Override
												public Vector2 getTargetVal(int time) {
													if (time < 90) {
														return IMoveFunction.vct2_tmp1.set(0, -4);
													} else {
														return IMoveFunction.vct2_tmp1.set(-4 * n, 0);
													}
												}
											}));
					sprite.AddComponent(new LambdaComponent(new Runnable(){

												@Override
												public void run() {
													ProjectileFactory.Create(transform.position.cpy(), BulletType.FormCircleS, BulletType.ColorBlue, new MoveSnipe(3f), new EnemyJudgeCircle(6));
												}
											}, 20));
					sprite.AddComponent(new ExtraDrop() {
							@Override
							public void LootLogic() {
								DropItem.CreateDropItem(transform.position.cpy(), 242);
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
