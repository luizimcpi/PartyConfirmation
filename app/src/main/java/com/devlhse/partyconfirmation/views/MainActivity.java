package com.devlhse.partyconfirmation.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.devlhse.partyconfirmation.R;
import com.devlhse.partyconfirmation.constants.PartyConstants;
import com.devlhse.partyconfirmation.util.SecurityPreferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mViewHolder.textToday = (TextView) findViewById(R.id.text_today);
        this.mViewHolder.textDaysLeft = (TextView) findViewById(R.id.text_days_left);
        this.mViewHolder.buttonConfirm = (Button) findViewById(R.id.button_confirm);
        this.mViewHolder.buttonConfirm.setOnClickListener(this);
        this.mSecurityPreferences = new SecurityPreferences(this);
        this.verifyPresence();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_confirm) {

            String presence = this.mSecurityPreferences.getStoredString(PartyConstants.PRESENCE_KEY);

            //Intent -> action to perform something
            Intent intent = new Intent(this, DetailsActivity.class);
            //passar valor para outras activities
            intent.putExtra(PartyConstants.PRESENCE_KEY, presence);

            startActivity(intent);
        }
    }

    private static class ViewHolder {
        TextView textToday;
        TextView textDaysLeft;
        Button buttonConfirm;
    }

    private void verifyPresence() {
        String presence = this.mSecurityPreferences.getStoredString(PartyConstants.PRESENCE_KEY);
        if ("".equals(presence))
            this.mViewHolder.buttonConfirm.setText(R.string.nao_confirmado);
        else if (PartyConstants.CONFIRMED.equals(presence))
            this.mViewHolder.buttonConfirm.setText(R.string.sim);
        else
            this.mViewHolder.buttonConfirm.setText(R.string.nao);
    }
}
