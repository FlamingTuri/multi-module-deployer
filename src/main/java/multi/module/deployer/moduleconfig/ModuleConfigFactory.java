package multi.module.deployer.moduleconfig;

public class ModuleConfigFactory {

    public static ModuleConfig setupModuleConfig(String unixCmd, String windowsCmd) {
        return new SetupModuleConfig(unixCmd, windowsCmd);
    }

    public static ModuleConfig noDeploymentWaitModuleConfig(String unixCmd, String windowsCmd) {
        return new NoDeploymentWaitModuleConfig(unixCmd, windowsCmd);
    }

    public static ModuleConfig httpModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        return new HttpModuleConfig(unixCmd, windowsCmd, port, address, requestURI);
    }

    public static ModuleConfig webSocketModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        return new WebSocketModuleConfig(unixCmd, windowsCmd, port, address, requestURI);
    }

    public static ModuleConfig mqttModuleConfig(String unixCmd, String windowsCmd, int port, String address) {
        return new MqttModuleConfig(unixCmd, windowsCmd, port, address);
    }
}
