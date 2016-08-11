package com.game.huyhoang.weirdchess.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.game.huyhoang.weirdchess.Database.SQLiteOpenHelper;
import com.game.huyhoang.weirdchess.R;

import org.w3c.dom.Text;

/**
 * Created by huyhoang on 19/07/2016.
 */
public class HomeActivity extends Activity {
    Button btnRestart;
    TextView tvHighScore;
    TextView tvYourScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(this, "UserDatabase");
        int highScore = sqLiteOpenHelper.getHighScore(1);
        int yourScore = getIntent().getExtras().getInt("score");
        if(highScore < yourScore){
            highScore = yourScore;
            sqLiteOpenHelper.updateHightScore(highScore);
        }

        tvHighScore = (TextView) findViewById(R.id.tvHighScore);
        tvYourScore = (TextView) findViewById(R.id.tvScore);

        tvYourScore.setText(" " + yourScore);
        tvHighScore.setText(" " + highScore);

        btnRestart = (Button) findViewById(R.id.btnResetGame);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HomeActivity.this, GameActivity.class);
        startActivity(intent);
    }
}
