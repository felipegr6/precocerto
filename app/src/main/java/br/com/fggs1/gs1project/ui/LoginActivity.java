package br.com.fggs1.gs1project.ui;

/**
 * Created by Gerson on 06/08/2016.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import br.com.fggs1.gs1project.R;
import br.com.fggs1.gs1project.helper.Persistencia;
import br.com.fggs1.gs1project.model.User;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private ValueEventListener listener;

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private String email;
    private String senha;

    private ProgressDialog dialog;

    @Bind(R.id.campoUsuario) EditText txtEmail;
    @Bind(R.id.campoSenha) EditText txtSenha;

    private Persistencia persistencia;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("usuarios");

        listener = new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<Map<String, User>> t =
                    new GenericTypeIndicator<Map<String, User>>() {
                    };

                Map<String, User> mUsers = dataSnapshot.getValue(t);

                for (User u : mUsers.values()) {
                    if (u.getEmail().equals(email) && u.getPassword().equals(senha)) {
                        goMain();
                        persistencia.setLogin(true);
                        if (dialog.isShowing()) dialog.dismiss();
                        return;
                    }
                }

                if (dialog.isShowing()) dialog.dismiss();
                Toast.makeText(LoginActivity.this, "Usuário/Senha inválidos.", Toast.LENGTH_SHORT)
                    .show();
            }

            @Override public void onCancelled(DatabaseError databaseError) {

            }
        };

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
        finish();
    }

    @SuppressWarnings("unused") @OnClick(R.id.btn_entrar) public void onEnterPressed() {

        email = txtEmail.getText().toString();
        senha = txtSenha.getText().toString();

        dialog = ProgressDialog.show(this, "", "Carregando...", false, false);

        if (email.isEmpty() || senha.isEmpty()) {
            if (dialog.isShowing()) dialog.dismiss();
            Toast.makeText(LoginActivity.this, "Email/Senha não preenchidos.", Toast.LENGTH_SHORT)
                .show();
            return;
        }

        myRef.addValueEventListener(listener);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressWarnings("unused") @OnClick(R.id.btn_cadastro) public void goToCadastro() {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }
}
