package com.duapps.f.commonlib.stub;

import android.app.Activity;

public abstract class StubActivity extends Activity {
    public static class Stub1 extends StubActivity {//注意此处必须是静态的,否则会出现错误
    }
}
