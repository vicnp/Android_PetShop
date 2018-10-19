package net.simplifiedcoding.androidphpmysql;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Belal on 26/11/16.
 */

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_EMAIL = "useremail";
    private static final String KEY_USER_ID = "userid";
    private static final String KEY_NOME= "mysharedpref12";
    private static final String KEY_SOBRENOME = "mysharedpref12";
    private static final String KEY_CPF = "mysharedpref12";
    private static final String KEY_CIDADE = "mysharedpref12";
    private static final String KEY_BAIRRO= "mysharedpref12";
    private static final String KEY_RUA = "mysharedpref12";
    private static final String KEY_CEP = "mysharedpref12";
    private static final String KEY_NUMERO = "mysharedpref12";
    private static final String KEY_TELEFONEUM = "mysharedpref12";
    private static final String KEY_TELEFONEDOIS = "mysharedpref12";


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

    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }
}
