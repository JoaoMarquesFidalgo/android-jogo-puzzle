package org.example.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUser(Utilizador utilizador) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(utilizador);
        prefsEditor.putString("Utilizador", json);
        prefsEditor.commit();
    }

    public Utilizador getUser() {
        Gson gson = new Gson();
        String json = prefs.getString("Utilizador", "");
        Utilizador obj = gson.fromJson(json, Utilizador.class);

        return obj;
    }
}
