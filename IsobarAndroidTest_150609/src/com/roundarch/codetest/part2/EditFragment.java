package com.roundarch.codetest.part2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.roundarch.codetest.ProgressDialogFragment;
import com.roundarch.codetest.R;

import java.lang.ref.WeakReference;

public class EditFragment extends Fragment implements OnClickListener {
    public static final int RESULT_SAVE = 1;
    public static final String EXTRA_MODEL = "extra_model";

    private DataModel mModel; // TODO - needs to be provided from original Activity/Fragment
    private EditText edit1;
    private EditText edit2;
    private EditText edit3;
    private Button editSaveButton;

    // TODO - This fragment should allow you to edit the fields of DataModel
    // TODO - Then when you click the save button, it should get the DataModel back to the prior activity
    // TODO - so it's up to date
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        view.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_save();
            }
        });

        edit1 = (EditText) view.findViewById(R.id.editText1);
        edit2 = (EditText) view.findViewById(R.id.editText2);
        edit3 = (EditText) view.findViewById(R.id.editText3);
        editSaveButton = (Button) view.findViewById(R.id.button1);
        editSaveButton.setOnClickListener(this);
//        mModel = (DataModel) savedInstanceState.getSerializable(EXTRA_MODEL);
        mModel = (DataModel) getActivity().getIntent().getSerializableExtra(EXTRA_MODEL);
        setModel(mModel);
        setRetainInstance(true);

        return view;
    }


    private void modifyModelOperation(final DataModel model) {
        showLoadingDialog();
        refreshModelFromViews();

        // TODO - you need to implement swapText
        swapText(model);

        // TODO - the BlackBox simulates a slow operation, so you will need to update
        // TODO - this code to prevent it from blocking the main thread
//        double newValue = BlackBox.doMagic(model.getText3());
//        model.setText3(newValue);
        new BlackBoxTask((EditActivity) getActivity()).execute(model);

        // TODO - once the model has been updated, you need to find a good way to
        // TODO - to provide it back to Part2Fragment in the MainActivity
//        getActivity().finish();
    }

    private void showLoadingDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ProgressDialogFragment editNameDialog = new ProgressDialogFragment();
        editNameDialog.show(fm, "progress_dialog_fragment");
    }

    private void refreshModelFromViews() {
        // TODO - update our model from the views in our layout
        String text1 = edit1.getText().toString();
        String text2 = edit2.getText().toString();
        double text3 = Double.parseDouble(edit3.getText().toString());
        mModel.setText1(text1);
        mModel.setText2(text2);
        mModel.setText3(text3);
    }

    // Modifies the data model to swap the values in text1 and text2
    private void swapText(DataModel model) {
        // TODO - swap the text1 and text2 fields on the data model
        String swapText1 = model.getText1();
        String swapText2 = model.getText2();
        model.setText1(swapText2);
        model.setText2(swapText1);
    }

    public void onClick_save() {
        modifyModelOperation(mModel);
    }

    // TODO - use this method from the Activity to set the model and update
    // TODO - the views in the layout.  You will need to implement the refreshViewsFromModel()
    // TODO - method as well
    public void setModel(DataModel model) {
        mModel = model;
        refreshViewsFromModel();
    }

    private void refreshViewsFromModel() {
        // TODO - update our views based on the model's state
        if (mModel != null) {
            edit1.setText(mModel.getText1());
            edit2.setText(mModel.getText2());
            edit3.setText(mModel.getText3() + "");
        }

    }

    @Override
    public void onClick(View v) {
        onClick_save();
    }

    private class BlackBoxTask extends AsyncTask<DataModel, Void, Void> {
        //        private WeakReference<EditActivity> weakReference;
        private EditActivity hostActivity;
        DataModel model;

        public BlackBoxTask(EditActivity editActivity) {
            this.hostActivity = editActivity;
        }

        @Override
        protected Void doInBackground(DataModel... params) {
//            weakReference = new WeakReference<>(hostActivity);
            DataModel model = params[0];
            this.model = model;
            double newValue = BlackBox.doMagic(model.getText3());
            model.setText3(newValue);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent returnIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(EXTRA_MODEL, model);
            returnIntent.putExtras(bundle);
            hostActivity.setResult(Activity.RESULT_OK, returnIntent);
            hostActivity.finish();
        }
    }
}
