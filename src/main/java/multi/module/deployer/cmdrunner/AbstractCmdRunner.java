package multi.module.deployer.cmdrunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public abstract class AbstractCmdRunner implements CmdRunner {

    protected final String scriptAbsolutePath;
    private final Runtime runtime;
    private File workingDir = null;

    public AbstractCmdRunner(String scriptExtension) {
        // create application folder to store its data
        String userHomeDirectory = System.getProperty("user.home");
        String projectFilesDir = userHomeDirectory + File.separator + ".multi-module-deployer";
        File customDir = new File(projectFilesDir);
        if (!customDir.exists() && !customDir.mkdirs()) {
            System.err.println("cannot create " + customDir);
            System.exit(-1);
        }
        // create script to execute commands in a new terminal instance
        String scriptName = "/exec-in-new-terminal." + scriptExtension;
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

    /**
     * Method run after the script has been copied to the user environment.
     * Useful to add file permissions.
     */
    protected abstract void postCopyOperations();

    @Override
    public void setWorkingDir(String workingDirPath) {
        workingDir = new File(workingDirPath);
    }

    /**
     * Runs string command in a separate process
     *
     * @param cmd the command to run
     */
    protected void run(String cmd) {
        try {
            runtime.exec(cmd, null, workingDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs string command in a new detached terminal instance
     *
     * @param cmd the command to run
     */
    protected void runInNewTerm(String cmd) {
        run(scriptAbsolutePath + " " + cmd);
    }

}
