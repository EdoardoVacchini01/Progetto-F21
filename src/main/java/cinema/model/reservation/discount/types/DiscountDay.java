package cinema.model.reservation.discount.types;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map.Entry;

import cinema.model.reservation.Reservation;
import cinema.model.reservation.discount.types.util.TypeOfDiscounts;

/**
 * Strategia di sconto sulla prenotazione basata sul giorno in cui gli
 * spettatori visioneranno il film.
 * 
 * @author Screaming Hairy Armadillo Team
 * 
 */
public class DiscountDay extends Discount {

	/**
	 * ATTRIBUTI
	 * 
	 */
	private HashMap<LocalDate, Double> discount;

	/**
	 * COSTRUTTORE
	 * 
	 * @param type
	 */
	public DiscountDay() {
		super(TypeOfDiscounts.DAY);
		discount = new HashMap<>();
	}

	/**
	 * METODO utilizzato per poter effettuare lo sconto sulla prenotazione e farsi
	 * restituire il nuovo totale, dato lo sconto
	 */
	@Override
	public double getTotal(Reservation r) {
		double totalPrice = 0;
		if (discount.size() > 0) {
			for (Entry<LocalDate, Double> entry : discount.entrySet()) {
				if (entry.getKey().equals(r.getProjection().getDateTime().toLocalDate())) {
					totalPrice += r.getProjection().getPrice() * (1 - entry.getValue()) * r.getNSeats();
					return totalPrice;
				}
			}
		}
		totalPrice += r.getProjection().getPrice() * r.getNSeats();
		return totalPrice;
	}

	/** METODO per farsi restituire le caratteristiche dello sconto per giornata */
	@Override
	public String toString() {
		return "[ " + this.getTypeOfDiscount() + " ]" + "\n"
				+ "Giorni e percentuale di sconti nelle specifiche giornate: \n" + discountsToString(discount);
	}

	private String discountsToString(HashMap<LocalDate, Double> discount) {
		String output = "";
		for (Entry<LocalDate, Double> entry : discount.entrySet()) {
			output += "Giorno: " + entry.getKey();
			output += "  Sconto: " + entry.getValue() * 100 + "%\n";
		}
		return output;
	}

	public void addDayDiscount(LocalDate day, double percentage) {
		this.discount.put(day, percentage);
	}
}