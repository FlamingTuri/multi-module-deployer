package multi.module.deployer.cmdrunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class UnixCmdRunner extends AbstractCmdRunner {

    public UnixCmdRunner() {
        super("sh");
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
    public void exec(String unixCmd, String windowsCmd) {
        run(unixCmd);
    }

    @Override
    public void execInNewTerm(String unixCmd, String windowsCmd) {
        runInNewTerm(unixCmd);
    }
}
