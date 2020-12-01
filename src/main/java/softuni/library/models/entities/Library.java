package softuni.library.models.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "libraries")
public class Library extends BaseEntity {

    private String name;
    private String location;
    private int rating;
    private Set<Book> books;

    public Library() {
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @ManyToMany
    @JoinTable(name = "libraries_books",
            joinColumns = @JoinColumn(name = "library_id", referencedColumnName = "id"))
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
