package multi.module.deployer.moduleconfig;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import multi.module.deployer.DeployWaiter;
import multi.module.deployer.cmdrunner.CmdRunner;

import java.util.function.Predicate;


public interface ModuleConfig<T> {

    /**
     * Gets the time to wait before rechecking for module deployment
     *
     * @return the time to wait
     */
    long getRetryOnFailDelay();

    /**
     * Sets the time to wait before rechecking for module deployment
     *
     * @param retryOnFailDelay the time to wait
     * @return this to enable fluency
     */
    ModuleConfig setRetryOnFailDelay(long retryOnFailDelay);

    ModuleConfig setSuccessCondition(Predicate<T> successCondition);

    /**
     * Gets the Unix commands used to deploy the module
     *
     * @return the Unix commands used to deploy the module
     */
    String getUnixCmd();

    /**
     * Gets the Windows commands used to deploy the module
     *
     * @return the Windows commands used to deploy the module
     */
    String getWindowsCmd();

    /**
     * Gets the port where the module will be contacted for checking it's initialization
     *
     * @return the port where the module will be contacted for checking it's initialization
     */
    int getPort();

    /**
     * Gets the address where the module will be contacted for checking it's initialization
     *
     * @return the address where the module will be contacted for checking it's initialization
     */
    String getAddress();

    /**
     * Gets the uri where the module will be contacted for checking it's initialization
     *
     * @return the uri where the module will be contacted for checking it's initialization
     */
    String getRequestURI();

    /**
     * The strategy used to deploy the module
     *
     * @param cmdRunner the CmdRunner instance
     */
    void deploy(CmdRunner cmdRunner);

    /**
     * The strategy used for waiting the module deployment
     *
     * @param deployWaiter the DeployWaiter instance
     * @return the future that will complete after the module deployment
     */
    Future<Void> waitDeployment(DeployWaiter deployWaiter);
}
