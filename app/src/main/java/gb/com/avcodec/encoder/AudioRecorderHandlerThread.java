package gb.com.avcodec.encoder;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import gb.com.avcodec.modle.AnyEvent;
import gb.com.avcodec.modle.OnEventMessage;
import gb.com.avcodec.util.Setup;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by abrain on 10/24/16.
 */
public class AudioRecorderHandlerThread extends HandlerThread implements Handler.Callback{

    private static final String TAG = AudioRecorderHandlerThread.class.getSimpleName();

    /* Handler associated with this HandlerThread*/
    private Handler mHandler;

    /* Reference to a handler from the thread that started this HandlerThread */
    private Handler mCallback;

    private static final int MSG_RECORDING_START = 100;
    private static final int MSG_RECORDING_STOP = 101;

    /* AudioRecord object to record audio from microphone input */
    private AudioRecorder audioRecorder;

    /* AudioEncoder object to take recorded ByteBuffer from the AudioRecord object*/
    private AudioEncoder audioEncoder;

    /* MediaMuxerWrapper object to add encoded data to a MediaMuxer which converts it to .mp4*/



    @SuppressLint("NewApi")
    public AudioRecorderHandlerThread(String name, int priority) {
        super(name, priority);

        checkNotNull(Setup.mediaMuxerWrapper);
        audioEncoder = new AudioEncoder(Setup.mediaMuxerWrapper);
        audioRecorder = new AudioRecorder(audioEncoder);
    }

    public void setCallback(Handler cb){
        mCallback = cb;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler = new Handler(getLooper(), this);
    }

    @Override
    public boolean handleMessage(Message message) {
        switch(message.what){
            case MSG_RECORDING_START:
                Log.d(TAG,  "recording start message received");
          //      mCallback.sendMessage(Message.obtain(null, Messages.MSG_RECORDING_START_CALLBACK));
                audioRecorder.start();
                audioEncoder.start();
                audioRecorder.record();
                break;
            case MSG_RECORDING_STOP:
                Log.d(TAG,  "recording stop message received");
              //  mCallback.sendMessage(Message.obtain(null, Messages.MSG_RECORDING_STOP_CALLBACK));
                audioRecorder.stopRecording();
              //  audioEncoder.stop();
                break;
        }
        return true;
    }

    public void startRecording(){
        EventBus.getDefault().post(new AnyEvent("开始录制视频"));
        Message msg = Message.obtain(null, MSG_RECORDING_START);
        mHandler.sendMessage(msg);
    }

    public void stopRecording(){
        Log.d(TAG, "here");
        EventBus.getDefault().post(new OnEventMessage("停止录制视频"));
        audioRecorder.setIsRecordingFalse();
        Message msg = Message.obtain(null, MSG_RECORDING_STOP);
        mHandler.sendMessage(msg);
    }
}
