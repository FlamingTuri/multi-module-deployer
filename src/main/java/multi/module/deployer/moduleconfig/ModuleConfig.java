package multi.module.deployer.moduleconfig;

import io.vertx.core.Future;
import multi.module.deployer.DeployWaiter;


public interface ModuleConfig {

    String getUnixCmd();

    String getWindowsCmd();

    int getPort();

    String getAddress();

    String getRequestURI();

    Future<Void> waitDeployment(DeployWaiter deployWaiter);
}
