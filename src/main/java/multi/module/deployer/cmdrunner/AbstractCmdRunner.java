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

    private static final String execInNewTerminalFolderRegex = "exec-in-new-terminal-\\d+\\.\\d+\\.\\d+";
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
        if (!scriptPath.toFile().exists() || replaceScript) {
            try {
                File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
                // gets the name of the folder containing exec-in-new-terminal scripts
                Optional<String> execInNewTerminalFolder = jarFile.isFile() ? getFromJar(jarFile) : getFromFile();
                if (execInNewTerminalFolder.isPresent()) {
                    // loads script from resources and copies it on user's filesystem
                    String scriptResourceName = Paths.get("/", execInNewTerminalFolder.get(), scriptName).toString();
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
        }
        postCopyOperations();
        // setup runtime to run low level commands
        processBuilder = new ProcessBuilder();
        commands = new String[]{interpreter, flags, ""};
    }

    private Optional<String> getFromJar(File jarFile) throws IOException {
        JarFile jar = new JarFile(jarFile.getPath());
        return jar.stream().map(ZipEntry::getName)
            .filter(fileName -> fileName.matches(execInNewTerminalFolderRegex + "/"))
            .findFirst();
    }

    private Optional<String> getFromFile() throws IOException {
        return Files.walk(new File("src/main/resources").toPath())
            .filter(e -> e.toFile().isDirectory() &&
                e.getFileName().toString().matches(execInNewTerminalFolderRegex))
            .map(e -> e.getFileName().toString())
            .findFirst();
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
