package multi.module.deployer.moduleconfig;

import multi.module.deployer.cmdrunner.CmdRunner;

/**
 * An abstract class that will run the commands in a new detached terminal instance
 */
public abstract class AbstractExecInNewTermModuleConfig<T> extends AbstractModuleConfig<T> {

    /**
     * Constructor with success condition set to AsyncResult::succeeded
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @param port       the port where the microservice is listening to
     * @param address    the microservice host address
     * @param requestURI the requested api
     */
    public AbstractExecInNewTermModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        super(unixCmd, windowsCmd, port, address, requestURI);
    }

    @Override
    public void deploy(CmdRunner cmdRunner) {
        cmdRunner.execInNewTerm(unixCmd, windowsCmd);
    }
}
