package multi.module.deployer.moduleconfig;

import io.vertx.core.Future;
import multi.module.deployer.DeployWaiter;

public class WebSocketModuleConfig extends AbstractModuleConfig {

    public WebSocketModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        super(unixCmd, windowsCmd, port, address, requestURI);
    }

    public Future<Void> waitDeployment(DeployWaiter deployWaiter) {
        return deployWaiter.waitWebsocketDeployment(port, address, requestURI);
    }
}
