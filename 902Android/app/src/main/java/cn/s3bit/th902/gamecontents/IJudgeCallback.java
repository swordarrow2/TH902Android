package cn.s3bit.th902.gamecontents;

public interface IJudgeCallback {
	public static final IJudgeCallback NONE = new IJudgeCallback() {

		@Override
		public float getDamage() {
			return 1;
		}

		@Override
		public boolean canHurt() {
			return false;
		}

		@Override
		public void onHurt(float damage) {
			return;
		}

		@Override
		public void onCollide() {
			return;
		}

		@Override
		public float getBombResist() {
			return 1f;
		}
	};
	public float getDamage();
	public boolean canHurt();
	public void onHurt(float damage);
	public void onCollide();
	public float getBombResist();
}
