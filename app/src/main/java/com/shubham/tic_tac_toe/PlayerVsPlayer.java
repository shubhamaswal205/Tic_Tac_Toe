package com.shubham.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;

import java.util.Random;

public class PlayerVsPlayer extends AppCompatActivity {

    private ImageView[] mBlocks = new ImageView[9];
    private enum TURN {CIRCLE, CROSS}
    private TURN turn;
    private int counter = 0;
    private TextView textView;
    private FrameLayout f1, f2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.activity_player_vs_player);


        textView = findViewById(R.id.display_board);
        f1 = findViewById(R.id.left_ll);
        f2 = findViewById(R.id.right_ll);


        Random random = new Random();
        int turnx = random.nextInt(2);
        if(turnx==0){
            turn=TURN.CROSS;
            f1.setAlpha(1);
            textView.setText("CROSS's turn");}
        else{
            turn=TURN.CIRCLE;
            f2.setAlpha(1);
            textView.setText("CIRCLE's turn");}


        for (int position = 0; position < 9; position++) {
            int resId = getResources().getIdentifier("block_" + (position + 1), "id", getPackageName());
            mBlocks[position] = findViewById(resId);
            final int finalPosition = position;
            mBlocks[position].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchTurn(finalPosition);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.reset) {
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);

        }
        return super.onOptionsItemSelected(item);
    }

    private void switchTurn(int position){

        if(turn == TURN.CIRCLE){
            mBlocks[position].setImageResource(R.drawable.circle);
            mBlocks[position].setId(Algorithm.CIRCLE);
            turn=TURN.CROSS;
            f2.setAlpha((float)0.2);
            f1.setAlpha(1);
            textView.setText("CROSS's turn");

        }
        else{
            mBlocks[position].setImageResource(R.drawable.cross);
            mBlocks[position].setId(Algorithm.CROSS);
            turn=TURN.CIRCLE;
            f1.setAlpha((float)0.2);
            f2.setAlpha(1);
            textView.setText("CIRCLE's turn");
        }

        mBlocks[position].setEnabled(false);
        counter++;
        if(Algorithm.isCompleted(position+1,mBlocks)){
            celebration(position);
            if(Algorithm.Winner.equalsIgnoreCase("CROSS")) {
                f1.setAlpha(1);
                f2.setBackgroundColor(getResources().getColor(R.color.redbg));
            }
            else{
                f2.setAlpha(1);
                f1.setBackgroundColor(getResources().getColor(R.color.bluebg));
            }
            textView.setText(Algorithm.Winner+" won!!");
            displayStick(Algorithm.sSet);
            disableAll();
        }
        else if(counter==9){
            textView.setText("DRAW!!");
        }


    }

    private void displayStick(int stick){
        View view;
        switch (stick) {
            case 1:
                view = findViewById(R.id.top_horizontal);
                break;
            case 2:
                view = findViewById(R.id.center_horizontal);
                break;
            case 3:
                view = findViewById(R.id.bottom_horizontal);
                break;
            case 4:
                view = findViewById(R.id.left_vertical);
                break;
            case 5:
                view = findViewById(R.id.center_vertical);
                break;
            case 6:
                view = findViewById(R.id.right_vertical);
                break;
            case 7:
                view = findViewById(R.id.left_right_diagonal);
                break;
            case 8:
                view = findViewById(R.id.right_left_diagonal);
                break;
            default:
                view = findViewById(R.id.center_vertical);
        }
        view.setVisibility(View.VISIBLE);
    }

    private void disableAll() {
        for (int i = 0; i < 9; i++)
            mBlocks[i].setEnabled(false);
    }

    private void celebration(int pos){
        ParticleSystem ps = new ParticleSystem(this, 100, R.drawable.star_pink, 800);
        ps.setScaleRange(0.7f, 1.3f);
        ps.setSpeedRange(0.1f, 0.25f);
        ps.setRotationSpeedRange(90, 180);
        ps.setFadeOut(600, new AccelerateInterpolator());
        ps.oneShot(mBlocks[pos], 70);

        ParticleSystem ps2 = new ParticleSystem(this, 100, R.drawable.star_white, 800);
        ps2.setScaleRange(0.7f, 1.3f);
        ps2.setSpeedRange(0.1f, 0.25f);
        ps.setRotationSpeedRange(90, 180);
        ps2.setFadeOut(600, new AccelerateInterpolator());
        ps2.oneShot(mBlocks[pos], 70);
    }

    public void setAnimation()
    {
        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.END);
        slide.setDuration(400);
        slide.setInterpolator(new AccelerateDecelerateInterpolator());
        getWindow().setExitTransition(slide);
        getWindow().setEnterTransition(slide);

    }
}