package mmd;

import multi.module.deployer.EntryPoint;

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
        EntryPoint entryPoint = new EntryPoint();

        linuxCmd = "cd ../ponger-ms; ./gradlew run";
        windowsCmd = "cd ..\\ponger-ms & gradlew.bat run";
        entryPoint.exec(linuxCmd, windowsCmd);

        sleep(5000);

        commonCmd = "node app.js";
        linuxCmd = "cd ../pinger-ms; " + commonCmd;
        windowsCmd = "cd ..\\pinger-ms & " + commonCmd;
        entryPoint.exec(linuxCmd, windowsCmd);

        sleep(3000);

        commonCmd = "npx ng serve -o --poll=3000";
        linuxCmd = "cd ../web-gui; " + commonCmd;
        windowsCmd = "cd ..\\web-gui & " + commonCmd;
        entryPoint.exec(linuxCmd, windowsCmd);
    }
}
