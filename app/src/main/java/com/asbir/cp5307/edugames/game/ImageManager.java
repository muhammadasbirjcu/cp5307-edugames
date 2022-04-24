package com.asbir.cp5307.edugames.game;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ImageManager {
    private String assetPath;
    private String[] imageNames;
    private AssetManager assetManager;

    ImageManager (AssetManager assetManager, String assetPath){
        this.assetManager = assetManager;
        this.assetPath = assetPath;
        try{
            this.imageNames = assetManager.list(assetPath);
        }catch (IOException ex){

        }

    }

    public Bitmap get(int i) throws IOException {
        InputStream stream = this.assetManager.open(this.assetPath + "/" + this.imageNames[i]);
        return BitmapFactory.decodeStream(stream);
    }

    public String getName(int i) {
        String fullName = this.imageNames[i];
        return fullName.substring(0, fullName.lastIndexOf("."));
    }

    public String[] getNames() throws IOException {
        ArrayList<String> names = new ArrayList<String>();
        for(int x = 0; x < this.imageNames.length; x++){
            names.add(getName(x));
        }
        return names.toArray(new String[]{});
    }

    public int count()  {
        return this.imageNames.length;
    }
}
