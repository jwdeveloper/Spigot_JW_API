package jw.utilites.files;

import jw.InitializerAPI;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public interface FileHelper {

    static String serverPath() {
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString();
    }

    static String pluginPath() {
        return FileHelper.serverPath() + File.separator + "plugins" + File.separator + InitializerAPI.getPlugin().getName() ;
    }
    static String pluginsPath() {
        return FileHelper.serverPath() + File.separator + "plugins";
    }
    static boolean isPathExists(String path) {
        return new File(path).exists();
    }



    public static void removeDirectory(File file)
    {
        if(file == null)
            return;

        if(file.isDirectory())
        {
            File[] files = file.listFiles();

            if(files != null)
            {
                for(File childFile : files)
                {
                    if(childFile.isDirectory())
                        removeDirectory(childFile);
                    else if(!childFile.delete())
                    {

                    }
                      //  getLogger().warning("Could not delete file: " + childFile.getName());
                }
            }
        }

        if(!file.delete())
        {
            
        }
          //  getLogger().warning("Could not delete file: " + file.getName());
    }

     static ArrayList<String> getFolderFilesName(String path, String... extensions) {
        ArrayList<String> result = new ArrayList<>();
        if(!isPathExists(path))
        {
            return result;
        }

        final File folder = new File(path);
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                // rekurencja Get_FileNames
            } else {
                if (extensions.length == 0) {
                    result.add(fileEntry.getName());
                    continue;
                }
                String name = fileEntry.getName();
                int index_of_dots = name.lastIndexOf('.');
                String extension = name.substring(index_of_dots + 1, name.length());
                for (String fileName : extensions) {
                    if (extension.equalsIgnoreCase(fileName.toLowerCase())) {
                        result.add(fileEntry.getName());
                        break;
                    }
                }
            }
        }
        return result;
    }
}
