package com.master.antonio.myassistant.wizard;

import com.master.antonio.myassistant.models.Beacon;
import com.master.antonio.myassistant.models.Dispositivo;
import com.siimkinks.sqlitemagic.Select;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.layouts.BasicWizardLayout;
import org.codepond.wizardroid.persistence.ContextVariable;

import static com.siimkinks.sqlitemagic.DispositivoTable.DISPOSITIVO;

/**
 * A sample to demonstrate a form in multiple steps.
 */
public class FormWizardEditar extends BasicWizardLayout {

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
    @ContextVariable
    private long IdDispositivo;
    @ContextVariable
    private String  IdBeacon;

    public FormWizardEditar() {
        super();
        setNextButtonText("Siguiente");
        setBackButtonText("Atrás");
        setFinishButtonText("Modificar");


    }

    public void setIdDispositivo(String _idDispositivo, String _idBeacon){
        IdDispositivo = Long.parseLong(_idDispositivo);
        IdBeacon = _idBeacon;
        Dispositivo aux = Select.from(DISPOSITIVO).where(DISPOSITIVO.ID.is(IdDispositivo)).takeFirst().execute();

        Marca = aux.marca;
        Modelo = aux.modelo;
        KeyVideo = aux.getIdYoutube();
        img = aux.getFoto();
        Manual = aux.getManual();
    }


    /*
        You must override this method and create a wizard flow by
        using WizardFlow.Builder as shown in this example
     */
    @Override
    public WizardFlow onSetup() {
        return new WizardFlow.Builder()
                .addStep(EditarStep1.class)
                .addStep(EditarStep2.class)
                .addStep(EditarStep3.class)
                .addStep(EditarStep4.class)
                .create();
    }

    /*
        You'd normally override onWizardComplete to access the wizard context and/or close the wizard
     */
    @Override
    public void onWizardComplete() {
        getActivity().finish();     //Terminate the wizard
    }
}
