package br.com.fggs1.gs1project.ui;

/**
 * Created by Gerson on 06/08/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import br.com.fggs1.gs1project.R;
import br.com.fggs1.gs1project.helper.Persistencia;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private Persistencia persistencia;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        persistencia = new Persistencia(this);

        View cadastro = findViewById(R.id.cadastro);
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent telaCadastro = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(telaCadastro);
            }
        });

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override public void onSuccess(LoginResult loginResult) {
                persistencia.setLogin(true);
                goMain();
            }

            @Override public void onCancel() {
                Log.w("fb", "cancel");
                persistencia.setLogin(false);
            }

            @Override public void onError(FacebookException error) {
                Log.w("fb", error.toString());
                persistencia.setLogin(false);
            }
        });
    }

    private void goMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void goToCadastro() {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
