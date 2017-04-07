package com.example.estudiante.juju;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer{

    private ArrayList<Bitmap> yay = new ArrayList<>();
    private ImageView yayu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ComClient.getInstance().addObserver(this);
        ComClient.getInstance().setValidar(true);

        yayu = (ImageView) findViewById(R.id.imagen);

    }

    public void runUi(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
if (yay.isEmpty() == false){
    yayu.setImageBitmap(yay.get(0));
}
            }
        });
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof byte[]) {
            byte[] aja = (byte[]) o;
            Bitmap sipi = BitmapFactory.decodeByteArray(aja, 0, aja.length);
            yay.add(sipi);
            runUi();
        }
    }
}
