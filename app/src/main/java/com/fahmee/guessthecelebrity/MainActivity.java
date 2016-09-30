package com.fahmee.guessthecelebrity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    ImageView imageView;
    ArrayList<String> celebUrls = new ArrayList<>();
    ArrayList<String> celebNames = new ArrayList<>();
    int locationAnswer=0;
    int incorrectAnswer;
    DownloadImage imageTask;
    DownloadContent task;
    int choosenCeleb;
    String[] answer = new String [4] ;
    Button button0;
    Button button1;
    Button button2;
    Button button3;


    public void guessResult(View view)throws Exception{

        if(view.getTag().toString().equals(Integer.toString(locationAnswer))){

            Toast.makeText(getApplicationContext(),"Correct!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"Wrong! It was " + celebNames.get(choosenCeleb),Toast.LENGTH_LONG).show();

        }

        guessLogic();
    }



    public void guessLogic(){

    Random random = new Random();

         choosenCeleb = random.nextInt(celebUrls.size());
        imageTask = new DownloadImage();

        Bitmap celebIm;

        try {
            celebIm = imageTask.execute(celebUrls.get(choosenCeleb)).get();

            imageView.setImageBitmap(celebIm);

            locationAnswer = random.nextInt(4);

            for(int i=0; i<4;i++){

                if(i==locationAnswer){
                    answer[i]= celebNames.get(choosenCeleb);


                }else{

                    incorrectAnswer = random.nextInt(celebUrls.size());
                    while(incorrectAnswer==choosenCeleb){
                        incorrectAnswer = random.nextInt(celebUrls.size());
                    }
                    answer[i]= celebNames.get(incorrectAnswer);

                }

            }
            button0.setText(answer[0]);
            button1.setText(answer[1]);
            button2.setText(answer[2]);
            button3.setText(answer[3]);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView);
        button0 = (Button)findViewById(R.id.button);
        button1 = (Button)findViewById(R.id.button2);
        button2 = (Button)findViewById(R.id.button3);
        button3 = (Button)findViewById(R.id.button4);


        task = new DownloadContent();

        String result=null;

        try {
            result = task.execute("http://www.posh24.com/celebrities").get();

            String[] celebImage = result.split("<div class=\"sidebarContainer\">");

            Pattern p = Pattern.compile("<img src=\"(.*?)\"");
            Matcher m = p.matcher(celebImage[0]);

            while(m.find()){
                celebUrls.add(m.group(1));

            }

            p = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(celebImage[0]);

            while (m.find()){

                celebNames.add(m.group(1));

            }

            ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    guessLogic();
    }
}
