package multi.module.deployer.moduleconfig;

import io.vertx.core.Future;
import multi.module.deployer.DeployWaiter;

public class NoDeploymentWaitModuleConfig extends AbstractExecInNewTermModuleConfig {

    public NoDeploymentWaitModuleConfig(String unixCmd, String windowsCmd) {
        super(unixCmd, windowsCmd, -1, null, null);
    }

    public Future<Void> waitDeployment(DeployWaiter deployWaiter) {
        return Future.succeededFuture();
    }
}
