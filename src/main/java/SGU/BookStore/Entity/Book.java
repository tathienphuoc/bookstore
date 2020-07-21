package SGU.BookStore.Entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the book database table.
 * 
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name="Book.findAll", query="SELECT b FROM Book b")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String description;

	@Lob
	private byte[] image;

	private float price;

	@Column(name="publish_year")
	private Integer publishYear;

	private String title;

	//bi-directional many-to-one association to Author
	@ManyToOne
	private Author author;

	//bi-directional many-to-one association to Category
	@ManyToOne
	private Category category;

	//bi-directional many-to-one association to Cartitem
	@OneToMany(mappedBy="book")
	private List<Cartitem> cartitems;


}