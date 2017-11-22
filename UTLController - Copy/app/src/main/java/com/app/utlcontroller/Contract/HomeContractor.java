package com.app.utlcontroller.Contract;

/**
 * Created by mukeshgarg on 11/15/2017.
 */

public class HomeContractor {
    public interface HomeView{
        public void displayCircuitFlow();
        public void displayMonitoringWindow();
        public void displayConfigurationWindow();
        public void displayTestWindow();
        public void displaySupportWindow();
        public void initialiseViews();
    }
}
