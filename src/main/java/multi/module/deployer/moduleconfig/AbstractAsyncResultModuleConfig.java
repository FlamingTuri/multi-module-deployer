package multi.module.deployer.moduleconfig;

import io.vertx.core.AsyncResult;
import multi.module.deployer.moduleconfig.info.ModuleInfo;

/**
 * @param <T> the type of the value passed to the Predicate that verifies
 *            if the deployment condition is fulfilled
 * @param <M> the implementation of moduleInfo whose parameters will be used
 *            to check the module deployment
 */
public abstract class AbstractAsyncResultModuleConfig<T, M extends ModuleInfo> extends AbstractExecInNewTermModuleConfig<AsyncResult<T>, M> {

    /**
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @param moduleInfo the module infos used to check the successful module deployment
     */
    public AbstractAsyncResultModuleConfig(String unixCmd, String windowsCmd, M moduleInfo) {
        super(unixCmd, windowsCmd, moduleInfo);
        this.setSuccessCondition(AsyncResult::succeeded);
    }
}
