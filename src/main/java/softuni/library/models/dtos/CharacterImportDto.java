package softuni.library.models.dtos;

import org.hibernate.validator.constraints.Length;
import softuni.library.config.LocalDateAdapter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CharacterImportDto {

    @XmlElement(name = "first-name")
    private String firstName;
    @XmlElement(name = "middle-name")
    private String middleName;
    @XmlElement(name = "last-name")
    private String lastName;
    @XmlElement
    private int age;
    @XmlElement
    private String role;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate birthday;
    @XmlElement(name = "book")
    private BookImportCharacterDto bookDto;

    public CharacterImportDto() {
    }

    @NotNull
    @Min(value = 10)
    @Max(value = 66)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @NotNull
    @Length(min = 3)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    @Length(min = 3)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    @Length(min = 1, max = 1)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @NotNull
    @Length(min = 5)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @XmlElement(name = "book")
    public BookImportCharacterDto getBookImportCharacterDto() {
        return bookDto;
    }

    public void setBookImportCharacterDto(BookImportCharacterDto bookImportCharacterDto) {
        this.bookDto = bookImportCharacterDto;
    }

    @NotNull
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
