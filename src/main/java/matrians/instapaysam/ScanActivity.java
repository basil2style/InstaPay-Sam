package matrians.instapaysam;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.HashSet;

import matrians.instapaysam.camera.CameraSingleFrag;

public class ScanActivity extends AppCompatActivity {

    void initCameraFrag() {
        getFragmentManager().beginTransaction().
                replace(R.id.camera, new CameraSingleFrag()).commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        if (savedInstanceState == null) {
            //Check permissions
            ensurePermissions(Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else initCameraFrag();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                showAlertDialog(permissions);
                return;
            }
        }
        initCameraFrag();
    }

    private void ensurePermissions(String... permissions) {
        HashSet<String> deniedPermissionList = new HashSet<>();

        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED !=
                    ContextCompat.checkSelfPermission(this, permission)) {
                deniedPermissionList.add(permission);
            }
        }

        if (!deniedPermissionList.isEmpty()) {
            ActivityCompat.requestPermissions(
                    this, deniedPermissionList.toArray(new String[0]), 0);
        }
        else initCameraFrag();
    }

    /**
     * Shows alert dialog.
     */
    private void showAlertDialog(final String... permissions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This function requires permissions to access Camera.");
        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ensurePermissions(permissions);
            }
        });
        builder.setNegativeButton("Don't grant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        builder.show();
    }
}
