package ru.vasire;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ru.vasire.report.BelongNumbers;
import ru.vasire.serializers.ObjectSerializer;
import ru.vasire.sms.Sms;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Sms sms = loadSmsFromFile(Main.class.getResource("/sms.json").getPath());

        BelongNumbers belongNumbers = BelongNumbers.SmsToBelongNumbers(sms);

        testSerialization("JSON", new ObjectMapper(), "belongNumbers.json", belongNumbers);

        testSerialization("XML", new XmlMapper(), "belongNumbers.xml", belongNumbers);

        testSerialization("Yaml", new ObjectMapper(new YAMLFactory()), "belongNumbers.yaml", belongNumbers);

        testJavaSerialization(belongNumbers, "belongNumbers.bin");

        testRecoveryFromJavaSerialization("belongNumbers.bin");
    }

    private static void testJavaSerialization(BelongNumbers belongNumbers, String path) {
        System.out.println(" ************ Java Serialization ****************** ");
        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
            objectOutputStream.writeObject(belongNumbers);
            objectOutputStream.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void testRecoveryFromJavaSerialization(String path) {
        System.out.println("After recovery from file");
        try (FileInputStream fileInputStream = new FileInputStream(path);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);) {
            BelongNumbers persListNew = (BelongNumbers) objectInputStream.readObject();
            System.out.println("belongNumbersNew = " + persListNew);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Sms loadSmsFromFile(String path) {
        Sms sms;
        try (BufferedReader br = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            ObjectMapper om = new ObjectMapper();
            sms = om.readValue(sb.toString(), Sms.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sms;
    }

    public static void testSerialization(String type, ObjectMapper objectMapper, String path, Object object) {
        System.out.println(" ************ "+ type+" ****************** ");
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
