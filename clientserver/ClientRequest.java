public class ClientRequest {
    private String command;
    private String data;

    public ClientRequest(String type, String data) {
        this.command = type;
        this.data = data;
    }

    public String getCommand() {
        return command;
    }

    public String getData() {
        return data;
    }
}
