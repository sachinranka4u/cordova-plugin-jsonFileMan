package com.sachin.testing;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;

import android.util.Log;
import android.content.Context;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

public class JSONFileRW extends CordovaPlugin {

    public static final String TAG = "JSONFileRW";

    /**
     * constructor
     */
    public JSONFileRW() {

    }

    private Context getApplicationContext() {
        return this.cordova.getActivity().getApplicationContext();
    }

    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

    }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action The action to execute.
     * @param args JSONArry of arguments for the plugin.
     * @param callbackId The callback id used when calling back into JavaScript.
     * @return A PluginResult object with a status and message.
     */
    public boolean execute(final String action, JSONArray args,
            CallbackContext callbackContext) throws JSONException {
        boolean result = false;
        String extn = null;
        boolean writeMode = false;
        Log.v(TAG, "execute: action=" + action);
        if (!action.equals("writeJSON")) {
            callbackContext.error("Invalid action : " + action);
            result = false;
        } else if (!action.equals("readJSON")) {
            callbackContext.error("Invalid action : " + action);
            result = false;
            return result;
        }
        if (action.equals("writeJSON")) {
            Log.v(TAG, "Write JSON to file mode");
            writeMode = true;
        } else {
            Log.v(TAG, "Read from file to JSON");
        }
        try {
            // getting JSONObject (write mode) or JSON key (read mode)
            if (writeMode) {
                // Write to JSON mode
                String jsonString = args.getString(0);
                // JSONObject jsonObj = new JSONObject(jsonString);
                JSONObject params = args.getJSONObject(1);
                extn = ".txt";
                // Optional parameter
                String fileName = (params.has("filename") ? params
                        .getString("filename") : "img_"
                        + System.currentTimeMillis())
                        + extn;

                String storagetype = params.has("externalStorage") ? Environment
                        .getExternalStorageDirectory() + ""
                        : getApplicationContext().getFilesDir()
                        .getAbsolutePath();

                String folder = params.has("folder") ? storagetype + "/"
                        + params.getString("folder") : storagetype
                        + "/Data/Ignite";

                Boolean overwrite = params.has("overwrite") ? params
                        .getBoolean("overwrite") : false;
                File dir = new File(folder);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(folder, fileName);

                // Avoid overwriting a file
                if (!overwrite && file.exists()) {
                    Log.v(TAG, "File already exists");
                    // return new PluginResult(PluginResult.Status.OK,
                    // "File already exists!");
                    callbackContext.error("File already exists! and overwrite is false");
                    return false;
                }

                file.createNewFile();
                FileWriter fOut = new FileWriter(file);
                fOut.write(jsonString);
                fOut.flush();
                fOut.close();
                callbackContext.success(file.getPath());
                Log.v(TAG, "Saved successfully");
                result = true;
            } else {
                // read from JSON mode
                String fileName = args.getString(0);
                File f = new File(fileName);
                if (f.exists()) {
                    FileInputStream is = new FileInputStream(f);
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    callbackContext.success(new String(buffer));
                    Log.v(TAG, "File read success!");
                    result = true;
                } else {
                    Log.v(TAG, "File does not exists, path passed : "
                            + fileName);
                    callbackContext
                            .error("Exception : FileNotFoundException path specified is "
                                    + fileName);
                    return false;
                }
            }
        } catch (Exception e) {
            Log.v(TAG, e.getMessage());
            callbackContext.error("Exception :" + e.getMessage());
            result = false;
        }

        return result;
    }
}
