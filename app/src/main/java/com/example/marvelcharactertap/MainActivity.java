package com.example.marvelcharactertap;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView characterImageView;
    private TextView scoreTextView, characterNameTextView;
    private int score = 0;
    private Random random = new Random();
    private Handler handler = new Handler();
    private MediaPlayer tapSound;
    private String correctCharacterName;
    private Map<String, Integer> characterMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        characterImageView = findViewById(R.id.characterImageView);
        scoreTextView = findViewById(R.id.scoreTextView);
        characterNameTextView = findViewById(R.id.characterNameTextView);
        characterNameTextView.setTextColor(getResources().getColor(android.R.color.white));
        scoreTextView.setTextColor(getResources().getColor(android.R.color.white));
        tapSound = MediaPlayer.create(this, R.raw.tap_sound);

        // Map character names to images
        characterMap.put("Iron Man", R.drawable.ic_marvel_character1);
        characterMap.put("Captain America", R.drawable.ic_marvel_character2);
        characterMap.put("Spider Man", R.drawable.ic_marvel_character3);
        characterMap.put("Wolverine", R.drawable.ic_marvel_character4);
        characterMap.put("Black Widow", R.drawable.ic_marvel_character5);
        characterMap.put("Wanda Maximoff", R.drawable.ic_marvel_character6);
        characterMap.put("Hulk", R.drawable.ic_marvel_character7);
        characterMap.put("Black Panther", R.drawable.ic_marvel_character8);

        characterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCharacterMatch();
            }
        });

        changeCharacterImage();
    }

    private void changeCharacterImage() {
        Object[] names = characterMap.keySet().toArray();
        correctCharacterName = (String) names[random.nextInt(names.length)];
        characterNameTextView.setText(correctCharacterName);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int randomIndex = random.nextInt(characterMap.size());
                String randomName = (String) names[randomIndex];
                characterImageView.setImageResource(characterMap.get(randomName));
                characterImageView.setTag(characterMap.get(randomName));
                handler.postDelayed(this, 800); // Update image every 800ms (0.8 seconds)
            }
        }, 0);
    }

    private void checkCharacterMatch() {
        int correctImageId = characterMap.get(correctCharacterName);
        int currentImageId = (Integer) characterImageView.getTag();

        Log.d("DEBUG", "Correct Character: " + correctCharacterName + ", Correct Image ID: " + correctImageId + ", Current Image ID: " + currentImageId);

        if (correctImageId == currentImageId) {
            score++;
            scoreTextView.setText("Score: " + score);
            if (tapSound != null) {
                tapSound.start();
            }
        } else {
            Log.d("DEBUG", "Incorrect tap.");
        }

        handler.removeCallbacksAndMessages(null);
        changeCharacterImage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tapSound != null) {
            tapSound.release();
            tapSound = null;
        }
    }
}
