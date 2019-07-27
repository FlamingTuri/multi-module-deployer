package multi.module.deployer.moduleconfig;

import multi.module.deployer.cmdrunner.CmdRunner;

public abstract class AbstractExecInNewTermModuleConfig extends AbstractModuleConfig {

    public AbstractExecInNewTermModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        super(unixCmd, windowsCmd, port, address, requestURI);
    }

    @Override
    public void deploy(CmdRunner cmdRunner) {
        cmdRunner.execInNewTerm(unixCmd, windowsCmd);
    }
}
