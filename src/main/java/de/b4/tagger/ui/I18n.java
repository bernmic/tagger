package de.b4.tagger.ui;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    private final static String APPTEXT_KEY = "ApplicationText";
    private I18n() {}

    private static ResourceBundle bundle;

    public static Locale getLocale() {
        return Locale.getDefault();
    }

    public static boolean isSupported(Locale l) {
        Locale[] availableLocales = Locale.getAvailableLocales();
        return Arrays.asList(availableLocales).contains(l);
    }

    public static void setLocale(Locale l) {
        Locale.setDefault(l);
    }

    public static String getMessage(String key) {
        if(bundle == null) {
            bundle = ResourceBundle.getBundle(I18n.class.getPackageName() + "." + APPTEXT_KEY);
        }
        return bundle.getString(key);
    }
}
