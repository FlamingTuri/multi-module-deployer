package multi.module.deployer.moduleconfig.configs;

import io.vertx.core.Future;
import multi.module.deployer.DeployWaiter;
import multi.module.deployer.cmdrunner.CmdRunner;
import multi.module.deployer.moduleconfig.AbstractModuleConfig;

/**
 * Class that waits for a module termination
 */
public class SetupModuleConfig extends AbstractModuleConfig {

    private Process process;

    /**
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     */
    public SetupModuleConfig(String unixCmd, String windowsCmd) {
        super(unixCmd, windowsCmd, -1, null, null);
    }

    @Override
    public void deploy(CmdRunner cmdRunner) {
        process = cmdRunner.exec(unixCmd, windowsCmd);
    }

    @Override
    public Future<Void> waitDeployment(DeployWaiter deployWaiter) {
        return deployWaiter.waitProcessTermination(process);
    }
}
