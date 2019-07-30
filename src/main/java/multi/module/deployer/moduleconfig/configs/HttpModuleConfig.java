package multi.module.deployer.moduleconfig.configs;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import multi.module.deployer.DeployWaiter;
import multi.module.deployer.moduleconfig.AbstractExecInNewTermModuleConfig;

import java.util.function.Predicate;

/**
 * Class that checks for a module deployment by waiting for a successful http GET response
 */
public class HttpModuleConfig extends AbstractExecInNewTermModuleConfig<HttpResponse<Buffer>> {

    /**
     * Constructor with success condition set to AsyncResult::succeeded
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @param port       the port where the microservice is listening to
     * @param address    the microservice host address
     * @param requestURI the requested api
     */
    public HttpModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        super(unixCmd, windowsCmd, port, address, requestURI);
    }

    /**
     * Constructor that allows to setup a custom module deployment condition
     *
     * @param unixCmd          the commands to run on Unix-like environments
     * @param windowsCmd       the commands to run on Windows environments
     * @param port             the port where the microservice is listening to
     * @param address          the microservice host address
     * @param requestURI       the requested api
     * @param successCondition the condition to fulfill to consider the module deployed
     */
    public HttpModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI,
                            Predicate<AsyncResult<HttpResponse<Buffer>>> successCondition) {
        super(unixCmd, windowsCmd, port, address, requestURI, successCondition);
    }

    @Override
    public Future<Void> waitDeployment(DeployWaiter deployWaiter) {
        return deployWaiter.waitHttpDeployment(port, address, requestURI, successCondition);
    }
}
