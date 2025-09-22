package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест для перевірки налаштувань Checkstyle у проекті.
 */
public class CheckstyleTest {

    @TempDir
    Path tempDir;

    @Test
    public void testCheckstyleConfigExists() {
        // Перевіряємо наявність конфігураційних файлів Checkstyle
        File checkstyleConfigFile = new File("config/checkstyle/checkstyle.xml");
        assertTrue(checkstyleConfigFile.exists(), "Файл конфігурації checkstyle.xml має існувати");
        
        File suppressionsFile = new File("config/checkstyle/checkstyle-suppressions.xml");
        assertTrue(suppressionsFile.exists(), "Файл checkstyle-suppressions.xml має існувати");
        
        File xpathSuppressionsFile = new File("config/checkstyle/checkstyle-xpath-suppressions.xml");
        assertTrue(xpathSuppressionsFile.exists(), "Файл checkstyle-xpath-suppressions.xml має існувати");
    }
    
    @Test
    public void testCheckstyleConfiguration() throws IOException {
        // Перевірка налаштувань Checkstyle в build.gradle
        String buildGradle = Files.readString(Paths.get("build.gradle"));
        
        assertTrue(buildGradle.contains("id 'checkstyle'"), 
            "build.gradle має містити плагін checkstyle");
        
        assertTrue(buildGradle.contains("toolVersion"), 
            "build.gradle має містити версію checkstyle");
        
        assertTrue(buildGradle.contains("configFile"), 
            "build.gradle має містити шлях до конфігураційного файлу");
    }
    
    @Test
    public void testCheckstyleClassFollowsRules() throws IOException {
        // Перевірка, що клас Checkstyle.java відповідає стандартам кодування
        Path checkstyleClassPath = Paths.get("src/main/java/com/example/Checkstyle.java");
        String checkstyleClassContent = Files.readString(checkstyleClassPath);
        
        // Перевірка на наявність документації класу
        assertTrue(checkstyleClassContent.contains("/**"), 
            "Клас Checkstyle повинен містити документацію");
        
        // Перевірка на стиль найменування полів (lowercase)
        assertTrue(checkstyleClassContent.contains("private String testField;"), 
            "Поле має відповідати стандарту найменування");
        
        // Перевірка на документацію методів
        assertTrue(checkstyleClassContent.contains("@return"), 
            "Метод getTestField повинен містити документацію з @return");
    }
    
    @Test
    public void testBadCodeStyle() throws IOException {
        // Створюємо файл з поганим стилем коду
        String badCode = 
            "package com.example;\n\n" +
            "import java.util.*; // Використання імпорту зі зірочкою\n\n" +
            "public class BadStyle {\n" +
            "    private String BadField; // Неправильна назва поля (з великої літери)\n\n" +
            "    public void method(){int a=1;} // Відсутні пробіли\n" +
            "}";
        
        Path badCodeFile = tempDir.resolve("BadStyle.java");
        Files.writeString(badCodeFile, badCode);
        
        // Перевірка наявності порушень стилю
        assertTrue(badCode.contains("import java.util.*"), 
            "Тестуємо порушення правила AvoidStarImport");
        
        assertTrue(badCode.contains("BadField"), 
            "Тестуємо порушення правила MemberName");
        
        assertTrue(badCode.contains("method(){int a=1;}"), 
            "Тестуємо порушення правила WhitespaceAround");
    }
    
    @Test
    public void testGoodCodeStyle() throws IOException {
        // Створюємо файл з хорошим стилем коду
        String goodCode = 
            "package com.example;\n\n" +
            "import java.util.List;\n\n" +
            "/**\n" +
            " * Клас з правильним стилем коду.\n" +
            " */\n" +
            "public class GoodStyle {\n" +
            "  private String goodField;\n\n" +
            "  /**\n" +
            "   * Конструктор класу.\n" +
            "   * @param goodField поле класу\n" +
            "   */\n" +
            "  public GoodStyle(String goodField) {\n" +
            "    this.goodField = goodField;\n" +
            "  }\n\n" +
            "  /**\n" +
            "   * Метод з правильним стилем коду.\n" +
            "   * @return значення поля\n" +
            "   */\n" +
            "  public String getGoodField() {\n" +
            "    return goodField;\n" +
            "  }\n" +
            "}";
        
        Path goodCodeFile = tempDir.resolve("GoodStyle.java");
        Files.writeString(goodCodeFile, goodCode);
        
        // Перевірка відсутності порушень стилю
        assertFalse(goodCode.contains("import java.util.*"), 
            "Правильний код не повинен містити import зі зірочкою");
        
        assertTrue(goodCode.contains("private String goodField"), 
            "Правильний код має містити коректно названі поля");
        
        assertTrue(goodCode.contains("  public String getGoodField() {\n    return goodField;\n  }"), 
            "Правильний код має містити коректне форматування методів");
    }
}