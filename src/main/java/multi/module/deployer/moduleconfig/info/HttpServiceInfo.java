package multi.module.deployer.moduleconfig.info;

/**
 * Class implementing the fields necessary to check the deployment of http based modules
 */
public class HttpServiceInfo extends ServiceInfo {

    private final String requestURI;

    /**
     * @param port       the port where the microservice is listening to
     * @param address    the microservice host address
     * @param requestURI the requested api
     */
    public HttpServiceInfo(int port, String address, String requestURI) {
        super(port, address);
        this.requestURI = requestURI;
    }

    /**
     * Gets the uri where the module will be contacted for checking it's initialization
     *
     * @return the uri where the module will be contacted for checking it's initialization
     */
    public String getRequestURI() {
        return requestURI;
    }
}
