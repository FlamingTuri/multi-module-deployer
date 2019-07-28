package mmd;

import multi.module.deployer.MultiModuleDeployer;
import multi.module.deployer.moduleconfig.ModuleConfig;
import multi.module.deployer.moduleconfig.ModuleConfigFactory;

public class App {

    private static String commonCmd;
    private static String linuxCmd;
    private static String windowsCmd;

    public static void main(String[] args) {
        MultiModuleDeployer multiModuleDeployer = new MultiModuleDeployer();

        // ponger-ms dependencies installation commands
        linuxCmd = "cd ../ponger-ms; pwd; ./gradlew build";
        windowsCmd = "cd ..\\ponger-ms & gradlew.bat build";
        ModuleConfig pongerBuild = ModuleConfigFactory.setupModuleConfig(linuxCmd, windowsCmd);

        // pinger-ms dependencies installation commands
        commonCmd = "npm install";
        linuxCmd = "cd ../pinger-ms; " + commonCmd;
        windowsCmd = "cd ..\\pinger-ms & " + commonCmd;
        ModuleConfig pingerBuild = ModuleConfigFactory.setupModuleConfig(linuxCmd, windowsCmd);

        // web-gui dependencies installation commands
        linuxCmd = "cd ../web-gui; pwd; " + commonCmd;
        windowsCmd = "cd ..\\web-gui & " + commonCmd;
        ModuleConfig webGuiBuild = ModuleConfigFactory.setupModuleConfig(linuxCmd, windowsCmd);

        multiModuleDeployer.add(pongerBuild, pingerBuild, webGuiBuild);

        // commands to run ponger-ms
        linuxCmd = "cd ../ponger-ms; ./gradlew run";
        windowsCmd = "cd ..\\ponger-ms & gradlew.bat run";
        multiModuleDeployer.add(ModuleConfigFactory.httpModuleConfig(linuxCmd, windowsCmd, 8080, "localhost", "/api/status"));

        // commands to run pinger-ms
        commonCmd = "node app.js";
        linuxCmd = "cd ../pinger-ms; " + commonCmd;
        windowsCmd = "cd ..\\pinger-ms & " + commonCmd;
        ModuleConfig pingerConfig = ModuleConfigFactory.httpModuleConfig(linuxCmd, windowsCmd, 3000, "localhost", "/api/status");

        // commands to run web-gui
        commonCmd = "npx ng serve -o --poll=3000";
        linuxCmd = "cd ../web-gui; " + commonCmd;
        windowsCmd = "cd ..\\web-gui & " + commonCmd;
        ModuleConfig webGuiConfig = ModuleConfigFactory.noDeploymentWaitModuleConfig(linuxCmd, windowsCmd);

        multiModuleDeployer.add(pingerConfig, webGuiConfig);

        multiModuleDeployer.deploy();
    }
}
