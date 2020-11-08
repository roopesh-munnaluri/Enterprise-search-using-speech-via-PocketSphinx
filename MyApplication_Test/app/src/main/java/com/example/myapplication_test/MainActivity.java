package com.example.myapplication_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

import static android.widget.Toast.makeText;
import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

public class MainActivity extends AppCompatActivity implements RecognitionListener{


    SpeechRecognizer recognizer;
    TextView recognizer_state, recognized_word;
    Button start;
    int conteo = 0;
    int permiso_flag=0;
    Handler a = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recognizer_state = (TextView) findViewById(R.id.textView2);
        recognized_word = (TextView) findViewById(R.id.textView3);
        start = (Button)findViewById(R.id.button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncTask<Void, Void, Exception>() {
                    @Override
                    protected Exception doInBackground(Void... params) {
                        try {
                            Assets assets = new Assets(getApplicationContext());
                            File assetDir = assets.syncAssets();
                            setupRecognizer(assetDir);
                        } catch (IOException e) {
                            return e;
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Exception result) {
                        if (result != null) {
                        } else {
                            FireRecognition();
                        }
                    }
                }.execute();

            }
        });

    }

    @Override
    public void onStop(){
        super.onStop();

    }


    public void FireRecognition(){
        Log.d("Recognition","Recognition Started");
        recognizer_state.setText("Recognition Started!");
        conteo = 0;
        recognizer.stop();
        recognizer.startListening("digits");
    }

    @Override
    public void onBeginningOfSpeech() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onEndOfSpeech() {
        // TODO Auto-generated method stub

    }

    private void setupRecognizer(File assetsDir) throws IOException {
        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))

                .setRawLogDir(assetsDir) // To disable logging of raw audio comment out this call (takes a lot of space on the device)

                .getRecognizer();
        recognizer.addListener(this);


        File digitsGrammar = new File(assetsDir, "digits.gram");
        recognizer.addGrammarSearch("digits", digitsGrammar);
        

    }

    @Override
    public void onResult(Hypothesis hup) {
        conteo +=1;
        if(conteo==1){
            //recognizer.stop();
            Timer();
        }
    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onTimeout() {

    }

    public void Timer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    a.post(new Runnable() {
                        @Override
                        public void run() {

                            FireRecognition();
                        }
                    });
                } catch (Exception e) {
                }
            }

        }).start();
    }

    @Override
    public void onPartialResult(Hypothesis arg0) {
        if(arg0 == null){ return; }
        String comando = arg0.getHypstr();
        recognized_word.setText(comando);
        conteo +=1;
        if(conteo==1){
            conteo = 0;
            Log.d("Res", comando);
            //recognizer.stop();
            Timer();
        }

    }


}