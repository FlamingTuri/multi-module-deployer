package multi.module.deployer.moduleconfig;

import io.vertx.core.Future;
import multi.module.deployer.DeployWaiter;

public class MqttModuleConfig extends AbstractExecInNewTermModuleConfig {

    public MqttModuleConfig(String unixCmd, String windowsCmd, int port, String address) {
        super(unixCmd, windowsCmd, port, address, null);
    }

    public Future<Void> waitDeployment(DeployWaiter deployWaiter) {
        return deployWaiter.waitMqttDeployment(port, address);
    }
}
