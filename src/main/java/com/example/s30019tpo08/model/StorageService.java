package com.example.s30019tpo08.model;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.*;

@Service
public class StorageService {

    private static final String STORAGE_DIR = "saved";

    public StorageService() {
        new File(STORAGE_DIR).mkdirs();
    }

    public void save(String id, FormattedCode code) throws IOException {
        File file = new File(STORAGE_DIR, id + ".ser");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(code);
        }
        scheduleDeletion(id, code.getExpirationTime().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    public FormattedCode load(String id) throws IOException, ClassNotFoundException {
        File file = new File(STORAGE_DIR, id + ".ser");
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            FormattedCode code = (FormattedCode) ois.readObject();
            if (code.isExpired()) {
                file.delete();
                return null;
            }
            return code;
        }
    }

    private void scheduleDeletion(String id, long epochMillis) {
        long delay = epochMillis - System.currentTimeMillis();
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            try {
                Files.deleteIfExists(Paths.get(STORAGE_DIR, id + ".ser"));
            } catch (IOException ignored) {}
        }, delay, TimeUnit.MILLISECONDS);
    }
}
