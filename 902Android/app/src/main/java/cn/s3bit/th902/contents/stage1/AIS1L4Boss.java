package cn.s3bit.th902.contents.stage1;

import cn.s3bit.th902.gamecontents.*;
import cn.s3bit.th902.gamecontents.components.*;
import cn.s3bit.th902.gamecontents.components.ai.*;
import cn.s3bit.th902.gamecontents.components.enemy.*;
import cn.s3bit.th902.utils.*;
import com.badlogic.gdx.math.*;

public class AIS1L4Boss extends Component {
	Transform transform;
	BossHP bossHp;
	@Override
	public void Initialize(Entity entity) {
		transform = entity.GetComponent(Transform.class);
		bossHp = entity.GetComponent(BossHP.class);
	}

	public int batch = 0;
	public int existTime = 0;
	Vector2 vct2_tmp = new Vector2();
	@Override
	public void Update() {
		existTime++;
		switch (bossHp.current) {
			case 0:
				part1();
				break;
			case 1:
				part2();
				break;
			case 2:
				part3();
				break;
			default:
				break;
		}
	}

	public void part1() {
		if (existTime % 10 == 0) {
			batch++;
			int co = batch;
			for (int i = batch * 17; i < batch * 17 + 360; i += 20) {
				co++;
				float size = Math.abs(MathUtils.cosDeg(i * 2.5f)) * 0.75f + 0.2f;
				Entity proj = ProjectileFactory.CreateSpecialBullet(transform.position.cpy(), co % 2 == 0 ? 225 : 226);
				proj.AddComponent(new MoveBasic(vct2_tmp.set(8, 0).rotate(i)));
				proj.GetComponent(Transform.class).scale.scl(size);
				proj.AddComponent(new EnemyJudgeCircle(15 * size));
			}
		}
	}

	public void part2() {
		if (existTime % 10 == 0) {
			batch++;
			for (int i = batch * 17; i < batch * 17 + 360; i += 30) {
				float size = Math.abs(MathUtils.cosDeg(i * 2.5f)) * 0.75f + 0.2f;
				Entity proj = ProjectileFactory.CreateSpecialBullet(transform.position.cpy(), 225);
				proj.AddComponent(new MoveBasic(vct2_tmp.set(8, 0).rotate(i)));
				proj.GetComponent(Transform.class).scale.scl(size);
				proj.AddComponent(new EnemyJudgeCircle(15 * size));
			}
			for (int i = -batch * 17; i < -batch * 17 + 360; i += 60) {
				float size = 0.5f;
				Entity proj = ProjectileFactory.CreateSpecialBullet(transform.position.cpy(), 226);
				proj.AddComponent(new MoveBasic(vct2_tmp.set(5, 0).rotate(i)));
				proj.GetComponent(Transform.class).scale.scl(size);
				proj.AddComponent(new EnemyJudgeCircle(15 * size));
			}
		}
	}

	public void part3() {
		Entity proj = ProjectileFactory.Create(new Vector2(RandomPool.get(1).random(0, 560), 720), BulletType.FormCircleLightM, RandomPool.get(1).randomBoolean() ? BulletType.ColorOrange : BulletType.ColorBlue);
		final Transform ptransform = proj.GetComponent(Transform.class);
		proj.AddComponent(new MoveFunction(MoveFunctionTarget.ACCEL, MoveFunctionType.ASSIGNMENT, new IMoveFunction() {
								  int flag = 2;
								  int bounce = RandomPool.get(1).random(250, 500);
								  @Override
								  public Vector2 getTargetVal(int time) {
									  if (flag > 0 && Math.abs(ptransform.position.y - bounce) < 30) {
										  final int m = flag * 8;
										  flag--;
										  bounce -= 90;
										  return vct2_tmp1.set(0.001f, m);
									  }
									  return vct2_tmp1.set(0.001f, -0.1f);
								  }
							  }));
		proj.AddComponent(new EnemyJudgeCircle(15));
	}
}
