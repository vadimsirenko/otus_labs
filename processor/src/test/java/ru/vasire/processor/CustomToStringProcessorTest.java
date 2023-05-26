package ru.vasire.processor;

import com.google.common.base.Joiner;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;
import ru.vasire.annotation.CustomToString;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

public class CustomToStringProcessorTest {


    private final JavaFileObject samplePerson = JavaFileObjects.forSourceString("ru.vasire.model.Person",
    "package ru.vasire.model;\n" +
            "\n" +
            "import ru.vasire.annotation.CustomToString;\n" +
            "\n" +
            "@CustomToString\n" +
            "public class Person {\n" +
            "    private String firstName;\n" +
            "    private String lastName;\n" +
            "    private int age;\n" +
            "\n" +
            "    public Person() {\n" +
            "    }\n" +
            "\n" +
            "    public Person(String firstName, String lastName, int age) {\n" +
            "        this.firstName = firstName;\n" +
            "        this.lastName = lastName;\n" +
            "        this.age = age;\n" +
            "    }\n" +
            "\n" +
            "    public String getFirstName() {\n" +
            "        return firstName;\n" +
            "    }\n" +
            "\n" +
            "    public void setFirstName(String firstName) {\n" +
            "        this.firstName = firstName;\n" +
            "    }\n" +
            "\n" +
            "    public String getLastName() {\n" +
            "        return lastName;\n" +
            "    }\n" +
            "\n" +
            "    public void setLastName(String lastName) {\n" +
            "        this.lastName = lastName;\n" +
            "    }\n" +
            "\n" +
            "    public int getAge() {\n" +
            "        return age;\n" +
            "    }\n" +
            "\n" +
            "    public void setAge(int age) {\n" +
            "        this.age = age;\n" +
            "    }\n" +
            "}");


    JavaFileObject expectedCustomToStringSourcePerson = JavaFileObjects.forSourceString("ru.vasire.model.PersontoString",
            "package ru.vasire.model;\n" +
                    "    \n" +
                    "    import java.lang.String;\n" +
                    "    \n" +
                    "    class PersontoString {\n" +
                    "      private String firstName;\n" +
                    "    \n" +
                    "      private String lastName;\n" +
                    "    \n" +
                    "      private int age;\n" +
                    "    \n" +
                    "      public String toString() {\n" +
                    "        return \"PersontoString{\" +\n" +
                    "            \"firstName= \"+ firstName +\n" +
                    "            \", lastName= \"+ lastName +\n" +
                    "            \", age= \"+ age +\n" +
                    "            \"}\";\n" +
                    "      }\n" +
                    "    }");

    @Test
    public void compilesWithProcessor() {
        Compilation compilation = javac()
                .withProcessors(new CustomToStringProcessor())
                .compile(samplePerson);

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("ru.vasire.model.PersontoString")
                .hasSourceEquivalentTo(expectedCustomToStringSourcePerson);
    }
}
