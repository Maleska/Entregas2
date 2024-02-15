package com.consulsoftware.entregas;

import static android.widget.Toast.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.consulsoftware.entregas.adapters.ListAdapter;
import com.consulsoftware.entregas.adapters.MetodoPagoActivity;
import com.consulsoftware.entregas.adapters.docAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;


public class InicioActivity  extends AppCompatActivity {

    private Button mPickDateButton,btnload,btnmodalaceptar;
    //Bundle extras = getIntent().getExtras();
    private Context mContext;
    public String docentrySAP,result,un, codeSAP;
    public  int valor;
    private RequestQueue queue;
    private ListView lv;
    private ArrayList<com.consulsoftware.entregas.adapters.ListAdapter> adapters;
    private docAdapter docAdapter;
    private ImageView ivLogout;
    private DBHandler dbHandler;
    private RadioButton rbmp,rbpe;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);;

        //user = extras.getString("usuario");
        mPickDateButton = findViewById(R.id.btnEntrar);
        btnload = findViewById(R.id.btnLoad);
        ivLogout = findViewById(R.id.ivLogOut);



        queue = Volley.newRequestQueue(this);
        lv = findViewById(R.id.lv);
        //ArrayAdapter<String> arrayAdapter;
        mContext = InicioActivity.this  ;
        adapters = new ArrayList<com.consulsoftware.entregas.adapters.ListAdapter>();
        adapters= new ArrayList<>();
        inicializar();

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();

        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        materialDateBuilder.setTitleText("Selecciona una fecha.");

        mPickDateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // getSupportFragmentManager() to
                        // interact with the fragments
                        // associated with the material design
                        // date picker tag is to get any error
                        // in logcat
                        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long  selection) {
                //mShowSelectedDateText.setText("Selected Date is : " + materialDatePicker.getHeaderText());
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(selection);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate  = format.format(calendar.getTime());

                makeText(mContext, formattedDate, LENGTH_SHORT).show();
            }
        });
        //validLicense();

  /*      btnload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarpedido1();
            }
        });*/

    }
    private  void inicializar(){

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                docentrySAP = ((TextView)view.findViewById(R.id.tvDocEntry)).getText().toString();
                un = ((TextView)view.findViewById(R.id.tv_UN)).getText().toString();
                //openAlertDialog();
                //onCreateDialog().show();
                onCreateDialog();
            }
        });
        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singout();
            }
        });

    }
    private void singout(){
        FirebaseAuth.getInstance().signOut();
        Intent activityConfig = new Intent(InicioActivity.this, MainActivity.class);
        startActivity(activityConfig);
    }
    @Override
    protected void onStart(){
        super.onStart();
        validUserWhitSAP();
    }
    public  void  consultarpedido1() {
        dbHandler = new DBHandler(InicioActivity.this);
        //String url = "http://172.16.101.125:8088/Service1.svc/getListOV";
        String url = "http://" + dbHandler.readsql() + "/getListOV";

        try {

            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray coursesJsonArray) {
                            String docnum,nombre,docentry,numpedido,total,uns;
                            ArrayList<HashMap> coursesBasicInfoList = new ArrayList<>();

                            try {
                                for (int i=0; i < coursesJsonArray.length(); i++){
                                    JSONObject courseInfo = coursesJsonArray.getJSONObject(i);

                                    docnum = courseInfo.getString("DocNum");
                                    nombre = courseInfo.getString("CardName");
                                    docentry =courseInfo.getString("DocEntry");
                                    numpedido = courseInfo.getString("U_IdOrdersShopify");
                                    total = courseInfo.getString("DocTotal");
                                    uns = courseInfo.getString("U_UN");

                                   adapters.add(new ListAdapter(numpedido,nombre,docentry,numpedido,total,uns));
                                    //ArrayList to group all the info
                                    HashMap<String, String> currentCourseInfoList = new HashMap<>();

                                    //currentCourseInfoList.put("name", cardcode);
                                    coursesBasicInfoList.add(currentCourseInfoList);
                                }
                                docAdapter = new docAdapter(mContext, adapters);
                                lv.setAdapter(docAdapter);

                            } catch (JSONException e) {
                                //Do something with error
                            }

                            //post your result in LiveData
                           // mutableCoursesBasicInfoList.postValue(coursesBasicInfoList);
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            // Do something when error occurred

                        }
                    }
            ){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String>  params = new HashMap<>();
                    params.put("Content-Type", "application/json");

                    return params;
                }
            };
            queue.add(request);
           /* JsonObjectRequest request = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray array = response.getJSONArray("");

                                for ( int i = 0; i< array.length(); i++ ) {
                                    JSONObject mJObject = array.getJSONObject(i);
                                    String name = mJObject.getString("name");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Log.i("REST:Response Status line", error.getMessage().toString());
                        }
                    });*/

            /*InputStream is = null;
            String result = "";
            JSONObject jArray = null;
            new ArrayList();*/
            /*DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = httpclient.execute(request);
            //Log.i("REST:Response Status line", response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();
            is = entity.getContent();*/

        }catch (Exception e) {

            // on below line handling the exception.
            e.printStackTrace();
            makeText(InicioActivity.this, "Fail to post the data : " + e.getMessage(), LENGTH_SHORT).show();
        }
    }
    public  void openAlertDialog(){
        String[] choices = {"Cambio de Método de pago", "Entregado"};

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle("Seleccione una opción");

        builder.setSingleChoiceItems(choices, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //onCreateDialog();
                        //dialog.dismiss();
                        /*Intent activityInicio = new Intent(InicioActivity.this, MetodoPagoActivity.class);
                        startActivityForResult(activityInicio,valor );*/
                        if (which == 0) {
                            startActivityForResult(new Intent(InicioActivity.this, MetodoPagoActivity.class), valor);
                        } else if (which == 1) {
                            dialog.dismiss();
                            ClosePedido();
                        }
                        /*else if(which == 2){

                        }*/
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    private  void ClosePedido(){
        //String url = "http://172.16.101.125:8088/Service1.svc/UpdateStatus?docentry=" +docentrySAP;
        dbHandler = new DBHandler(InicioActivity.this);
        //String url = "http://172.16.101.125:8088/Service1.svc/getListOV";
        String url = "http://" + dbHandler.readsql() + "/UpdateStatus?docentry=" +docentrySAP + "&un='"+un +"'&peyMethod='"+
                result +"'&code='"+ codeSAP +"'";
        try {
            Map<String, String> params = new HashMap();
            params.put("docentry", docentrySAP);

            JSONObject parameters = new JSONObject(params);

            StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //TODO: handle success
                    makeText(mContext, "Documento actualizado", LENGTH_SHORT).show();
                    consultarpedido1();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    //TODO: handle failure
                }
            });
            queue.add(jsonRequest);
            //Volley.newRequestQueue(mContext).add(jsonRequest);

        }catch (Exception e){
            makeText(mContext, e.getMessage(), LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == valor) {
            if (resultCode == RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user.
                result = data.getStringExtra("data");
                //Toast.makeText(mContext,resultCode, LENGTH_SHORT);
                //startActivity(new Intent(Intent.ACTION_VIEW, data.getData()));
            }
        }
    }
    public void  onCreateDialog() {

        View dialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        // Get the layout inflater.
        LayoutInflater inflater = getLayoutInflater();
        dialog = inflater.inflate(R.layout.modal_opciones,null);
        builder.setView(dialog);
        builder.setTitle("Opciones");
        /*builder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                makeText(mContext, position, LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
       // builder.create();
        final AlertDialog a = builder.show();

        rbmp = dialog.findViewById(R.id.rbMP);
        rbpe = dialog.findViewById(R.id.rbPE);
        btnmodalaceptar = dialog.findViewById(R.id.btnModalAceptar);

        btnmodalaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rbmp.isChecked()){
                    startActivityForResult(new Intent(InicioActivity.this, MetodoPagoActivity.class), valor);
                } else if (rbpe.isChecked()) {
                    a.dismiss();
                    ClosePedido();
                }
            }
        });
    }
    public synchronized void validLicense(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Licencia");

        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()){
                       HashMap<String,String> valores = (HashMap<String, String>) task.getResult().getValue();
                        Iterator myVeryOwnIterator = valores.keySet().iterator();
                        //String licencia = valores.get(Licencia.class);
                        for (Object key : valores.keySet()) {
                            if (key.equals("Activo")){
                                String value= valores.get(key);
                                if (value.equals("true")){
                                    consultarpedido1();
                                }else {
                                    makeText(mContext, "Licencia inactiva", LENGTH_SHORT).show();
                                }
                            }
                            //
                            //Toast.makeText(mContext, "Key: "+key+" Value: "+value, Toast.LENGTH_LONG).show();
                        }
                    }
            }
        });

        /*mDatabase = FirebaseDatabase.getInstance().getReference();

        Query query = myRef
                .child("Licencia").child("Vigencia");

        mDatabase.child("Licencia").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot singleSnapshot : task.getResult().getChildren()) {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        Licencia licencia = (Licencia) task.getResult().getValue();
                    }
                }
            }
        });*/

       /* mDatabase.child("Licencia").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                        for (DataSnapshot singleSnapshot : task.getResult()){

                        }
                    Map<String,String> resultados = (Map<String, String>) task.getResult();
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });*/

    }
    public void  validUserWhitSAP(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Usuarios").child(FirebaseAuth.getInstance().getUid());
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    HashMap<String,String> valores = (HashMap<String, String>) task.getResult().getValue();
                    Iterator myVeryOwnIterator = valores.keySet().iterator();

                    for (Object key : valores.keySet()) {
                        if (key.equals("CodeSAP")){
                            String value= valores.get(key);
                            codeSAP = value ;
                        }
                        //
                        //Toast.makeText(mContext, "Key: "+key+" Value: "+value, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    /*public void ConsultarPedidos(){

        String url = "http://172.16.101.125:8088/Service1.svc/getListOV";

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    /*txtUser.setText(jsonObject.getString("userId"));
                    String title = jsonObject.getString("title");
                    txtTitle.setText(title);
                    txtBody.setText(jsonObject.getString("body"));* /

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this  ).add(postResquest);
    }*/
}
