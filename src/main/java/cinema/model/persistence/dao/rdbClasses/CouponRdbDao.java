package cinema.model.persistence.dao.rdbClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cinema.model.persistence.dao.interfaces.ICouponDao;
import cinema.model.reservation.discount.coupon.Coupon;
import cinema.model.reservation.discount.coupon.util.CouponException;

/**
 * Si interfaccia con un database relazionale per implementare la persistenza
 * dei dati dei coupon gestiti dall'applicazione.
 * 
 * @author Screaming Hairy Armadillo Team
 *
 */
public class CouponRdbDao implements ICouponDao {

	/**
	 * Connessione al database.
	 */
	private Connection connection;

	/**
	 * Costruttore dell'interfaccia verso il database relazionale.
	 * 
	 * @param connection connessione al database relazionale che impelemta la
	 *                   persistenza delle informazioni.
	 */
	public CouponRdbDao(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Esegue la query per recuperare tutti i coupon presenti sul database
	 * relazionale.
	 */
	@Override
	public ArrayList<Coupon> getAllCoupons() throws SQLException, CouponException {
		String sql = "SELECT * FROM Coupon;";
		PreparedStatement pstatement = connection.prepareStatement(sql);
		ResultSet result = pstatement.executeQuery();
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		while (result.next()) {
			coupons.add(
					new Coupon(result.getString("promocode"), result.getDouble("amount"), result.getBoolean("used")));
		}
		return coupons;
	}

	/**
	 * Esegue la query al database relazionale per recuperare le informazioni sul
	 * coupon identificato da {@code promocode}.
	 */
	@Override
	public Coupon getCoupon(String promocode) throws SQLException, CouponException {
		String sql = "SELECT * FROM Coupon WHERE promocode = ?;";
		PreparedStatement pstatement = connection.prepareStatement(sql);
		pstatement.setString(1, promocode);
		ResultSet result = pstatement.executeQuery();
		Coupon coupon = new Coupon(promocode, result.getDouble("amount"), result.getBoolean("used"));
		return coupon;
	}

	/**
	 * Esegue la query al database relazionale per rendere persistente la proprietà
	 * del coupon identificato da {@code promocode} di essere stato usato.
	 */
	@Override
	public void setCouponUsed(String promocode) throws SQLException {
		String sql = "UPDATE Coupon SET used = 1 WHERE promocode = ?;";
		PreparedStatement pstatement = connection.prepareStatement(sql);
		pstatement.setString(1, promocode);
		pstatement.executeUpdate();
	}

}