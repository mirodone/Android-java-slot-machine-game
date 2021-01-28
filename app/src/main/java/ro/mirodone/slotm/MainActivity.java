package ro.mirodone.slotm;

import androidx.appcompat.app.AppCompatActivity;
import ro.mirodone.slotm.ImageViewScrolling.IEventEnd;
import ro.mirodone.slotm.ImageViewScrolling.ImageViewScrolling;
import ro.mirodone.slotm.ImageViewScrolling.Util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements IEventEnd {

    private static final String TAG = "myApp";

    ImageView btn_up, btn_down;
    ImageViewScrolling image1, image2, image3;
    TextView text_score;

    int count_done=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        btn_down = findViewById(R.id.btn_down);
        btn_up = findViewById(R.id.btn_up);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);

        text_score = findViewById(R.id.text_score);

        //set event

        image1.setEventEnd(MainActivity.this);
        image2.setEventEnd(MainActivity.this);
        image3.setEventEnd(MainActivity.this);

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Util.SCORE >= 50)
                {
                    btn_up.setVisibility(View.GONE);
                    btn_down.setVisibility(View.VISIBLE);

                    image1.setValueRandom(new Random().nextInt(7),
                            new Random().nextInt((15-5)+1)+5);




                    Log.v(TAG, "IMAGE 1 >>> "  );

                    image2.setValueRandom(new Random().nextInt(7),
                            new Random().nextInt((15-5)+1)+5);

                    Log.v(TAG, "IMAGE 2 >>> "  );
                    image3.setValueRandom(new Random().nextInt(7),
                            new Random().nextInt((15-5)+1)+5);

                    Log.v(TAG, "IMAGE 3 >>> "  );

                    Util.SCORE -=50;
                    text_score.setText(String.valueOf(Util.SCORE));

                }else {
                    Toast.makeText(MainActivity.this, "Not enough credit", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void eventEnd(int result, int count) {

            if(count_done <2)
                count_done++;
            else {
                btn_down.setVisibility(View.GONE);
                btn_up.setVisibility(View.VISIBLE);

                count_done = 0;

                //calculate result

                if((image1.getValue() == image2.getValue())&& (image2.getValue() == image3.getValue())){
                    Toast.makeText(this, "You won big !  300 credits",Toast.LENGTH_SHORT).show();
                    int bigScore = Util.SCORE +=300;
                    text_score.setText(String.valueOf(bigScore));

                }else if(image1.getValue() == image2.getValue() ||
                        image1.getValue() == image3.getValue() ||
                        image2.getValue() == image3.getValue()){

                    Toast.makeText(this, "You won small !  50 credits",Toast.LENGTH_SHORT).show();
                    int smallScore = Util.SCORE +=50;
                    text_score.setText(String.valueOf(smallScore));

                }else {
                    Toast.makeText(this, "You lost!   -50 credits",Toast.LENGTH_SHORT).show();
                }

            }



    }
}
