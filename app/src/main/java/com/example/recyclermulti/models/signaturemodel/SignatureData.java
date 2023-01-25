package com.example.recyclermulti.models.signaturemodel;

import android.graphics.Bitmap;

public class SignatureData {

    int position;
    Bitmap bitmap;

    public SignatureData(int position, Bitmap bitmap) {
        this.position = position;
        this.bitmap = bitmap;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
