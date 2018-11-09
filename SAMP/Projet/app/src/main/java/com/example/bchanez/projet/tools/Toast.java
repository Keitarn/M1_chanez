package com.example.bchanez.projet.tools;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

public class Toast {

    public static void toast(String msg, Context context, WindowManager windowManager) {
        int duration = android.widget.Toast.LENGTH_SHORT;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        android.widget.Toast toast = android.widget.Toast.makeText(context, msg, duration);
        toast.setGravity(Gravity.TOP, 0, height / 2);
        toast.show();
    }
}