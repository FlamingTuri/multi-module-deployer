package multi.module.deployer;

import org.apache.commons.lang3.SystemUtils;

public class EntryPoint {

    private final CmdForOs cmdRunner;

    public EntryPoint() {
        if (SystemUtils.IS_OS_WINDOWS) {
            cmdRunner = new WindowsCmdRunner();
        } else {
            cmdRunner = new UnixCmdRunner();
        }
    }

    public void exec(String linuxCmd, String windowsCmd) {
        cmdRunner.exec(linuxCmd, windowsCmd);
    }
}
