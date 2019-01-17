package metalink;

import Services.FileService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MetalinkTask  extends Task {

    protected FileService fileService = new FileService();

    private String url;
    private String file;
    protected List<MetaFile> filesets = new ArrayList();

    @Override
    public void execute() throws BuildException {
        super.execute();
        GetUrl();
        Metalink generatedMetalink = GetMetalinkFile();
        fileService.SaveMetalinkFile(generatedMetalink);
    }

    protected void GetUrl() {
        //todo if url from build empty set form property
        getProject();
        setUrl(getProject().getProperty("server.files.url"));
    }

    private Metalink GetMetalinkFile()
    {
        Metalink newMetalink = new Metalink();
        //todo load files form path
        List<File> files = getFiles();
        //todo change io.files to my FileObject
        newMetalink.files = getMetaLinkFiles(files);

        return newMetalink;
    }

    public static String GetMd5(File file){
        try {
            FileInputStream fis = new FileInputStream(file);
            String md5 = DigestUtils.md5Hex(fis);
            fis.close();
            return md5;
        } catch (Exception e) {
            return "";
        }
    }
    //todo add to fileservice
    public List<File> getFiles() {
        File folder = new File(".");
        File[] listOfFiles = folder.listFiles();
        return Arrays.asList(listOfFiles);
    }

    //todo add to fileservice
    public List<MetaFile> getMetaLinkFiles(List<File> files) {

        List<MetaFile> metaLinkFiles = new ArrayList<>();
        files.stream().forEach(f -> {
            MetaFile metaLinkFile = new MetaFile();
            metaLinkFile.name = f.getName();
            metaLinkFile.size = f.length();
            Hash hash = new Hash();
            hash.value = DigestUtils.md5Hex(f.getName());
            metaLinkFile.hash = hash;
            metaLinkFile.url = getUrl() + f.getName();
            metaLinkFiles.add(metaLinkFile);
        });
        return metaLinkFiles;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void addFileset(MetaFile fileset) {
        filesets.add(fileset);
    }
}