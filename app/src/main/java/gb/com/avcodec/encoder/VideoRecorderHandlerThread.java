package gb.com.avcodec.encoder;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import gb.com.avcodec.util.CameraPreviewCallback;
import gb.com.avcodec.util.CameraWrapper;
import gb.com.avcodec.util.Setup;

/**
 * =====================================================================================
 * <p/>
 * 版权：深圳国保警用装备制造有限公司，版权所有(c)2017
 * <p/>
 * 作者：Administrator on 2017/6/24 14:50
 * <p/>
 * 邮箱：xjs250@163.com
 * <p/>
 * 创建日期：2017/6/24 14:50
 * <p/>
 * 描述：
 * =====================================================================================
 */
public class VideoRecorderHandlerThread extends HandlerThread implements Handler.Callback {

    private CameraPreviewCallback mCameraPreviewCallback;
    public Handler mHandler;
    private static final int MSG_RECORDING_START = 520;
    private static final int MSG_RECORDING_STOP = 521;
    private VideoEncoderFromBuffer videoEncoder;
    public VideoRecorderHandlerThread(String name, int priority) {
        super(name, priority);
      //  mCameraPreviewCallback = new CameraPreviewCallback();
        videoEncoder = new VideoEncoderFromBuffer(CameraWrapper.IMAGE_WIDTH,
                CameraWrapper.IMAGE_HEIGHT);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler = new Handler(getLooper(), this);// 使得该handler和handlerThread建立联系
    }

    @Override
    public boolean handleMessage(Message msg) {//在子线程中运行的
        switch (msg.what){
            case MSG_RECORDING_START:
//                CameraWrapper.mCamera.addCallbackBuffer(CameraWrapper.mImageCallbackBuffer);
//                CameraWrapper.mCamera.setPreviewCallbackWithBuffer(mCameraPreviewCallback);
                byte[] data = (byte[]) msg.obj;
                videoEncoder.encodeFrame(data);
                break;
            case MSG_RECORDING_STOP:
//                CameraWrapper.mCamera.setPreviewCallbackWithBuffer(null);
//                mCameraPreviewCallback.close();
//                Setup.mediaMuxerWrapper.stopMuxing();
                break;

        }

        return true;
    }
    public void startRecording(){
        Message msg = Message.obtain(null, MSG_RECORDING_START);
        mHandler.sendMessage(msg);
    }

    public void stopRecording(){
        Message msg = Message.obtain(null, MSG_RECORDING_STOP);
        mHandler.sendMessage(msg);
    }

}
