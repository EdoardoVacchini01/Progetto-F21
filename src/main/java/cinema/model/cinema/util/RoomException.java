package cinema.model.cinema.util;

/**
 * Gestisce le eccezioni generate dalla classe Room.
 * 
 * @author Screaming Hairy Armadillo Team
 *
 */
@SuppressWarnings("serial")
public class RoomException extends Exception {

	/**
	 * Costruttore dell'eccezione.
	 * 
	 * @param message messaggio da riportare.
	 */
	public RoomException(String message) {
		super(message);
	}
}
