package br.com.fggs1.gs1project.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;
import br.com.fggs1.gs1project.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

        String nome = txtEmail.getText().toString();
        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();
        String confirmarSenha = txtConfirmarSenha.getText().toString();

        if (nome.isEmpty()) {
            Toast.makeText(this, "Campo Nome em branco.", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Campo E-mail em branco.", Toast.LENGTH_SHORT).show();
        } else if (senha.isEmpty()) {
            Toast.makeText(this, "Campo Senha em branco.", Toast.LENGTH_SHORT).show();
        } else if (confirmarSenha.isEmpty()) {
            Toast.makeText(this, "Campo Confirmar Senha em branco.", Toast.LENGTH_SHORT).show();
        } else if (!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "Campos Senha e Confirmar Senha não coincidem", Toast.LENGTH_SHORT)
                .show();
        }
    }
}
