package com.google.android.gms.samples.vision.amazingocr;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class Output_activity_offline extends AppCompatActivity {

    final Context CONTEXT = this;
    private static final String TAG = "Output_activity";

    EditText output;
    Button pdfButton2;
    String userText;

    ProgressDialog progressDialog;
    String str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_offline2);

        output=(EditText)findViewById(R.id.img_output_offline);
        pdfButton2= (Button) findViewById(R.id.pdfButtonOffline);

        Bundle bundle = getIntent().getExtras();
        final String message = bundle.getString("outputstr");


        Toast.makeText(Output_activity_offline.this,"Text Detected",Toast.LENGTH_SHORT).show();



        output.setText(message);



        pdfButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View view = (LayoutInflater.from(Output_activity_offline.this)).inflate(R.layout.user_input, null);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Output_activity_offline.this);
                alertBuilder.setView(view);
                final EditText userInput = (EditText) view.findViewById(R.id.userinput);

                alertBuilder.setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userText = String.valueOf(userInput.getText());
                                pdfConverter(message, userText);

                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

    }

    public void pdfConverter(String pdfText, String userLekha){

        FileInputStream fis = null;
        DataInputStream in = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String outputPath =  getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath()
                + userLekha+ ".pdf";

        try {
            Document document = new Document();
            // Log.d(TAG,textFilePath);

            ///document.open();

            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            Log.d(TAG,"You did it");
            document.open();
            document.add(new Paragraph(pdfText));
            document.close();

            /// document.add(new Paragraph("Hello Android!! :)"));

            // Log.d(TAG,"You did it");

            ///document.close();
        } catch (Exception e) {
            showAlertDialog("Converting text...", "An error has occurred: " + e.getMessage());
        }

    }



    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CONTEXT);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }



    private void setClipboard(String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
