package jw.utilites;

import com.google.gson.*;
import jw.InicializerAPI;
import jw.utilites.json.ItemStackAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileHelper {


    public static String CurrentPath() {
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString();
    }

    public static String PluginPath() {
        return FileHelper.CurrentPath() + File.separator + "plugins" + File.separator + InicializerAPI.GetPlugin().getName() + File.separator;
    }

    public static void Create_Path(String path) {
        File theDir = new File(path);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
    }


    public static ArrayList<String> Get_FileNames(String folder_path, String... extentions) {
        Create_Path(folder_path);
        final File folder = new File(folder_path);
        ArrayList<String> result = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                // rekurencja Get_FileNames
            } else {
                if (extentions.length == 0) {
                    result.add(fileEntry.getName());
                    continue;
                }
                String name = fileEntry.getName();
                int index_of_dots = name.lastIndexOf('.');
                String extention = name.substring(index_of_dots + 1, name.length());
                for (int i = 0; i < extentions.length; i++) {
                    if (extention.equalsIgnoreCase(extentions[i].toLowerCase())) {
                        result.add(fileEntry.getName());
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static boolean Save(Object data, String path, String file_name) {

        File folder = new File(path);
        if (folder.exists() == false) {
            folder.mkdirs();
        }

        final File file = new File(path + File.separator + file_name);
        if (!file.exists()) {
            CreateFileInNoExist(path, file_name);
        }

        try (FileWriter file_ = new FileWriter(path + File.separator + file_name)) {
            Gson gson = new GsonBuilder().registerTypeAdapter(ItemStack.class, new ItemStackAdapter()).addSerializationExclusionStrategy(new ItemStackAdapter.GSON_Skip_Strategy()).create();
            String json = gson.toJson(data);
            if (json == null || json == "")
                json = "[]";

            file_.write(gson.toJson(data));
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static <T> T Load(String path, String file_name, Class<T> type) {
        T result = null;
        final File file = new File(path + File.separator + file_name);
        if (!file.exists()) {
            CreateFileInNoExist(path, file_name);
            return result;
        }
        try (FileReader reader = new FileReader(path + File.separator + file_name)) {

            Gson gson = new GsonBuilder().registerTypeAdapter(ItemStack.class, new ItemStackAdapter()).addSerializationExclusionStrategy(new ItemStackAdapter.GSON_Skip_Strategy()).create();
            T res = gson.fromJson(reader, type);
            reader.close();
            return res;
        } catch (FileNotFoundException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Load File error: " + path);

        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Load File error: " + path);
        }
        return result;
    }

    public static <T> ArrayList<T> Load_List(String path, String file_name, Class<T> type) {
        ArrayList<T> result = new ArrayList<>();
        final File file = new File(path + File.separator + file_name);
        if (!file.exists()) {
            CreateFileInNoExist(path, file_name);
            return result;
        }
        try (FileReader reader = new FileReader(path + File.separator + file_name)) {

            Gson gson = new GsonBuilder().registerTypeAdapter(ItemStack.class, new ItemStackAdapter()).addSerializationExclusionStrategy(new ItemStackAdapter.GSON_Skip_Strategy()).create();
            JsonArray arry = new JsonParser().parse(reader).getAsJsonArray();

            for (JsonElement jsonElement : arry)
                result.add(gson.fromJson(jsonElement, type));

            reader.close();
            return result;
        } catch (FileNotFoundException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Load File error: " + path);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Load File error: " + path);
        }
        return result;
    }

    private static void CreateFileInNoExist(String path, String file_name) {

        try {

            final File file = new File(path + File.separator + file_name);
            String dic = path.substring(0, path.length() - 2);
            final File dictonary = new File(dic);
            if (!file.exists()) {
                file.getParentFile().mkdir();
                file.createNewFile();

            }

            final FileWriter w = new FileWriter(file);
            w.write("[]");
            w.flush();
            w.close();
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage() + "  " + path + "  " + file_name);
        }

    }

}
