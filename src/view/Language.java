package view;

import java.util.Locale;
import java.util.ResourceBundle;

public class Language {
    private ResourceBundle bundle;
    static private Language language;
    static final String bundleName = "view.ui";
    public Language() {
        Locale.setDefault(new Locale("en", "US"));
        bundle = ResourceBundle.getBundle(bundleName);
    }

    static public Language getInstance() {
        if (language == null) {
            language = new Language();
        }
        return language;
    }

    public void setLanguage(String lang) {
        if (lang == "en") {
            Locale.setDefault(new Locale("en", "US"));
            bundle = ResourceBundle.getBundle(bundleName);
        } else if (lang == "vi") {
            Locale.setDefault(new Locale("vi", "VN"));
            bundle = ResourceBundle.getBundle(bundleName);
        }
    }

    public String getString(String string) {
        return bundle.getString(string);
    }
}
