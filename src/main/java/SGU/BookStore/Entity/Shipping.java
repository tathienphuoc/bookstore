package SGU.BookStore.Entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name="Shipping.findAll", query="SELECT s FROM Shipping s")
public class Shipping implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String address;

    @Column(name="card_number")
    private Integer cardNumber;

    @Column(name="card_owner")
    private String cardOwner;

    private String city;

    @Column(name="number_phone")
    private Integer numberPhone;

    private byte status;

    //bi-directional many-to-one association to Account
    @ManyToOne
    private Account account;

    @Transient
    List<Cartitem> cartitems;
}
