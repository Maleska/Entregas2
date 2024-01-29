package com.consulsoftware.entregas.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.consulsoftware.entregas.R;

public class MetodoPagoActivity  extends AppCompatActivity {
public Button aceptar;
public RadioButton rbEfectivo,rbTarjeta,rbTransCC,rbTransDE,rbTA,rbTK,rbMP,rbOP,rbPP,rbTC;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_metodo_pago);
        init();
        inicializacion();
    }

    private void init(){
        aceptar = findViewById(R.id.btnAceptar);
        rbEfectivo = findViewById(R.id.rbEfectivo);
        rbTarjeta = findViewById(R.id.rbTarjeta);
        rbTransCC = findViewById(R.id.rbCC);
        rbTransDE = findViewById(R.id.rbDE);
        rbTA = findViewById(R.id.rbTA);
        rbTK = findViewById(R.id.rbTK);
        rbMP = findViewById(R.id.rbMP);
        rbOP = findViewById(R.id.rbOP);
        rbPP = findViewById(R.id.rbPP);
        rbTC = findViewById(R.id.rbTC);
    }
    private void inicializacion(){
            aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    if (rbEfectivo.isChecked()){
                        intent.putExtra("data", "1");
                    } else if (rbTarjeta.isChecked()) {
                        intent.putExtra("data", "2");
                    } else if (rbTransCC.isChecked()) {
                        intent.putExtra("data", "3");
                    } else if (rbTransDE.isChecked()) {
                        intent.putExtra("data", "4");
                    }else if (rbTA.isChecked()){
                        intent.putExtra("data", "5");
                    }else if (rbTK.isChecked()){
                        intent.putExtra("data","6");
                    }else if(rbMP.isChecked()){
                        intent.putExtra("data","7");
                    }else if(rbOP.isChecked()){
                        intent.putExtra("data","8");
                    }else if (rbPP.isChecked()){
                        intent.putExtra("data","9");
                    }else if (rbTC.isChecked()){
                        intent.putExtra("data","10");
                    }

                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        finish();
    }
}
