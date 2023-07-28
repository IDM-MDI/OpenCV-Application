package by.ishangulyyev.opencv_application.model;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

import java.util.ArrayList;
import java.util.List;

public class ObjectTracker {

    private Mat current;
    private Mat prev;
    private Mat rgb;
    private Mat diff;
    private List<MatOfPoint> contours;
    private boolean isInitiated = false;

    public ObjectTracker() {
        current = new Mat();
        prev = new Mat();
        rgb = new Mat();
        diff = new Mat();
        contours = new ArrayList<>();
    }

    public Mat getCurrent() {
        return current;
    }

    public void setCurrent(Mat current) {
        this.current = current;
    }

    public Mat getPrev() {
        return prev;
    }

    public void setPrev(Mat prev) {
        this.prev = prev;
    }

    public Mat getRgb() {
        return rgb;
    }

    public void setRgb(Mat rgb) {
        this.rgb = rgb;
    }

    public Mat getDiff() {
        return diff;
    }

    public void setDiff(Mat diff) {
        this.diff = diff;
    }

    public List<MatOfPoint> getContours() {
        return contours;
    }

    public void setContours(List<MatOfPoint> contours) {
        this.contours = contours;
    }

    public boolean isInitiated() {
        return isInitiated;
    }

    public void setInitiated(boolean initiated) {
        isInitiated = initiated;
    }

    public void cloneCurrentToPrev() {
        prev = current.clone();
    }
}
