package multi.module.deployer.moduleconfig;

import io.vertx.core.Future;
import multi.module.deployer.DeployWaiter;
import multi.module.deployer.cmdrunner.CmdRunner;


public interface ModuleConfig {

    String getUnixCmd();

    String getWindowsCmd();

    int getPort();

    String getAddress();

    String getRequestURI();

    void deploy(CmdRunner cmdRunner);

    Future<Void> waitDeployment(DeployWaiter deployWaiter);
}
