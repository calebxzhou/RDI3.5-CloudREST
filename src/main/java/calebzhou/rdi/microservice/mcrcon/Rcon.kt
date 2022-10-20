package calebzhou.rdi.microservice.mcrcon

/**
 * Created by calebzhou on 2022-10-16,21:52.
 */

import java.net.Socket
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.atomic.AtomicInteger

class McRcon(host: String, port: Int) {
    val conn = Socket(host, port)
    val lastId = AtomicInteger(0)

    fun authenticate(password: String) {
        sendMessage(MessageType.AUTHENTICATE, password)
    }

    fun sendCommand(body: String): Message {
        return sendMessage(MessageType.COMMAND, body)
    }

    fun sendMessage(messageType: MessageType, body: String): Message {
        // Construct the request.
        val message: Message = Message()
        message.length = body.length + HEADER_SIZE
        message.id = lastId.addAndGet(1)
        message.type = messageType
        message.body = body
        val bytes: ByteArray = encodeMessage(message)

        // Send the request.
        conn.outputStream.write(bytes)
        conn.outputStream.flush()

        // Determine the length of the response.
        val lengthBytes: ByteArray = conn.inputStream.readNBytes(4)
        val lengthBuf: ByteBuffer = ByteBuffer.wrap(lengthBytes)
        lengthBuf.order(ByteOrder.LITTLE_ENDIAN)
        val respSize: Int = lengthBuf.getInt()
        lengthBuf.flip()

        // Read the response bytes.
        val respBuf: ByteBuffer = ByteBuffer.allocate(respSize+4).put(lengthBuf)
        respBuf.order(ByteOrder.LITTLE_ENDIAN)
        respBuf.put(conn.inputStream.readNBytes(respSize))
        respBuf.flip()
        val respBytes: ByteArray = ByteArray(respSize+4)
        respBuf.get(respBytes, 0, respBytes.size)

        // Decode and return the response.
        val decoded: Message = decodeMessage(respBytes)
        return decoded
    }
}