package multi.module.deployer.moduleconfig;

import io.vertx.core.Vertx;

import java.util.function.Predicate;

/**
 * Abstract class inherited by all the module configs versions
 */
public abstract class AbstractModuleConfig<T> implements ModuleConfig<T> {

    protected final Vertx vertx;
    protected final String unixCmd;
    protected final String windowsCmd;
    protected final int port;
    protected final String address;
    protected final String requestURI;
    protected long retryOnFailDelay = 2000;
    protected Predicate<T> successCondition;

    public AbstractModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        this(Vertx.vertx(), unixCmd, windowsCmd, port, address, requestURI);
    }

    /**
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @param port       the port where the microservice is listening to
     * @param address    the microservice host address
     * @param requestURI the requested api
     */
    public AbstractModuleConfig(Vertx vertx, String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        this.vertx = vertx;
        this.unixCmd = unixCmd;
        this.windowsCmd = windowsCmd;
        this.port = port;
        this.address = address;
        this.requestURI = requestURI;
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
    public ModuleConfig setRetryOnFailDelay(long retryOnFailDelay) {
        this.retryOnFailDelay = retryOnFailDelay;
        return this;
    }

    @Override
    public ModuleConfig setSuccessCondition(Predicate<T> successCondition) {
        this.successCondition = successCondition;
        return this;
    }

    @Override
    public String getUnixCmd() {
        return unixCmd;
    }

    @Override
    public String getWindowsCmd() {
        return windowsCmd;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getRequestURI() {
        return requestURI;
    }

    protected String format(int port, String host) {
        return host + ":" + port;
    }

    protected String format(int port, String host, String requestURI) {
        return format(port, host) + requestURI;
    }

}
