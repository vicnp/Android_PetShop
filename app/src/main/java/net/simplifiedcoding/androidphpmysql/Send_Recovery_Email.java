package net.simplifiedcoding.androidphpmysql;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Send_Recovery_Email extends AppCompatActivity {
    TextView txtemail;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_send__recovery__email);
        txtemail = (TextView) findViewById(R.id.txt_email);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Enviando email...");
    }

    public void SendEmail(View v) {
        if(TextUtils.isEmpty(txtemail.getText())){
            Toast.makeText(v.getContext(),"Digite um email!",Toast.LENGTH_SHORT).show();
        }else{
            if(isEmailValid(txtemail.getText().toString())){

                final String email = txtemail.getText().toString().trim();
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        Constants.URL_RECOVERY,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try {
                                    //Log.d("Response", response);
                                    JSONObject obj = new JSONObject(response);
                                    if(!obj.getBoolean("error")){
                                        Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                                        finish();
                                    }else{
                                        Toast.makeText(
                                                getApplicationContext(),
                                                obj.getString("message"),
                                                Toast.LENGTH_LONG
                                        ).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //progressDialog.dismiss();
                                Toast.makeText(
                                        getApplicationContext(),
                                        error.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        return params;
                    }

                };

                RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

            }else{
                Toast.makeText(v.getContext(),"Digite um email válido!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
