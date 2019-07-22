package mmd;

import multi.module.deployer.CmdRunner;
import multi.module.deployer.CmdRunnerImpl;

public class App {

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String commonCmd;
        String linuxCmd;
        String windowsCmd;
        CmdRunner entryPoint = new CmdRunnerImpl();

        // download dependencies and build ponger-ms
        linuxCmd = "cd ../ponger-ms; ./gradlew build";
        windowsCmd = "cd ..\\ponger-ms & gradlew.bat build";
        entryPoint.exec(linuxCmd, windowsCmd);

        // download pinger-ms dependencies
        commonCmd = "npm install";
        linuxCmd = "cd ../pinger-ms; " + commonCmd;
        windowsCmd = "cd ..\\pinger-ms & " + commonCmd;
        entryPoint.exec(linuxCmd, windowsCmd);

        // download web-gui dependencies
        linuxCmd = "cd ../web-gui; " + commonCmd;
        windowsCmd = "cd ..\\web-gui & " + commonCmd;
        entryPoint.exec(linuxCmd, windowsCmd);

        // run ponger-ms
        linuxCmd = "cd ../ponger-ms; ./gradlew run";
        windowsCmd = "cd ..\\ponger-ms & gradlew.bat run";
        entryPoint.execInNewTerm(linuxCmd, windowsCmd);
        // wait for it's setup
        sleep(5000);

        // run pinger-ms
        commonCmd = "node app.js";
        linuxCmd = "cd ../pinger-ms; " + commonCmd;
        windowsCmd = "cd ..\\pinger-ms & " + commonCmd;
        entryPoint.execInNewTerm(linuxCmd, windowsCmd);
        // wait for it's setup
        sleep(3000);

        // run web-gui
        commonCmd = "npx ng serve -o --poll=3000";
        linuxCmd = "cd ../web-gui; " + commonCmd;
        windowsCmd = "cd ..\\web-gui & " + commonCmd;
        entryPoint.execInNewTerm(linuxCmd, windowsCmd);
    }
}
