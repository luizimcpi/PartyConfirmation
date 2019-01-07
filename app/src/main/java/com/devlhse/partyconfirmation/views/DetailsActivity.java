package com.devlhse.partyconfirmation.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.devlhse.partyconfirmation.R;
import com.devlhse.partyconfirmation.constants.PartyConstants;
import com.devlhse.partyconfirmation.util.SecurityPreferences;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        this.mSecurityPreferences = new SecurityPreferences(this);
        this.mViewHolder.checkParticipation = (CheckBox) findViewById(R.id.check_participation);
        this.mViewHolder.checkParticipation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.check_participation){
            if(this.mViewHolder.checkParticipation.isChecked()){
                this.mSecurityPreferences.storeString(PartyConstants.PRESENCE_KEY, PartyConstants.CONFIRMED);
            }else{
                this.mSecurityPreferences.storeString(PartyConstants.PRESENCE_KEY, PartyConstants.NOT_CONFIRMED);
            }
        }
    }

    private static class ViewHolder{
        CheckBox checkParticipation;
    }
}
