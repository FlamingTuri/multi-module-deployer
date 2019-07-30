package multi.module.deployer.moduleconfig.configs;

import io.vertx.core.Future;
import multi.module.deployer.DeployWaiter;
import multi.module.deployer.moduleconfig.AbstractExecInNewTermModuleConfig;

/**
 * Class that does not wait for a module deployment
 */
public class NoDeploymentWaitModuleConfig extends AbstractExecInNewTermModuleConfig {

    /**
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     */
    public NoDeploymentWaitModuleConfig(String unixCmd, String windowsCmd) {
        super(unixCmd, windowsCmd, -1, null, null);
    }

    @Override
    public Future<Void> waitDeployment(DeployWaiter deployWaiter) {
        return Future.succeededFuture();
    }
}
