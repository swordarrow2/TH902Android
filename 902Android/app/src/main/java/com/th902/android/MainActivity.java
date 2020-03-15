package com.th902.android;

import android.os.*;
import cn.s3bit.th902.*;
import com.badlogic.gdx.backends.android.*;

public class MainActivity extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration aac=new AndroidApplicationConfiguration();
        aac.hideStatusBar = true;
		initialize(new GameMain(), aac);
    }
}
