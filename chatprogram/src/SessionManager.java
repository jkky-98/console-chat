import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private List<ServerSession> sessions = new ArrayList<>();

    public synchronized void add(ServerSession session) {
        sessions.add(session);
    }

    public synchronized void remove(ServerSession session) {
        sessions.remove(session);
    }

    public synchronized void closeAll() {
        // 세션을 모두 닫기
        for (ServerSession session : sessions) {
            session.close();
        }
        sessions.clear();
    }
}
