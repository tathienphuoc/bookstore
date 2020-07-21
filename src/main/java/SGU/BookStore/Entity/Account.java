package SGU.BookStore.Entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="full_name")
	private String fullName;

	@Lob
	private byte[] image;

	private String pwd;

	@Column(name="user_name")
	private String userName;

	//bi-directional many-to-one association to Role
	@ManyToOne
	private Role role;

	//bi-directional many-to-one association to Cartitem
	@OneToMany(mappedBy="account")
	private List<Cartitem> cartitems;

	@Transient
	int quantityPurchsed;

	//bi-directional many-to-one association to Shipping
	@OneToMany(mappedBy="account")
	private List<Shipping> shippings;


	public Account copy(){
		Account account= Account.builder()
				.id(this.id)
				.fullName(this.fullName)
				.image(this.image)
				.pwd(this.pwd)
				.userName(this.userName)
				.role(this.role)
				.build();
		return account;
	}
}