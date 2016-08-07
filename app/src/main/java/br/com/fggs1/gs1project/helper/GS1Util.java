package br.com.fggs1.gs1project.helper;

import java.text.NumberFormat;
import java.util.Locale;

public class GS1Util {

    private GS1Util() {

    }

    public static String getFormattedValue(double value) {

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        return nf.format(value);
    }
}
