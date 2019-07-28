package multi.module.deployer.moduleconfig.configs;

import io.vertx.core.Future;
import multi.module.deployer.DeployWaiter;
import multi.module.deployer.moduleconfig.AbstractExecInNewTermModuleConfig;

/**
 * Class that checks for a module deployment by waiting for a successful http GET response
 */
public class HttpModuleConfig extends AbstractExecInNewTermModuleConfig {

    public HttpModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        super(unixCmd, windowsCmd, port, address, requestURI);
    }

    public Future<Void> waitDeployment(DeployWaiter deployWaiter) {
        return deployWaiter.waitHttpDeployment(port, address, requestURI);
    }
}
