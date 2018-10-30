package net.simplifiedcoding.androidphpmysql;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

/**
 * Created by Belal on 26/11/16.
 */

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_EMAIL = "usermail";
    private static final String KEY_USER_ID = "userid";
    private static final String KEY_NOME= "usernome";
    private static final String KEY_SOBRENOME = "usersobre";
    private static final String KEY_CPF = "usercpf";
    private static final String KEY_CIDADE = "usercidade";
    private static final String KEY_BAIRRO= "userbairro";
    private static final String KEY_RUA = "userrua";
    private static final String KEY_CEP = "usercep";
    private static final String KEY_NUMERO = "usernumero";
    private static final String KEY_TELEFONEUM = "usertelefoneum";
    private static final String KEY_TELEFONEDOIS = "usertelefonedois";


    private SharedPrefManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(User userParams){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID, userParams.getId());
        editor.putString(KEY_USER_EMAIL, userParams.getEmail());
        editor.putString(KEY_USERNAME, userParams.getUsername());
        editor.putString(KEY_NOME, userParams.getNome());
        editor.putString(KEY_SOBRENOME, userParams.getSobrenome());
        editor.putString(KEY_CPF, userParams.getCpf());
        editor.putString(KEY_TELEFONEUM, userParams.getTelefoneUm());
        editor.putString(KEY_TELEFONEDOIS, userParams.getTelefoneDois());
        editor.putString(KEY_CIDADE, userParams.getCidade());
        editor.putString(KEY_BAIRRO, userParams.getBairro());
        editor.putString(KEY_RUA, userParams.getRua());
        editor.putString(KEY_NUMERO, userParams.getNumero());
        editor.putString(KEY_CEP, userParams.getCep());

        editor.apply();

        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USERNAME, null) != null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true; 
    }


    public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getUser_Name(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NOME, null);
    }

    public String getUser_SobreNome(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SOBRENOME, null);
    }

    public String getUser_CPF(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CPF, null);
    }

    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

}
