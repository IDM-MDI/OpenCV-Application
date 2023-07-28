package by.ishangulyyev.opencv_application.validator;

import android.content.pm.PackageManager;

import java.util.stream.IntStream;

public class PermissionValidator {

    private PermissionValidator() {}

    public static boolean haveCamera(int[] grantResults) {
        return grantResults.length > 0 && IntStream.of(grantResults).anyMatch(result -> result == PackageManager.PERMISSION_GRANTED);
    }
}
