package com.example.myapplication_test;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

import static android.widget.Toast.makeText;
import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, RecognitionListener {

    Button button_search,button_input;
    EditText input;
    String text;
    int status = 0;
    Spinner spinner;
    SpeechRecognizer recognizer;
    int conteo = 0;
    Handler a = new Handler();
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
            return;
        }
        input = (EditText) findViewById(R.id.editTextTextPersonName);
        button_search = (Button)findViewById(R.id.button);
        button_search.setEnabled(false);
        button_search.setTextColor(this.getResources().getColor(R.color.dark_grey));
        button_input = (Button)findViewById(R.id.button1);
        button_input.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(status == 0) {
                    button_input.setText("Stop");
                    input.setText("");
                    status = status + 1;
                    button_search.setEnabled(false);
                    button_search.setText("");
                    runRecognizerSetup();
                }
                else{
                    button_input.setText("Listen");
                    status = status + 1;
                    button_search.setEnabled(true);
                    button_search.setTextColor(R.color.dark_grey);
                    button_search.setText("Search");
                    status = 0;
                    recognizer.stop();
                }
            }
        });
        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner);
        // Spinner click listener
        spinner.setOnItemSelectedListener( this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Amazon");
        categories.add("Best Buy");
        categories.add("Wb Mason");
        categories.add("Walmart");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_spinner = spinner.getSelectedItem().toString();
                if (input_spinner == "Amazon"){
                    text = input.getText().toString();
                    String url = "https://www.amazon.com/s?k=" + text ;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
                if (input_spinner == "Best Buy"){
                    text = input.getText().toString();
                    String url =  "https://www.bestbuy.com/site/searchpage.jsp?st="+ text;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
                if (input_spinner == "Wb Mason"){
                    text = input.getText().toString();
                    String url = "https://www.wbmason.com/SearchResults.aspx?Keyword="+ text +"&sc=BM&fi=1&fr=1";
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
                if(input_spinner == "Walmart"){
                    text = input.getText().toString();
                    String url =  "https://www.walmart.com/search/?query="+ text;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
            }
        });
    }
    public void runRecognizerSetup() {
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                   // input.setText("Started");
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
                    input.setText("Recognition Started!");
                    recognizer.startListening("digits");
                    GifImageView gifImageView = (GifImageView) findViewById(R.id.imageView);
                    gifImageView.setGifImageResource(R.drawable.play);
                }
            }
        }.execute();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    @Override
    public void onStop(){
        super.onStop();
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

        File digitsGrammar = new File(assetsDir, "sample.dmp");
        recognizer.addNgramSearch("digits",digitsGrammar);
    }
    @Override
    public void onResult(Hypothesis hup) {
        if(hup.getHypstr() == null) {
            input.setText("Nothing Recognized");
        }
        else{
            input.setText(hup.getHypstr());
        }
    }
    @Override
    public void onError(Exception e) {

    }
    @Override
    public void onTimeout() {

    }
    @Override
    public void onPartialResult(Hypothesis arg0) {
        if(arg0 == null){ return; }
        String comando = arg0.getHypstr();
        conteo +=1;
        if(conteo==1){
            conteo = 0;
            Log.d("Res", comando);
        }
    }
}