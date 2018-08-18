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
 *   Source file for About Activity Class                                    *
 *   Shows a brief text about how to use the app and author info             *
 *   Last updated 08/14/2018                                                 *
 ****************************************************************************/


/****************************************************************************/


package petrosimple.gasproperties;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;


// ------------------------------------------------------------------------ //
// Displays About activity
public class AboutActivity extends AppCompatActivity {
    // -------------------------------------------------------------------- //
    // Actions to perform on activity creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        WebView webView = (WebView) findViewById(R.id.webView1);
        webView.loadData(String.format("%s", getString(R.string.about_app)), "text/html", "utf-8");
    }
}


/****************************************************************************/
