package com.master.antonio.myassistant.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.master.antonio.myassistant.R;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Created by Antonio on 20/06/2017.
 */

public final class MyAssistantUtilities {

    private MyAssistantUtilities() {

    }

    public static String[] getEstancias() {
        String[] estancias = {"Baño", "Cocina", "Cochera", "Despacho", "Dormitorio", "Gimnasio", "Lavadero", "Sala de Estar", "Salón"};
        return estancias;
    }

    public static int[] getIconos() {
        int[] iconos = {R.drawable.bano, R.drawable.cocina, R.drawable.garaje, R.drawable.despacho, R.drawable.dormitorio, R.drawable.gimnasio, R.drawable.lavadero, R.drawable.estar, R.drawable.tv};
        return iconos;
    }

    public static UUID getGuidFromByteArray(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        UUID uuid = new UUID(bb.getLong(), bb.getLong());
        return uuid;
    }

    public static Boolean estaConectado(Context ctx) {
        ConnectivityManager connectivity = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            return (info != null && info.isConnected());
        }
        return false;
    }
}
