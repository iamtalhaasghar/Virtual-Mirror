package com.example.fyp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.AugmentedFace;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.ux.AugmentedFaceNode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AugmentedActivity extends AppCompatActivity {

    private static final double MIN_OPENGL_VERSION = 3.0;

    private FaceArFragment arFragment;

    private ModelRenderable faceRegionsRenderable;
    private Texture faceMeshTexture;
    Boolean internetavailable =true;

    private final HashMap<AugmentedFace, AugmentedFaceNode> faceNodeMap = new HashMap<>();
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }
        setContentView(R.layout.activity_augmented);
        arFragment = (FaceArFragment) getSupportFragmentManager().findFragmentById(R.id.face_fragment);
        //
        //Firebase
        FirebaseApp.initializeApp(this);

        if(internetavailable) {

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference modelRef = storage.getReference().child("glasses1.sfb");
            try {
                File file = File.createTempFile("glasses1", "sfb");

                modelRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        buildModel(file);

                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            int clickedPosition = getIntent().getIntExtra("clickedVal", 0);
            String selectedModel;
            selectedModel = new String("glasses1.sfb");
            switch (clickedPosition) {
                case 0:
                    selectedModel = new String("glasses1.sfb");
                    break;
                case 1:
                    selectedModel = new String("glasses2.sfb");
                    break;
                case 2:
                    selectedModel = new String("glasses3.sfb");
                    break;
                case 3:
                    selectedModel = new String("glasses4.sfb");
                    break;
                case 4:
                    selectedModel = new String("glasses5.sfb");
//              selectedModelID = R.raw.glasses2;
                    break;
                default:
            }
            // Load the face regions renderable.
            ModelRenderable.builder()
                    .setSource(this, Uri.parse(selectedModel))
                    .build()
                    .thenAccept(
                            modelRenderable -> {
                                faceRegionsRenderable = modelRenderable;
                                modelRenderable.setShadowCaster(false);
                                modelRenderable.setShadowReceiver(false);
                            });

            // Load the face mesh texture.
            Texture.builder()
                    .setSource(this, R.drawable.empty_texture)
                    .build()
                    .thenAccept(texture -> faceMeshTexture = texture);

            ArSceneView sceneView = arFragment.getArSceneView();

            sceneView.setCameraStreamRenderPriority(Renderable.RENDER_PRIORITY_FIRST);

            Scene scene = sceneView.getScene();

            scene.addOnUpdateListener(
                    (FrameTime frameTime) -> {
                        if (faceRegionsRenderable == null || faceMeshTexture == null) {
                            return;
                        }

                        Collection<AugmentedFace> faceList =
                                sceneView.getSession().getAllTrackables(AugmentedFace.class);

                        // Make new AugmentedFaceNodes for any new faces.
                        for (AugmentedFace face : faceList) {
                            if (!faceNodeMap.containsKey(face)) {
                                AugmentedFaceNode faceNode = new AugmentedFaceNode(face);
                                faceNode.setParent(scene);
                                faceNode.setLocalScale(new Vector3(0.8f, 0.8f, 0.8f));
                                faceNode.setFaceRegionsRenderable(faceRegionsRenderable);
                                faceNode.setFaceMeshTexture(faceMeshTexture);
                                faceNodeMap.put(face, faceNode);
                            }
                        }

                        Iterator<Map.Entry<AugmentedFace, AugmentedFaceNode>> iter =
                                faceNodeMap.entrySet().iterator();
                        while (iter.hasNext()) {
                            Map.Entry<AugmentedFace, AugmentedFaceNode> entry = iter.next();
                            AugmentedFace face = entry.getKey();
                            if (face.getTrackingState() == TrackingState.STOPPED) {
                                AugmentedFaceNode faceNode = entry.getValue();
                                faceNode.setParent(null);
                                iter.remove();
                            }
                        }
                    });

        }

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void buildModel(File file) {
        ModelRenderable
                .builder()
                .setSource(this, Uri.parse(file.getAbsolutePath()))
                .build()
                .thenAccept(modelRenderable -> {
                    Toast.makeText(this,"Build",Toast.LENGTH_SHORT).show();
                    faceRegionsRenderable = modelRenderable;
                    modelRenderable.setShadowCaster(false);
                    modelRenderable.setShadowReceiver(false);
                });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Texture.builder()
                    .setSource(this, R.drawable.makeup)
                    .build()
                    .thenAccept(
                            texture -> faceMeshTexture = texture);
        }
        ArSceneView sceneView = arFragment.getArSceneView();

        sceneView.setCameraStreamRenderPriority(Renderable.RENDER_PRIORITY_FIRST);

        Scene scene = sceneView.getScene();

        scene.addOnUpdateListener(
                (FrameTime frameTime) -> {
                    if (faceRegionsRenderable == null || faceMeshTexture == null) {
                        return;
                    }

                    Collection<AugmentedFace> faceList =
                            sceneView.getSession().getAllTrackables(AugmentedFace.class);

                    // Make new AugmentedFaceNodes for any new faces.
                    for (AugmentedFace face : faceList) {
                        if (!faceNodeMap.containsKey(face)) {
                            AugmentedFaceNode faceNode = new AugmentedFaceNode(face);
                            faceNode.setParent(scene);
                            faceNode.setLocalScale(new Vector3(0.8f,0.8f,0.8f));
                            faceNode.setFaceRegionsRenderable(faceRegionsRenderable);
                            faceNode.setFaceMeshTexture(faceMeshTexture);
                            faceNodeMap.put(face, faceNode);
                        }
                    }

                    Iterator<Map.Entry<AugmentedFace, AugmentedFaceNode>> iter =
                            faceNodeMap.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry<AugmentedFace, AugmentedFaceNode> entry = iter.next();
                        AugmentedFace face = entry.getKey();
                        if (face.getTrackingState() == TrackingState.STOPPED) {
                            AugmentedFaceNode faceNode = entry.getValue();
                            faceNode.setParent(null);
                            iter.remove();
                        }
                    }
                });
    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (ArCoreApk.getInstance().checkAvailability(activity)
                == ArCoreApk.Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE) {
            Toast.makeText(activity, "ArCore required", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Toast.makeText(activity, "Your device does not support ARcore", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }
}