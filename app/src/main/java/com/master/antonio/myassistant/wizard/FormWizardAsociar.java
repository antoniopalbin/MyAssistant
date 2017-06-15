package com.master.antonio.myassistant.wizard;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.layouts.BasicWizardLayout;
import org.codepond.wizardroid.persistence.ContextVariable;

/**
 * A sample to demonstrate a form in multiple steps.
 */
public class FormWizardAsociar extends BasicWizardLayout {

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
    private String IdBeacon;




    public FormWizardAsociar() {
        super();
        setNextButtonText("Siguiente");
        setBackButtonText("Atrás");
        setFinishButtonText("Crear");


    }

    public void setIdBeacon(String _id){
        IdBeacon = _id;
        //Beacon aux = Select.from(BEACON).where(BEACON.ID_BEACON.is(IdBeacon)).takeFirst().execute();
        //beac = aux;
    }


    /*
        You must override this method and create a wizard flow by
        using WizardFlow.Builder as shown in this example
     */
    @Override
    public WizardFlow onSetup() {
        return new WizardFlow.Builder()
                .addStep(AsociarStep1.class)
                .addStep(AsociarStep2.class)
                .addStep(AsociarStep3.class)
                .addStep(AsociarStep4.class)
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
