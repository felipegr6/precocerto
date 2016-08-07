package br.com.fggs1.gs1project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;
import br.com.fggs1.gs1project.R;
import br.com.fggs1.gs1project.helper.Persistencia;
import br.com.fggs1.gs1project.model.User;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {

    @Bind(R.id.campoUsuario) EditText txtNome;
    @Bind(R.id.campoEmail) EditText txtEmail;
    @Bind(R.id.campoSenha) EditText txtSenha;
    @Bind(R.id.campoSenhaConfirm) EditText txtConfirmarSenha;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        ButterKnife.bind(this);
    }

    @SuppressWarnings("unused") @OnClick(R.id.btn_cadastrar) public void onSubmit() {

        String nome = txtNome.getText().toString();
        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();
        String confirmarSenha = txtConfirmarSenha.getText().toString();

        if (nome.isEmpty()) {
            Toast.makeText(this, "Campo Nome em branco.", Toast.LENGTH_SHORT).show();
            return;
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Campo E-mail em branco.", Toast.LENGTH_SHORT).show();
            return;
        } else if (senha.isEmpty()) {
            Toast.makeText(this, "Campo Senha em branco.", Toast.LENGTH_SHORT).show();
            return;
        } else if (confirmarSenha.isEmpty()) {
            Toast.makeText(this, "Campo Confirmar Senha em branco.", Toast.LENGTH_SHORT).show();
            return;
        } else if (!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "Campos Senha e Confirmar Senha não coincidem", Toast.LENGTH_SHORT)
                .show();
            return;
        }

        Persistencia persistencia = new Persistencia(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("usuarios");

        User user = new User(nome, email, senha);

        reference.push().setValue(user);
        persistencia.setLogin(true);

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
