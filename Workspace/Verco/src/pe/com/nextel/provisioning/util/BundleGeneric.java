package pe.com.nextel.provisioning.util;

import java.util.ResourceBundle;

public class BundleGeneric {
    public BundleGeneric() {
    }

    public static String getBundle(String nameBundle, String key) {
        String rs = ResourceBundle.getBundle(nameBundle).getString(key);
        return rs;
    }
    
    public static String getBundle(String key) {
        String nameBundle = "pe.com.nextel.provisioning.util.monitoreo";
        String rs = ResourceBundle.getBundle(nameBundle).getString(key);
        return rs;
    }

}
