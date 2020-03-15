package cn.s3bit.th902.contents;

import cn.s3bit.th902.*;
import cn.s3bit.th902.gamecontents.*;
import cn.s3bit.th902.gamecontents.components.*;
import cn.s3bit.th902.gamecontents.components.ai.*;
import cn.s3bit.th902.gamecontents.components.enemy.*;
import com.badlogic.gdx.math.*;
import com.th902.android.*;

public final class ExampleDanmakuScene extends DanmakuScene {
	@Override
	public void Initialize(Entity entity) {
		yield.append(new EmptyRunnable(), 20); //Wait For 20 Frames.
		yield.append(new Runnable(){

				@Override
				public void run() {
					final Entity sprite = BaseSprite.Create(new Vector2(FightScreen.TOP / 2, FightScreen.RIGHT / 2), 0);
					final Transform transform = sprite.GetComponent(Transform.class);
					sprite.AddComponent(new ExtraDrop() {
							@Override
							public void LootLogic() {
								DropItem.CreateDropItem(transform.position.cpy(), DropItem.TypePower);
							}
						});
					sprite.AddComponent(new MoveFunction(
											MoveFunctionTarget.POSITION,
											MoveFunctionType.ASSIGNMENT,
											new IMoveFunction(){

												@Override
												public Vector2 getTargetVal(int time) {
													return IMoveFunction.vct2_tmp1.set(0, 0).rotate(time * 2).add(300, 400);
												}
											}));
					sprite.AddComponent(new LambdaComponent(new Runnable(){

												@Override
												public void run() {
													ProjectileFactory.Create(transform.position.cpy(), BulletType.FormCircleS, BulletType.ColorBlue, new MoveSnipe(3f), new EnemyJudgeCircle(6));
												}
											}, 30));
				}
			});
		yield.append(new EmptyRunnable(), 20);
	}
}
