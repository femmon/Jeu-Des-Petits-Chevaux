/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Project
  Created date: 14/01/2020
  By: Phuc Quang, Tran Quang, Duc, Hong, Van
  Last modified: 14/01/2020
  By: Phuc Quang, Tran Quang, Duc, Hong, Van
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/
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
