import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerSession implements Runnable {

    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private boolean closed = false;
    private final Dispatcher dispatcher;
    private final SessionManager sessionManager;

    public ServerSession(Socket socket, ChatService chatService, SessionManager sessionManager) throws IOException {
        this.socket = socket;
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());
        this.dispatcher = new Dispatcher(chatService, this, outputStream);
        this.sessionManager = sessionManager;
        this.sessionManager.add(this);
        MyLogger.log("Server session 생성");
    }

    @Override
    public void run() {
        boolean endCondi = false;

        try {
            while (!endCondi) {
                String commandFromClient = inputStream.readUTF();
                MyLogger.log("client -> server : " + commandFromClient);

                dispatcher.doDispatch(commandFromClient);

                if (commandFromClient.equals("/exit")) {
                    endCondi = true;
                    MyLogger.log("client exit");
                }
            }
        } catch (IOException e) {
            MyLogger.log(e);
        } finally {
            sessionManager.remove(this);
            close();
        }
    }

    //세션 종료시, 서버 종료시 동시에 호출될 수 있다.
    public synchronized void close() {
        if (closed) {
            return;
        }

        SocketCloseUtil.closeAll(socket, inputStream, outputStream);
        closed = true;
        MyLogger.log("연결 종료: " + socket + " isClosed: " + socket.isClosed());
    }

    public void sendMessage(String message) throws IOException {
        outputStream.writeUTF(message);
    }
}
