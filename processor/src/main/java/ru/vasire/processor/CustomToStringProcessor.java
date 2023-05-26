package ru.vasire.processor;

import com.google.auto.service.AutoService;
import ru.vasire.annotation.CustomToString;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("ru.vasire.annotation.CustomToString")
@SupportedSourceVersion(SourceVersion.RELEASE_19)
@AutoService(Processor.class)
public class CustomToStringProcessor  extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) return false;



        // Получаем мапу с классом (ключ) и списком полей класса (value)
        Map<TypeElement, List<VariableElement>> collect = roundEnv.getElementsAnnotatedWith(CustomToString.class)
                .stream()
                .filter(element -> element.getKind() == ElementKind.CLASS)
                .filter(element-> element.getEnclosedElements().stream().noneMatch(
                        enclosed -> enclosed.getKind().equals(ElementKind.METHOD) &&
                                enclosed.getSimpleName().toString().equals("toString")))
                .collect(Collectors.toMap(
                        element -> (TypeElement) element,
                        element -> element.getEnclosedElements().stream()
                                .filter(enclosed -> enclosed.getKind().equals(ElementKind.FIELD)&&
                                        !enclosed.getModifiers().contains(Modifier.STATIC))
                                .map(enclosed -> (VariableElement) enclosed)
                                .collect(Collectors.toList())
                ));
        // Добавляем метод в классы с анотацией и отсутствием метода "toString"
        for (Map.Entry<TypeElement, List<VariableElement>> entry : collect.entrySet()) {
            TypeElement typeElement = entry.getKey();
            List<VariableElement> paramsList = entry.getValue();


            String sourceJavaFileContent;
            try {
                JavaFileObject jfo = elementUtils.getFileObjectOf(typeElement);
                sourceJavaFileContent = jfo.getCharContent(false).toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            messager.printMessage(Diagnostic.Kind.NOTE, typeElement.getSimpleName()+":"+ Arrays.toString(paramsList.toArray()));
            try {
                BuilderClassTemplate.TemplateBuilder
                        .aBuilderClassTemplate()
                        // пакет
                        .withTargetPackage(elementUtils.getPackageOf(typeElement).getQualifiedName().toString())
                        // клсаа для изменнеия
                        .withTargetClass(typeElement)
                        // список полей для включения в toString
                        .withToStringFields(paramsList)
                        // Содержимое исходного файла класса
                        .withSourceJavaFileContent(sourceJavaFileContent)
                        .build()
                        .writeToFiler(filer);
            } catch (Exception e) {
                messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
        }

        // если не нашли класса с анотацией или "toString" метод уже есть, то завершаем обработку
        return true;
    }
}
