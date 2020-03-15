package cn.s3bit.th902;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import cn.s3bit.th902.gamecontents.components.player.*;

public class PlayerInputProcessor extends InputAdapter {
    private Vector2 vct2_downPosPlayer = new Vector2();
    private Vector2 vct2_downPosStage = new Vector2();
    private Vector2 vct2_tmp1 = new Vector2();

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (pointer == 0) {
            vct2_downPosStage = FightScreen.instance.stage.screenToStageCoordinates(vct2_downPosStage.set(screenX, screenY));
            vct2_downPosPlayer.set(Player.instance.transform.position);
        }
        if (pointer == 1) {
            Player.instance.slow = true;
        }
		if (pointer == 2) {
			Player.instance.bombPress = true;
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer == 1) {
            Player.instance.slow = false;
        }
		if (pointer == 2) {
			Player.instance.bombPress = false;
        }
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (pointer == 0) {
            vct2_tmp1 = FightScreen.instance.stage.screenToStageCoordinates(vct2_tmp1.set(screenX, screenY));
            Player.instance.transform.position.set(vct2_downPosPlayer).add(vct2_tmp1.sub(vct2_downPosStage));
        }
        return super.touchDragged(screenX, screenY, pointer);
    }
}
