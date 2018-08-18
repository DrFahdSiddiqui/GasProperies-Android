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
 *   Source file for MainActivity Class                                      *
 *   Shows the input form, settins menu and buttons                          *
 *   Last updated 08/14/2018                                                 *
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Locale;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.exp;
import static java.lang.StrictMath.log10;
import static java.lang.StrictMath.pow;

import static petrosimple.gasproperties.Data.*;


// ------------------------------------------------------------------------ //
// Displays Main activity
public class MainActivity extends AppCompatActivity {
    // -------------------------------------------------------------------- //
    // Actions to perform on activity creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText in_tr = (EditText) findViewById(R.id.in_tr);
        EditText in_sg = (EditText) findViewById(R.id.in_sg);
        EditText in_h2s = (EditText) findViewById(R.id.in_h2s);
        EditText in_co2 = (EditText) findViewById(R.id.in_co2);
        EditText in_pmin = (EditText) findViewById(R.id.in_pmin);
        EditText in_pmax = (EditText) findViewById(R.id.in_pmax);
        EditText in_pstep = (EditText) findViewById(R.id.in_pstep);

        EditText in_offP = (EditText) findViewById(R.id.in_offP);
        EditText in_offZ = (EditText) findViewById(R.id.in_offZ);
        EditText in_offFVF = (EditText) findViewById(R.id.in_offFVF);
        EditText in_offRho = (EditText) findViewById(R.id.in_offRho);
        EditText in_offVisc = (EditText) findViewById(R.id.in_offVisc);
        EditText in_offComp = (EditText) findViewById(R.id.in_offComp);

        if (in_tr != null) {
            in_tr.setText(String.format(Locale.getDefault(), "%.1f", T));
        }
        in_sg.setText(String.format(Locale.getDefault(), "%.4f", sg));
        in_h2s.setText(String.format(Locale.getDefault(), "%.1f", h2s));
        in_co2.setText(String.format(Locale.getDefault(), "%.1f", co2));
        in_pmin.setText(String.format(Locale.getDefault(), "%.1f", pmin));
        in_pmax.setText(String.format(Locale.getDefault(), "%.1f", pmax));
        in_pstep.setText(String.format(Locale.getDefault(), "%d", pstep));

        try {
            in_offP.setText(String.format(Locale.getDefault(), "%.1f", offP));
            in_offZ.setText(String.format(Locale.getDefault(), "%.1f", offZ));
            in_offFVF.setText(String.format(Locale.getDefault(), "%.3f", offFVF));
            in_offRho.setText(String.format(Locale.getDefault(), "%.2f", offRho));
            in_offVisc.setText(String.format(Locale.getDefault(), "%.3f", offVisc));
            in_offComp.setText(String.format(Locale.getDefault(), "%4.3e", offComp));
        } catch (Exception e) {
        }
    }


    // -------------------------------------------------------------------- //
    // Actions to perform on options menu creation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    } // onCreateOptionsMenu


    // -------------------------------------------------------------------- //
    // Actions to perform on each option click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            settings(findViewById(android.R.id.content));
            return true;
        }
        if (id == R.id.action_about) {
            about(findViewById(android.R.id.content));
            return true;
        }
        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected


    // -------------------------------------------------------------------- //
    // Actions to perform on calc button click
    // Calculates z-factor from correlation, viscosity from correlation,
    // and gas properties
    public void calc(View view) {
        EditText in_tr = (EditText) findViewById(R.id.in_tr);
        EditText in_sg = (EditText) findViewById(R.id.in_sg);
        EditText in_h2s = (EditText) findViewById(R.id.in_h2s);
        EditText in_co2 = (EditText) findViewById(R.id.in_co2);
        EditText in_pmin = (EditText) findViewById(R.id.in_pmin);
        EditText in_pmax = (EditText) findViewById(R.id.in_pmax);
        EditText in_pstep = (EditText) findViewById(R.id.in_pstep);
        EditText in_offP = (EditText) findViewById(R.id.in_offP);
        EditText in_offZ = (EditText) findViewById(R.id.in_offZ);
        EditText in_offFVF = (EditText) findViewById(R.id.in_offFVF);
        EditText in_offRho = (EditText) findViewById(R.id.in_offRho);
        EditText in_offVisc = (EditText) findViewById(R.id.in_offVisc);
        EditText in_offComp = (EditText) findViewById(R.id.in_offComp);

        //Input conversion to double
        try {
            T = Double.parseDouble(in_tr.getText().toString());
        } catch (Exception e) {
        }
        try {
            sg = Double.parseDouble(in_sg.getText().toString());
        } catch (Exception e) {
        }
        try {
            h2s = Double.parseDouble(in_h2s.getText().toString());
        } catch (Exception e) {
        }
        try {
            co2 = Double.parseDouble(in_co2.getText().toString());
        } catch (Exception e) {
        }
        try {
            pmin = Double.parseDouble(in_pmin.getText().toString());
            if (pmin < 1.0) {
                pmin = 1.0;
            }
        } catch (Exception e) {
        }
        try {
            pmax = Double.parseDouble(in_pmax.getText().toString());
        } catch (Exception e) {
        }
        try {
            pstep = Integer.parseInt(in_pstep.getText().toString());
            if (pstep >= maxsize) {
                pstep = maxsize;
            }
        } catch (Exception e) {
        }
        try {
            offP = Double.parseDouble(in_offP.getText().toString());
        } catch (Exception e) {
        }
        try {
            offZ = Double.parseDouble(in_offZ.getText().toString());
        } catch (Exception e) {
        }
        try {
            offFVF = Double.parseDouble(in_offFVF.getText().toString());
        } catch (Exception e) {
        }
        try {
            offRho = Double.parseDouble(in_offRho.getText().toString());
        } catch (Exception e) {
        }
        try {
            offVisc = Double.parseDouble(in_offVisc.getText().toString());
        } catch (Exception e) {
        }
        try {
            offComp = Double.parseDouble(in_offComp.getText().toString());
        } catch (Exception e) {
        }
        if (pmax < pmin) {
            Double temp = pmax;
            pmax = pmin;
            pmin = temp;
        }
        if (pmin <= 1.0) pmin = 1.0;
        if (pstep < 1) pstep = 30;
        p[0] = pmin;

        for (int i = 1; i < pstep; i++) {
            p[i] = p[i - 1] + (pmax - pmin) / (pstep - 1);
        }

        //Molecular Weight
        T = T + 460.0;
        h2s = h2s / 100;
        co2 = co2 / 100;
        mw = sg * 28.97;
        Tpc = 169.2 + 349.5 * sg - 74 * pow(sg, 2);
        Ppc = 756.8 - 131 * sg - 3.6 * pow(sg, 2);

        Double corr = 120 * (pow((co2 + h2s), 0.9) - pow((co2 + h2s), 1.6)) + 15 * (pow(h2s, 0.5) - pow(h2s, 4));
        Ppc = Ppc * (Tpc - corr) / (Tpc + h2s * (1 - h2s) * corr);
        Tpc = Tpc - corr;
        Tpr = T / Tpc;
        Double Zresult[];

        for (int i = 0; i < pstep; i++) {
            Ppr = p[i] / Ppc;
            Zresult = Zfactor(Tpr, Ppr, Ppc, Zcorr, tol);
            Z[i] = Zresult[0];
            comp[i] = Zresult[1];
            rho[i] = p[i] * mw / (Z[i] * 10.73 * T);
            fvf[i] = 14.7 / 520 * Z[i] * T / p[i];
            mu[i] = Viscosity(Tpr, Ppr, mw, T, rho[i], sg, h2s, co2, visccorr);
            if (i > 0) {
                mp[i] = mp[i - 1] + (p[i - 1] / mu[i - 1] / Z[i - 1] + p[i] / mu[i] / Z[i]) * (p[i] - p[i - 1]);

            } else {
                mp[i] = 2 * p[i] / mu[i] / Z[i];
            }
        }

        //Start Output Activity
        Intent intent = new Intent(this, OutputActivity.class);
        startActivity(intent);
    } // calc


    // -------------------------------------------------------------------- //
    // Calculates Z-factors and gas compressibility based on selected correlation
    public Double[] Zfactor(Double Tpr, Double Ppr, Double Ppc, int pos, Double tol) {
        Double Z = 1.0;
        Double cg = 1.0;
        Double fy = 1.0;
        Double fdy = 1.0;
        Double yn = 1e-3;
        Double y = 1.0;
        switch (pos) {
            case 0://Hall and Yarborough
                Double t = 1 / Tpr;
                Double a = 0.06125 * t * exp(-1.2 * pow((1 - t), 2));
                Double A = 14.76 * t - 9.76 * pow(t, 2) + 4.58 * pow(t, 3);
                Double B = 90.7 * t - 242.2 * pow(t, 2) + 42.4 * pow(t, 3);
                Double C = 2.18 + 2.82 * t;
                //while (abs(fy)>tol) {
                for (int i = 0; (i < 1000) || (abs(fy) > tol); ++i) {
                    y = yn;
                    fy = -a * Ppr + (y + pow(y, 2) + pow(y, 3) - pow(y, 4)) / pow((1 - y), 3) - A * pow(y, 2) + B * pow(y, C);
                    fdy = (1 + 4 * y + 4 * pow(y, 2) - 4 * pow(y, 3) + pow(y, 4)) / pow((1 - y), 4) - 2 * A * y + B * C * pow(y, C);
                    yn = y - fy / fdy;
                }
                Z = a * Ppr / y;
                if (Z < 0.01) Z = 0.01;
                cg = 1 / (Ppr * Ppc) + 1 / Z * (a / Ppc * (1 / y - a * Ppr / pow(y, 2) / fdy));
                break;

            case 1://Dranchuk and Abou-Kassem
                Z = 1.0;
                cg = 1.0;
                fy = 1.0;
                yn = .5;
                y = 1.0;
                Double Rr = 0.0, C4 = 0.0, A1 = 0.3265, A2 = -1.0700, A3 = -0.5339, A4 = 0.01569, A5 = -0.05165, A6 = 0.5475, A7 = -0.7361, A8 = 0.1844, A9 = 0.1056, A10 = 0.6134, A11 = 0.7210;
                Double C1 = (A1 + A2 / Tpr + A3 / pow(Tpr, 3) + A4 / pow(Tpr, 4) + A5 / pow(Tpr, 5));
                Double C2 = (A6 + A7 / Tpr + A8 / pow(Tpr, 2));
                Double C3 = A9 * (A7 / Tpr + A8 / pow(Tpr, 2));

                //while (abs(fy)>tol) {
                for (int i = 0; (i < 1000) || (abs(fy) > tol); ++i) {
                    y = yn;
                    Rr = 0.27 * Ppr / (y * Tpr);
                    C4 = A10 * (1 + A11 * pow(Rr, 2)) * (pow(Rr, 2) / pow(Tpr, 3)) * exp(-A11 * pow(Rr, 2));
                    fy = y - (1 + C1 * Rr + C2 * pow(Rr, 2) - C3 * pow(Rr, 5) + C4);
                    fdy = 1 + C1 * Rr / y + 2 * C2 * pow(Rr, 2) / y - 5 * C3 * pow(Rr, 5) / y + 2 * A10 * (pow(Rr, 2) / pow(Tpr, 3) / y) * (1 + A11 * pow(Rr, 2) + pow((A11 * pow(Rr, 2)), 2)) * exp(-A11 * pow(Rr, 2));
                    yn = y - fy / fdy;
                }
                Z = yn;
                if (Z < 0.01) Z = 0.01;
                Double dzdRr = C1 + 2 * C2 * Rr - 5 * C3 * pow(Rr, 4) + 2 * A10 * (Rr / pow(Tpr, 3)) * (1 + A11 * pow(Rr, 2) - pow((A11 * pow(Rr, 2)), 2)) * exp(-A11 * pow(Rr, 2));
                cg = (1 / Ppr - 0.27 / pow(Z, 2) / Tpr * (dzdRr / (1 + Rr / Z * dzdRr))) / Ppc;
                break;
        }
        return new Double[]{Z, cg};
    } // Zfactor


    // -------------------------------------------------------------------- //
    // Calculates gas viscosity based on selected correlation
    public Double Viscosity(Double Tpr, Double Ppr, Double M, Double T, Double rhog, Double sg, Double h2s, Double co2, int pos) {
        Double mu = 1e-3;
        switch (pos) {
            case 0:
                //Lee et al
                Double a1 = ((9.379 + .01607 * M) * pow(T, 1.5)) / (209.2 + 19.26 * M + T);
                Double a2 = 3.448 + (986.4 / T) + .01009 * M;
                Double a3 = 2.447 - .2224 * a2;
                mu = a1 * pow(10, -4) * exp(a2 * pow((rhog / 62.428), a3));
                break;
            case 1:
                //Carr et al
                Double b1 = -2.46211820, b2 = 2.970547414, b3 = -.286264054, b4 = .00805420522, b5 = 2.80860949, b6 = -3.49803305, b7 = .3603702, b8 = -.01044324, b9 = -.793385648, b10 = 1.39643306, b11 = -.149144925, b12 = .00441015512, b13 = .0839387178, b14 = -.18648848, b15 = .0203367881, b16 = -.000609579263;
                Double mu_g1 = (1.709 / 100000 - 2.062 / 1000000 * sg) * (T - 460) + 8.188 / 1000 - 6.15 / 1000 * log10(sg) + co2 * (9.08 / 1000 * log10(sg) + 6.24 / 1000) + h2s * (8.49 / 1000 * log10(sg) + 3.73 / 1000);
                Double X_1 = b1 + b2 * Ppr + b3 * pow(Ppr, 2) + b4 * pow(Ppr, 3) + (b5 + b6 * Ppr + b7 * pow(Ppr, 2) + b8 * pow(Ppr, 3)) * Tpr + (b9 + b10 * Ppr + b11 * pow(Ppr, 2) + b12 * pow(Ppr, 3)) * pow(Tpr, 2) + (b13 + b14 * Ppr + b15 * pow(Ppr, 2) + b16 * pow(Ppr, 3)) * pow(Tpr, 3);
                mu = mu_g1 * exp(X_1) / Tpr;
                break;
        }
        return mu;
    } // Viscosity


    // -------------------------------------------------------------------- //
    // Displays settings activity
    public void settings(View view) {
        EditText in_tr = (EditText) findViewById(R.id.in_tr);
        EditText in_sg = (EditText) findViewById(R.id.in_sg);
        EditText in_h2s = (EditText) findViewById(R.id.in_h2s);
        EditText in_co2 = (EditText) findViewById(R.id.in_co2);
        EditText in_pmin = (EditText) findViewById(R.id.in_pmin);
        EditText in_pmax = (EditText) findViewById(R.id.in_pmax);
        EditText in_pstep = (EditText) findViewById(R.id.in_pstep);

        EditText in_offP = (EditText) findViewById(R.id.in_offP);
        EditText in_offZ = (EditText) findViewById(R.id.in_offZ);
        EditText in_offFVF = (EditText) findViewById(R.id.in_offFVF);
        EditText in_offRho = (EditText) findViewById(R.id.in_offRho);
        EditText in_offVisc = (EditText) findViewById(R.id.in_offVisc);
        EditText in_offComp = (EditText) findViewById(R.id.in_offComp);

        try {
            T = Double.parseDouble(in_tr.getText().toString());
        } catch (Exception e) {
        }
        try {
            sg = Double.parseDouble(in_sg.getText().toString());
        } catch (Exception e) {
        }
        try {
            h2s = Double.parseDouble(in_h2s.getText().toString());
        } catch (Exception e) {
        }
        try {
            co2 = Double.parseDouble(in_co2.getText().toString());
        } catch (Exception e) {
        }
        try {
            pmin = Double.parseDouble(in_pmin.getText().toString());
            if (pmin < 1) {
                pmin = 14.7;
            }
        } catch (Exception e) {
        }
        try {
            pmax = Double.parseDouble(in_pmax.getText().toString());
        } catch (Exception e) {
        }
        try {
            pstep = Integer.parseInt(in_pstep.getText().toString());
            if (pstep >= maxsize) {
                pstep = maxsize;
            }
        } catch (Exception e) {
        }
        try {
            offP = Double.parseDouble(in_offP.getText().toString());
        } catch (Exception e) {
        }
        try {
            offZ = Double.parseDouble(in_offZ.getText().toString());
        } catch (Exception e) {
        }
        try {
            offFVF = Double.parseDouble(in_offFVF.getText().toString());
        } catch (Exception e) {
        }
        try {
            offRho = Double.parseDouble(in_offRho.getText().toString());
        } catch (Exception e) {
        }
        try {
            offVisc = Double.parseDouble(in_offVisc.getText().toString());
        } catch (Exception e) {
        }
        try {
            offComp = Double.parseDouble(in_offComp.getText().toString());
        } catch (Exception e) {
        }
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    } // settings


    // -------------------------------------------------------------------- //
    // Displays about activity
    public void about(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    } // about
}


/****************************************************************************/
