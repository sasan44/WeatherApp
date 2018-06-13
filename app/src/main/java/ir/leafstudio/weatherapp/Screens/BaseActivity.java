package ir.leafstudio.weatherapp.Screens;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import ir.leafstudio.weatherapp.R;

public class BaseActivity extends AppCompatActivity {

    AnimationDrawable animationDrawable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);


    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = getLayoutInflater().inflate(layoutResID, null);
//        inflater.inflate(layoutResID, getContentView());

        Toolbar myToolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(myToolbar);
//        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//
        ConstraintLayout constraintLayout = findViewById(R.id.root_layout);
        View rootView = findViewById(android.R.id.content);//    android:background="@drawable/gradient_list"

//        constraintLayout.setBackground(getResources().getDrawable(R.drawable.gradient_list));
        rootView.setBackground(getResources().getDrawable(R.drawable.gradient_list));
//          animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable = (AnimationDrawable) rootView.getBackground();
        configAnime(animationDrawable);

        super.setContentView(view);
    }

    private ConstraintLayout getContentView() {
        ConstraintLayout contentView = new ConstraintLayout(this);
        contentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        contentView.setBackground(getResources().getDrawable(R.drawable.gradient_list));
        animationDrawable = (AnimationDrawable) contentView.getBackground();
        contentView.setBackground(animationDrawable); // your common background for all activities
        configAnime(animationDrawable);
        return contentView;
    }


    void configAnime(AnimationDrawable animationDrawable) {
        animationDrawable.setAlpha(100);
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }
}
