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

    public synchronized List<ServerSession> findAllNotMe(ServerSession session) {
        List<ServerSession> result = new ArrayList<ServerSession>();

        userDataBase.values()
                .stream()
                .filter(key -> !key.equals(session))
                .forEach(result::add);

        return result;

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
