package loveletter.src.utils;

import java.io.PrintStream;

// Le gestionnaire global pour désactiver/activer les impressions
public class PrintDisabler {
    private static PrintStream originalOut = System.out;
    private static PrintStream disabledOut = new PrintStream(new NoOpOutputStream());
    private static boolean printEnabled = true;

    public static void disablePrint() {
        if (printEnabled) {
            System.setOut(disabledOut);
            printEnabled = false;
        }
    }

    public static void enablePrint() {
        if (!printEnabled) {
            System.setOut(originalOut);
            printEnabled = true;
        }
    }
}