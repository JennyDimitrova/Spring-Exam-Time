package softuni.library.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement(name = "characters")
@XmlAccessorType(XmlAccessType.FIELD)
public class CharacterImportRootDto {

    @XmlElement(name = "character")
    private List<CharacterImportDto> characterImportDtos;

    public CharacterImportRootDto() {
    }

    public List<CharacterImportDto> getCharacterImportDtos() {
        return characterImportDtos;
    }

    public void setCharacterImportXmlDtos(List<CharacterImportDto> characterImportDtos) {
        this.characterImportDtos = characterImportDtos;
    }
}
