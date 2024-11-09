package ee.taltech.iti0202.texteditor.textformatter;

import java.util.Arrays;
import java.util.Objects;

public class BinaryFormatter implements TextFormatter {

    /**
     * Strategy to implement.
     * Converts all characters to binary, except the last if it is a newline.
     * Empty input is allowed.
     * Ex. Hello, world!\n
     * => 01001000011001010110110001101100011011110010110000100000011101110110111101110010011011000110010000100001\n
     *
     * @param text to format.
     * @return formatted string.
     */
    @Override
    public String format(String text) {
        if (text == null) {
            return "";
        }
        byte[] bytes = text.getBytes();
        StringBuilder binaryStringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            if (b == 10 && i == bytes.length - 1) {
                binaryStringBuilder.append("\n");
            } else {
                StringBuilder binary = new StringBuilder(Integer.toBinaryString(b & 0xFF));
                while (binary.length() < 8) {
                    binary.insert(0, "0");
                }
                binaryStringBuilder.append(binary);
            }
        }
        return binaryStringBuilder.toString();
    }
}
