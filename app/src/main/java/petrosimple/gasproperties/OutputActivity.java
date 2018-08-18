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
 *   Source file for OutputsActivity Class                                   *
 *   Last updated 08/14/2018                                                 *
 ****************************************************************************/

/*****************************************************************************
 * TODO                                                                      *
 *   Code cleanup and re-usability                                           *
 ****************************************************************************/


/****************************************************************************/


package petrosimple.gasproperties;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.Locale;

import static petrosimple.gasproperties.Data.*;


// ------------------------------------------------------------------------ //
// Displays Outputs activity
public class OutputActivity extends AppCompatActivity {
    // -------------------------------------------------------------------- //
    // Actions to perform on activity creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        //Casting all the output views
        TextView op_pres = (TextView) findViewById(R.id.op_pres);
        TextView op_rs = (TextView) findViewById(R.id.op_rs);
        TextView op_fvf = (TextView) findViewById(R.id.op_fvf);
        TextView op_rho = (TextView) findViewById(R.id.op_rho);
        TextView op_visc = (TextView) findViewById(R.id.op_visc);
        TextView op_comp = (TextView) findViewById(R.id.op_comp);
        TextView op_mp = (TextView) findViewById(R.id.op_mp);

        //Setting the output to required answers
        String[] Strp = new String[pstep];
        String[] StrZ = new String[pstep];
        String[] Strfvf = new String[pstep];
        String[] Strrho = new String[pstep];
        String[] Strmu = new String[pstep];
        String[] Strcomp = new String[pstep];
        String[] Strmp = new String[pstep];

        for (int i = 0; i < pstep; i++) {
            Strp[i] = String.format(Locale.getDefault(), "%.1f", p[i]);
            StrZ[i] = String.format(Locale.getDefault(), "%.4f", Z[i]);
            Strfvf[i] = String.format(Locale.getDefault(), "%1.4e", fvf[i]);
            Strrho[i] = String.format(Locale.getDefault(), "%.3f", rho[i]);
            Strmu[i] = String.format(Locale.getDefault(), "%.3e", mu[i]);
            Strcomp[i] = String.format(Locale.getDefault(), "%1.4e ", comp[i]);
            Strmp[i] = String.format(Locale.getDefault(), "%1.2e ", mp[i]);
        }
        int j = 0;
        for (int i = 0; i < Strp.length; i++) {
            op_pres.append(Strp[i] + "\n");
            op_rs.append(StrZ[i] + "\n");
            op_fvf.append(Strfvf[i] + "\n");
            op_rho.append(Strrho[i] + "\n");
            op_visc.append(Strmu[i] + "\n");
            op_comp.append(Strcomp[i] + "\n");
            op_mp.append(Strmp[i] + "\n");
            if ((i < Strp.length - 1) && ((i + 1) % 10 == 0)) {
                op_pres.append("________" + "\n");
                op_rs.append("________" + "\n");
                op_fvf.append("________" + "\n");
                op_rho.append("________" + "\n");
                op_visc.append("________" + "\n");
                op_comp.append("________" + "\n");
                op_mp.append("________" + "\n");
            }
        }

        // Z-factor Graph
        GraphView graph1 = (GraphView) findViewById(R.id.gr_Z);
        graph1.getGridLabelRenderer().setGridColor(Color.WHITE);
        graph1.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);
        graph1.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.WHITE);
        graph1.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph1.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph1.setTitle("Z-factor");
        graph1.getGridLabelRenderer().setHorizontalAxisTitle("Pressure [psia]");
        graph1.getGridLabelRenderer().setVerticalAxisTitle("Z-factor");
        graph1.getViewport().setScalable(false);
        graph1.getViewport().setScrollable(false);
        graph1.setBackgroundColor(Color.rgb(0, 0, 0));
        graph1.getGridLabelRenderer().setPadding(60);
        graph1.getGridLabelRenderer().reloadStyles();

        DataPoint[] datapointsZ = new DataPoint[pstep];
        DataPoint[] datapointsoffZ = new DataPoint[1];
        for (int i = 0; i < pstep; i++) {
            datapointsZ[i] = new DataPoint(Double.parseDouble(p[i].toString()), Double.parseDouble(Z[i].toString()));
        }
        LineGraphSeries<DataPoint> seriesZ = new LineGraphSeries<>(datapointsZ);
        graph1.addSeries(seriesZ);
        seriesZ.setColor(Color.CYAN);
        seriesZ.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series seriesZ, DataPointInterface dataPoint) {
                Toast.makeText(OutputActivity.this, "Calculated:" + dataPoint, Toast.LENGTH_LONG).show();
            }
        });

        if (offP > 0.0 && offZ > 0.0) {
            datapointsoffZ[0] = new DataPoint(Double.parseDouble(offP.toString()), Double.parseDouble(offZ.toString()));
            PointsGraphSeries<DataPoint> seriesoffZ = new PointsGraphSeries<>(datapointsoffZ);
            graph1.addSeries(seriesoffZ);
            seriesoffZ.setTitle("Measured Lab/Field Data");
            seriesoffZ.setShape(PointsGraphSeries.Shape.TRIANGLE);
            seriesoffZ.setColor(Color.GREEN);
            seriesZ.setTitle("Calculated");
            graph1.getLegendRenderer().setVisible(true);
            graph1.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
            graph1.getLegendRenderer().setBackgroundColor(Color.BLACK);
            graph1.getLegendRenderer().setTextColor(Color.WHITE);
            graph1.getGridLabelRenderer().reloadStyles();
            seriesoffZ.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series seriesoffZ, DataPointInterface dataPoint) {
                    Toast.makeText(OutputActivity.this, "Test Point:" + dataPoint, Toast.LENGTH_LONG).show();
                }
            });
        }

        // FVF Graph
        GraphView graph2 = (GraphView) findViewById(R.id.gr_fvf);
        graph2.getGridLabelRenderer().setGridColor(Color.WHITE);
        graph2.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);
        graph2.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.WHITE);
        graph2.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph2.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph2.setTitle("Formation Volume Factor (βg)");
        graph2.getGridLabelRenderer().setHorizontalAxisTitle("Pressure [psia]");
        graph2.getGridLabelRenderer().setVerticalAxisTitle("FVF/1000 [rcf/scf]");
        graph2.getViewport().setScalable(false);
        graph2.getViewport().setScrollable(false);
        graph2.setBackgroundColor(Color.rgb(0, 0, 0));
        graph2.getGridLabelRenderer().setPadding(60);
        graph2.getGridLabelRenderer().reloadStyles();

        DataPoint[] datapointsFVF = new DataPoint[pstep];
        DataPoint[] datapointsoffFVF = new DataPoint[1];
        for (int i = 0; i < pstep; i++) {
            datapointsFVF[i] = new DataPoint(Double.parseDouble(p[i].toString()), Double.parseDouble(Double.toString(fvf[i] * 1000.0)));
        }
        LineGraphSeries<DataPoint> seriesFVF = new LineGraphSeries<>(datapointsFVF);
        graph2.addSeries(seriesFVF);
        seriesFVF.setColor(Color.CYAN);
        seriesFVF.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series seriesFVF, DataPointInterface dataPoint) {
                Toast.makeText(OutputActivity.this, "Calculated:" + dataPoint, Toast.LENGTH_LONG).show();
            }
        });

        if (offP > 0.0 && offFVF > 0.0) {
            datapointsoffFVF[0] = new DataPoint(Double.parseDouble(offP.toString()), Double.parseDouble(Double.toString(offFVF * 1000)));
            PointsGraphSeries<DataPoint> seriesoffFVF = new PointsGraphSeries<DataPoint>(datapointsoffFVF);
            graph2.addSeries(seriesoffFVF);
            seriesoffFVF.setTitle("Measured Lab/Field Data");
            seriesoffFVF.setShape(PointsGraphSeries.Shape.TRIANGLE);
            seriesoffFVF.setColor(Color.GREEN);
            seriesFVF.setTitle("Calculated");
            graph2.getLegendRenderer().setVisible(true);
            graph2.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
            graph2.getLegendRenderer().setBackgroundColor(Color.BLACK);
            graph2.getLegendRenderer().setTextColor(Color.WHITE);
            graph2.getGridLabelRenderer().reloadStyles();
            seriesoffFVF.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series seriesoffFVF, DataPointInterface dataPoint) {
                    Toast.makeText(OutputActivity.this, "Test Point:" + dataPoint, Toast.LENGTH_LONG).show();
                }
            });
        }

        // Density Graph
        GraphView graph3 = (GraphView) findViewById(R.id.gr_rho);
        graph3.getGridLabelRenderer().setGridColor(Color.WHITE);
        graph3.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);
        graph3.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.WHITE);
        graph3.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph3.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph3.setTitle("Density (ρg)");
        graph3.getGridLabelRenderer().setHorizontalAxisTitle("Pressure [psia]");
        graph3.getGridLabelRenderer().setVerticalAxisTitle("Gas Density [lb/ft3]");
        graph3.getViewport().setScalable(false);
        graph3.getViewport().setScrollable(false);
        graph3.setBackgroundColor(Color.rgb(0, 0, 0));
        graph3.getGridLabelRenderer().setPadding(60);
        graph3.getGridLabelRenderer().reloadStyles();

        DataPoint[] datapointsRho = new DataPoint[pstep];
        DataPoint[] datapointsoffRho = new DataPoint[1];
        for (int i = 0; i < pstep; i++) {
            datapointsRho[i] = new DataPoint(Double.parseDouble(p[i].toString()), Double.parseDouble(rho[i].toString()));
        }
        LineGraphSeries<DataPoint> seriesRho = new LineGraphSeries<DataPoint>(datapointsRho);
        graph3.addSeries(seriesRho);
        seriesRho.setColor(Color.CYAN);
        seriesRho.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series seriesRho, DataPointInterface dataPoint) {
                Toast.makeText(OutputActivity.this, "Calculated:" + dataPoint, Toast.LENGTH_LONG).show();
            }
        });

        if (offP > 0.0 && offRho > 0.0) {
            datapointsoffRho[0] = new DataPoint(Double.parseDouble(offP.toString()), Double.parseDouble(offRho.toString()));
            PointsGraphSeries<DataPoint> seriesoffRho = new PointsGraphSeries<DataPoint>(datapointsoffRho);
            graph3.addSeries(seriesoffRho);
            seriesoffRho.setTitle("Measured Lab/Field Data");
            seriesoffRho.setShape(PointsGraphSeries.Shape.TRIANGLE);
            seriesoffRho.setColor(Color.GREEN);
            seriesRho.setTitle("Calculated");
            graph3.getLegendRenderer().setVisible(true);
            graph3.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
            graph3.getLegendRenderer().setBackgroundColor(Color.BLACK);
            graph3.getLegendRenderer().setTextColor(Color.WHITE);
            graph3.getGridLabelRenderer().reloadStyles();
            seriesoffRho.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series seriesoffRho, DataPointInterface dataPoint) {
                    Toast.makeText(OutputActivity.this, "Test Point:" + dataPoint, Toast.LENGTH_LONG).show();
                }
            });
        }


//Viscosity Graph
        GraphView graph4 = (GraphView) findViewById(R.id.gr_visc);
        graph4.getGridLabelRenderer().setGridColor(Color.WHITE);
        graph4.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);
        graph4.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.WHITE);
        graph4.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph4.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph4.setTitle("Viscosity (μg)");
        graph4.getGridLabelRenderer().setHorizontalAxisTitle("Pressure [psia]");
        graph4.getGridLabelRenderer().setVerticalAxisTitle("Viscosity/100 [cp]");
        graph4.getViewport().setScalable(false);
        graph4.getViewport().setScrollable(false);
        graph4.setBackgroundColor(Color.rgb(0, 0, 0));
        graph4.getGridLabelRenderer().setPadding(60);
        graph4.getGridLabelRenderer().reloadStyles();

        DataPoint[] datapointsVisc = new DataPoint[pstep];
        DataPoint[] datapointsoffVisc = new DataPoint[1];
        for (int i = 0; i < pstep; i++) {
            datapointsVisc[i] = new DataPoint(Double.parseDouble(p[i].toString()), Double.parseDouble(Double.toString(mu[i] * 100.0)));
        }
        LineGraphSeries<DataPoint> seriesVisc = new LineGraphSeries<DataPoint>(datapointsVisc);
        graph4.addSeries(seriesVisc);
        seriesVisc.setColor(Color.CYAN);
        seriesVisc.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series seriesVisc, DataPointInterface dataPoint) {
                Toast.makeText(OutputActivity.this, "Calculated:" + dataPoint, Toast.LENGTH_LONG).show();
            }
        });

        if (offP > 0.0 && offVisc > 0.0) {
            datapointsoffVisc[0] = new DataPoint(Double.parseDouble(offP.toString()), Double.parseDouble(Double.toString(offVisc * 100)));
            PointsGraphSeries<DataPoint> seriesoffVisc = new PointsGraphSeries<DataPoint>(datapointsoffVisc);
            graph4.addSeries(seriesoffVisc);
            seriesoffVisc.setTitle("Measured Lab/Field Data");
            seriesoffVisc.setShape(PointsGraphSeries.Shape.TRIANGLE);
            seriesoffVisc.setColor(Color.GREEN);
            seriesVisc.setTitle("Calculated");
            graph4.getLegendRenderer().setVisible(true);
            graph4.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
            graph4.getLegendRenderer().setBackgroundColor(Color.BLACK);
            graph4.getLegendRenderer().setTextColor(Color.WHITE);
            graph4.getGridLabelRenderer().reloadStyles();
            seriesoffVisc.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series seriesoffVisc, DataPointInterface dataPoint) {
                    Toast.makeText(OutputActivity.this, "Test Point:" + dataPoint, Toast.LENGTH_LONG).show();
                }
            });
        }

//Viscosity Graph
        GraphView graph6 = (GraphView) findViewById(R.id.gr_mp);
        graph6.getGridLabelRenderer().setGridColor(Color.WHITE);
        graph6.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);
        graph6.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.WHITE);
        graph6.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph6.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph6.setTitle("Pseudo Pressure (Ψ(p))");
        graph6.getGridLabelRenderer().setHorizontalAxisTitle("Pressure [psia]");
        graph6.getGridLabelRenderer().setVerticalAxisTitle("Pseudo-Pressure x1E8 [psi2/cp]");
        graph6.getViewport().setScalable(false);
        graph6.getViewport().setScrollable(false);
        graph6.setBackgroundColor(Color.rgb(0, 0, 0));
        graph6.getGridLabelRenderer().setPadding(60);
        graph6.getGridLabelRenderer().reloadStyles();

        datapointsVisc = new DataPoint[pstep];
        datapointsoffVisc = new DataPoint[1];
        for (int i = 0; i < pstep; i++) {
            datapointsVisc[i] = new DataPoint(Double.parseDouble(p[i].toString()), Double.parseDouble(Double.toString(mp[i] / 1e8)));
        }
        seriesVisc = new LineGraphSeries<DataPoint>(datapointsVisc);
        graph6.addSeries(seriesVisc);
        seriesVisc.setColor(Color.CYAN);
        seriesVisc.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series seriesVisc, DataPointInterface dataPoint) {
                Toast.makeText(OutputActivity.this, "Calculated:" + dataPoint, Toast.LENGTH_LONG).show();
            }
        });

//Compressibility Graph
        GraphView graph5 = (GraphView) findViewById(R.id.gr_comp);
        graph5.getGridLabelRenderer().setGridColor(Color.WHITE);
        graph5.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);
        graph5.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.WHITE);
        graph5.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph5.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph5.setTitle("Compressibility (Cg)");
        graph5.getGridLabelRenderer().setHorizontalAxisTitle("Pressure [psia]");
        graph5.getGridLabelRenderer().setVerticalAxisTitle("Compressibility /1000 [1/psia]");
        graph5.getViewport().setScalable(false);
        graph5.getViewport().setScrollable(false);
        graph5.setBackgroundColor(Color.rgb(0, 0, 0));
        graph5.getGridLabelRenderer().setPadding(60);
        graph5.getGridLabelRenderer().reloadStyles();

        j = 0;
        for (int i = 0; i < pstep; i++) {
            if (comp[i] > 0) {
                j++;
            }
        }
        if (j == 0) {
            comp[0] = 0.000001;
            j = 1;
        }

        Double[] tempcomp = new Double[j];
        DataPoint[] datapointsComp = new DataPoint[j];
        DataPoint[] datapointsoffComp = new DataPoint[1];
        j = 0;
        for (int i = 0; i < pstep; i++) {
            if (comp[i] > 0) {
                datapointsComp[j] = new DataPoint(Double.parseDouble(p[i].toString()), Double.parseDouble(Double.toString(comp[i] * 1000)));
                tempcomp[j] = comp[i] * 1000;
                j++;
            }
        }

        LineGraphSeries<DataPoint> seriesComp = new LineGraphSeries<DataPoint>(datapointsComp);
        graph5.addSeries(seriesComp);
        seriesComp.setColor(Color.CYAN);
        seriesComp.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series seriesComp, DataPointInterface dataPoint) {
                Toast.makeText(OutputActivity.this, "Calculated:" + dataPoint, Toast.LENGTH_LONG).show();
            }
        });

        if (offP > 0.0 && offComp > 0.0) {
            datapointsoffComp[0] = new DataPoint(Double.parseDouble(offP.toString()), Double.parseDouble(Double.toString(offComp * 1000)));
            PointsGraphSeries<DataPoint> seriesoffComp = new PointsGraphSeries<DataPoint>(datapointsoffComp);
            graph5.addSeries(seriesoffComp);
            seriesoffComp.setTitle("Measured Lab/Field Data");
            seriesComp.setTitle("Calculated");
            seriesoffComp.setShape(PointsGraphSeries.Shape.TRIANGLE);
            seriesoffComp.setColor(Color.GREEN);
            graph5.getLegendRenderer().setVisible(true);
            graph5.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
            graph5.getLegendRenderer().setBackgroundColor(Color.BLACK);
            graph5.getLegendRenderer().setTextColor(Color.WHITE);
            graph5.getGridLabelRenderer().reloadStyles();
            seriesoffComp.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series seriesoffComp, DataPointInterface dataPoint) {
                    Toast.makeText(OutputActivity.this, "Test Point:" + dataPoint, Toast.LENGTH_LONG).show();
                }
            });
        }
    }//onCreate Ends


    //About onClick method
    public void report(View view) {
        Intent intent2 = new Intent(this, TextActivity.class);
        startActivity(intent2);
    }


    public void onDestroy() {
        for (int i = 0; i < maxsize; i++) {
            p[i] = 0.0;
            Z[i] = 0.0;
            fvf[i] = 0.0;
            rho[i] = 0.0;
            mu[i] = 0.0;
            comp[i] = 0.0;
        }

        super.onDestroy();
    }


}