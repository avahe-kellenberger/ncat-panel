public class WindowsCommandController implements CommandController {

    public ProcessBuilder whoAmI() {
        return new ProcessBuilder("whoami /all");
    }

    public ProcessBuilder systemInfo() {
        return new ProcessBuilder("systeminfo");
    }

    public ProcessBuilder netconfig() {
        return new ProcessBuilder("net use");
    }

    public ProcessBuilder schTasks() {
        return new ProcessBuilder("schtasks");
    }

    public ProcessBuilder taskList() {
        return new ProcessBuilder("tasklist");
    }

    public ProcessBuilder ipconfig() {
        return new ProcessBuilder("ipconfig /all");
    }

    public ProcessBuilder netstat() {
        return new ProcessBuilder("net view");
    }

    public ProcessBuilder upload() {
        // TODO
        return new ProcessBuilder("whoami");
    }

    public ProcessBuilder download() {
        // TODO
        return new ProcessBuilder("whoami");
    }


}
