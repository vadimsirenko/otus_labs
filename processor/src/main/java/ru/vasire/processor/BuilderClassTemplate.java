package ru.vasire.processor;

import com.squareup.javapoet.*;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ExecutableType;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class BuilderClassTemplate {

    private TypeElement targetClass;
    private List<VariableElement> toStringFields;
    private String targetPackage;
    private String sourceJavaFileContent;

    private BuilderClassTemplate() {
    }


    public void writeToFiler(Filer filer) throws IOException {
        TypeSpec typeSpec = TypeSpec
                .classBuilder( targetClass.getSimpleName()+"toString")
                .addFields(toStringFields.stream().map(field-> FieldSpec.builder(TypeName.get(field.asType()), String.valueOf(field), Modifier.PRIVATE).build()).toList())
                .addMethod(toStrintMethod())
                .build();
        JavaFile javaFile = JavaFile.builder(targetPackage, typeSpec).build();
        javaFile.writeTo(filer);
    }

    private MethodSpec toStrintMethod() {

        StringBuilder sb = new StringBuilder();
        sb.append("return \"").append(targetClass.getSimpleName()).append("toString")
                .append("{\" +");
        for (int i = 0 ; toStringFields.size() > i; i++ ) {
            VariableElement field = toStringFields.get(i);
            if(i>0){
                sb.append("\n\", ");
            }
            else{
                sb.append("\n\"");
            }
            sb.append(field.getSimpleName()).append("= \"+ ").append(field.getSimpleName()).append(" +");
        }
        sb.append("\n\"}\"" );
        return MethodSpec.methodBuilder("toString")
                .returns(ClassName.get(String.class))
                .addModifiers(Modifier.PUBLIC)
                .addStatement(sb.toString())
                .build();
    }


    public static final class TemplateBuilder {
        private TypeElement targetClass;
        private List<VariableElement> toStringFields;
        private String targetPackage;
        private String sourceJavaFileContent;

        private TemplateBuilder() {
        }

        public static TemplateBuilder aBuilderClassTemplate() {
            return new TemplateBuilder();
        }

        public TemplateBuilder withTargetClass(TypeElement targetClass) {
            this.targetClass = targetClass;
            return this;
        }

        public TemplateBuilder withToStringFields(List<VariableElement> toStringFields) {
            this.toStringFields = toStringFields;
            return this;
        }
        public TemplateBuilder withTargetPackage(String targetPackage) {
            this.targetPackage = targetPackage;
            return this;
        }

        public BuilderClassTemplate build() {

            BuilderClassTemplate builderClassTemplate = new BuilderClassTemplate();

            if (targetClass == null) throw new IllegalStateException("Target class could not be null");
            builderClassTemplate.targetClass = targetClass;
            if (targetPackage == null) throw new IllegalStateException("Target package could not be null");
            builderClassTemplate.targetPackage = targetPackage;

            if (sourceJavaFileContent.isEmpty()) throw new IllegalStateException("Source java file content package could not be empty");
            builderClassTemplate.sourceJavaFileContent = sourceJavaFileContent;


            if (toStringFields == null) throw new IllegalStateException("Setter methods could not be null");
            builderClassTemplate.toStringFields = toStringFields;
            return builderClassTemplate;
        }

        public TemplateBuilder withSourceJavaFileContent(String sourceJavaFileContent) {
            this.sourceJavaFileContent = sourceJavaFileContent;
            return this;
        }
    }
}