package multi.module.deployer.moduleconfig.configs;

import io.vertx.core.Future;
import multi.module.deployer.DeployWaiter;
import multi.module.deployer.moduleconfig.AbstractExecInNewTermModuleConfig;

/**
 * Class that checks for a module deployment by waiting for a successful mqtt connection
 */
public class MqttModuleConfig extends AbstractExecInNewTermModuleConfig {

    public MqttModuleConfig(String unixCmd, String windowsCmd, int port, String address) {
        super(unixCmd, windowsCmd, port, address, null);
    }

    public Future<Void> waitDeployment(DeployWaiter deployWaiter) {
        return deployWaiter.waitMqttDeployment(port, address);
    }
}
