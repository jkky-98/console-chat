import java.io.IOException;
import java.util.List;

public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public String join(String name, ServerSession session) {
        if (!chatRepository.existsByName(name)) {
            chatRepository.join(name, session);

            List<ServerSession> allWithoutMe = chatRepository.findAllServerSessionWithoutMe(session);

            for (ServerSession s : allWithoutMe) {
                try {
                    s.sendMessage(name+ "님이 입장하셨습니다. ❗");
                } catch (IOException e) {
                    MyLogger.log(e);
                }
            }

            return "채팅방에 입장하셨습니다 - 닉네임: " + name;
        } else {
            return "이미 존재하는 회원입니다. 다른 닉네임을 사용해주세요.";
        }
    }

    public void messageResponse(String message, ServerSession session) throws IOException {

        List<ServerSession> allSession = chatRepository.findAllServerSession();

        String nameMessageSender = chatRepository.findNameByServerSession(session);

        for (ServerSession s : allSession) {
            if (!s.equals(session)) {
                s.sendMessage("[" + nameMessageSender + "] : " + message);
            } else {
                s.sendMessage("[Me] : " + message);
            }
        }
    }

    public String changeName(String nameNeedToChange, ServerSession session) {
        return chatRepository.changeName(nameNeedToChange, session);
    }

    public String getUsers() {
        return chatRepository.findAll().toString();
    }

    public String exit(ServerSession session) {
        return chatRepository.leave(session);
    }
}
