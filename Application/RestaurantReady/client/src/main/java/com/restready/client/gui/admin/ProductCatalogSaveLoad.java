package com.restready.client.gui.admin;

import com.restready.common.ProductCatalog;
import com.restready.common.util.Log;

import java.io.*;

public class ProductCatalogSaveLoad {

    // TODO: Json?

    public static void saveTo(File file, ProductCatalog catalog) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(catalog);
        } catch (IOException e) {
            Log.error(ProductCatalogSaveLoad.class.getSimpleName(), "Unable to save file: " + file.getName(), e);
            throw new RuntimeException(e);
        }
    }

    public static ProductCatalog loadFrom(File file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (ProductCatalog) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.error(ProductCatalogSaveLoad.class.getSimpleName(), "Unable to load file: " + file.getName(), e);
            throw new RuntimeException(e);
        }
    }
}
