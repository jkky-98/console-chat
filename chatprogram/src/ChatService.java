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
            return "Join Success: " + name;
        } else {
            return "이미 존재하는 회원입니다.";
        }
    }

    public String messageResponse(String message, ServerSession session) throws IOException {

        List<ServerSession> allSession = chatRepository.findAllNotMe(session);
        for (ServerSession s : allSession) {
            s.sendMessage(message);
        }

        return "전체 회원 " + allSession.size() +  "명에게 성공적으로 메세지를 보냈습니다.";
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
