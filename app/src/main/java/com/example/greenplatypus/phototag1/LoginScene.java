package com.example.greenplatypus.phototag1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Login screen has username and password fields.
 * test play button is 'greyed' (unusable) until at least three characters are in username field
 * Username and password is passed to player database
 * check to see if username already has a selfie in database
 * press play if selfie exists pass user to main screen
 * press play if selfie does not exist pass user to take a selfie screen
 */

public class LoginScene extends BasePhotoTagActivity {
    EditText userNameText;
    EditText userPasswordText;
    Button playOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_scene);

        userNameText = (EditText) findViewById(R.id.loginScreenUserName);
        userPasswordText = (EditText) findViewById(R.id.loginScreenPassword);

        userNameText.addTextChangedListener(mTextWatcher);
        userPasswordText.addTextChangedListener(mTextWatcher);

        playOn = (Button) findViewById(R.id.PlayOn);
        playOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TakeSelfie.class);
                startActivity(i);
            }
        });

        // run once to disable if empty
        checkFieldsForEmptyValues();

    }
    //  create a textWatcher member
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();

        }
    };

    void GotoTakeSelfie() {


    }
    @Override
    protected void onStart() {
        super.onStart();

    }
    public void checkFieldsForEmptyValues(){
        String userName = acquireUserName();
        String userPassword = acquirePassword();
        Log.d("LOGIN_SCENE:", "checkFieldsForEmptyValues() userName.length= " + userName.length() + " pwd.len= " + userPassword.length());
        if(userName.length() > 2 && userPassword.length() >3 ){
            enablePlayOn();
        }else {
            disablePlayOn();
        }
    }

    void enablePlayOn(){
        playOn.setEnabled(true);
        playOn.setBackgroundColor(Color.BLUE);
    }

    void disablePlayOn(){
        playOn.setEnabled(false);
        playOn.setBackgroundColor(Color.argb(134, 219, 247, 125));
    }
   /* public void updateButtonState() {
        if (checkEditText(userNameText) && checkEditText(userPasswordText)) {
            playOn.setEnabled(false);
            playOn.setBackgroundColor(Color.argb(134, 219, 247, 125));
        } else {
            playOn.setEnabled(true);
            playOn.setBackgroundColor(Color.BLUE);
        }
    }
*/



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Close app?
    }

    /*  public void playOn(View view){
          if(isReady() && login()){
              navigator.navigateToTakeSelfie(this);
          }
      }
  */

    public String acquireUserName() {
        return userNameText.getText().toString();
    }

    public String acquirePassword() {
        return userPasswordText.getText().toString();
    }


}
