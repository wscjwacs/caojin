package gb.com.avcodec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gb.com.avcodec.util.CameraWrapper;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        findViewById(R.id.btn_resolu1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraWrapper.IMAGE_WIDTH = 1920;
                CameraWrapper.IMAGE_HEIGHT = 1080;

            }
        });


        findViewById(R.id.btn_resolu2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraWrapper.IMAGE_WIDTH = 1280;
                CameraWrapper.IMAGE_HEIGHT = 720;

            }
        });

        findViewById(R.id.btn_resolu3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraWrapper.IMAGE_WIDTH = 640;
                CameraWrapper.IMAGE_HEIGHT = 480;

            }
        });

        findViewById(R.id.btn_resolu4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraWrapper.IMAGE_WIDTH = 320;
                CameraWrapper.IMAGE_HEIGHT = 240;

            }
        });
    }
}
