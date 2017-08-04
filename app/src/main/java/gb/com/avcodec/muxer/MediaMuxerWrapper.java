package gb.com.avcodec.muxer;

import android.annotation.SuppressLint;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Environment;
import android.util.Log;
import com.google.common.base.Throwables;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by abrain on 10/25/16.
 */
public class MediaMuxerWrapper {
    private static final String TAG = MediaMuxerWrapper.class.getSimpleName();
    private MediaMuxer muxer =null;
    private boolean isMuxing;
    private String outputFile;




    private  int totalTracks;
    private int trackCounter;




    @SuppressLint("NewApi")
    public MediaMuxerWrapper(int totalTracks){
            this.totalTracks =totalTracks;
        //    initMuxer();
    }



    @SuppressLint("NewApi")
    public void startMuxing(){
        isMuxing = true;
        muxer.start();
    }
    @SuppressLint("NewApi")
    public void stopMuxing(){
        isMuxing = false;
        if (muxer !=null){
            muxer.stop();
            muxer.release();
            muxer =null;
        }
        trackCounter=0;
    }





    @SuppressLint("NewApi")
    synchronized public int addTrack(MediaFormat format) {
        checkState(!isStarted(), "Muxer already started");
        if (muxer ==null){
            initMuxer();
        }
        int trackIndex = muxer.addTrack(format);
        trackCounter++;
        if (isStarted()) {
            //  muxer.start();
            startMuxing();
            notifyAll();
        } else {
            while (!isStarted()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Throwables.propagate(e);
                }
            }
        }
        return trackIndex;
    }


    @SuppressLint("NewApi")
    synchronized public void writeSampleData(int trackIndex, ByteBuffer byteBuf,
                                             MediaCodec.BufferInfo bufferInfo) {
        checkState(isStarted(), "Muxer not started");
        muxer.writeSampleData(trackIndex, byteBuf, bufferInfo);
    }




    private boolean isStarted() {
        return trackCounter == totalTracks;
    }
    @SuppressLint("NewApi")
    private void initMuxer(){
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath()+
                File.separator+"VVID_"+System.currentTimeMillis()+".mp4";
        try {
            muxer = new MediaMuxer(outputFile, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
        } catch (IOException e) {
            Log.d(TAG, "exception creating new media muxer ", e);
        }
    }




    //    @SuppressLint("NewApi")
//    public void addAudioEncoder(AudioEncoder encoder){
//        audioFormat = encoder.getEncoder().getOutputFormat();
//        audioTrackIndex = muxer.addTrack(audioFormat);
//        Log.d(TAG, "added audio track");
//    }



//
//    public MediaMuxerWrapper(MediaMuxer muxer, int totalTracks) {
//
//        this.muxer = checkNotNull(muxer);
//        this.totalTracks = totalTracks;
//    }



//    @SuppressLint("NewApi")
//    public void muxAudio(ByteBuffer buffer, MediaCodec.BufferInfo bufferInfo){
//        try{
//            muxer.writeSampleData(audioTrackIndex, buffer, bufferInfo);
//            Log.d(TAG, "muxed sample of length "+buffer.remaining());
//        }catch(IllegalArgumentException e){
//            Log.d(TAG, "argument to writeSampleData incorrect : ",e);
//        }catch(IllegalStateException e){
//            Log.d(TAG, "muxer in illegal state : ",e);
//        }
//    }



//-------------------------------------------------------------------------------------------------------------------------------
}