package pw.yumc.JsonDoclet;

import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.RootDoc;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    static MiaoScriptEngine engine = MiaoScriptEngine.getDefault();

    public static boolean start(RootDoc root) throws ScriptException, NoSuchMethodException {
        return (boolean) engine.invokeFunction("start", root);
    }

    public static int optionLength(String option) {
        System.out.println(option);
        switch (option) {
            case "-js":
            case "-d":
                return 2;
            default:
                return 0;
        }
    }

    public static boolean validOptions(String[][] options, DocErrorReporter reporter) throws ScriptException, IOException {
        boolean canStart = false;
        for (String[] option : options) {
            System.out.println(Arrays.toString(option));
            if ("-js".equals(option[0])) {
                File parsejs = new File(option[1]);
                if (!parsejs.exists()) {
                    reporter.printError("Parse Script " + parsejs.getCanonicalPath() + " Not Exist!");
                } else {
                    System.out.println("Parse Script File " + parsejs.getCanonicalPath());
                    engine.eval("load('" + parsejs.getCanonicalPath() + "')");
                    canStart = true;
                }
            }
        }
        if (!canStart) {
            reporter.printError("Correct Json Doclet Can't Start!");
        }
        return canStart;
    }
}
