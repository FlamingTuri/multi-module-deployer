package multi.module.deployer.cmdrunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 * Class implementing a command runner for Unix-like environments
 */
public class UnixCmdRunner extends AbstractCmdRunner {

    public UnixCmdRunner() {
        super("sh", "bash", "-c");
    }

    @Override
    protected void postCopyOperations() {
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr-xr-x");
        try {
            Path scriptPath = new File(scriptAbsolutePath).toPath();
            Files.setPosixFilePermissions(scriptPath, perms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Process exec(String unixCmd, String windowsCmd) {
        return run(unixCmd);
    }

    @Override
    public Process execInNewTerm(String unixCmd, String windowsCmd) {
        return runInNewTerm(wrap(unixCmd));
    }
}
