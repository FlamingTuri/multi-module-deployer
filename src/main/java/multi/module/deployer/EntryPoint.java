package multi.module.deployer;

import org.apache.commons.lang3.SystemUtils;

public class EntryPoint implements CmdForOs {

    private final CmdForOs cmdRunner;

    public EntryPoint() {
        if (SystemUtils.IS_OS_WINDOWS) {
            cmdRunner = new WindowsCmdRunner();
        } else {
            cmdRunner = new UnixCmdRunner();
        }
    }

    @Override
    public void setWorkingDir(String workingDirPath) {
        cmdRunner.setWorkingDir(workingDirPath);
    }

    @Override
    public void exec(String linuxCmd, String windowsCmd) {
        cmdRunner.exec(linuxCmd, windowsCmd);
    }
}
