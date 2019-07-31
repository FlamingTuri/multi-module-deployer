package multi.module.deployer.moduleconfig;

import io.vertx.core.Vertx;
import multi.module.deployer.moduleconfig.info.ModuleInfo;

import java.util.function.Predicate;

/**
 * Abstract class that should be extended by all the module configs implementations
 *
 * @param <T> the type of the value passed to the Predicate that verifies
 *            if the deployment condition is fulfilled
 * @param <M> the implementation of moduleInfo whose parameters will be used
 *            to check the module deployment
 */
public abstract class AbstractModuleConfig<T, M extends ModuleInfo> implements ModuleConfig<T> {

    protected final String unixCmd;
    protected final String windowsCmd;
    protected final M moduleInfo;
    protected Vertx vertx;
    protected Predicate<T> successCondition;
    protected long retryOnFailDelay = 2000;

    /**
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @param moduleInfo the module infos used to check the successful module deployment
     */
    public AbstractModuleConfig(String unixCmd, String windowsCmd, M moduleInfo) {
        this.unixCmd = unixCmd;
        this.windowsCmd = windowsCmd;
        this.moduleInfo = moduleInfo;
    }

    @Override
    public long getRetryOnFailDelay() {
        return retryOnFailDelay;
    }

    @Override
    public ModuleConfig<T> setRetryOnFailDelay(long retryOnFailDelay) {
        this.retryOnFailDelay = retryOnFailDelay;
        return this;
    }

    @Override
    public ModuleConfig<T> setSuccessCondition(Predicate<T> successCondition) {
        this.successCondition = successCondition;
        return this;
    }

    /**
     * Sets the used vertx instance
     *
     * @param vertx the vertx instance to set
     */
    protected void setVertxInstance(Vertx vertx) {
        this.vertx = vertx;
    }

    /**
     * For logging utility purposes.
     *
     * @param port the service port
     * @param host the service host
     * @return ${host}:${port};
     */
    protected String format(int port, String host) {
        return host + ":" + port;
    }

    /**
     * For logging utility purposes.
     *
     * @param port       the service port
     * @param host       the service host
     * @param requestURI the request URI
     * @return ${host}:${port}${requestURI};
     */
    protected String format(int port, String host, String requestURI) {
        return format(port, host) + requestURI;
    }

}
