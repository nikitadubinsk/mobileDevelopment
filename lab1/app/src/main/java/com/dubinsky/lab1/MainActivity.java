package com.dubinsky.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView yourPoints;
    TextView computerPoints;
    TextView whoTurn;
    TextView cube1;
    TextView cube2;
    TextView btn;
    int number1;
    int number2;
    public Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        yourPoints = findViewById(R.id.yourPoints);
        computerPoints = findViewById(R.id.computerPoints);
        whoTurn = findViewById(R.id.whoTurn);

        cube1 = findViewById(R.id.cube1);
        cube2 = findViewById(R.id.cube2);

        btn = findViewById(R.id.button);
    }

    public void onClick(View view) {
        while (yourTurn()) {
            return;
        }

        if ((Integer.parseInt(yourPoints.getText().toString()) > 99) && (Integer.parseInt(computerPoints.getText().toString()) < Integer.parseInt(yourPoints.getText().toString()))) {
            goToYouWinActivity();
            clearAll();
            return;
        }

        findViewById(R.id.button).setEnabled(false);
        computerTurn();
        findViewById(R.id.button).setEnabled(true);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if ((Integer.parseInt(computerPoints.getText().toString()) > 99) && (Integer.parseInt(computerPoints.getText().toString()) > Integer.parseInt(yourPoints.getText().toString()))) {
                    goToComputerWinActivity();
                    clearAll();
                    return;
                }
            }
        }, 2000);
    }

    public void goToComputerWinActivity() {
        Intent intent = new Intent(this, computerWinActivity.class);
        startActivity(intent);
    }

    public void goToYouWinActivity() {
        Intent intent = new Intent(this, yourWinActivity.class);
        startActivity(intent);
    }

    public void clearAll() {
        whoTurn.setText("");
        computerPoints.setText("0");
        yourPoints.setText("0");
        cube1.setText("0");
        cube2.setText("0");
    }

    public void computerTurn() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                number1 = random.nextInt(5) + 1;
                number2 = random.nextInt(5) + 1;
                int p2 = Integer.parseInt(computerPoints.getText().toString());

                cube1.setText(String.valueOf(number1));
                cube2.setText(String.valueOf(number2));

                whoTurn.setText("Ход компьютера:");
                p2 = p2 + number1 + number2;
                computerPoints.setText(String.valueOf(p2));

                if (number1 == number2) {
                    computerTurn();
                }
            }
        }, 1000);
    }

    public boolean yourTurn() {
        number1 = random.nextInt(5) + 1;
        number2 = random.nextInt(5) + 1;
        int p1 = Integer.parseInt(yourPoints.getText().toString());

        cube1.setText(String.valueOf(number1));
        cube2.setText(String.valueOf(number2));

        whoTurn.setText("Ваш ход:");
        p1 = p1 + number1 + number2;
        yourPoints.setText(String.valueOf(p1));

        if (number1 == number2) {
            return true;
        } else return false;

    }

}
