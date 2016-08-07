package br.com.fggs1.gs1project.ui;

import android.app.Activity;
import android.os.Bundle;

import br.com.fggs1.gs1project.R;
import butterknife.ButterKnife;

/**
 * Created by Gerson on 07/08/2016.
 */
public class CadastroActivity extends Activity{

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        ButterKnife.bind(this);
    }
}
