package multi.module.deployer.moduleconfig.info;

public class HttpServiceInfo extends ServiceInfo {

    private final String requestURI;

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
