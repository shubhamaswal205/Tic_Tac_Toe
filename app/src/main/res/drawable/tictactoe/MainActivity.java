package sumit.tictactoe;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout pp, pc;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pp = findViewById(R.id.pp);
        pc = findViewById(R.id.pc);

        pp.setOnClickListener(this);
        pc.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
        switch (v.getId()){
            case R.id.pp:
                intent = new Intent(this, PvsP.class);
                break;
            case R.id.pc:
                intent = new Intent(this, PvsC.class);
                break;
        }
        startActivity(intent,options.toBundle());

    }
}
