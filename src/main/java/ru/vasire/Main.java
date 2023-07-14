package ru.vasire;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ru.vasire.report.BelongNumbers;
import ru.vasire.serializers.ObjectSerializer;
import ru.vasire.sms.Sms;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Sms sms;

        try (BufferedReader br = new BufferedReader(new FileReader(Main.class.getResource("/sms.json").getPath(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            ObjectMapper om = new ObjectMapper();
            sms = om.readValue(sb.toString(), Sms.class);
        }
        BelongNumbers belongNumbers = BelongNumbers.SmsToBelongNumbers(sms);

        System.out.println(" ************ JSON ****************** ");
        ObjectMapper objectMapper = new ObjectMapper();
        testSerialization(objectMapper, "belongNumbers.json", belongNumbers);

        System.out.println(" ************ XML ****************** ");
        XmlMapper xmlMapper = new XmlMapper();
        testSerialization(xmlMapper, "belongNumbers.xml", belongNumbers);

        System.out.println(" ************ Yaml ****************** ");
        ObjectMapper objectMapperYaml = new ObjectMapper(new YAMLFactory());
        testSerialization(objectMapperYaml, "belongNumbers.yaml", belongNumbers);

        System.out.println(" ************ Java Serialization ****************** ");
        try (FileOutputStream fileOutputStream = new FileOutputStream("belongNumbers.bin");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
            objectOutputStream.writeObject(belongNumbers);
            objectOutputStream.flush();
        }
        System.out.println("After recovery from file");
        try (FileInputStream fileInputStream = new FileInputStream("belongNumbers.bin");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);) {
            BelongNumbers persListNew = (BelongNumbers) objectInputStream.readObject();
            System.out.println("belongNumbersNew = " + persListNew);
        }
    }

    public static void testSerialization(ObjectMapper objectMapper, String path, Object object) {
        System.out.println("Init");
        // Write object to Console
        System.out.println(ObjectSerializer.writeValueAsString(objectMapper, object));
        // Write object to file
        ObjectSerializer.writeValueToFile(objectMapper, path, object);
        // Recovery object from file
        BelongNumbers persListNew = ObjectSerializer.readValue(objectMapper, path, BelongNumbers.class);
        System.out.println("After recovery from file");
        // Write object to Console after recovery object from file
        System.out.println(ObjectSerializer.writeValueAsString(objectMapper, persListNew));
    }
}
