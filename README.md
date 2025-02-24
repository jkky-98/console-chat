# console-chat
Java의 Socket과 Thread를 활용한 멀티 유저 콘솔 채팅 프로그램 실시간 메시지 송수신, 닉네임 변경, 사용자 목록 조회 등의 기능 제공

다음은 **GitHub README** 파일 작성 예시입니다.

---

# **Console-Based Chat Application using Java Sockets**

This is a simple **console-based chat application** implemented in **Java** using **socket programming**. It allows multiple clients to connect to a server and exchange messages in real-time.

## **Features**
- 📡 **Multi-client support**: Multiple users can join and communicate.
- 🔄 **Message broadcasting**: Messages can be sent to specific users or broadcasted to all.
- 🛠 **Dispatcher-based message handling**: Efficient command processing using the `Dispatcher` class.
- 📜 **Session management**: Handles user connections with `SessionManager`.
- 🧹 **Graceful shutdown**: Ensures clean resource management with `SocketCloseUtil`.

## **Architecture Overview**
The application consists of the following components:

### **1️⃣ Server (`Server.java`)**
- Listens for incoming client connections.
- Creates a new `ServerSession` for each connected client.
- Manages active sessions using `SessionManager`.

### **2️⃣ Client (`Client.java`)**
- Connects to the server using a socket.
- Reads user input and sends commands to the server.
- Receives and displays messages from the server.

### **3️⃣ ServerSession (`ServerSession.java`)**
- Manages a single client connection.
- Reads incoming messages and forwards them to the `Dispatcher`.

### **4️⃣ Dispatcher (`Dispatcher.java`)**
- Processes client commands (`/join`, `/message`, `/exit`, etc.).
- Routes messages to the appropriate recipients.

### **5️⃣ ChatService (`ChatService.java`)**
- Manages chat-related logic.
- Stores chat history and user information.

### **6️⃣ ChatRepository (`ChatRepository.java`)**
- Provides data persistence for messages and users.

### **7️⃣ SessionManager (`SessionManager.java`)**
- Manages all active client sessions.
- Supports adding, removing, and closing all sessions.

### **8️⃣ Logging (`MyLogger.java`)**
- Provides structured logging for debugging and monitoring.

### **9️⃣ Utility (`SocketCloseUtil.java`)**
- Ensures proper resource cleanup (socket, input/output streams).

## **How to Run**
### **1️⃣ Start the Server**
```sh
javac Server.java
java Server
```
This will start the server and wait for client connections.

### **2️⃣ Start a Client**
```sh
javac Client.java
java Client
```
Clients will be prompted to enter chat commands.

### **3️⃣ Available Commands**
| Command | Description |
|---------|------------|
| `/join|{name}` | Join the chat with a name |
| `/message|{message}` | Send a message to all users |
| `/change|{new_name}` | Change username |
| `/users` | View all connected users |
| `/exit` | Disconnect from the server |

## **Future Improvements**
- ✅ Support for **private messaging** between users.
- ✅ Database integration for storing **chat history**.
- ✅ Implementing **WebSocket-based chat UI** for a modern experience.
