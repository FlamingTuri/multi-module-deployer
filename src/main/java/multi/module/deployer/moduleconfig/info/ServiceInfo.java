package multi.module.deployer.moduleconfig.info;

/**
 * Class implementing the fields necessary to check the deployment of service modules
 */
public class ServiceInfo implements ModuleInfo {

    private final int port;
    private final String address;

    /**
     * @param port    the port where the microservice is listening to
     * @param address the microservice host address
     */
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
