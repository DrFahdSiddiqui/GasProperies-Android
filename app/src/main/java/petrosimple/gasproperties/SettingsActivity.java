/*****************************************************************************
 *                                                                           *
 *                  GAS FLUID PROPERTIES CALCULATION PROGRAM                 *
 *                                Version 2.0                                *
 *        Written for Java/Android by : Fahd Siddiqui and Aqsa Qureshi       *
 *           https://github.com/DrFahdSiddiqui/GasProperies-Android          *
 *                                                                           *
 * ------------------------------------------------------------------------- *
 * LICENSE: MOZILLA 2.0                                                      *
 *   This Source Code Form is subject to the terms of the Mozilla Public     *
 *   License, v. 2.0. If a copy of the MPL was not distributed with this     *
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.                *
 ****************************************************************************/

/*****************************************************************************
 * DOCUMENTATION                                                             *
 *   Source file for Settings Activity Class                                 *
 *   Displays options for selecting correlations                             *
 *   Last updated 08/08/2018                                                 *
 ****************************************************************************/

/*****************************************************************************
 * TODO                                                                      *
 *   Code cleanup and re-usability                                           *
 ****************************************************************************/


/****************************************************************************/


package petrosimple.gasproperties;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import static petrosimple.gasproperties.Data.*;


// ------------------------------------------------------------------------ //
// Displays Settings activity
public class SettingsActivity extends AppCompatActivity {
    // -------------------------------------------------------------------- //
    // Actions to perform on activity creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Z-factor
        Spinner sp_Z = (Spinner) findViewById(R.id.sp_Z);
        List<String> Zcategories = new ArrayList<String>();
        Zcategories.add("     Hall and Yarborough");
        Zcategories.add("     Dranchuk and Abou-Kassem");
        ArrayAdapter<String> ZdataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, Zcategories);
        ZdataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_Z.setAdapter(ZdataAdapter);
        sp_Z.setSelection(Zcorr);

        //Viscosity
        Spinner sp_visc = (Spinner) findViewById(R.id.sp_visc);
        List<String> categoriesvisc = new ArrayList<String>();
        categoriesvisc.add("     Lee et al");
        categoriesvisc.add("     Carr et al");
        ArrayAdapter<String> dataAdaptervisc = new ArrayAdapter<String>(this, R.layout.spinner_item, categoriesvisc);
        dataAdaptervisc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_visc.setAdapter(dataAdaptervisc);
        sp_visc.setSelection(visccorr);
    } // onCreate


    // -------------------------------------------------------------------- //
    // Actions to perform on save button click
    // Saves the correlations settings to be used
    public void save(View view) {
        Spinner sp_Z = (Spinner) findViewById(R.id.sp_Z);
        Spinner sp_visc = (Spinner) findViewById(R.id.sp_visc);
        Zcorr = sp_Z.getSelectedItemPosition();
        visccorr = sp_visc.getSelectedItemPosition();
        ZText = sp_Z.getSelectedItem().toString();
        viscText = sp_visc.getSelectedItem().toString();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    } // save
}


/****************************************************************************/
