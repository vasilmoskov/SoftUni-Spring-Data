package softuni.exam.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <T> T parse(Class<T> classToBeBound, String xmlPath) throws JAXBException;
}
