package com.anysoftkeyboard.ui.settings.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Benny on 24/05/16.
 */
public class SKPreference extends Preference {

    private Typeface tf1, tf2;

    public SKPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SKPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SKPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SKPreference(Context context) {
        super(context);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        tf1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/ropa_soft_bold.ttf");
        tf2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/ropa_soft_light.ttf");
        ((TextView) view.findViewById(android.R.id.title)).setTypeface(tf1);
        ((TextView) view.findViewById(android.R.id.summary)).setTypeface(tf2);

    }
}
