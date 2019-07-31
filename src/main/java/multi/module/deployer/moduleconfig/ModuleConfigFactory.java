package multi.module.deployer.moduleconfig;


import io.vertx.core.AsyncResult;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.WebSocket;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.mqtt.messages.MqttConnAckMessage;
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
     * @return the created NoDeploymentWaitModuleConfig instance
     */
    public static ModuleConfig noDeploymentWaitModuleConfig(String unixCmd, String windowsCmd) {
        return new NoDeploymentWaitModuleConfig(unixCmd, windowsCmd);
    }

    /**
     * Creates a new HttpModuleConfig instance
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @param port       the port where the microservice is listening to
     * @param address    the microservice host address
     * @param requestURI the requested api
     * @return the created HttpModuleConfig instance
     */
    public static ModuleConfig<AsyncResult<HttpResponse<Buffer>>> httpModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        return new HttpModuleConfig(unixCmd, windowsCmd, port, address, requestURI);
    }

    /**
     * Creates a new WebSocketModuleConfig instance
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @param port       the port where the microservice is listening to
     * @param address    the microservice host address
     * @param requestURI the requested api
     * @return the created WebSocketModuleConfig instance
     */
    public static ModuleConfig<AsyncResult<WebSocket>> webSocketModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        return new WebSocketModuleConfig(unixCmd, windowsCmd, port, address, requestURI);
    }

    /**
     * Creates a new MqttModuleConfig instance
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @param port       the port where the microservice is listening to
     * @param address    the microservice host address
     * @return the created MqttModuleConfig instance
     */
    public static ModuleConfig<AsyncResult<MqttConnAckMessage>> mqttModuleConfig(String unixCmd, String windowsCmd, int port, String address) {
        return new MqttModuleConfig(unixCmd, windowsCmd, port, address);
    }
}
