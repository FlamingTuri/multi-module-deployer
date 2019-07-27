package multi.module.deployer;

import multi.module.deployer.cmdrunner.CmdRunnerImpl;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.Test;

import java.io.File;

public class SetupTest {

    @Test
    public void testRequiredFilesExistence() {
        new CmdRunnerImpl();
        String projectFilesDir = System.getProperty("user.home") + File.separator + ".multi-module-deployer";
        File customDir = new File(projectFilesDir);
        assert (customDir.exists());

        String scriptName = "exec-in-new-terminal.";
        if (SystemUtils.IS_OS_WINDOWS) {
            scriptName += "bat";
        } else {
            scriptName += "sh";
        }
        File scriptFile = new File(projectFilesDir + File.separator + scriptName);
        assert (scriptFile.exists());
    }
}
