package com.example.androidassignments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private static final int REQUEST_IMAGE_CAPTURE = 101;

    private ImageButton cameraButton;
    private SwitchCompat switchToggle;
    private CheckBox checkBoxOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        print("inside onCreate");
        setContentView(R.layout.activity_list_items);

        cameraButton = findViewById(R.id.imageButtonCamera);
        switchToggle = findViewById(R.id.switchToggle);
        checkBoxOption = findViewById(R.id.checkBoxOption);

        // 1. Setup camera permission or trigger request
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            setupCameraButton();
        }

        // 2. Setup switch
        if (switchToggle != null) {
            switchToggle.setOnCheckedChangeListener((btn, isChecked) -> {
                CharSequence text = isChecked ? "Switch is On" : "Switch is Off";
                int duration = isChecked ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG;
                Toast.makeText(ListItemsActivity.this, text, duration).show();
            });
        }

        // 3. Setup checkbox
        if (checkBoxOption != null) {
            checkBoxOption.setOnCheckedChangeListener((btn, isChecked) -> {
                new AlertDialog.Builder(ListItemsActivity.this)
                        .setTitle(R.string.dialog_title)
                        .setMessage(R.string.dialog_message)
                        .setPositiveButton(R.string.ok, (dialog, which) -> {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("Response", "Here is my response");
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
            });
        }
    }

    private void setupCameraButton() {
        if (cameraButton != null) {
            cameraButton.setOnClickListener(v -> {
                print("cameraButton clicked — launching camera Intent");
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } else {
                    print("No camera app found");
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                print("Camera permission granted by user");
                setupCameraButton();
            } else {
                print("Camera permission denied by user");
                Toast.makeText(this, "Camera permission required to take pictures", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        print("onActivityResult(requestCode=" + requestCode + ", resultCode=" + resultCode + ")");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            if (imageBitmap != null) {
                print("Received image — setting on button");
                cameraButton.setImageBitmap(imageBitmap);
            } else {
                print("No image data returned");
            }
        }
    }

    @Override protected void onStart() { super.onStart(); print("inside onStart"); }
    @Override protected void onResume() { super.onResume(); print("inside onResume"); }
    @Override protected void onPause() { super.onPause(); print("inside onPause"); }
    @Override protected void onStop() { super.onStop(); print("inside onStop"); }
    @Override protected void onDestroy() { super.onDestroy(); print("inside onDestroy"); }

    private void print(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
