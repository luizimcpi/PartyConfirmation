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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mViewHolder.textToday = (TextView) findViewById(R.id.text_today);
        this.mViewHolder.textDaysLeft = (TextView) findViewById(R.id.text_days_left);
        this.mViewHolder.buttonConfirm = (Button) findViewById(R.id.button_confirm);
        this.mViewHolder.buttonConfirm.setOnClickListener(this);
        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.textToday.setText(SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime()));

        String daysLeft = String.format("%s %s", String.valueOf(this.getDaysLeftToEndOfYear()), getString(R.string.dias));
        this.mViewHolder.textDaysLeft.setText(daysLeft);
    }

    /*métodos do ciclo de vida de uma acitivity são callbacks
     * quando um termina outro é chamado imediatamente*/
    @Override
    protected void onStart() {
        super.onStart();
    }

    /* este método sempre é chamado quando a activity
    é colocada visivelmente na tela
     */
    @Override
    protected void onResume() {
        super.onResume();
        this.verifyPresence();
    }

    /*Não deve ser utilizado para persistencia de dados ou chamadas a API*/
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    private int getDaysLeftToEndOfYear(){
        Calendar calendarToday = Calendar.getInstance();
        int today = calendarToday.get(Calendar.DAY_OF_YEAR);

        Calendar calendarLastDay = Calendar.getInstance();
        int lastDayDecember = calendarLastDay.getActualMaximum(Calendar.DAY_OF_YEAR);

        return lastDayDecember - today;
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
