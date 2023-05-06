package cn.edu.fzu.sm.wuweida.util;

import java.io.File;
import java.util.List;

public class FilesLooping {
    public static List<File> getFilesList(File file,List<File> tempList){
        File[]  filesList=file.listFiles();
        for(File file1:filesList){
            if (file1.isDirectory()){
                getFilesList(file1,tempList);
            }else {
                tempList.add(file1);
            }
        }
        return tempList;
    }
}
