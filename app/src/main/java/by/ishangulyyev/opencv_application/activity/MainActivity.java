package by.ishangulyyev.opencv_application.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.util.Collections;
import java.util.List;

import by.ishangulyyev.opencv_application.R;
import by.ishangulyyev.opencv_application.service.ObjectTrackerService;
import by.ishangulyyev.opencv_application.util.PermissionUtils;
import by.ishangulyyev.opencv_application.validator.PermissionValidator;

public class MainActivity extends CameraActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private CameraBridgeViewBase cameraBridgeViewBase;
    private ObjectTrackerService service;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtils.requestCameraPermission(this);

        cameraBridgeViewBase = findViewById(R.id.cameraPreview);
        cameraBridgeViewBase.setCvCameraViewListener(this);

        if(OpenCVLoader.initDebug()) {
            cameraBridgeViewBase.enableView();
        }
    }

    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(cameraBridgeViewBase);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(!PermissionValidator.haveCamera(grantResults)) {
            PermissionUtils.requestCameraPermission(this);
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        service = new ObjectTrackerService();
    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return service.processFrame(inputFrame);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraBridgeViewBase.enableView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraBridgeViewBase.disableView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraBridgeViewBase.disableView();
    }
}