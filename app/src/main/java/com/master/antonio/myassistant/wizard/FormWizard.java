package com.master.antonio.myassistant.wizard;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.layouts.BasicWizardLayout;
import org.codepond.wizardroid.persistence.ContextVariable;

/**
 * A sample to demonstrate a form in multiple steps.
 */
public class FormWizard extends BasicWizardLayout {

    /**
     * Tell WizarDroid that these are context variables and set default values.
     * These values will be automatically bound to any field annotated with {@link ContextVariable}.
     * NOTE: Context Variable names are unique and therefore must
     * have the same name and type wherever you wish to use them.
     */
    @ContextVariable
    private String firstname = "WizarDroid";
    @ContextVariable
    private String lastname = "CondPond.org";

    public FormWizard() {
        super();
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
        super.onWizardComplete();   //Make sure to first call the super method before anything else
        //... Access context variables here before terminating the wizard
        //...
        //String fullname = firstname + lastname;
        //Store the data in the DB or pass it back to the calling activity
        getActivity().finish();     //Terminate the wizard
    }
}
