package ee.taltech.iti0202.texteditor.textformatter;

import java.util.Objects;

public class UppercaseFormatter implements TextFormatter {

    /**
     * Strategy to be implemented.
     *
     * @param text to be formatted.
     * @return string all uppercase.
     */
    @Override
    public String format(String text) {
        if (text == null) {
            return "";
        }
        if (!Objects.equals(text, "")) {
            return text.toUpperCase();
        } else {
            return "";
        }
    }
}
