package multi.module.deployer.moduleconfig;

import io.vertx.core.AsyncResult;
import multi.module.deployer.moduleconfig.info.ModuleInfo;

public abstract class AbstractAsyncResultModuleConfig<T, M extends ModuleInfo> extends AbstractExecInNewTermModuleConfig<AsyncResult<T>, M> {

    public AbstractAsyncResultModuleConfig(String unixCmd, String windowsCmd, M module) {
        super(unixCmd, windowsCmd, module);
        this.setSuccessCondition(AsyncResult::succeeded);
    }
}
