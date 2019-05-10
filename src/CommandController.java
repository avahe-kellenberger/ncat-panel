/**
 * Invokes the given commands.
 */
public interface CommandController {

    public ProcessBuilder whoAmI();

    public ProcessBuilder systemInfo();

    public ProcessBuilder netconfig();

    public ProcessBuilder schTasks();

    public ProcessBuilder taskList();

    public ProcessBuilder ipconfig();

    public ProcessBuilder netstat();

    public ProcessBuilder upload();

    public ProcessBuilder download();

}
