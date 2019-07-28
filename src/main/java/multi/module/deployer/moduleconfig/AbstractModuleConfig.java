package multi.module.deployer.moduleconfig;

/**
 * Abstract class inherited by all the module configs versions
 */
public abstract class AbstractModuleConfig implements ModuleConfig {

    protected final String unixCmd;
    protected final String windowsCmd;
    protected final int port;
    protected final String address;
    protected final String requestURI;

    public AbstractModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        this.unixCmd = unixCmd;
        this.windowsCmd = windowsCmd;
        this.port = port;
        this.address = address;
        this.requestURI = requestURI;
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

}
