package SGU.BookStore.Entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the cartitem database table.
 * 
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name="Cartitem.findAll", query="SELECT c FROM Cartitem c")
public class Cartitem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name="order_date")
	private Date orderDate;

	private Integer quantity;

	//bi-directional many-to-one association to Account
	@ManyToOne
	private Account account;

	private byte status;

	//bi-directional many-to-one association to Book
	@ManyToOne
	private Book book;

	@Transient
	int quantityPurchsed;

	@Transient
	Integer shippingID;
}