package multi.module.deployer.moduleconfig.info;

public class ServiceInfo implements ModuleInfo {

    private final int port;
    private final String address;

    public ServiceInfo(int port, String address) {
        this.port = port;
        this.address = address;
    }

    /**
     * Gets the port where the module will be contacted for checking it's initialization
     *
     * @return the port where the module will be contacted for checking it's initialization
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets the address where the module will be contacted for checking it's initialization
     *
     * @return the address where the module will be contacted for checking it's initialization
     */
    public String getAddress() {
        return address;
    }
}


/**
 * TODO:
 * Gets the Unix commands used to deploy the module
 *
 * @return the Unix commands used to deploy the module
 * <p>
 * Gets the Windows commands used to deploy the module
 * @return the Windows commands used to deploy the module
 * <p>
 * Gets the Windows commands used to deploy the module
 * @return the Windows commands used to deploy the module
 * <p>
 * Gets the Windows commands used to deploy the module
 * @return the Windows commands used to deploy the module
 * <p>
 * Gets the Windows commands used to deploy the module
 * @return the Windows commands used to deploy the module
 * <p>
 * Gets the Windows commands used to deploy the module
 * @return the Windows commands used to deploy the module
 * <p>
 * Gets the Windows commands used to deploy the module
 * @return the Windows commands used to deploy the module
 * <p>
 * Gets the Windows commands used to deploy the module
 * @return the Windows commands used to deploy the module
 * <p>
 * Gets the Windows commands used to deploy the module
 * @return the Windows commands used to deploy the module
 */
//String getUnixCmd();

/**
 * Gets the Windows commands used to deploy the module
 *
 * @return the Windows commands used to deploy the module
 */
//  String getWindowsCmd();
