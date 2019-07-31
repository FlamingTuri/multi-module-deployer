package multi.module.deployer.moduleconfig;

import multi.module.deployer.cmdrunner.CmdRunner;
import multi.module.deployer.moduleconfig.info.ModuleInfo;

/**
 * An abstract class that will run the commands in a new detached terminal instance
 */
public abstract class AbstractExecInNewTermModuleConfig<T, M extends ModuleInfo> extends AbstractModuleConfig<T, M> {

    /**
     * Constructor with success condition set to AsyncResult::succeeded
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     */
    public AbstractExecInNewTermModuleConfig(String unixCmd, String windowsCmd, M module) {
        super(unixCmd, windowsCmd, module);
    }

    @Override
    public void deploy(CmdRunner cmdRunner) {
        cmdRunner.execInNewTerm(unixCmd, windowsCmd);
    }
}
