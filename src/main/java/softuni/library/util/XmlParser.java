package softuni.library.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <O> O parseXml(Class<O> objectClass, String fromFilePath) throws JAXBException;

    <O> void exportHml(O fromObject, Class<O> fromObjectClass, String toFilePath) throws JAXBException;
}
