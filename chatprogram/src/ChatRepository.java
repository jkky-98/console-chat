import exception.RepositoryException;

import java.util.*;

public class ChatRepository {

    private Map<String, ServerSession> userDataBase = new HashMap<String, ServerSession>();

    public synchronized Boolean existsByName(String userName) {
        if (userDataBase.containsKey(userName)) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized void join(String userName,  ServerSession session) {
        userDataBase.put(userName, session);
    }

    public synchronized String findNameByServerSession(ServerSession session) {

        Optional<String> first = userDataBase.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(session))
                .map(
                        Map.Entry::getKey
                )
                .findFirst();

        return first.orElseThrow(() -> new RepositoryException("\uD83E\uDD14 입장 하셨나요??"));
    }

    public synchronized List<ServerSession> findAllServerSession() {

        return userDataBase.values()
                .stream()
                .toList();
    }

    public synchronized List<ServerSession> findAllServerSessionWithoutMe(ServerSession session) {

        return userDataBase.values()
                .stream()
                .filter(s -> !s.equals(session))
                .toList();
    }

    public synchronized Set<String> findAll() {
        return userDataBase.keySet();
    }

    public synchronized String changeName(String userName, ServerSession session) {

        for (Map.Entry<String, ServerSession> stringServerSessionEntry : userDataBase.entrySet()) {
            if (stringServerSessionEntry.getValue().equals(session)) {
                String oldkey = stringServerSessionEntry.getKey();
                ServerSession oldValue = stringServerSessionEntry.getValue();

                userDataBase.remove(oldkey);
                join(userName, session);
                return "이름 변경을 완료했습니다.";
            }
        }

        return "이름 변경을 실패했습니다.";
    }

    public synchronized String leave(ServerSession session) {
        for (Map.Entry<String, ServerSession> stringServerSessionEntry : userDataBase.entrySet()) {
            if (stringServerSessionEntry.getValue().equals(session)) {
                userDataBase.remove(stringServerSessionEntry.getKey());
                return "성공적으로 접속종료 되었습니다.";
            }
        }
        return "회원이 존재하지 않습니다.";
    }
}
