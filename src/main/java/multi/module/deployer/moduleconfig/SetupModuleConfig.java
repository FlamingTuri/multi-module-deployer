package multi.module.deployer.moduleconfig;

import io.vertx.core.Future;
import multi.module.deployer.DeployWaiter;
import multi.module.deployer.cmdrunner.CmdRunner;

public class SetupModuleConfig extends AbstractModuleConfig {

    public SetupModuleConfig(String unixCmd, String windowsCmd) {
        super(unixCmd, windowsCmd, -1, null, null);
    }

    @Override
    public void deploy(CmdRunner cmdRunner) {
        cmdRunner.exec(unixCmd, windowsCmd);
    }

    @Override
    public Future<Void> waitDeployment(DeployWaiter deployWaiter) {
        return Future.succeededFuture();
    }
}
