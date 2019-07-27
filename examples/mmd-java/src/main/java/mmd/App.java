package mmd;

import io.vertx.core.Future;
import multi.module.deployer.CmdRunner;
import multi.module.deployer.CmdRunnerImpl;
import multi.module.deployer.DeployWaiter;

public class App {

    private static String commonCmd;
    private static String linuxCmd;
    private static String windowsCmd;

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CmdRunner cmdRunner = new CmdRunnerImpl();
        DeployWaiter dw = new DeployWaiter();

        Future.future(v -> {
            // download dependencies and build ponger-ms
            linuxCmd = "cd ../ponger-ms; ./gradlew build";
            windowsCmd = "cd ..\\ponger-ms & gradlew.bat build";
            cmdRunner.exec(linuxCmd, windowsCmd);

            // download pinger-ms dependencies
            commonCmd = "npm install";
            linuxCmd = "cd ../pinger-ms; " + commonCmd;
            windowsCmd = "cd ..\\pinger-ms & " + commonCmd;
            cmdRunner.exec(linuxCmd, windowsCmd);

            // download web-gui dependencies
            linuxCmd = "cd ../web-gui; " + commonCmd;
            windowsCmd = "cd ..\\web-gui & " + commonCmd;
            cmdRunner.exec(linuxCmd, windowsCmd);
        }).compose(v -> {
            // run ponger-ms
            linuxCmd = "cd ../ponger-ms; ./gradlew run";
            windowsCmd = "cd ..\\ponger-ms & gradlew.bat run";
            cmdRunner.execInNewTerm(linuxCmd, windowsCmd);
            // wait for ponger-ms setup
            return dw.waitHttpDeployment(8080, "localhost", "/api/status");
        }).compose(v -> {
            // run pinger-ms
            commonCmd = "node app.js";
            linuxCmd = "cd ../pinger-ms; " + commonCmd;
            windowsCmd = "cd ..\\pinger-ms & " + commonCmd;
            cmdRunner.execInNewTerm(linuxCmd, windowsCmd);
            // wait for pinger-ms setup
            return dw.waitHttpDeployment(3000, "localhost", "/api/status");
        }).setHandler(a -> {
            // run web-gui
            commonCmd = "npx ng serve -o --poll=3000";
            linuxCmd = "cd ../web-gui; " + commonCmd;
            windowsCmd = "cd ..\\web-gui & " + commonCmd;
            cmdRunner.execInNewTerm(linuxCmd, windowsCmd);
            // close vertx instance and lets the app terminate
            dw.deployCompleted();
        });
    }
}
