package softuni.library.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.library.models.dtos.AuthorImportDto;
import softuni.library.models.entities.Author;
import softuni.library.repositories.AuthorRepository;
import softuni.library.services.AuthorService;
import softuni.library.util.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String AUTHOR_PATH = "src/main/resources/files/json/authors.json";

    private final AuthorRepository authorRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, Gson gson, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.authorRepository = authorRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public boolean areImported() {
        return this.authorRepository.count() > 0;
    }

    @Override
    public String readAuthorsFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(AUTHOR_PATH)));
    }

    @Override
    public String importAuthors() throws IOException {
        StringBuilder sb = new StringBuilder();

        AuthorImportDto[] authorImportDtos = this.gson.fromJson(this.readAuthorsFileContent(), AuthorImportDto[].class);

        for (AuthorImportDto authorImportDto : authorImportDtos) {

            if (this.validatorUtil.isValid(authorImportDto)) {
                this.authorRepository.saveAndFlush(this.modelMapper.map(authorImportDto, Author.class));
                sb.append(String.format("Successfully imported Author: %s %s - %s", authorImportDto.getFirstName(),
                        authorImportDto.getLastName(), authorImportDto.getBirthTown()))
                        .append(System.lineSeparator());
            } else {
                sb.append("Invalid Author").append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
