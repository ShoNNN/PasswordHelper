package com.vladislavkulikov.school.passwordlayout;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView mResultTextView;
    private EditText mSourceTextView;
    private View mCopyConvertedPasswordButton;
    private ImageView mPasswordQualityImageView;
//    private TextView mQualityTextView;

    private TextView mResultPasswordGeneratorTextView;
    private View mGeneratePasswordButton;
    private CompoundButton mUseUpperCase;
    private CompoundButton mUseDigits;
    private CompoundButton mUseServiceSimbols;
    private SeekBar mSeekBarPasswordLength;
    private View mCopyGeneratedPasswordButton;
    private TextView mGeneratedPasswordLength;

    private PasswordsConverter mHelper;
    private PasswordsGenerator mGenerator;

    private final int DEFAULT_PASSWORD_LENGTH = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPasswordConverter();
        initPasswordGenerator();

    }

    private void initPasswordGenerator() {
        /*----- generate password -----*/

        mUseUpperCase = findViewById(R.id.check_upperCase);
        mUseDigits = findViewById(R.id.check_digits);
        mUseServiceSimbols = findViewById(R.id.check_serviceSimbols);
        mResultPasswordGeneratorTextView = findViewById(R.id.resultPasswordGenerator_textView);
        mGeneratedPasswordLength = findViewById(R.id.generatedPasswordLength_textView);
        mGeneratePasswordButton = findViewById(R.id.generatePassword_button);
        mSeekBarPasswordLength = findViewById(R.id.password_length_seekbar);
        mCopyGeneratedPasswordButton = findViewById(R.id.copy_generation_password_button);

        mSeekBarPasswordLength.setMax(10);

        mSeekBarPasswordLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String length = getResources().getQuantityString(R.plurals.plurals_password_length, progress, progress);
                mGeneratedPasswordLength.setText(length);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mGeneratePasswordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mGenerator = new PasswordsGenerator(
                        getResources().getStringArray(R.array.englishSimbols),
                        getResources().getStringArray(R.array.digits),
                        getResources().getStringArray(R.array.serviceSimbols),
                        DEFAULT_PASSWORD_LENGTH + mSeekBarPasswordLength.getProgress(),
                        mUseUpperCase.isChecked(),
                        mUseDigits.isChecked(),
                        mUseServiceSimbols.isChecked());

                mResultPasswordGeneratorTextView.setText(mGenerator.generate());
            }
        });

        mCopyGeneratedPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setPrimaryClip(ClipData.newPlainText(
                        MainActivity.this.getString(R.string.clipboard_title),
                        mResultPasswordGeneratorTextView.getText()
                ));
                Toast.makeText(MainActivity.this, R.string.clipboard_title, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initPasswordConverter() {
        /*----- convert password -----*/

        mResultTextView = findViewById(R.id.result_text);
        mSourceTextView = findViewById(R.id.password_convert_edit_text);
        mCopyConvertedPasswordButton = findViewById(R.id.copy_button);
        mPasswordQualityImageView = findViewById(R.id.quality);

        mHelper = new PasswordsConverter(
                getResources().getStringArray(R.array.russian),
                getResources().getStringArray(R.array.english)
        );

        mCopyConvertedPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setPrimaryClip(ClipData.newPlainText(
                        MainActivity.this.getString(R.string.clipboard_title),
                        mResultTextView.getText()
                ));

                Toast.makeText(MainActivity.this, R.string.clipboard_title, Toast.LENGTH_LONG).show();
            }


        });

        mSourceTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mResultTextView.setText(mHelper.convert(s));
                mCopyConvertedPasswordButton.setEnabled(s.length() > 0);
                mPasswordQualityImageView.setImageLevel(mHelper.getQuality(s) * 1000);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
