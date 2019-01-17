import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "file")
public class MetaFile {
    public String name;
    public long size;
    public Hash hash = new Hash();
    public String url;
}

