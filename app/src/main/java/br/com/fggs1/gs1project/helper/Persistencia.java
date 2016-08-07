package br.com.fggs1.gs1project.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Persistencia {

    private final String ARG_IS_LOGGED = "isLogged";
    private SharedPreferences preferences;

    public Persistencia(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setLogin(boolean isLogged) {

        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(ARG_IS_LOGGED, isLogged);
        editor.apply();
    }

    public boolean isLogged() {
        return preferences.getBoolean(ARG_IS_LOGGED, false);
    }
}
