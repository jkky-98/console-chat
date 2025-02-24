import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 12345;
    private static ChatRepository chatRepository = new ChatRepository();

    public static void main(String[] args) throws IOException {

        MyLogger.log("Server starting....");
        ServerSocket serverSocket = new ServerSocket(PORT);
        MyLogger.log("Server started - listening on port " + PORT);

        while (true) {
            // 클라이언트 연결 대기, 연결 성공시 socket 반환
            Socket socket = serverSocket.accept();
            MyLogger.log("Server accepted : " + socket);

            ServerSession serverSession = new ServerSession(
                    socket, new ChatService(chatRepository), new SessionManager()
            );

            Thread thread = new Thread(serverSession);
            thread.start();
        }
    }
}
