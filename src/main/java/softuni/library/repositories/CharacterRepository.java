package softuni.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.library.models.entities.Character;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    @Query("select c from Character c where c.age >= 32 " +
            "order by c.book.name,c.lastName desc,c.age")
    List<Character> getCharacters();
}
