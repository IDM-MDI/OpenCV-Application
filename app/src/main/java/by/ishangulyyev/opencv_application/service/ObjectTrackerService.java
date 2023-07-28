package by.ishangulyyev.opencv_application.service;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import by.ishangulyyev.opencv_application.model.ObjectTracker;

public class ObjectTrackerService {

    private final ObjectTracker tracker;

    public ObjectTrackerService() {
        tracker = new ObjectTracker();
    }

    public Mat processFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        if(!tracker.isInitiated()) {
            return initiateProcess(inputFrame);
        }
        return processCamera(inputFrame);
    }

    private Mat processCamera(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        tracker.setRgb(inputFrame.rgba());
        tracker.setCurrent(inputFrame.gray());

        Core.absdiff(tracker.getCurrent(), tracker.getPrev(), tracker.getDiff());
        Imgproc.threshold(tracker.getDiff(), tracker.getDiff(), 40, 255, Imgproc.THRESH_BINARY);
        Imgproc.findContours(tracker.getDiff(), tracker.getContours(), new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(tracker.getRgb(), tracker.getContours(), -1, new Scalar(255, 0, 0), 4);

        tracker.getContours().stream().map(Imgproc::boundingRect).forEach(rect -> Imgproc.rectangle(tracker.getRgb(), rect, new Scalar(0, 0, 255), 3));

        tracker.getContours().clear();
        tracker.cloneCurrentToPrev();
        return tracker.getRgb();
    }

    private Mat initiateProcess(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        tracker.setPrev(inputFrame.gray());
        tracker.setInitiated(true);
        return tracker.getPrev();
    }
}
