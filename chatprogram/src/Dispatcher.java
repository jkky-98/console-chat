import java.io.DataOutputStream;
import java.io.IOException;

public class Dispatcher {

    private final ChatService chatService;
    private final ServerSession serverSession;
    private final DataOutputStream dataOutputStream;

    public Dispatcher(ChatService chatService, ServerSession serverSession, DataOutputStream outputStream) {
        this.chatService = chatService;
        this.serverSession = serverSession;
        this.dataOutputStream = outputStream;

    }

    public void doDispatch(String command) throws IOException {

        if (command.startsWith("/join|") && command.split("\\|").length == 2) {

            String name = command.split("\\|")[1];
            String joinResult = chatService.join(name, serverSession);
            dataOutputStream.writeUTF(joinResult);

        } else if (
                command.startsWith("/message|") && command.split("\\|").length == 2
        ) {
            String message = command.split("\\|")[1];
            String messageResult = chatService.messageResponse(message, serverSession);

            dataOutputStream.writeUTF(messageResult);

        } else if (
                command.startsWith("/change|") && command.split("\\|").length == 2
        ) {
            String nameNeedToChange = command.split("\\|")[1];
            String changeNameResult = chatService.changeName(nameNeedToChange, serverSession);
            dataOutputStream.writeUTF(changeNameResult);
        } else if (
                command.equals("/users")
        ) {
            String usersResult = chatService.getUsers();
            dataOutputStream.writeUTF(usersResult);
        } else if (
                command.equals("/exit")
        ) {
            String exitResult = chatService.exit(serverSession);
            dataOutputStream.writeUTF(exitResult);
        }
    }
}
