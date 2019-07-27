package multi.module.deployer.moduleconfig;

public abstract class AbstractModuleConfig implements ModuleConfig {

    private final String unixCmd;
    private final String windowsCmd;
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
