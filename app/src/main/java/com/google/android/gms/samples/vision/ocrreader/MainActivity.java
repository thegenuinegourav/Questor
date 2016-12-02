package com.google.android.gms.samples.vision.ocrreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

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

        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog, false)
                .show();
        dialog.findViewById(R.id.dialog_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = "";
                EditText input = (EditText) dialog.findViewById(R.id.input);
                if (!input.getText().toString().equals(""))
                    inputText = input.getText().toString().toLowerCase();
                String result = textValue.getText().toString();
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
        dialog.findViewById(R.id.dialog_cancel).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
    }

}
