package com.shuan.palakkadweekly.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.shuan.palakkadweekly.Parser.HttpUrlConnectionParser;
import com.shuan.palakkadweekly.Parser.php;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.edittext.MaterialEditText;
import com.shuan.palakkadweekly.fancyButton.FancyButton;

import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    public MaterialEditText name,email,password,phNo,addr;
    public FancyButton submit,clear;
    public ProgressDialog pDialog;
    public String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public HttpUrlConnectionParser parser=new HttpUrlConnectionParser();
    public php call=new php();
    public HashMap<String,String> regData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name= (MaterialEditText) findViewById(R.id.name);
        email= (MaterialEditText) findViewById(R.id.email);
        password= (MaterialEditText) findViewById(R.id.password);
        phNo= (MaterialEditText) findViewById(R.id.phone_number);
        addr= (MaterialEditText) findViewById(R.id.address);
        submit= (FancyButton) findViewById(R.id.btn_register);
        clear= (FancyButton) findViewById(R.id.btn_clear);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(email.getText().toString().trim().matches(emailPattern) && s.length()>0){}
                else {email.setError("Invalid Email Id");}
            }
        });

        submit.setOnClickListener(this);
        clear.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                if(name.getText().toString().length()==0){
                    name.setError("Enter User Name");
                }else if(email.getText().toString().length()==0){
                    email.setError("Enter Valid Email Id");
                }else if(password.getText().toString().length()==0){
                    password.setError("Enter Correct Password");
                }else if(phNo.getText().toString().length()==0){
                    phNo.setError("Enter Valid Phone Number");
                }else if(addr.getText().toString().length()==0){
                    addr.setError("Enter Valid Address");
                }else {
                    new Register().execute();
                }
                break;
            case R.id.btn_clear:
                clearText();
                break;
        }
    }

    public class Register extends AsyncTask<String,String,String>{

        String rName=name.getText().toString();
        String remail=email.getText().toString();
        String rpassword=password.getText().toString();
        String rphno=phNo.getText().toString();
        String raddr=addr.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(RegisterActivity.this);
            pDialog.setTitle("Registration");
            pDialog.setMessage("Saving your Data!... Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            regData=new HashMap<String,String>();

            regData.put("name",rName);
            regData.put("emailId",remail);
            regData.put("password",rpassword);
            regData.put("phoneNumber",rphno);
            regData.put("address",raddr);


             try{
                 JSONObject json=parser.makeHttpUrlConnection(call.register,regData);
                 int succ=json.getInt("success");
                 if(succ==0){
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_SHORT).show();
                         }
                     });
                 }else {
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             Toast.makeText(getApplicationContext(),"Successfully Register",Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                             finish();
                         }
                     });
                 }
             }catch (Exception e){}

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.cancel();
        }
    }

    private void clearText() {
        name.setText("");
        email.setText("");
        password.setText("");
        phNo.setText("");
        addr.setText("");
    }
}
