package com.app.utlcontroller.Controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.utlcontroller.R;

/**
 * Created by Mints on 7/19/2017.
 */

public class SupportFragment extends Fragment {

    TextView valCustomerSupport;
    WebView webView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.support_fragment,null);

        RadioButton txtCustomerSupport= (RadioButton) v.findViewById(R.id.txtCustomerSupport);
        RadioButton txtUserManual= (RadioButton) v.findViewById(R.id.txtUserManual);
        RadioGroup radioGroup= (RadioGroup) v.findViewById(R.id.radioGroup);
        valCustomerSupport= (TextView) v.findViewById(R.id.valCustomerSupport);
        webView= (WebView) v.findViewById(R.id.webView);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setMinimumFontSize(18);


        //webView.loadUrl("http://www.lamarchemfg.com/");
        webView.loadUrl("file:///android_asset/usermanual.html");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.txtCustomerSupport){
                    valCustomerSupport.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.GONE);
                }else{
                    valCustomerSupport.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }
            }
        });


        return v;
    }
}
