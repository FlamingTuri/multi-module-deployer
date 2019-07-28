package multi.module.deployer;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import multi.module.deployer.cmdrunner.CmdRunner;
import multi.module.deployer.cmdrunner.CmdRunnerImpl;
import multi.module.deployer.moduleconfig.ModuleConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Library entry point to deploy modules and wait for their setup
 */
public class MultiModuleDeployer {

    private final List<ModuleConfig[]> moduleConfigList = new ArrayList<>();
    private final CmdRunner cmdRunner = new CmdRunnerImpl();
    private final DeployWaiter deployWaiter = new DeployWaiter();

    /**
     * Gets the CmdRunner instance
     *
     * @return the CmdRunner instance
     */
    public CmdRunner getCmdRunner() {
        return cmdRunner;
    }

    /**
     * Gets the DeployWaiter instance
     *
     * @return the DeployWaiter instance
     */
    public DeployWaiter getDeployWaiter() {
        return deployWaiter;
    }

    /**
     * Adds a new list of modules to be deployed simultaneously
     *
     * @param moduleConfigs the list of modules deploy simultaneously
     * @return this, to enable fluency
     */
    public MultiModuleDeployer add(ModuleConfig... moduleConfigs) {
        moduleConfigList.add(moduleConfigs);
        return this;
    }

    /**
     * Starts the modules deployment. The deploy order is the same of the insertion one
     */
    public void deploy() {
        deploy(0);
    }

    private void deploy(int idx) {
        if (idx < moduleConfigList.size()) {
            List<Future> futures = new ArrayList<>();
            ModuleConfig[] moduleConfigs = this.moduleConfigList.get(idx);
            for (ModuleConfig moduleConfig : moduleConfigs) {
                // deploys the module according to the commands specified
                moduleConfig.deploy(cmdRunner);
                // waits the module deployment
                Future deploymentFuture = moduleConfig.waitDeployment(deployWaiter);
                futures.add(deploymentFuture);
            }
            // after all the current idx modules have been successfully deployed
            // starts the deployment of the modules at the next idx
            CompositeFuture.all(futures).setHandler(h -> deploy(idx + 1));
        } else {
            // there are no more modules to deploy
            deployWaiter.deployCompleted();
        }
    }
}
