//Designed and developed by Nathan Garcia
//2020

package com.example.adlistener.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adlistener.R;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;

import java.util.ArrayList;
import java.util.Locale;

public class listenerPage extends Fragment {

    private EditText editText;
    private SpeechRecognizer asr;
    private Intent asrIntent;
    private AppEventsLogger logger;

    //31 total detection phrases
    private final String[] uttList = {
            "want to get",
            "wanna get",
            "gonna get",
            "going to get",
            "might get",
            "will get",
            "i'll get",
            "need to get",
            "may get",
            "like to get",
            "thinking about getting",
            "going to buy",
            "gonna buy",
            "want to buy",
            "wanna buy",
            "will buy",
            "i'll buy",
            "may buy",
            "might buy",
            "like to buy",
            "thinking about buying",
            "hope to purchase",
            "like to purchase",
            "going to order",
            "want to order",
            "thinking about ordering",
            "will order",
            "i'll order",
            "like to order",
            "i need",
            "i want",
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.listener, container,false);

        //Initialize App Event Logger. For use
        logger = AppEventsLogger.newLogger(getContext());

        //Initialized the use of Recognition Listener
        editText = rootView.findViewById(R.id.editText);
        asr = SpeechRecognizer.createSpeechRecognizer(getActivity().getBaseContext());
        asrIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        asrIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        asrIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        asr.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onError(int error) {
                if (error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT || error == SpeechRecognizer.ERROR_NO_MATCH) {
                    asr.stopListening();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) { 
                        e.printStackTrace();
                    }
                    asr.startListening(asrIntent);
                }
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> speech = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (!speech.isEmpty()) {
                    editText.append("\n");
                    editText.append(speech.get(0));
                    scan(speech.get(0));
                }
                if (!SpeechRecognizer.isRecognitionAvailable(getContext()))
                     asr.startListening(asrIntent);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        //When listen button is hit, starts the listening loop.
        final Button listenButton = rootView.findViewById(R.id.button);
        listenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.setText("listening...");
                asr.startListening(asrIntent);
            }
        });

        //When the stop button is hit, the listener object is destroyed.
        final Button stopButton = rootView.findViewById(R.id.button2);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asr.destroy();
            }
        });

        return rootView;
    }
    private void scan(String transcription){

        String product;
        transcription = transcription.toLowerCase();

        for (String utterance:uttList) {
            if (transcription.contains(utterance)) {

                editText.append("\nITEM FOUND: ");

                //Removing all excess junk from item.
                utterance = utterance.replaceAll("'","");
                transcription = transcription.replaceAll("'","");
                String toDelete = transcription.substring(0, (transcription.indexOf(utterance)+utterance.length()+1));
                product = transcription.replace(toDelete, "");

                editText.append(product);
                sendData(product);
                break;
            }
        }

    }
    //This captures the product mentioned by the users and sends the data bundle to the AD logger.
    private void sendData(String product){
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT, product);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "product");
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, "USD");
        logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_WISHLIST, params);
    }
}