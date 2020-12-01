package softuni.library.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.library.models.dtos.LibraryImportDto;
import softuni.library.models.dtos.LibraryImportRootDto;
import softuni.library.models.entities.Book;
import softuni.library.models.entities.Library;
import softuni.library.repositories.BookRepository;
import softuni.library.repositories.LibraryRepository;
import softuni.library.services.LibraryService;
import softuni.library.util.ValidatorUtil;
import softuni.library.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class LibraryServiceImpl implements LibraryService {

    private static final String LIBRARY_PATH = "src/main/resources/files/xml/libraries.xml";


    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;

    public LibraryServiceImpl(ModelMapper modelMapper, ValidatorUtil validatorUtil, XmlParser xmlParser, BookRepository bookRepository, LibraryRepository libraryRepository) {
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
    }

    @Override
    public boolean areImported() {
        return this.libraryRepository.count() > 0;
    }

    @Override
    public String readLibrariesFileContent() throws IOException {
        return Files.readString(Path.of(LIBRARY_PATH));
    }

    @Override
    public String importLibraries() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        LibraryImportRootDto libraryImportRootDto = this.xmlParser.parseXml(LibraryImportRootDto.class, LIBRARY_PATH);


        for (LibraryImportDto libraryDto : libraryImportRootDto.getLibrary()) {


            Optional<Book> bookById =
                    this.bookRepository.findById((long) libraryDto.getBook().getId());

            if (this.validatorUtil.isValid(libraryDto) && bookById.isPresent()) {

                Library library = this.modelMapper.map(libraryDto, Library.class);

                Set<Book> bookSet = new HashSet<>();
                bookSet.add(bookById.get());

                library.setBooks(bookSet);

                this.libraryRepository.saveAndFlush(library);

                sb.append(String.format("Successfully importedLibrary - " +
                        "%s – %s%n", library.getName(), library.getLocation()));

            } else {
                sb.append("Invalid Library").append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}

