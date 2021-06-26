package com.example.fyp;

import android.util.Log;

import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ux.ArFragment;

public class FootArFragment extends ArFragment {
    @Override
    protected Config getSessionConfiguration(Session session) {
        getPlaneDiscoveryController().setInstructionView(null);
        Config config = new Config(session);
        // setup auto focus
        config.setFocusMode(Config.FocusMode.AUTO);
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        session.configure(config);
        getArSceneView().setupSession(session);
        if ((((FootActivity) getActivity()).setupAugmentedImagesDb(config, session))) {
            Log.d("SetupAugImgDb", "Success");
        } else {
            Log.e("SetupAugImgDb","Failure occurred in setting up image database");
        }
        return config;
    }

}
