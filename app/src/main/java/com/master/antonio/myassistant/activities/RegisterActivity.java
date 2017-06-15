package com.master.antonio.myassistant.activities;

import android.content.Intent;
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
import com.master.antonio.myassistant.models.Actividad;
import com.siimkinks.sqlitemagic.Select;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import rx.subscriptions.CompositeSubscription;

import static com.siimkinks.sqlitemagic.ActividadTable.ACTIVIDAD;


/**
 * Created by anton on 30/04/2017.
 */

public class RegisterActivity extends AppCompatActivity {
    private ListView ListRegister;
    List<Actividad> Actividades;
    private int dia;
    private int mes;
    private int año;
    private Date fecha;
    private CompositeSubscription subscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Resgistro de actividad");

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            String getName = (String) bd.get("name");

            dia = (int) bd.get("dia");
            mes = (int) bd.get("mes");
            año = (int) bd.get("año");

            fecha = new GregorianCalendar(año, mes, dia).getTime();
        }

        showLVRegister_Complejo();
    }

    private void showLVRegister_Complejo() {
        ListRegister = (ListView) findViewById(R.id.ListRegister);
        ListRegister.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        Date desde = new Date(fecha.getTime());
        fecha.setHours(23);
        fecha.setMinutes(59);
        Date hasta = new Date(fecha.getTime());

        Actividades = Select.from(ACTIVIDAD).where(ACTIVIDAD.TIMESTAMP.greaterThan(desde.getTime()).and(ACTIVIDAD.TIMESTAMP.lessThan(hasta.getTime()))).orderBy(ACTIVIDAD.TIMESTAMP.asc()).queryDeep().execute();

        ArrayAdapter adaptadorVehiculos =
                new ArrayAdapter(this // Context
                        , R.layout.registeritemlayout //Resource
                        , Actividades // Vector o lista
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
                        Actividad tmpV = Actividades.get(position);
                        iconoView.setImageResource(tmpV.getBeacon().getIcono());
                        TextTitulo.setText(tmpV.getBeacon().getDescripcion());
                        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                        String dateText = df2.format(new Date(tmpV.getTimestamp()));
                        TextTime.setText(dateText);
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
