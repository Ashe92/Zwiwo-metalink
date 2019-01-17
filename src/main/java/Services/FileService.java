package Services;

import metalink.Metalink;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.FileOutputStream;

public class FileService {

    public void SaveMetalinkFile(Metalink metalink)
    {
        try
        {
            FileOutputStream file = new FileOutputStream("zad-zwiwo.xml", true);
            JAXBContext jaxbContext = JAXBContext.newInstance(Metalink.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.marshal(metalink, file);
        }catch(Exception e ){
            System.out.println(e.toString());
        }
    }
}
