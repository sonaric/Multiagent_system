/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.xmlparser;

import core.Agent;
import dataAgent.AgentList;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Stanislav
 */
public class XmlMarshalDemarshal implements XmlParser{
    
    public AgentList agents = AgentList.getInstance();
    
    @Override
    public String marshallParser(Object object) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        marshaller.marshal(object, sw);
        return sw.toString();
    }
    
    @Override
    public Object unmarhallParser(String in, Class cl) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(cl);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader sr = new StringReader(in);
        Object object = unmarshaller.unmarshal(sr);
        return object;
    }
    
}
