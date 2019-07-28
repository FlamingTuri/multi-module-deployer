package multi.module.deployer.moduleconfig;


import multi.module.deployer.moduleconfig.configs.*;

/**
 * Factory used to initialize module configs
 */
public class ModuleConfigFactory {

    /**
     * Creates a new SetupModuleConfig instance
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @return the created SetupModuleConfig instance
     */
    public static ModuleConfig setupModuleConfig(String unixCmd, String windowsCmd) {
        return new SetupModuleConfig(unixCmd, windowsCmd);
    }

    /**
     * Creates a new NoDeploymentWaitModuleConfig instance
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @return the created SetupModuleConfig instance
     */
    public static ModuleConfig noDeploymentWaitModuleConfig(String unixCmd, String windowsCmd) {
        return new NoDeploymentWaitModuleConfig(unixCmd, windowsCmd);
    }

    /**
     * Creates a new HttpModuleConfig instance
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @return the created SetupModuleConfig instance
     */
    public static ModuleConfig httpModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        return new HttpModuleConfig(unixCmd, windowsCmd, port, address, requestURI);
    }

    /**
     * Creates a new WebSocketModuleConfig instance
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @return the created SetupModuleConfig instance
     */
    public static ModuleConfig webSocketModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        return new WebSocketModuleConfig(unixCmd, windowsCmd, port, address, requestURI);
    }

    /**
     * Creates a new MqttModuleConfig instance
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @return the created SetupModuleConfig instance
     */
    public static ModuleConfig mqttModuleConfig(String unixCmd, String windowsCmd, int port, String address) {
        return new MqttModuleConfig(unixCmd, windowsCmd, port, address);
    }
}
