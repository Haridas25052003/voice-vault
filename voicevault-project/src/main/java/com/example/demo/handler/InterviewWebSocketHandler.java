package com.example.demo.handler;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InterviewWebSocketHandler extends AbstractWebSocketHandler {

    // ================================================================
    // STORAGE: Each user's session gets its own byte accumulator
    // Why ConcurrentHashMap? Multiple users connect simultaneously
    // Key   = session ID (unique per connection)
    // Value = list of byte chunks received from that user
    // ================================================================
    private Map<String, List<byte[]>> sessionAudioData = new ConcurrentHashMap<>();

    // Where to save the final audio file on your computer
    private static final String SAVE_DIRECTORY = "C:/interview-recordings/";
    // Change this path as needed. On Linux/Mac use: "/tmp/interview-recordings/"


    // ================================================================
    // EVENT 1: Called by Spring when browser CONNECTS via WebSocket
    // ================================================================
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String sessionId = session.getId();
        System.out.println("✅ NEW CONNECTION: Session ID = " + sessionId);

        // Initialize empty list for this session to store audio chunks
        sessionAudioData.put(sessionId, new ArrayList<>());

        // Send confirmation message back to browser
        session.sendMessage(new TextMessage("CONNECTED:" + sessionId));

        System.out.println("Total active sessions: " + sessionAudioData.size());
    }


    // ================================================================
    // EVENT 2: Called when browser sends BINARY data (voice bytes)
    // ================================================================
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        
        String sessionId = session.getId();
        
        // Extract the raw bytes from the message
        byte[] audioChunk = message.getPayload().array();
        
        System.out.println("🎤 Received audio chunk from session: " + sessionId 
                         + " | Size: " + audioChunk.length + " bytes");

        // Add this chunk to the session's accumulator list
        List<byte[]> chunks = sessionAudioData.get(sessionId);
        
        if (chunks != null) {
            chunks.add(audioChunk);
            System.out.println("Total chunks accumulated: " + chunks.size() 
                             + " | Total bytes so far: " + getTotalBytes(chunks));
        }
    }


    // ================================================================
    // EVENT 3: Called when browser sends TEXT data (commands like "END")
    // ================================================================
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        
        String sessionId = session.getId();
        String command = message.getPayload();
        
        System.out.println("📨 Text message from " + sessionId + ": " + command);

        if ("END_INTERVIEW".equals(command)) {
            System.out.println("🛑 END command received. Saving audio file...");
            saveAudioFile(session);
        }
    }


    // ================================================================
    // EVENT 4: Called when browser DISCONNECTS (tab closed, etc.)
    // ================================================================
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        
        String sessionId = session.getId();
        System.out.println("❌ Connection CLOSED: " + sessionId + " | Reason: " + status);

        // Clean up memory - remove this session's data
        sessionAudioData.remove(sessionId);
        
        System.out.println("Remaining active sessions: " + sessionAudioData.size());
    }


    // ================================================================
    // PRIVATE HELPER: Save accumulated bytes to disk as a file
    // ================================================================
    private void saveAudioFile(WebSocketSession session) throws Exception {
        
        String sessionId = session.getId();
        List<byte[]> chunks = sessionAudioData.get(sessionId);

        if (chunks == null || chunks.isEmpty()) {
            session.sendMessage(new TextMessage("ERROR:No audio data received"));
            return;
        }

        // Step A: Merge all chunks into one big byte array
        int totalBytes = getTotalBytes(chunks);
        byte[] mergedAudio = new byte[totalBytes];
        
        int position = 0;
        for (byte[] chunk : chunks) {
            System.arraycopy(chunk, 0, mergedAudio, position, chunk.length);
            position += chunk.length;
        }
        System.out.println("Merged " + chunks.size() + " chunks = " + totalBytes + " total bytes");

        // Step B: Create save directory if it doesn't exist
        Path dirPath = Paths.get(SAVE_DIRECTORY);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
            System.out.println("Created directory: " + SAVE_DIRECTORY);
        }

        // Step C: Generate unique filename using timestamp
        String timestamp = LocalDateTime.now()
                              .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "interview_" + sessionId + "_" + timestamp + ".webm";
        // .webm is the format browsers record audio in by default

        // Step D: Write bytes to file
        Path filePath = Paths.get(SAVE_DIRECTORY + fileName);
        Files.write(filePath, mergedAudio);

        System.out.println("✅ Audio file saved: " + filePath.toAbsolutePath());

        // Step E: Notify browser that file was saved successfully
        session.sendMessage(new TextMessage("SAVED:" + fileName));
    }


    // ================================================================
    // UTILITY: Calculate total bytes across all chunks
    // ================================================================
    private int getTotalBytes(List<byte[]> chunks) {
        int total = 0;
        for (byte[] chunk : chunks) {
            total += chunk.length;
        }
        return total;
    }
}