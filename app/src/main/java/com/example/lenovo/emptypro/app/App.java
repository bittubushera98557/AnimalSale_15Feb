package com.example.lenovo.emptypro.app;

import android.app.Application;

import com.example.lenovo.emptypro.ApiCallClasses.MainRepository;
import com.example.lenovo.emptypro.ApiCallClasses.MainServiceGenerator;
import com.example.lenovo.emptypro.R;
import com.example.lenovo.emptypro.Utils.GlobalData;
import com.example.lenovo.emptypro.Utils.RxBus;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by Aakash on 06/02/2018.
 */

public class App extends Application {

    private static App app;
    private RxBus bus;

    private MainRepository mainRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        bus=new RxBus();

        mainRepository = MainRepository.getInstance(MainServiceGenerator.mainServices(GlobalData.BASE_URL));


        Trace myTrace = FirebasePerformance.getInstance().newTrace("test_trace");
        myTrace.start();

    Paper.init(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Comfortaa-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }


    public static App getApp() {
        return app;
    }

    public MainRepository getMainRepository() {
        return mainRepository;
    }
    public RxBus bus() {
        return bus;
    }
}
