package io.graversen.minecraft.rcon;

public class RconConnectException extends RuntimeException {
    public RconConnectException(String message, Object... args) {
        super(String.format(message, args));
    }

    public RconConnectException(Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
    }
}
