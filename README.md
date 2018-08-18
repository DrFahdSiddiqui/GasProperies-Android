# OrificeGasFlow
## What is it?
This app computes the gas flow rate using the orifice plate energy equation. The app follows the standard procedure described in ISO 5167-2. The orifice discharge coefficient is calculated using the Reader-Harris/Gallagher (1998) equation.

## Where to get it?
[Google Play Store](https://play.google.com/store/apps/details?id=petrosimple.orificeflow)

## Why?
Because I once needed such a tool while on field :). 
[Blog](http://fahdsiddiqui.com/orificegasflowandroid-java/)


## How to use?
[Youtube Video](https://youtu.be/wxOqhRE2JvY)

This app requires the pipe diameter (D), orifice diameter (d), upstream pressure (P1), the differential pressure across the orifice (P1-P2), downstream temperature (T), gas specific gravity and gas isentropic expansion factor/heat capacity ratio (k=Cp/Cv). The user has the option of specifying the units for each, hence the values can have any mix of units.

The tap location can be selected from the ISO 5167-2 specified: Flange Taps, Corner Taps, D and D/2 Taps and an option to specify manual tap locations. The Flange Taps standard measures the pressure and differential at 1inch intervals from the orifice plate on either side. The Corner Taps are located just upstream and downstream of the orifice plate. The D and D/2 Taps are located at a distance of pipe diameter on the upstream side and half pipe diameter distance on the downstream side of the orifice plate. Even though the user can manually specify these distances as well it is not recommended as it is not covered by the ISO 5167-2 standard. In general large deviations from the standard values will produce a larger uncertainty.

The correlations for computing Z-factor and viscosity can be changed by selecting SETTINGS from the MENU. It is also possible to use the Square Root Circular Flow Recorder Chart readings by first converting them. The app can convert the chart readings by clicking the "USE CHART" button. The readings can be converted as well as substituted for pressure, differential pressure and temperature for onward calculation of gas flow rate.

The results of calculation are displayed in SI/Filed units. This app computes the volumetric gas flow rate at standard conditions of 60F and 14.7 psia for field units and 0C and 1 atmosphere for SI units. The volumetric gas rate at pipe upstream conditions, mass flow rate and velocities through the pipe and orifice are also computed. The intermediate calculations are also displayed for reference, such as Reynolds number, orifice coefficient, gas Z-factor and gas viscosity. More detailed intermediate values are displayed in the text only report. The units can be switched between field and SI units by clicking the "Change Units" button.

Finally the report of the calculation is also available in copyable tab delimited text form to paste into a mobile word processor. The "Email Report" button launches the default android email app with the report in the text of the body.
