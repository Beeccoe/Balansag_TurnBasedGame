package com.example.balansag_turnbasedgame;
import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import mcm.edu.ph.lastname_turnbasedgame.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SoundPool soundPool;
    private int skill1sound, skill2sound, skill3sound, skill4sound, protagatksound, antagatksound;

    AnimationDrawable protagswing, antagscratch, skill2overlay, skill3overlay, skill1overlay, skill4overlay, dmgoverlay;
    TextView txtProtagName, txtAntagName, txtProtagHP, txtAntagHP, txtLog, txtturnCount;
    Button btnNextTurn;
    ImageButton skill1,skill2,skill3,skill4;

    String protagName = "Tiefling";
    int protagHP = 1500;
    int protagMinDamage = 100;
    int protagMaxDamage = 150;

    String antagName = "Bugbear";
    int antagHP = 5000;
    int angtagMinDamage = 50;
    int antagMaxDamage = 60;
    int turnNumber= 1;

    boolean disabledstatus = false;
    int statuscounter = 0;
    int buttoncounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //XML ids for text and button

        ImageView protagAtk = (ImageView)findViewById(R.id.protagatk);
        protagAtk.setBackgroundResource(R.drawable.swing);
        protagswing = (AnimationDrawable) protagAtk.getBackground();

        ImageView dmgOv = (ImageView)findViewById(R.id.dmgov);
        dmgOv.setBackgroundResource(R.drawable.dmgoverlay);
        dmgoverlay = (AnimationDrawable) dmgOv.getBackground();

        ImageView antagAtk = (ImageView)findViewById(R.id.antagatk);
        antagAtk.setBackgroundResource(R.drawable.scratch);
        antagscratch = (AnimationDrawable) antagAtk.getBackground();

        ImageView skill2ov = (ImageView)findViewById(R.id.skill2ov);
        skill2ov.setBackgroundResource(R.drawable.skill2overlay);
        skill2overlay = (AnimationDrawable) skill2ov.getBackground();

        ImageView skill3ov = (ImageView)findViewById(R.id.skill3ov);
        skill3ov.setBackgroundResource(R.drawable.skill3overlay);
        skill3overlay = (AnimationDrawable) skill3ov.getBackground();

        ImageView skill4ov = (ImageView)findViewById(R.id.skill4ov);
        skill4ov.setBackgroundResource(R.drawable.skill4overlay);
        skill4overlay = (AnimationDrawable) skill4ov.getBackground();

        ImageView skill1ov = (ImageView)findViewById(R.id.skill1ov);
        skill1ov.setBackgroundResource(R.drawable.skill1soverlay);
        skill1overlay = (AnimationDrawable) skill1ov.getBackground();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }

        skill1sound = soundPool.load(this, R.raw.skill1sound, 1);
        skill2sound = soundPool.load(this, R.raw.skill2sound, 1);
        skill3sound = soundPool.load(this, R.raw.skill3sound, 1);
        skill4sound = soundPool.load(this, R.raw.skill4sound, 1);
        protagatksound = soundPool.load(this, R.raw.protagatksound,1);
        antagatksound = soundPool.load(this, R.raw.antagatksound,1);



        txtProtagName = findViewById(R.id.txtProtagName);
        txtAntagName = findViewById(R.id.txtAntagName);
        txtProtagHP = findViewById(R.id.txtProtagHP);
        txtAntagHP = findViewById(R.id.txtAntagHP);
        btnNextTurn = findViewById(R.id.btnNxtTurn);
        txtturnCount = findViewById(R.id.turnCount);

        txtLog = findViewById(R.id.txtCombatLog);

        txtProtagName.setText(protagName);
        txtProtagHP.setText("HP: "+String.valueOf(protagHP) +"/1500");

        txtAntagName.setText(antagName);
        txtAntagHP.setText("HP: "+String.valueOf(antagHP) +"/5000");

        skill1 = findViewById(R.id.btnSkill1);
        skill2 = findViewById(R.id.btnSkill2);
        skill3 = findViewById(R.id.btnSkill3);
        skill4 = findViewById(R.id.btnSkill4);


        btnNextTurn.setOnClickListener(this);
        skill1.setOnClickListener(this);
        skill2.setOnClickListener(this);
        skill3.setOnClickListener(this);
        skill4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        txtLog.findViewById(R.id.txtCombatLog);

        Random randomizer = new Random();
        int protagdps = randomizer.nextInt(protagMaxDamage - protagMinDamage) + protagMinDamage;
        int antagdps = randomizer.nextInt(antagMaxDamage - angtagMinDamage) + angtagMinDamage;

        if(turnNumber % 2 != 1){//if it is enemy's turn, disable button
            skill1.setEnabled(false);
            skill2.setEnabled(false);
            skill3.setEnabled(false);
            skill4.setEnabled(false);
        }
        else if(turnNumber%2 == 1){
            skill1.setEnabled(true);
            skill2.setEnabled(true);
            skill3.setEnabled(true);
            skill4.setEnabled(true);
        }

        if(buttoncounter>0){
            skill1.setEnabled(false);
            skill2.setEnabled(false);
            skill3.setEnabled(false);
            skill4.setEnabled(false);
        }
        else if(buttoncounter==0){
            skill1.setEnabled(true);
            skill2.setEnabled(true);
            skill3.setEnabled(true);
            skill4.setEnabled(true);
        }

        switch(v.getId()) {
            case R.id.btnSkill1:

                skill1overlay.start();
                skill2overlay.stop();
                skill3overlay.stop();
                skill4overlay.stop();
                protagswing.stop();
                antagscratch.stop();
                dmgoverlay.stop();
                soundPool.play(skill1sound,1,1,0, 0,1);
                antagHP = antagHP - 500;
                turnNumber++;
                txtAntagHP.setText("HP: "+String.valueOf(antagHP) +"/5000");
                btnNextTurn.setText("ATTACK");
                txtturnCount.setText("Turn "+String.valueOf(turnNumber-1) +"");

                txtLog.setText(""+String.valueOf(protagName) +" cast eldritch blast!\n" +
                        "it dealt "+String.valueOf(500) + " points of damage!");


                if(antagHP <= 0){ //even
                    skill1overlay.stop();
                    skill2overlay.stop();
                    skill3overlay.stop();
                    skill4overlay.stop();
                    protagswing.start();
                    antagscratch.stop();
                    dmgoverlay.stop();
                    txtLog.setText(""+String.valueOf(protagName) +" dealt "+String.valueOf(protagdps) + " damage to the enemy. The player is victorious!");
                    protagHP = 1500;
                    antagHP = 3000;
                    turnNumber= 2;
                    btnNextTurn.setText("Reset Game");
                    txtturnCount.setText("Turn "+String.valueOf(turnNumber-1) +"");
                }
                buttoncounter=3;


                break;

            case R.id.btnSkill2:

                skill1overlay.stop();
                skill2overlay.start();
                skill3overlay.stop();
                skill4overlay.stop();
                protagswing.stop();
                antagscratch.stop();
                dmgoverlay.stop();
                soundPool.play(skill2sound,1,1,0, 0,1);
                protagHP = protagHP + 50;
                turnNumber++;
                txtProtagHP.setText("HP: "+String.valueOf(protagHP) +"/1500");

                btnNextTurn.setText("ATTACK");
                txtturnCount.setText("Turn "+String.valueOf(turnNumber-1) +"");

                txtLog.setText(""+String.valueOf(protagName) +" cast heal! It increased 50 points of HP!\n" +
                        ""+String.valueOf(protagName) +" now has "+String.valueOf(protagHP) + " HP!");

                if(antagHP < 0){ //even
                    skill1overlay.stop();
                    skill2overlay.stop();
                    skill3overlay.stop();
                    skill4overlay.stop();
                    protagswing.start();
                    antagscratch.stop();
                    dmgoverlay.stop();
                    txtLog.setText(""+String.valueOf(protagName) +" dealt "+String.valueOf(protagdps) + " damage to the enemy. The player is victorious!");
                    protagHP = 1500;
                    antagHP = 3000;
                    turnNumber= 2;
                    btnNextTurn.setText("Reset Game");
                }
                buttoncounter=3;

            break;

            case R.id.btnSkill3:

                skill1overlay.stop();
                skill2overlay.stop();
                skill3overlay.start();
                skill4overlay.stop();
                protagswing.stop();
                antagscratch.stop();
                dmgoverlay.stop();
                soundPool.play(skill3sound,1,1,0, 0,1);
                antagHP = antagHP - 50;
                turnNumber++;
                txtAntagHP.setText("HP: "+String.valueOf(antagHP) +"/5000");
                btnNextTurn.setText("ATTACK");
                txtturnCount.setText("Turn "+String.valueOf(turnNumber-1) +"");

                txtLog.setText(""+String.valueOf(protagName) +" cast binding! "+String.valueOf(antagName) +" can't move!\n" +
                        "It dealt "+String.valueOf(50) + " points of damage!");

                disabledstatus = true;
                statuscounter = 4;

                if(antagHP < 0){ //even
                    skill1overlay.stop();
                    skill2overlay.stop();
                    skill3overlay.stop();
                    skill4overlay.stop();
                    protagswing.start();
                    antagscratch.stop();
                    dmgoverlay.stop();
                    txtLog.setText(""+String.valueOf(protagName) +" dealt "+String.valueOf(protagdps) + " damage to the enemy. The player is victorious!");
                    protagHP = 1500;
                    antagHP = 3000;
                    turnNumber= 2;
                    btnNextTurn.setText("Reset Game");
                    txtturnCount.setText("Turn "+String.valueOf(turnNumber-1) +"");
                }
                buttoncounter=3;

                break;
            case R.id.btnSkill4:

                skill1overlay.stop();
                skill2overlay.stop();
                skill3overlay.stop();
                skill4overlay.start();
                protagswing.stop();
                antagscratch.stop();
                dmgoverlay.stop();
                soundPool.play(skill4sound,1,1,0, 0,1);
                protagHP = protagHP + 500;
                turnNumber++;
                btnNextTurn.setText("ATTACK");
                txtturnCount.setText("Turn "+String.valueOf(turnNumber-1) +"");

                txtLog.setText(""+String.valueOf(protagName) +" went to sleep!\n" +
                        "It restored "+String.valueOf(500) +" points of HP!");

                if(antagHP < 0){ //even
                    skill1overlay.stop();
                    skill2overlay.stop();
                    skill3overlay.stop();
                    skill4overlay.stop();
                    protagswing.start();
                    antagscratch.stop();
                    dmgoverlay.stop();
                    txtLog.setText(""+String.valueOf(protagName) +" dealt "+String.valueOf(protagdps) + " damage to the enemy. The player is victorious!");
                    protagHP = 1500;
                    antagHP = 3000;
                    turnNumber= 2;
                    btnNextTurn.setText("Reset Game");
                    txtturnCount.setText("Turn "+String.valueOf(turnNumber-1) +"");
                }
                buttoncounter=3;

                break;

            case R.id.btnNxtTurn:


                if(turnNumber % 2 == 1){ //odd
                    skill1overlay.stop();
                    skill2overlay.stop();
                    skill3overlay.stop();
                    skill4overlay.stop();
                    protagswing.start();
                    antagscratch.stop();
                    dmgoverlay.stop();
                    soundPool.play(protagatksound,1,1,0, 0,1);
                    antagHP = antagHP - protagdps;
                    turnNumber++;
                    txtAntagHP.setText("HP: "+String.valueOf(antagHP) +"/5000");
                    btnNextTurn.setText("ATTACK");
                    txtturnCount.setText("Turn "+String.valueOf(turnNumber-1) +"");

                    txtLog.setText(""+String.valueOf(protagName) +" dealt "+String.valueOf(protagdps) + " damage to the enemy.");

                    if(antagHP < 0){ //even
                        skill1overlay.stop();
                        skill2overlay.stop();
                        skill3overlay.stop();
                        skill4overlay.stop();
                        protagswing.start();
                        antagscratch.stop();
                        dmgoverlay.stop();
                        txtLog.setText(""+String.valueOf(protagName) +" dealt "+String.valueOf(protagdps) + " damage to the enemy. The player is victorious!");
                        protagHP = 1500;
                        antagHP = 3000;
                        turnNumber= 1;
                        buttoncounter=0;
                        btnNextTurn.setText("Reset Game");
                        txtturnCount.setText("Turn "+String.valueOf(turnNumber-1) +"");
                    }

                    buttoncounter--;
                }
                else if(turnNumber%2 != 1){ //even

                    if(disabledstatus==true){
                        txtLog.setText("The enemy is still stunned for "+String.valueOf(statuscounter)+ " turns");
                        skill1overlay.stop();
                        skill2overlay.stop();
                        skill3overlay.stop();
                        skill4overlay.stop();
                        protagswing.stop();
                        antagscratch.stop();
                        dmgoverlay.stop();
                        statuscounter--;
                        turnNumber++;
                        btnNextTurn.setText("ATTACK");
                        txtturnCount.setText("Turn "+String.valueOf(turnNumber-1) +"");
                        if(statuscounter==0){
                            disabledstatus=false;
                        }
                    }
                    else{
                        soundPool.play(antagatksound,1,1,0, 0,1);
                        skill1overlay.stop();
                        skill2overlay.stop();
                        skill3overlay.stop();
                        skill4overlay.stop();
                        protagswing.stop();
                        antagscratch.start();
                        dmgoverlay.start();
                        protagHP = protagHP - antagdps;
                        turnNumber++;
                        txtProtagHP.setText("HP: "+String.valueOf(protagHP) +"/1500");
                        btnNextTurn.setText("ATTACK");
                        txtturnCount.setText("Turn "+String.valueOf(turnNumber-1) +"");
                        protagswing.stop();

                        txtLog.setText(""+String.valueOf(antagName)+" dealt "+String.valueOf(antagdps)+ " damage to you.");

                        if(protagHP < 0){
                            txtLog.setText(""+String.valueOf(antagName)+" dealt "+String.valueOf(antagdps)+ " damage to you. Game Over");
                            skill1overlay.stop();
                            skill2overlay.stop();
                            skill3overlay.stop();
                            skill4overlay.stop();
                            protagswing.stop();
                            antagscratch.start();
                            dmgoverlay.start();
                            protagswing.stop();
                            protagHP = 1500;
                            antagHP = 3000;
                            turnNumber= 1;
                            buttoncounter=0;
                            btnNextTurn.setText("Reset Game");
                            txtturnCount.setText("Turn "+String.valueOf(turnNumber-1) +"");
                        }
                    }
                }
                break;
        }
    }

}