package com.shubham.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
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

import static java.lang.Math.min;
import static java.lang.StrictMath.max;

public class PlayerVsComp extends AppCompatActivity {

    CountDownTimer cTimer = null;

    private ImageView[] mBlocks = new ImageView[9];
    private enum TURN {CIRCLE, CROSS} // Circle is Computer
    private TURN turn=TURN.CROSS;
    private int counter = 0;
    private TextView textView;
    private FrameLayout f1, f2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.activity_player_vs_comp);


        textView=findViewById(R.id.display_board);
        f1 = findViewById(R.id.left_ll);
        f2 = findViewById(R.id.right_ll);

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


        Random random = new Random();
        int turnx = random.nextInt(2);
        if(turnx==0){
            turn= TURN.CROSS;
            f1.setAlpha(1);
            textView.setText("CROSS's turn");}
        else{
            turn= TURN.CIRCLE;
            f2.setAlpha(1);
            textView.setText("CIRCLE's turn");
            switchTurn(-1);
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

        if(turn == TURN.CROSS){

            mBlocks[position].setImageResource(R.drawable.cross);
            mBlocks[position].setId(Algorithm.CROSS);
            turn= TURN.CIRCLE;
            textView.setText("CIRCLE's turn");
            f1.setAlpha((float)0.2);
            f2.setAlpha(1);


        }
        else{

            position = BestPosition(mBlocks);
            mBlocks[position].setImageResource(R.drawable.circle);
            mBlocks[position].setId(Algorithm.CIRCLE);
            turn=TURN.CROSS;
            f2.setAlpha((float)0.2);
            f1.setAlpha(1);
            textView.setText("CROSS's turn");

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



        if(noMovesLeft(mBlocks) && turn==TURN.CIRCLE)
            switchTurn(-1);
    }
    private void disableAll() {
        for (int i = 0; i < 9; i++)
            mBlocks[i].setEnabled(false);
    }

    private void enableAll(){
        for (int i = 0; i < 9; i++)
            mBlocks[i].setEnabled(true);
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


    private int evaluate(ImageView[] sBlocks){


        //for row
        for(int i=0;i<7;i+=3){
            if(sBlocks[i].getId()==sBlocks[i+1].getId() && sBlocks[i].getId()==sBlocks[i+2].getId()){
                if(sBlocks[i].getId()==Algorithm.CIRCLE)
                    return 10;
                if(sBlocks[i].getId()==Algorithm.CROSS)
                    return -10;
            }
        }
        //for column
        for(int i=0;i<3;i++){
            if(sBlocks[i].getId()==sBlocks[i+3].getId() && sBlocks[i].getId()==sBlocks[i+6].getId()){
                if(sBlocks[i].getId()==Algorithm.CIRCLE)
                    return 10;
                if(sBlocks[i].getId()==Algorithm.CROSS)
                    return -10;
            }
        }
        //for diagonal
        if(sBlocks[0].getId()==sBlocks[4].getId() && sBlocks[0].getId()==sBlocks[8].getId()){
            if(sBlocks[0].getId()==Algorithm.CIRCLE)
                return 10;
            if(sBlocks[0].getId()==Algorithm.CROSS)
                return -10;
        }
        if(sBlocks[2].getId()==sBlocks[4].getId() && sBlocks[2].getId()==sBlocks[6].getId()){
            if(sBlocks[2].getId()==Algorithm.CIRCLE)
                return 10;
            if(sBlocks[2].getId()==Algorithm.CROSS)
                return -10;
        }

        return 0;
    }


    private int minimax(ImageView[] Board, int depth, boolean isMax){

        int score = evaluate(Board);

        if(score == 10)
            return score;
        if(score == -10)
            return score;

        if(!noMovesLeft(Board))
            return 0;

        if(isMax){
            int best = -1000;

            for(int i=0;i<9;i++){
                if(Board[i].getId()!=Algorithm.CIRCLE && Board[i].getId()!=Algorithm.CROSS){
                    int x=Board[i].getId();
                    Board[i].setId(Algorithm.CIRCLE);
                    best = max(best,minimax(Board,depth+1,!isMax));
                    Board[i].setId(x);
                }
            }
            return best;
        }
        else{
            int best = 1000;

            for(int i=0;i<9;i++){
                if(Board[i].getId()!=Algorithm.CIRCLE && Board[i].getId()!=Algorithm.CROSS){
                    int x=Board[i].getId();
                    Board[i].setId(Algorithm.CROSS);
                    best = min(best,minimax(Board,depth+1,!isMax));
                    Board[i].setId(x);
                }
            }
            return best;
        }
    }

    int BestPosition(ImageView[] board){
        int position=-1;
        int bestVal = -1000;
        for(int i=0;i<9;i++){
            if(board[i].getId()!=Algorithm.CIRCLE && board[i].getId()!=Algorithm.CROSS){
                int x = board[i].getId();

                board[i].setId(Algorithm.CIRCLE);
                int moveVal = minimax(board,0,false);
                board[i].setId(x);

                if(moveVal>bestVal){
                    bestVal=moveVal;
                    position=i;
                }
            }
        }
        return position;
    }

    private Boolean noMovesLeft(ImageView[] Board){
        for(int i=0;i<9;i++)
            if(Board[i].getId()!=Algorithm.CROSS && Board[i].getId()!= Algorithm.CIRCLE)
                return true;

        return false;
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