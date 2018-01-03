package com.google.android.gms.samples.vision.amazingocr;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.io.FileOutputStream;

import static com.google.android.gms.samples.vision.amazingocr.R.styleable.AlertDialog;
import static com.google.android.gms.samples.vision.amazingocr.R.styleable.View;


public class Output_activity extends AppCompatActivity {
    final Context CONTEXT = this;
    private static final String TAG = "Output_activity";

    EditText output;
    Button pdfButton1;
    String userText;
    Button pdfTextShareB;
    Button pdfShareB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_activity);

        output=(EditText) findViewById(R.id.img_output);
        pdfButton1 = (Button) findViewById(R.id.pdfButton);
        pdfTextShareB = (Button) findViewById(R.id.pdfTextShareButton);

        Bundle bundle = getIntent().getExtras();
        final String message = bundle.getString("key");

        output.setText(message);



        pdfButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View view = (LayoutInflater.from(Output_activity.this)).inflate(R.layout.user_input, null);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Output_activity.this);
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


        pdfTextShareB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pdfTextIntent = new Intent(Intent.ACTION_SEND);
                pdfTextIntent.setType("text/plain");
                String shareSubject = "Email from Amazing OCR Android App";
                pdfTextIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                pdfTextIntent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(pdfTextIntent, "Share Using"));
            }
        });




    }

    public void pdfConverter(String pdfText, String userLekha){

        String outputPath =  getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath()
                + userLekha + ".pdf";

        try {
            Document document = new Document();
            // Log.d(TAG,textFilePath);

            ///document.open();

            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            Log.d(TAG,"You did it");
            document.open();
            document.add(new Paragraph(pdfText));
            Log.d(TAG,pdfText);
            document.close();

            /// document.add(new Paragraph("Hello Android!! :)"));

            // Log.d(TAG,"You did it");

            ///document.close();


        } catch (Exception e) {
            showAlertDialog("Converting text...", "An error has occurred: " + e.getMessage());
        }

        Log.d(TAG, userLekha);
        Log.d(TAG,outputPath);

        Toast.makeText(this, outputPath, Toast.LENGTH_LONG);


//        Uri pdfUri=Uri.parse(getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath()
//                +"data"+ userLekha + ".pdf");
//
//        File pdfDir = new File(Environment.getExternalStoragePublicDirectory(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath());
//        File pdfFile = null;
//        try {
//             pdfFile = File.createTempFile(userLekha, ".pdf", pdfDir);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Intent pdfTextIntent = new Intent(Intent.ACTION_SEND);
//        pdfTextIntent.setType("pdf/*");
//        String shareSubject = "Email from Amazing OCR Android App";
//        pdfTextIntent.putExtra(Intent.EXTRA_STREAM, pdfFile);
//        pdfTextIntent.putExtra(Intent.EXTRA_TEXT, shareSubject);
//        startActivity(Intent.createChooser(pdfTextIntent, "Share Using"));


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

