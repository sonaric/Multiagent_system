/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.xmlparser;

import javax.xml.bind.JAXBException;

/**
 *
 * @author Stanislav
 */
public interface XmlParser {
    String marshallParser(Object object) throws JAXBException;
    public Object unmarhallParser(String in, Class cl) throws JAXBException;
}
