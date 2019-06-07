public class WindowsCommandController implements CommandController {

    public String whoAmI() {
        return"whoami /all";
    }

    public String systemInfo() {
        return"systeminfo";
    }

    public String netconfig() {
        return"net use";
    }

    public String schTasks() {
        return"schtasks";
    }

    public String taskList() {
        return"tasklist";
    }

    public String ipconfig() {
        return"ipconfig /all";
    }

    public String netstat() {
        return"net view";
    }

    public String upload() {
        // TODO
        return"whoami";
    }

    public String download() {
        // TODO
        return"whoami";
    }


}
