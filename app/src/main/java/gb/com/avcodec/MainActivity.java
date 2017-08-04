package gb.com.avcodec;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gb.com.avcodec.encoder.AudioEncoder;
import gb.com.avcodec.encoder.AudioRecorder;
import gb.com.avcodec.encoder.AudioRecorderHandlerThread;
import gb.com.avcodec.encoder.VideoRecorderHandlerThread;
import gb.com.avcodec.modle.AnyEvent;
import gb.com.avcodec.modle.OnEventMessage;
import gb.com.avcodec.muxer.MediaMuxerWrapper;
import gb.com.avcodec.ui.CameraSurfacePreview;
import gb.com.avcodec.util.CameraPreviewCallback;
import gb.com.avcodec.util.CameraWrapper;
import gb.com.avcodec.util.Setup;

public class MainActivity extends BaseActivity {


    @Bind(R.id.btn_start)
    Button btnStart;
    @Bind(R.id.camera_preview)
    CameraSurfacePreview cameraPreview;
    @Bind(R.id.btn_jump)
    Button btnJump;
    @Bind(R.id.ll)
    LinearLayout ll;

    private CameraPreviewCallback mCameraPreviewCallback;
    private boolean istart;


    //你嫂子愧疚

    /* AudioRecord object to record audio from microphone input */
    private AudioRecorder audioRecorder;

    /* AudioEncoder object to take recorded ByteBuffer from the AudioRecord object*/
    private AudioEncoder audioEncoder;

    private AudioRecorderHandlerThread audioRecorderHandlerThread;

    private VideoRecorderHandlerThread videoRecorderHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mCameraPreviewCallback = new CameraPreviewCallback();
        Setup.mediaMuxerWrapper = new MediaMuxerWrapper(2);
        audioRecorderHandlerThread = new AudioRecorderHandlerThread("Audio Recorder Thread",
                Process.THREAD_PRIORITY_URGENT_AUDIO);
        audioRecorderHandlerThread.start();
    }


    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        istart = !istart;
        if (istart) {
            btnStart.setText("Stop");
            //setPreviewCallbackWithBuffer之前我们需要重新addCallbackBuffer
            //-------------------------------------录制音频------------------------------------------------------------------------------------
            audioRecorderHandlerThread.startRecording();
            //-------------------------------------录制视频-------------------------------------------------------------------------------------
            CameraWrapper.mCamera.addCallbackBuffer(CameraWrapper.mImageCallbackBuffer);
            CameraWrapper.mCamera.setPreviewCallbackWithBuffer(mCameraPreviewCallback);

            //----------------------------------------------------------------------------------------------------------------------------------

        } else {
            btnStart.setText("Start");
            //------------------------------------停止视频--------------------------------------------------------------------------------------
            CameraWrapper.mCamera.setPreviewCallbackWithBuffer(null);
            mCameraPreviewCallback.close();
            // Setup.mediaMuxerWrapper.stopMuxing();
            //------------------------------------停止音频--------------------------------------------------------------------------------------
            audioRecorderHandlerThread.stopRecording();

            //-----------------------------------------------------------------------------------------------------------------------------------

        }
    }

    @Override
    public void onVideoStartMainThread(AnyEvent event) {
        super.onVideoStartMainThread(event);

        Toast.makeText(MainActivity.this, "" + event.getDiscribe(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onVideoStopMainThread(OnEventMessage event) {
        super.onVideoStopMainThread(event);
        Toast.makeText(MainActivity.this, "" + event.getMsg(), Toast.LENGTH_SHORT).show();

    }

    public void startVideo() {
        //setPreviewCallbackWithBuffer之前我们需要重新addCallbackBuffer
        //-------------------------------------录制音频------------------------------------------------------------------------------------
        audioRecorderHandlerThread.startRecording();
        //-------------------------------------录制视频-------------------------------------------------------------------------------------
        CameraWrapper.mCamera.addCallbackBuffer(CameraWrapper.mImageCallbackBuffer);
        CameraWrapper.mCamera.setPreviewCallbackWithBuffer(mCameraPreviewCallback);

        //----------------------------------------------------------------------------------------------------------------------------------
    }

    public void stopVideo() {

        //------------------------------------停止视频--------------------------------------------------------------------------------------
        CameraWrapper.mCamera.setPreviewCallbackWithBuffer(null);
        mCameraPreviewCallback.close();
        // Setup.mediaMuxerWrapper.stopMuxing();
        //------------------------------------停止音频--------------------------------------------------------------------------------------
        audioRecorderHandlerThread.stopRecording();

        //-----------------------------------------------------------------------------------------------------------------------------------

    }


    public void onViewClicked2(View view) {
        startActivity(new Intent(MainActivity.this,Main3Activity.class));
    }
}
