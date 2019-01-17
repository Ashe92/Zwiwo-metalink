import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
public class Hash {
    @XmlAttribute(name = "type")
    public String type = "MD5";
    @XmlValue
    public String value;
}
