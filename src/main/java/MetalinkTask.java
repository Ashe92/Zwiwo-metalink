import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MetalinkTask  extends Task {

    public static final String MetalinkUrl = "server.files.url";
    public static final String UserDir = ".";
    private FileService fileService = new FileService();

    //elements of metalink
    private String url;
    private String file;
    private List<FileSet> filesets = new ArrayList();

    @Override
    public void execute() throws BuildException {
        super.execute();
        Validate();
        GetUrl();
        Metalink generatedMetalink = GetMetalinkFile();
        fileService.SaveMetalinkFile(generatedMetalink);
    }

    private void Validate() {
        if (url == null) throw new BuildException("Attribute \"url\" is not specified!");
        if (file == null) throw new BuildException("Attribute \"file\" is not specified!");
    }

    private Metalink GetMetalinkFile()
    {
        Metalink newMetalink = new Metalink();
        List<File> files = getFilesFromActualDir();
        newMetalink.files = getMetaLinkFiles(files);

        return newMetalink;
    }

    private List<File> getFilesFromActualDir() {
        File folder = new File(UserDir);
        File[] listOfFiles = folder.listFiles();
        return Arrays.asList(listOfFiles);
    }

    //todo add to fileservice
    private List<MetaFile> getMetaLinkFiles(List<File> files) {

        List<MetaFile> metaLinkFiles = new ArrayList<>();
        files.stream().forEach(f -> {
            MetaFile metaLinkFile = new MetaFile();
            metaLinkFile.name = f.getName();
            metaLinkFile.size = f.length();
            Hash hash = new Hash();
            hash.value = GetMd5(f);
            metaLinkFile.hash = hash;
            metaLinkFile.url = GetUrl() + f.getName();
            metaLinkFiles.add(metaLinkFile);
        });
        return metaLinkFiles;
    }

    private static String GetMd5(File file){
        try {
            FileInputStream fis = new FileInputStream(file);
            String md5 = DigestUtils.md5Hex(fis);
            fis.close();
            return md5;
        } catch (Exception e) {
            return "";
        }
    }

    public String  GetUrl() {
        if (url == null) {
            setUrl(getProject().getProperty(MetalinkUrl ));
        }
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

    public void addFileset(FileSet fileset) {
        filesets.add(fileset);
    }
}