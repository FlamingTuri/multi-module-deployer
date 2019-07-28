package multi.module.deployer.moduleconfig.configs;

import io.vertx.core.Future;
import multi.module.deployer.DeployWaiter;
import multi.module.deployer.moduleconfig.AbstractExecInNewTermModuleConfig;

/**
 * Class that does not wait for a module deployment
 */
public class NoDeploymentWaitModuleConfig extends AbstractExecInNewTermModuleConfig {

    public NoDeploymentWaitModuleConfig(String unixCmd, String windowsCmd) {
        super(unixCmd, windowsCmd, -1, null, null);
    }

    public Future<Void> waitDeployment(DeployWaiter deployWaiter) {
        return Future.succeededFuture();
    }
}
