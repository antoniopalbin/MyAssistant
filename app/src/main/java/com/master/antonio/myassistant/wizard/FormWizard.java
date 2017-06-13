package com.master.antonio.myassistant.wizard;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.Button;

import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.activities.ListDispositivosBeaconsActivity;
import com.master.antonio.myassistant.models.Beacon;
import com.master.antonio.myassistant.models.Dispositivo;
import com.siimkinks.sqlitemagic.Select;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.layouts.BasicWizardLayout;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.util.ArrayList;

import static com.siimkinks.sqlitemagic.BeaconTable.BEACON;

/**
 * A sample to demonstrate a form in multiple steps.
 */
public class FormWizard extends BasicWizardLayout {

    @ContextVariable
    private String Marca;
    @ContextVariable
    private String Modelo;
    @ContextVariable
    private String KeyVideo;
    @ContextVariable
    private byte[] img;
    @ContextVariable
    private String Manual;
    @ContextVariable
    private byte[] thumbnail;

    private String IdBeacon;



    public FormWizard() {
        super();
        setNextButtonText("Siguiente");
        setBackButtonText("Atrás");
        setFinishButtonText("Crear");
    }

    public void setIdBeacon(String _id){
        IdBeacon = _id;
    }


    /*
        You must override this method and create a wizard flow by
        using WizardFlow.Builder as shown in this example
     */
    @Override
    public WizardFlow onSetup() {
        return new WizardFlow.Builder()
                .addStep(Step1.class)
                .addStep(Step2.class)
                .addStep(Step3.class)
                .addStep(Step4.class)
                .create();
    }

    /*
        You'd normally override onWizardComplete to access the wizard context and/or close the wizard
     */
    @Override
    public void onWizardComplete() {

        //Realizamos la insercción de la beacon
        Beacon aux = Select.from(BEACON).where(BEACON.ID_BEACON.is(IdBeacon)).takeFirst().execute();

        System.out.println("Beacon id "+aux.getIdBeacon());
        System.out.println("Marca "+ Marca);
        System.out.println("Modelo "+ Modelo);
        System.out.println("manual"+ Manual);


        /*ArrayList<Dispositivo> dispositivos = new ArrayList<>();
        Dispositivo dis = new Dispositivo("20", Marca, Modelo, KeyVideo, img, img, manual, aux);
        dispositivos.add(dis);
        Dispositivo.persist(dispositivos).ignoreNullValues().execute();*/

        getActivity().finish();     //Terminate the wizard


    }
}
