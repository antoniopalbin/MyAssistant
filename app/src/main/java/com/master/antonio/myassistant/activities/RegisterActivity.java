package com.master.antonio.myassistant.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.master.antonio.myassistant.R;

import java.util.ArrayList;

/**
 * Created by anton on 30/04/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    private class Register {
        private String titulo="Sin marca";
        private String timestamp="Sin matricula";
        private int icono=android
                .R
                .mipmap
                .sym_def_app_icon;

        public Register(){}

        public Register(String titulo
                , String timestamp
                , int icono) {
            this.titulo = titulo;
            this.timestamp = timestamp;
            this.icono = icono;
        }

        public String getTitulo() {
            return titulo;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public int getIcono() {
            return icono;
        }
    }

    private ListView ListRegister;
    private ArrayList<Register> Registros = new ArrayList<Register>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Añadimos registros de prueba
        Registros.add(new Register("Cocina","01/05/2017 12:05",R.mipmap.cocina));
        Registros.add(new Register("Salón","01/05/2017  13:50",R.mipmap.tv));
        Registros.add(new Register("Dormitorio","01/05/2017 14:50",R.mipmap.dormitorio));
        Registros.add(new Register("Baño","01/05/2017 17:50",R.mipmap.bano));

        showLVRegister_Complejo();
    }

    private void showLVRegister_Complejo() {
        ListRegister=(ListView) findViewById(R.id.ListRegister);
        ListRegister.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        ArrayAdapter adaptadorVehiculos=
                new ArrayAdapter( this // Context
                        , R.layout.registeritemlayout //Resource
                        , Registros // Vector o lista
                ) {
                    public View getView(int position
                            , View convertView
                            , ViewGroup parent) {
                        LayoutInflater inflater = (LayoutInflater) getContext()
                                .getSystemService(getContext().LAYOUT_INFLATER_SERVICE);

                        // Creamos la vista para cada fila
                        View fila = inflater.inflate(R.layout.registeritemlayout, parent, false);

                        // Creamos cada uno de los widgets que forman una fila
                        ImageView iconoView = (ImageView) fila.findViewById(R.id.imgIcono);
                        TextView TextTitulo = (TextView) fila.findViewById(R.id.textTitulo);
                        TextView TextTime = (TextView) fila.findViewById(R.id.textTime);

                        // Establecemos los valores que queremos que muestren los widgets
                        Register tmpV = Registros.get(position);
                        iconoView.setImageResource(tmpV.getIcono());
                        TextTitulo.setText(tmpV.getTitulo());
                        TextTime.setText(tmpV.getTimestamp());
                        return fila;
                    }
                };

        ListRegister.setAdapter(adaptadorVehiculos);

        /*ListRegister.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SparseBooleanArray checked = lvVehiculos.getCheckedItemPositions();
                if (checked == null) {
                    Toast.makeText(MainActivity.this, "Checked is null", Toast.LENGTH_LONG).show();
                    return;
                }
                String msg = "Items marcados: ";
                for (int i = 0; i < lvVehiculos.getCount(); ++i) {
                    msg += (checked.get(i)) ? i + ", " : "";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
