package com.forseti.drilltracker.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.forseti.drilltracker.R;
import com.forseti.drilltracker.data.Drill;

public class DetailedDrillFragment extends DialogFragment {
    Drill drill;

    public void setDrill(Drill drill) {
        this.drill = drill;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        View detailedDrillFragment = inflater.inflate(R.layout.detailed_drill, null);

        final TextView drillName = (TextView) detailedDrillFragment.findViewById(R.id.drill_name);
        final TextView drillSummary = (TextView) detailedDrillFragment.findViewById(R.id.drill_summary);
        final TextView drillInstructions = (TextView) detailedDrillFragment.findViewById(R.id.detailed_drill_instructions);

        drillName.setText(drill.getName());
        drillSummary.setText(drill.getDescription());
        drillInstructions.setText(drill.getInstructions());

        builder.setView(detailedDrillFragment);

        return builder.create();
    }
}
