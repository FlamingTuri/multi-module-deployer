package multi.module.deployer.moduleconfig;

import io.vertx.core.Vertx;
import multi.module.deployer.moduleconfig.info.ModuleInfo;

import java.util.function.Predicate;

/**
 * Abstract class inherited by all the module configs versions
 *
 * @param <T>
 * @param <M>
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
     */
    public AbstractModuleConfig(String unixCmd, String windowsCmd, M moduleInfo) {
        this.unixCmd = unixCmd;
        this.windowsCmd = windowsCmd;
        this.moduleInfo = moduleInfo;
    }

    /**
     * Gets the time to wait before rechecking for module deployment
     *
     * @return the time to wait
     */
    @Override
    public long getRetryOnFailDelay() {
        return retryOnFailDelay;
    }

    /**
     * Sets the time to wait before rechecking for module deployment
     *
     * @param retryOnFailDelay the time to wait
     * @return this to enable fluency
     */
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

    protected void setVertxInstance(Vertx vertx) {
        this.vertx = vertx;
    }

    protected String format(int port, String host) {
        return host + ":" + port;
    }

    protected String format(int port, String host, String requestURI) {
        return format(port, host) + requestURI;
    }

}
