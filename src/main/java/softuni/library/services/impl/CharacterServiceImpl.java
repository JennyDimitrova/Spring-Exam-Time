package softuni.library.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.library.models.dtos.CharacterImportDto;
import softuni.library.models.dtos.CharacterImportRootDto;
import softuni.library.models.entities.Book;
import softuni.library.models.entities.Character;
import softuni.library.repositories.AuthorRepository;
import softuni.library.repositories.BookRepository;
import softuni.library.repositories.CharacterRepository;
import softuni.library.services.CharacterService;
import softuni.library.util.ValidatorUtil;
import softuni.library.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {
    private final String PATH_CHARACTERS = "src/main/resources/files/xml/characters.xml";

    private final CharacterRepository characterRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;
    private final BookRepository bookRepository;

    public CharacterServiceImpl(CharacterRepository characterRepository, ModelMapper modelMapper,
                                ValidatorUtil validatorUtil, XmlParser xmlParser,
                                BookRepository bookRepository) {
        this.characterRepository = characterRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean areImported() {
        return this.characterRepository.count() > 0;
    }

    @Override
    public String readCharactersFileContent() throws IOException {
        return Files.readString(Path.of(PATH_CHARACTERS));
    }

    @Override
    public String importCharacters() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        CharacterImportRootDto characterImportRootDto =
                this.xmlParser.parseXml(CharacterImportRootDto.class, PATH_CHARACTERS);


        for (CharacterImportDto characterDto : characterImportRootDto.getCharacterImportDtos()) {

            Optional<Book> bookById =
                    this.bookRepository.findById((long) characterDto.getBookImportCharacterDto().getId());


            if (this.validatorUtil.isValid(characterDto) && bookById.isPresent()) {
                Character character = this.modelMapper.map(characterDto, Character.class);

                character.setBook(bookById.get());
                this.characterRepository.saveAndFlush(character);

                sb.append(String.format("Successfully imported Character - " +
                                "%s %s %s - age: %d",
                        character.getFirstName(), character.getMiddleName(),
                        character.getLastName(), character.getAge()))
                        .append(System.lineSeparator());

            } else {
                sb.append("Invalid Character").append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    @Override
    public String findCharactersInBookOrderedByLastNameDescendingThenByAge() {
        StringBuilder sb = new StringBuilder();

        List<Character> characters = this.characterRepository.getCharacters();

        for (Character character : characters) {
            sb.append(String.format("Character name %s %s %s, age %d, in book %s",
                    character.getFirstName(), character.getMiddleName(), character.getLastName(),
                    character.getAge(), character.getBook().getName()))
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }

}
