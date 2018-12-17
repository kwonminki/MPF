package com.example.olleh.mpf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ChooseTag extends AppCompatActivity {
    static String Tag;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_tag_choose);
    }

    public void bt_Character(View view) {
        Tag="char";
        Intent intent = new Intent(getApplicationContext(), CameraMain.class);
        startActivity(intent);
    }
    public void bt_View(View view) {
        Tag="view";
        Intent intent = new Intent(getApplicationContext(), CameraMain.class);
        startActivity(intent);
    }
    public void bt_Documents(View view) {
        Tag="docu";
        Intent intent = new Intent(getApplicationContext(), CameraMain.class);
        startActivity(intent);
    }
    public void bt_Others(View view) {
        Tag="othe";
        Intent intent = new Intent(getApplicationContext(), CameraMain.class);
        startActivity(intent);
    }
    public void bt_Food(View view) {
        Tag="food";
        Intent intent = new Intent(getApplicationContext(), CameraMain.class);
        startActivity(intent);
    }
}
