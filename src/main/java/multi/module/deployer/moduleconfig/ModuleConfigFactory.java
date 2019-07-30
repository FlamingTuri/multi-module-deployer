package multi.module.deployer.moduleconfig;


import io.vertx.core.AsyncResult;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.WebSocket;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.mqtt.messages.MqttConnAckMessage;
import multi.module.deployer.moduleconfig.configs.*;

import java.util.function.Predicate;

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
     * @param port       the port where the microservice is listening to
     * @param address    the microservice host address
     * @param requestURI the requested api
     * @return the created SetupModuleConfig instance
     */
    public static ModuleConfig httpModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        return new HttpModuleConfig(unixCmd, windowsCmd, port, address, requestURI);
    }

    /**
     * Creates a new HttpModuleConfig instance
     *
     * @param unixCmd          the commands to run on Unix-like environments
     * @param windowsCmd       the commands to run on Windows environments
     * @param port             the port where the microservice is listening to
     * @param address          the microservice host address
     * @param requestURI       the requested api
     * @param successCondition the condition to fulfill to consider the module deployed
     * @return the created SetupModuleConfig instance
     */
    public static ModuleConfig httpModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI, Predicate<AsyncResult<HttpResponse<Buffer>>> successCondition) {
        return new HttpModuleConfig(unixCmd, windowsCmd, port, address, requestURI, successCondition);
    }

    /**
     * Creates a new WebSocketModuleConfig instance
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @param port       the port where the microservice is listening to
     * @param address    the microservice host address
     * @param requestURI the requested api
     * @return the created SetupModuleConfig instance
     */
    public static ModuleConfig webSocketModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        return new WebSocketModuleConfig(unixCmd, windowsCmd, port, address, requestURI);
    }

    /**
     * Creates a new WebSocketModuleConfig instance
     *
     * @param unixCmd          the commands to run on Unix-like environments
     * @param windowsCmd       the commands to run on Windows environments
     * @param port             the port where the microservice is listening to
     * @param address          the microservice host address
     * @param requestURI       the requested api
     * @param successCondition the condition to fulfill to consider the module deployed
     * @return the created SetupModuleConfig instance
     */
    public static ModuleConfig webSocketModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI, Predicate<AsyncResult<WebSocket>> successCondition) {
        return new WebSocketModuleConfig(unixCmd, windowsCmd, port, address, requestURI, successCondition);
    }

    /**
     * Creates a new MqttModuleConfig instance
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @param port       the port where the microservice is listening to
     * @param address    the microservice host address
     * @return the created SetupModuleConfig instance
     */
    public static ModuleConfig mqttModuleConfig(String unixCmd, String windowsCmd, int port, String address) {
        return new MqttModuleConfig(unixCmd, windowsCmd, port, address);
    }

    /**
     * Creates a new MqttModuleConfig instance
     *
     * @param unixCmd          the commands to run on Unix-like environments
     * @param windowsCmd       the commands to run on Windows environments
     * @param port             the port where the microservice is listening to
     * @param address          the microservice host address
     * @param successCondition the condition to fulfill to consider the module deployed
     * @return the created SetupModuleConfig instance
     */
    public static ModuleConfig mqttModuleConfig(String unixCmd, String windowsCmd, int port, String address, Predicate<AsyncResult<MqttConnAckMessage>> successCondition) {
        return new MqttModuleConfig(unixCmd, windowsCmd, port, address, successCondition);
    }
}
