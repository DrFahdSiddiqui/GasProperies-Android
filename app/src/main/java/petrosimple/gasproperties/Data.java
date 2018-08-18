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
 *   Source file for data objects Class                                      *
 *   Last updated 08/14/2018                                                 *
 ****************************************************************************/

/*****************************************************************************
 * TODO                                                                      *
 *   Better way of coding (this looks like a hacky way)                      *
 ****************************************************************************/


/****************************************************************************/


package petrosimple.gasproperties;


// ------------------------------------------------------------------------ //
// Class for holding data pieces for sharing between activities
public class Data {
    public static Data mInstance = null;

    static public Double sg = 0.7, co2 = 0.0, h2s = 0.0, T = 120.0, mw = 100.0, Tpc = 1.0, Ppc = 1.0, Tpr = 1.0, Ppr = 1.0, tol = 1e-10;
    static int Zcorr = 0, visccorr = 0, pstep = 30, maxsize = 100;
    static Double pmin = 14.7, pmax = 4500.0;
    static Double offP = 0.0, offZ = 0.0, offFVF = 0.0, offVisc = 0.0, offComp = 0.0, offRho = 0.0;
    static Double[] p = new Double[maxsize];
    static Double[] Z = new Double[maxsize];
    static Double[] fvf = new Double[maxsize];
    static Double[] rho = new Double[maxsize];
    static Double[] mu = new Double[maxsize];
    static Double[] comp = new Double[maxsize];
    static Double[] mp = new Double[maxsize];
    static String ZText = "     Hall and Yarborough", viscText = "     Lee et al";

    public static Data ta() {
        if (mInstance == null) {
            for (int i = 0; i < maxsize; i++) {
                p[i] = 0.0;
                Z[i] = 0.0;
                fvf[i] = 0.0;
                rho[i] = 0.0;
                mu[i] = 0.0;
                comp[i] = 0.0;
                mp[i] = 0.0;
            }
            mInstance = new Data();
        }
        return mInstance;
    }
}


/****************************************************************************/
