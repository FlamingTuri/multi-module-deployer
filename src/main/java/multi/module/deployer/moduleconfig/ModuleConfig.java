package multi.module.deployer.moduleconfig;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import multi.module.deployer.cmdrunner.CmdRunner;

import java.util.function.Predicate;

/**
 * @param <T> the type of the value passed to the Predicate that verifies the fulfill of the condition
 */
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
    ModuleConfig<T> setRetryOnFailDelay(long retryOnFailDelay);

    /**
     * Sets the condition to fulfill to consider the module deployed
     *
     * @param successCondition the condition to fulfill
     * @return this to enable fluency
     */
    ModuleConfig<T> setSuccessCondition(Predicate<T> successCondition);

    /**
     * The strategy used to deploy the module
     *
     * @param cmdRunner the CmdRunner instance
     */
    void deploy(CmdRunner cmdRunner);

    /**
     * The strategy used for waiting the module deployment
     *
     * @param vertx a Vertx instance
     * @return the future that will complete after the module deployment
     */
    Future<Void> waitDeployment(Vertx vertx);
}
