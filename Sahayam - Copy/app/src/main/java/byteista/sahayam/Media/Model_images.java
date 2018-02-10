package byteista.sahayam.Media;

/**
 * Created by shrey on 7/5/2017.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class Model_images {
    String str_folder;
    String mdate;
    int nmnths;
    List<String[]> al_imagepath;

    public Model_images() {
        al_imagepath=new ArrayList<String[]>();
        mdate="nothing";
        nmnths=0;
    }

    public String getStr_folder() {
        return str_folder;
    }

    public void setStr_folder(String str_folder) {
        this.str_folder = str_folder;
    }

    public List<String[]> getAl_imagepath() {
        return al_imagepath;
    }

    public void setAl_imagepath(List<String[]> al_imagepath) {
        this.al_imagepath = al_imagepath;
    }

    public int getSize()
    {
        return this.al_imagepath.size()-nmnths;
    }
}
