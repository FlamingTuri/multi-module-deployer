package multi.module.deployer.moduleconfig;

import multi.module.deployer.cmdrunner.CmdRunner;
import multi.module.deployer.moduleconfig.info.ModuleInfo;

/**
 * An abstract class that will run the commands in a new detached terminal instance
 *
 * @param <T> the type of the value passed to the Predicate that verifies
 *            if the deployment condition is fulfilled
 * @param <M> the implementation of moduleInfo whose parameters will be used
 *            to check the module deployment
 */
public abstract class AbstractExecInNewTermModuleConfig<T, M extends ModuleInfo> extends AbstractModuleConfig<T, M> {

    /**
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @param moduleInfo the module infos used to check the successful module deployment
     */
    public AbstractExecInNewTermModuleConfig(String unixCmd, String windowsCmd, M moduleInfo
    ) {
        super(unixCmd, windowsCmd, moduleInfo);
    }

    @Override
    public void deploy(CmdRunner cmdRunner) {
        cmdRunner.execInNewTerm(unixCmd, windowsCmd);
    }
}
