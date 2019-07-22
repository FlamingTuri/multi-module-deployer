package multi.module.deployer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public abstract class AbstractCmdRunner implements CmdForOs {

    protected final String projectFilesDir;
    protected final String scriptName;
    protected final String scriptAbsolutePath;
    protected final Runtime runtime;
    protected File workingDir = null;

    public AbstractCmdRunner(String scriptExtension) {
        // create application folder to store its data
        projectFilesDir = System.getProperty("user.home") + File.separator + ".multi-module-deployer";
        File customDir = new File(projectFilesDir);
        if (!customDir.exists() && !customDir.mkdirs()) {
            System.err.println("cannot create " + customDir);
            System.exit(-1);
        }
        // create script to execute commands in a new terminal instance
        scriptName = "/exec-in-new-terminal." + scriptExtension;
        scriptAbsolutePath = projectFilesDir + scriptName;
        InputStream in = getClass().getResourceAsStream(scriptName);
        try {
            Files.copy(in, Paths.get(scriptAbsolutePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        postCopyOperations();
        // setup runtime to run low level commands
        runtime = Runtime.getRuntime();
    }

    protected abstract void postCopyOperations();

    @Override
    public void setWorkingDir(String workingDirPath) {
        workingDir = new File(workingDirPath);
    }

    protected void run(String cmd) {
        try {
            runtime.exec(scriptAbsolutePath + " " + cmd, null, workingDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
