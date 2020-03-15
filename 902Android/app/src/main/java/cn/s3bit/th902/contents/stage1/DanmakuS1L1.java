package cn.s3bit.th902.contents.stage1;

import cn.s3bit.th902.gamecontents.*;
import cn.s3bit.th902.gamecontents.components.*;
import cn.s3bit.th902.gamecontents.components.ai.*;
import cn.s3bit.th902.gamecontents.components.enemy.*;
import cn.s3bit.th902.utils.*;
import com.badlogic.gdx.math.*;
import com.th902.android.*;

public class DanmakuS1L1 extends DanmakuScene {
	static int count = 0;
	@Override
	public void Initialize(Entity entity) {
		count = 0;
		for (int i=0; i < 36; i++) {
			//center 285, 368
			yield.append(new EmptyRunnable(), 22);
			yield.append(new Runnable(){

					@Override
					public void run() {
						Entity sprite = BaseSprite.Create(new Vector2(20, 730), 3, 20);
						final Transform transform1 = sprite.GetComponent(Transform.class);
						sprite.AddComponent(new MoveBasic(0.1f, -2f, RandomPool.get(1).random(0f, 0.05f), RandomPool.get(1).random(-0.001f, 0)));

						sprite.AddComponent(new ExtraDrop() {
								@Override
								public void LootLogic() {
									count++;
									if (count % 4 == 0)
										DropItem.CreateDropItem(transform1.position.cpy(), 241);
									else if (count % 3 == 0)
										DropItem.CreateDropItem(transform1.position.cpy(), 242);
									Entity proj = ProjectileFactory.Create(transform1.position.cpy(), BulletType.FormCircleS, BulletType.ColorRed);
									proj.AddComponent(new MoveSnipe(9f));
									proj.AddComponent(new EnemyJudgeCircle(6));
								}
							});

						sprite = BaseSprite.Create(new Vector2(560, 730), 3, 20);
						final Transform transform2 = sprite.GetComponent(Transform.class);
						sprite.AddComponent(new MoveBasic(-0.1f, -2f, RandomPool.get(1).random(-0.05f, 0f), RandomPool.get(1).random(-0.001f, 0)));
						sprite.AddComponent(new ExtraDrop() {
								@Override
								public void LootLogic() {
									count++;
									if (count % 4 == 0)
										DropItem.CreateDropItem(transform2.position.cpy(), 241);
									else if (count % 3 == 0)
										DropItem.CreateDropItem(transform2.position.cpy(), 242);
									for (int j=0; j < 4; j++) {
										Entity proj = ProjectileFactory.Create(transform2.position.cpy(), BulletType.FormCircleS, BulletType.ColorRed);
										proj.AddComponent(new MoveBasic(RandomPool.get(1).random(-3f, -1f), RandomPool.get(1).random(-3f, 3f)));
										proj.AddComponent(new EnemyJudgeCircle(6));
										proj = ProjectileFactory.Create(transform2.position.cpy(), BulletType.FormCircleS, BulletType.ColorRed);
										proj.AddComponent(new MoveBasic(RandomPool.get(1).random(1f, 3f), RandomPool.get(1).random(-3f, 3f)));
										proj.AddComponent(new EnemyJudgeCircle(6));
									}
								}
							});
					}
				});
		}
		yield.append(new EmptyRunnable(), 180);
	}
}
