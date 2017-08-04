package gb.com.avcodec.app;

import android.app.Application;

import gb.com.avcodec.util.CrashHandler;

/**
 * =====================================================================================
 * <p/>
 * 版权：深圳国保警用装备制造有限公司，版权所有(c)2017
 * <p/>
 * 作者：Administrator on 2017/6/24 11:55
 * <p/>
 * 邮箱：xjs250@163.com
 * <p/>
 * 创建日期：2017/6/24 11:55
 * <p/>
 * 描述：
 * =====================================================================================
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

    }
}
