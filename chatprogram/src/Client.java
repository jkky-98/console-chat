import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;



public class Client {

    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", PORT);
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                DataInputStream input = new DataInputStream(socket.getInputStream());
                ) {
            Scanner sc = new Scanner(System.in);
            boolean endCondi = false;
            MyLogger.log("소켓 연결 시작");

            // 📌 서버에서 오는 메시지를 별도 스레드에서 수신
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String received = input.readUTF(); // 서버에서 메시지 수신
                        System.out.println("\n📩 [서버 메시지] " + received);
                        System.out.print("명령어 입력: "); // 입력 프롬프트 다시 표시
                    }
                } catch (IOException e) {
                    System.out.println("서버 연결 종료");
                }
            });

            receiveThread.setDaemon(true);
            receiveThread.start();

            while (!endCondi) {
                System.out.println("명령어 목록");
                System.out.println("입장: /join|{name}");
                System.out.println("메세지: /message|{message}");
                System.out.println("이름 변경: /change|{name}");
                System.out.println("전체 사용자 조회: /users");
                System.out.println("종료: /exit");

                System.out.println("명령어 입력: ");
                String command = sc.nextLine();

                if (command.startsWith("/join|") && command.split("\\|").length == 2) {
                    output.writeUTF(command);
                } else if (
                        command.startsWith("/message|") && command.split("\\|").length == 2
                ) {
                    output.writeUTF(command);
                } else if (
                        command.startsWith("/change|") && command.split("\\|").length == 2
                ) {
                    output.writeUTF(command);
                } else if (
                        command.equals("/users")
                ) {
                    output.writeUTF(command);
                } else if (
                        command.equals("/exit")
                ) {
                    output.writeUTF(command);
                    endCondi = true;
                    MyLogger.log("클라이언트 종료");
                } else {
                    System.out.println("잘못된 명령어입니다. 다시 입력하세요.");
                }
            }
        } catch (IOException e) {
            MyLogger.log(e);
        }
    }
}
