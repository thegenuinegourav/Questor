package com.google.android.gms.samples.vision.ocrreader;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.android.gms.samples.vision.ocrreader.OcrCaptureActivity.TextBlockObject;

public class MainActivity extends AppCompatActivity {

    private TextView textValue;

    private MenuItem mSearchAction;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textValue = (TextView) findViewById(R.id.text_value);

        String textReceived = getIntent().getStringExtra(TextBlockObject);
        ((TextView) findViewById(R.id.text_value)).setText(textReceived);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    private static boolean isContain(String source, String subItem) {
        String pattern = "\\b" + subItem + "\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(source);
        return m.find();
    }


    public void doSearch(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Search");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("GO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputText = "";
                if (!input.getText().toString().equals(""))
                    inputText = input.getText().toString();
                String result = textValue.getText().toString().toLowerCase();

                if (isContain(result, inputText)) {
                    int counter = 0, pos = 0, counterForWords = 0;
                    for (int i = 0; i < result.indexOf(inputText); i++) {
                        if (result.charAt(i) == '\n') {
                            counter++;
                            pos = i;
                        }
                    }

                    for (int i = pos; i < result.indexOf(inputText); i++) {
                        if (result.charAt(i) == ' ') {
                            counterForWords++;
                        }
                    }

                    counter++;
                    counterForWords++;

                    Toast.makeText(MainActivity.this, "Word present at line " + counter + ", position " + counterForWords, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}
