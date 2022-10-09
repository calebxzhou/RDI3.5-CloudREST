package calebzhou.rdi.microservice.service

import com.google.gson.Gson
import java.io.*
import java.net.InetSocketAddress
import java.net.Socket

/**
 *
 * @author zh32
 * */
class ServerListPing(host: String, port: Int) {
    var address: InetSocketAddress
    var timeout = 7000
    private val gson = Gson()

    init {
        address = InetSocketAddress(host, port)
    }

    private fun readVarInt(dataInputStream: DataInputStream): Int {
        var i = 0
        var j = 0
        while (true) {
            val k = dataInputStream.readByte().toInt()
            i = i or (k and 0x7F shl j++) * 7
            if (j > 5) throw RuntimeException("VarInt too big")
            if (k and 0x80 != 128) break
        }
        return i
    }

    private fun writeVarInt(out: DataOutputStream, paramInt: Int) {
        var paramInt = paramInt
        while (true) {
            if (paramInt and -0x80 == 0) {
                out.writeByte(paramInt)
                return
            }
            out.writeByte(paramInt and 0x7F or 0x80)
            paramInt = paramInt ushr 7
        }
    }

    fun fetchData(): StatusResponse {
        val socket = Socket()
        val dataOutputStream: DataOutputStream
        socket.soTimeout = timeout
        socket.connect(address, timeout)
        val outputStream = socket.getOutputStream()
        dataOutputStream = DataOutputStream(outputStream)
        val inputStream = socket.getInputStream()
        val inputStreamReader = InputStreamReader(inputStream)
        val b = ByteArrayOutputStream()
        val handshake = DataOutputStream(b)
        handshake.writeByte(0x00) //packet id for handshake
        writeVarInt(handshake, 4) //protocol version
        writeVarInt(handshake, address.hostString.length) //host length
        handshake.writeBytes(address.hostString) //host string
        handshake.writeShort(address.port) //port
        writeVarInt(handshake, 1) //state (1 for handshake)
        writeVarInt(dataOutputStream, b.size()) //prepend size
        dataOutputStream.write(b.toByteArray()) //write handshake packet
        dataOutputStream.writeByte(0x01) //size is only 1
        dataOutputStream.writeByte(0x00) //packet id for ping
        val dataInputStream = DataInputStream(inputStream)
        val size = readVarInt(dataInputStream) //size of packet
        var id = readVarInt(dataInputStream) //packet id
        if (id == -1) {
            throw IOException("Premature end of stream.")
        }
        if (id != 0x00) { //we want a status response
            throw IOException("Invalid packetID")
        }
        val length = readVarInt(dataInputStream) //length of json string
        if (length == -1) {
            throw IOException("Premature end of stream.")
        }
        if (length == 0) {
            throw IOException("Invalid string length.")
        }
        val `in` = ByteArray(length)
        dataInputStream.readFully(`in`) //read json string
        val json = String(`in`)
        val now = System.currentTimeMillis()
        dataOutputStream.writeByte(0x09) //size of packet
        dataOutputStream.writeByte(0x01) //0x01 for ping
        dataOutputStream.writeLong(now) //time!?
        readVarInt(dataInputStream)
        id = readVarInt(dataInputStream)
        if (id == -1) {
            throw IOException("Premature end of stream.")
        }
        if (id != 0x01) {
            throw IOException("Invalid packetID")
        }
        val pingtime = dataInputStream.readLong() //read response
        val response = gson.fromJson(json, StatusResponse::class.java)
        response.time = (now - pingtime).toInt()
        dataOutputStream.close()
        outputStream.close()
        inputStreamReader.close()
        inputStream.close()
        socket.close()
        return response
    }

    data class StatusResponse(val players: Players, val version: Version, var time:Int)

    data class Players (
        val max: Int = 0,
        val online: Int = 0,
        val sample: List<Player> = ArrayList()
    )

    data class Player (
        val name: String ,
        val id: String
    )

    data class Version (
        val name: String ,
        val protocol: String
    )
}