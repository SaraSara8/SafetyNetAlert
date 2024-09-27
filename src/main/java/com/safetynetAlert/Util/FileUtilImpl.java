package com.safetynetAlert.Util;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class FileUtilImpl implements FileUtil {

    @Override
    public byte[] readFile(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath)); // Actual file reading
    }

    @Override
    public Any deserializeFile(byte[] byteData) {
        return JsonIterator.deserialize(byteData);
    }

    @Override
    public Boolean writeFile(String filePath,  Object dataMap) throws IOException {

        // Création de l'ObjectMapper pour la conversion en JSON
        ObjectMapper objectMapper = new ObjectMapper();

        // Configurer l'ObjectMapper pour formater le JSON avec des retours à la ligne
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Écrire les données dans le fichier
        objectMapper.writeValue(new File(filePath), dataMap);

        return true;

    }

}
