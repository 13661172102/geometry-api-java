
package com.northpool.geometry;
import java.util.Locale;

class StringUtils {

    static void appendDouble(double value, int precision,
            StringBuilder stringBuilder) {
        if (precision < 0) {
            precision = 0;
        } else if (precision > 17) {
            precision = 17;
        }

        String format = "%." + precision + "g";

        String str_dbl = String.format(Locale.US, format, value);

        boolean b_found_dot = false;
        boolean b_found_exponent = false;

        for (int i = 0; i < str_dbl.length(); i++) {
            char c = str_dbl.charAt(i);

            if (c == '.') {
                b_found_dot = true;
            } else if (c == 'e' || c == 'E') {
                b_found_exponent = true;
                break;
            }
        }

        if (b_found_dot && !b_found_exponent) {
            StringBuilder buffer = removeTrailingZeros_(str_dbl);
            stringBuilder.append(buffer);
        } else {
            stringBuilder.append(str_dbl);
        }
    }

    static void appendDoubleF(double value, int decimals,
            StringBuilder stringBuilder) {
        if (decimals < 0) {
            decimals = 0;
        } else if (decimals > 17) {
            decimals = 17;
        }

        String format = "%." + decimals + "f";

        String str_dbl = String.format(Locale.US, format, value);

        boolean b_found_dot = false;

        for (int i = 0; i < str_dbl.length(); i++) {
            char c = str_dbl.charAt(i);

            if (c == '.') {
                b_found_dot = true;
                break;
            }
        }

        if (b_found_dot) {
            StringBuilder buffer = removeTrailingZeros_(str_dbl);
            stringBuilder.append(buffer);
        } else {
            stringBuilder.append(str_dbl);
        }
    }

    static private StringBuilder removeTrailingZeros_(String str_dbl) {
        StringBuilder buffer = new StringBuilder(str_dbl);
        int non_zero = buffer.length() - 1;

        while (buffer.charAt(non_zero) == '0') {
            non_zero--;
        }

        buffer.delete(non_zero + 1, buffer.length());

        if (buffer.charAt(non_zero) == '.') {
            buffer.deleteCharAt(non_zero);
        }

        return buffer;
    }
}
