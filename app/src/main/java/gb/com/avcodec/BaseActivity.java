package gb.com.avcodec;

import android.app.Activity;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import gb.com.avcodec.modle.AnyEvent;
import gb.com.avcodec.modle.OnEventMessage;


public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
     }


//
//    /**
//     * 使用onEventMainThread来接收事件，那么不论分发事件在哪个线程运行，接收事件永远在UI线程执行
//     * 可以用来更新UI
//     * @param event
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventMainThread(AnyEvent event) {
//       //  System.out.println("------>>>MAIN "+Thread.currentThread().getId()+"msg-> "+event.getDiscribe());
//
//    }
//
//    /**
//     * 使用onEventBackgroundThread来接收事件，如果分发事件在子线程运行，那么接收事件直接在同样线程
//     * 运行，如果分发事件在UI线程，那么会启动一个子线程运行接收事件
//     * @param event
//     */
//    @Subscribe(threadMode = ThreadMode.BACKGROUND)
//    public void onEventBackgroundThread(AnyEvent event) {
//
//       // System.out.println("------>>>BACKGROUND "+Thread.currentThread().getId()+"msg-> "+event.getDiscribe());
//    }
//
//
//    /**
//     * 使用onEventBackgroundThread来接收事件，如果分发事件在子线程运行，那么接收事件直接在同样线程
//     * 运行，如果分发事件在UI线程，那么会启动一个子线程运行接收事件
//     * @param event
//     */
//    @Subscribe(threadMode = ThreadMode.BACKGROUND)
//    public void onEventBackgroundThread2(OnEventMessage event) {
//
//     }
//

//    /**
//     * 使用onEventAsync接收事件，无论分发事件在（UI或者子线程）哪个线程执行，接收都会在另外一个子线程执行
//     * @param event
//     */
//    @Subscribe(threadMode = ThreadMode.ASYNC)
//    public void onEventAsync(AnyEvent event) {
//     }
//    @Subscribe(threadMode = ThreadMode.POSTING)
//    public void onEventPost(AnyEvent event){
//     }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }






    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoStartMainThread(AnyEvent event) {

        System.out.println("--------->>>"+event.getDiscribe());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoStopMainThread(OnEventMessage event) {
        System.out.println("--------->>>"+event.getMsg());
    }


//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAudioStartMainThread(AnyEvent event) {
//
//    }
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAudioStopMainThread(AnyEvent event) {
//
//    }
}
