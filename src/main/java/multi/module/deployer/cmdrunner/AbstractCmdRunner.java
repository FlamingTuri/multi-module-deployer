package multi.module.deployer.cmdrunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Abstract class wrapping the logic for running low level commands
 */
public abstract class AbstractCmdRunner implements CmdRunner {

    protected final String scriptAbsolutePath;
    private final ProcessBuilder processBuilder;
    private String[] commands;

    public AbstractCmdRunner(String scriptExtension, String interpreter, String flags, boolean replaceScript) {
        // create application folder to store its data
        String userHomeDirectory = System.getProperty("user.home");
        String projectFilesDir = userHomeDirectory + File.separator + ".multi-module-deployer";
        File customDir = new File(projectFilesDir);
        if (!customDir.exists() && !customDir.mkdirs()) {
            System.err.println("cannot create " + customDir);
            System.exit(-1);
        }
        // create script to execute commands in a new terminal instance
        String scriptName = "exec-in-new-terminal." + scriptExtension;
        Path scriptPath = Paths.get(projectFilesDir, scriptName);
        scriptAbsolutePath = scriptPath.toString();
        try {
            String jarPath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            JarFile jar = new JarFile(jarPath);
            Optional<String> exceInNewTerminalFolder = jar.stream().map(ZipEntry::getName)
                .filter(fileName -> fileName.matches("exec-in-new-terminal-\\d+\\.\\d+\\.\\d+/"))
                .findFirst();
            if (exceInNewTerminalFolder.isPresent()) {
                String scriptResourceName = Paths.get("/", exceInNewTerminalFolder.get(), scriptName).toString();
                try (InputStream in = getClass().getResourceAsStream(scriptResourceName)) {
                    Files.copy(in, scriptPath, StandardCopyOption.REPLACE_EXISTING);
                }
            } else {
                throw new IOException();
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        postCopyOperations();
        // setup runtime to run low level commands
        processBuilder = new ProcessBuilder();
        commands = new String[]{interpreter, flags, ""};
    }

    /**
     * Method run after the script has been copied to the user environment.
     * Useful to add file permissions.
     */
    protected abstract void postCopyOperations();

    @Override
    public void setWorkingDir(String workingDirPath) {
        setWorkingDir(new File(workingDirPath));
    }

    @Override
    public void setWorkingDir(File workingDir) {
        if (workingDir.isDirectory()) {
            processBuilder.directory(workingDir);
        }
    }

    /**
     * Wraps the string between ""
     *
     * @param string the string to wrap
     * @return the wrapped string
     */
    protected String wrap(String string) {
        return String.format("\"%s\"", string);
    }

    /**
     * Runs string command in a separate process
     *
     * @param cmd the command to run
     * @return the instance of the run process
     */
    protected Process run(String cmd) {
        Process process = null;
        try {
            commands[2] = cmd;
            processBuilder.command(commands);
            // runs the commands
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return process;
    }

    /**
     * Runs string command in a new detached terminal instance
     *
     * @param cmd the command to run
     * @return the instance of the run process, this is not the detached terminal one
     */
    protected Process runInNewTerm(String cmd) {
        return run(scriptAbsolutePath + " " + cmd);
    }

}
