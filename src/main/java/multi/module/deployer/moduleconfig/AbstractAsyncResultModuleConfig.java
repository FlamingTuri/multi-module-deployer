package multi.module.deployer.moduleconfig;

import io.vertx.core.AsyncResult;

public abstract class AbstractAsyncResultModuleConfig<T> extends AbstractExecInNewTermModuleConfig<AsyncResult<T>> {

    public AbstractAsyncResultModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        super(unixCmd, windowsCmd, port, address, requestURI);
        this.setSuccessCondition(AsyncResult::succeeded);
    }
}
