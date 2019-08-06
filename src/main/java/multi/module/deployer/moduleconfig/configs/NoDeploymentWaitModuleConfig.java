package multi.module.deployer.moduleconfig.configs;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import multi.module.deployer.moduleconfig.AbstractExecInNewTermModuleConfig;
import multi.module.deployer.moduleconfig.info.ModuleInfo;

/**
 * Class that does not wait for a module deployment
 */
public class NoDeploymentWaitModuleConfig extends AbstractExecInNewTermModuleConfig<Void, ModuleInfo> {

    /**
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     */
    public NoDeploymentWaitModuleConfig(String unixCmd, String windowsCmd) {
        super(unixCmd, windowsCmd, null);
    }

    @Override
    public Future<Void> waitDeployment(Vertx vertx) {
        return Future.succeededFuture();
    }
}
