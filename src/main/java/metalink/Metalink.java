package metalink;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "metalink")
public class Metalink {
    public Date Published = new Date();
    public List<MetaFile> files = new ArrayList<MetaFile>();
}
