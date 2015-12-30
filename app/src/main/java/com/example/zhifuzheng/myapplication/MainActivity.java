package com.example.zhifuzheng.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    TextView textViewZip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tvGpx);
        textViewZip = (TextView)findViewById(R.id.tvGpxZip);
        findViewById(R.id.btnLoad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();

            }
        });
        findViewById(R.id.btnLoadZip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataZip();

            }
        });
    }

    private void loadData() {
        try {
            InputStream is = this.getResources().openRawResource(R.raw.track);

            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int count;
                while ((count = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, count);
                }
                byte[] bytes = baos.toByteArray();
                String output = new String(bytes);
                textView.setText(output);
                Log.d("gpx", output);

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                is.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadDataZip() {
        try {
            InputStream is = this.getResources().openRawResource(R.raw.mytracks);
            ZipInputStream zis = new ZipInputStream(is);
            try {
                ZipEntry ze;
                if ((ze = zis.getNextEntry()) != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int count;
                    while ((count = zis.read(buffer)) != -1) {
                        baos.write(buffer, 0, count);
                    }
                    byte[] bytes = baos.toByteArray();
                    String output = new String(bytes);
                    textViewZip.setText(output);
                    Log.d("gpx", output);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                zis.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
