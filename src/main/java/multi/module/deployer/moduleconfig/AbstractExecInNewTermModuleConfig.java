package multi.module.deployer.moduleconfig;

import multi.module.deployer.cmdrunner.CmdRunner;

/**
 * An abstract class that will run the commands in a new detached terminal instance
 */
public abstract class AbstractExecInNewTermModuleConfig extends AbstractModuleConfig {

    public AbstractExecInNewTermModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        super(unixCmd, windowsCmd, port, address, requestURI);
    }

    @Override
    public void deploy(CmdRunner cmdRunner) {
        cmdRunner.execInNewTerm(unixCmd, windowsCmd);
    }
}
