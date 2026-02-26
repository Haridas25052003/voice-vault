PROJECT NAME:
Virtual Interview System – WebSocket Audio Streaming (Spring Boot)

------------------------------------------------------------
1. PROJECT OVERVIEW
------------------------------------------------------------

This project demonstrates a backend implementation of a 
Virtual Interview System using Spring Boot WebSocket.

The system allows:

- User to start an interview session
- Establish WebSocket connection with backend
- Stream audio data (binary) every 5 seconds
- Accumulate audio bytes per session
- End interview session
- Store final audio file to disk

This project is built to understand:
- WebSocket fundamentals
- Stateful communication
- Binary message handling
- Session management
- File storage in Java
- Real-time streaming architecture


------------------------------------------------------------
2. TECHNOLOGY STACK
------------------------------------------------------------

Backend:
- Java 17+
- Spring Boot
- Spring WebSocket
- Maven

Frontend (Basic HTML for testing only):
- HTML5
- JavaScript (WebSocket API)


------------------------------------------------------------
3. WHY WEBSOCKET?
------------------------------------------------------------

HTTP is stateless and request-response based.
For real-time audio streaming, we need:

- Persistent connection
- Full-duplex communication
- Low overhead
- Continuous data transfer

WebSocket provides:

- One-time handshake (HTTP 101 upgrade)
- Persistent TCP connection
- Binary frame support
- Stateful session handling


------------------------------------------------------------
4. ARCHITECTURE FLOW
------------------------------------------------------------

User clicks "Start Interview"
        ↓
WebSocket Handshake (HTTP → 101 Switching Protocols)
        ↓
WebSocketSession created
        ↓
User sends binary audio chunks every 5 seconds
        ↓
Backend accumulates bytes in:
Map<sessionId, ByteArrayOutputStream>
        ↓
User clicks "End Interview"
        ↓
Backend writes accumulated bytes to .wav file
        ↓
Session closed and buffer cleared


------------------------------------------------------------
5. PROJECT STRUCTURE
------------------------------------------------------------

src/main/java/com/example/interview/

    ├── config/
    │     └── WebSocketConfig.java
    │
    ├── handler/
    │     └── InterviewWebSocketHandler.java
    │
    ├── service/
    │     └── AudioStorageService.java
    │
    └── InterviewApplication.java


------------------------------------------------------------
6. CORE COMPONENTS
------------------------------------------------------------

1) WebSocketConfig
   - Enables WebSocket support
   - Registers WebSocket handler
   - Defines endpoint: /interview

2) InterviewWebSocketHandler
   - Handles connection events
   - Receives binary messages
   - Manages WebSocketSession
   - Handles close event

3) AudioStorageService
   - Stores audio per session
   - Writes final audio file to disk
   - Clears session data after completion


------------------------------------------------------------
7. SESSION MANAGEMENT
------------------------------------------------------------

Each connected user gets a unique:

    WebSocketSession

Audio is stored using:

    Map<String, ByteArrayOutputStream>

Key   → sessionId
Value → User's accumulated audio bytes

This prevents mixing of audio between users.


------------------------------------------------------------
8. HOW TO RUN
------------------------------------------------------------

1. Clone the project
2. Run using:
       mvn spring-boot:run
3. Open test HTML file in browser
4. Click Start Interview
5. Speak (audio simulated)
6. Click End Interview
7. Check generated audio file in project directory


------------------------------------------------------------
9. LEARNING OBJECTIVES
------------------------------------------------------------

After completing this project, you should understand:

- Difference between HTTP and WebSocket
- Stateless vs Stateful communication
- WebSocket handshake mechanism
- Binary message handling
- Session-based data isolation
- Real-time backend architecture design


------------------------------------------------------------
10. FUTURE IMPROVEMENTS
------------------------------------------------------------

- Add authentication using JWT during handshake
- Store audio files in cloud (AWS S3)
- Convert raw bytes to proper WAV format
- Integrate speech-to-text API
- Scale using Redis or message broker
- Deploy using Docker & Kubernetes


------------------------------------------------------------
AUTHOR
------------------------------------------------------------

Developed as part of BE Technology Learning
Core Java + Spring Boot + WebSocket Deep Dive
