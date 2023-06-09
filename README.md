# **Автоматическое логирование.**

Класс анотаций `ru.vasire.annotation.Log`
Прикладной класс для проверки `ru.vasire.model.Person`

Разработано два варианта внедрения

## Первый вариант реализации на основе Proxy

Тестовый класс: `ru.vasire.ProxyDemo`

## Второй вариант реализации на ASM

Для сборки используется Task в Gradle **loggerDemoJar** 

Для проверки запускать 

`java -javaagent:loggerDemo.jar -jar loggerDemo.jar`

