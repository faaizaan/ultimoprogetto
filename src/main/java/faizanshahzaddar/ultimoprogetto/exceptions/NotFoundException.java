package faizanshahzaddar.ultimoprogetto.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("il recordo con id " + id + " non e stato trovato");
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}
