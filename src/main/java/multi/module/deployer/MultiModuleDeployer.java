package multi.module.deployer;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import multi.module.deployer.cmdrunner.CmdRunner;
import multi.module.deployer.cmdrunner.CmdRunnerImpl;
import multi.module.deployer.moduleconfig.ModuleConfig;

import java.util.ArrayList;
import java.util.List;

public class MultiModuleDeployer {

    private final List<ModuleConfig[]> moduleConfigList = new ArrayList<>();
    private final CmdRunner cmdRunner = new CmdRunnerImpl();
    private final DeployWaiter deployWaiter = new DeployWaiter();

    public CmdRunner getCmdRunner() {
        return cmdRunner;
    }

    public DeployWaiter getDeployWaiter() {
        return deployWaiter;
    }

    public MultiModuleDeployer add(ModuleConfig... a) {
        moduleConfigList.add(a);
        return this;
    }

    public void deploy() {
        deploy(0);
    }

    private void deploy(int idx) {
        if (idx < moduleConfigList.size()) {
            List<Future> futures = new ArrayList<>();
            ModuleConfig[] moduleConfigs = this.moduleConfigList.get(idx);
            for (ModuleConfig moduleConfig : moduleConfigs) {
                moduleConfig.deploy(cmdRunner);
                Future deploymentFuture = moduleConfig.waitDeployment(deployWaiter);
                futures.add(deploymentFuture);
            }
            CompositeFuture.all(futures).setHandler(h -> deploy(idx + 1));
        } else {
            deployWaiter.deployCompleted();
        }
    }
}
