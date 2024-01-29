package com.consulsoftware.entregas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigActivity extends AppCompatActivity {

    Button btnSave;
    RadioButton rbServicesLayer, rbWCF;
    RadioGroup radioGroup;
    private DBHandler dbHandler;
    public static boolean wcf;
    public static boolean sl;
    EditText etIPublica;

    @Override
    protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_config);

        btnSave = (Button) findViewById(R.id.btnsave);
        etIPublica = (EditText) findViewById(R.id.etIPublica);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroupServices);

        // and passing our context to it.
        dbHandler = new DBHandler(ConfigActivity.this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String ippublica = etIPublica.getText().toString();

                    if (ippublica.equals("")){
                        Toast.makeText(ConfigActivity.this, "Favor de agregar una ip", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(radioGroup.getCheckedRadioButtonId() != -1){
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        if (selectedId == 2131231264){
                            sl = true;
                            wcf = false;
                        }else {
                            sl = false;
                            wcf = true;
                        }
                    }

                    dbHandler.addNewUser(etIPublica.getText().toString(),(sl == true) ? 1:0,(wcf == true) ? 1:0);
                }catch (Exception e){
                    Toast.makeText(ConfigActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }

                etIPublica.setText("");
                finish();

            }
        });


    }

}
