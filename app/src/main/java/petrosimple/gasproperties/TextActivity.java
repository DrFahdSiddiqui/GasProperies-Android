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
 *   Source file for Text Activity Class                                     *
 *   Shows the output result as a text report for emailing and sharing etc.  *
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
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import static petrosimple.gasproperties.Data.*;

// ------------------------------------------------------------------------ //
// Displays Text activity
public class TextActivity extends AppCompatActivity {
    // -------------------------------------------------------------------- //
    // Actions to perform on activity creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textonly);

        TextView op_result = (TextView) findViewById(R.id.eEditTex1t);
        op_result.setText("");
        op_result.append(String.format(Locale.getDefault(), ""
                        + "###########################################" + "\n"
                        + "INPUT DATA" + "\n"
                        + "###########################################" + ""
                        + "\nGas Sp Gr=" + "%.4f" + " air=1"
                        + "\nH2S=" + "%.1f" + " mol%%"
                        + "\nCO2=" + "%.1f" + " mol%%"
                        + "\nTemperature=" + "%.1f" + " F"
                        + "\nTemperature=" + "%.1f" + " R"
                        + "\nMolecular Weight=" + "%.1f" + " "
                , sg, h2s, co2, T - 460, T, sg * 28.97));

        op_result.append(String.format(Locale.getDefault(), ""
                        + "\n\nUsing Sutton Correlation and" + ""
                        + "\nafter correctng with Wichert and Aziz method" + ""
                        + "\nPpc=" + "%.1f" + " psia"
                        + "\nTpc=" + "%.1f" + " R"
                        + "\nTpr=" + "%.2f" + " "
                , Ppc, Tpc, Tpr));


        if (offP > 0.0) {
            op_result.append("\n_________________________________________________________");
            op_result.append("\nMEASURED LAB/FIELD TEST POINT DATA");
            op_result.append("\n_________________________________________________________");
            op_result.append(String.format(Locale.getDefault(), "\nTest Point Pressure: %.1f psia", offP));
            if (offZ > 0.0) {
                op_result.append(String.format(Locale.getDefault(), "\nZ-factor: %.4f", offZ));
            }
            if (offFVF > 0.0) {
                op_result.append(String.format(Locale.getDefault(), "\nFormation Volume Factor: %.3f rb/stb", offFVF));
            }
            if (offRho > 0.0) {
                op_result.append(String.format(Locale.getDefault(), "\nDensity: %.2f lb/ft3", offRho));
            }
            if (offVisc > 0.0) {
                op_result.append(String.format(Locale.getDefault(), "\nViscosity: %.3f cp", offVisc));
            }
            if (offComp > 0.0) {
                op_result.append(String.format(Locale.getDefault(), "\nCompressibility: %1.3e 1/psi", offComp));
            }
        }

        op_result.append("\n_________________________________________________________");
        op_result.append("\nCORRELATIONS USED");
        op_result.append("\n_________________________________________________________");
        op_result.append("\nZ-factor:" + ZText);
        op_result.append("\nViscosity:" + viscText);

        op_result.append("\n\n"
                + "###########################################" + "\n"
                + "TABULATED PVT DATA                         " + "\n"
                + "###########################################" + "\n"
        );

        op_result.append("_________________________________________________________\n");
        op_result.append(" Pressure \tZ \tFVF \tDensity \tViscosity \tCompressibility\n [psia] \t\t \t[rcf/scf] \t[lb/ft3] \t[cp] \t[1/pisa]\n");
        op_result.append("_________________________________________________________\n");
        int j = 0;
        for (int i = 0; i < pstep; i++) {
            op_result.append(String.format(Locale.getDefault(), "%.1f", p[i]));
            op_result.append(String.format(Locale.getDefault(), "\t%.3f", Z[i]));
            op_result.append(String.format(Locale.getDefault(), "\t%.3e", fvf[i]));
            op_result.append(String.format(Locale.getDefault(), "\t%.2f", rho[i]));
            op_result.append(String.format(Locale.getDefault(), "\t%.3e", mu[i]));
            op_result.append(String.format(Locale.getDefault(), "\t%4.3e", comp[i]));
            op_result.append("\n ");
        }
        op_result.append("_________________________________________________________\n");
    } // onCreate


    // -------------------------------------------------------------------- //
    // Actions to perform on email button click
    // Opens a sharing dialog box for forwarding the report text.
    public void email(View view) {
        TextView op_result = (TextView) findViewById(R.id.eEditTex1t);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Oil Properties Report from PetroSimple App");
        intent.putExtra(Intent.EXTRA_TEXT, op_result.getText());

        startActivity(Intent.createChooser(intent, "Send Email"));
    } // email
}


/****************************************************************************/
