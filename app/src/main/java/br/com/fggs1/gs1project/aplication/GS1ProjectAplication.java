package br.com.fggs1.gs1project.aplication;

import android.app.Application;
import com.facebook.FacebookSdk;

/**
 * Created by Gerson on 06/08/2016.
 */
public class GS1ProjectAplication extends Application {
    @Override public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
    }
}
