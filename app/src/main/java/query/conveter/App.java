package query.conveter;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class App {
    //static String createLocation = "D:\\kosmos\\query-conveter\\app\\src\\main\\java\\query\\conveter\\";
    //static String xmlLocation = "D:\\\\kosmos\\\\query-conveter\\\\app\\\\src\\\\main\\\\resources\\\\";
    //static String filePath = "D:\\kosmos\\spring-example\\demo\\src\\main\\resources\\static\\miplatform\\java\\bra\\";

    static String createLocation = "/Users/pp01713/cursor-workspace/query-conveter/app/src/main/java/query/conveter/";
    static String xmlLocation = "/Users/pp01713/cursor-workspace/query-conveter/app/src/main/resources/";
    static String filePath = "/Users/pp01713/cursor-workspace/kosomos-demo/src/main/resources/static/miplatform/java/bra/";
    static String fileName = "bra01_s01_1";
    
        public static void main(String[] args) {
    
            try {
            String content = readFile(filePath+fileName+".java");
            String modifiedCode = processJavaCode(content);

            // 임시 파일에 코드 저장
            String className = extractClassName(content);
            String outputFileName = className + "_Generated.java";

            System.out.println("Generated code has been saved to: " + outputFileName);

            File directory = new File(createLocation);

            System.out.println("createLocation: " + createLocation);

            // 생성된 코드를 파일로 저장
            try (PrintWriter writer = new PrintWriter(new File(directory, outputFileName))) {
                writer.println(modifiedCode);
            }

            System.out.println("Compile and run the generated code to test all SQL queries.");

        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }

    private static String extractClassName(String sourceCode) {
        Pattern pattern = Pattern.compile("public\\s+class\\s+(\\w+)");
        Matcher matcher = pattern.matcher(sourceCode);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "GeneratedClass";
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private static String processJavaCode(String sourceCode) {
        StringBuilder result = new StringBuilder();
        String className = extractClassName(sourceCode);

        // 클래스 시작 부분 추가
        result.append("package query.conveter;\n\n");
        result.append("import java.io.FileWriter;\n");
        result.append("import java.io.IOException;\n\n");
        result.append("import com.github.vertical_blank.sqlformatter.core.FormatConfig;\n");
        result.append("import com.github.vertical_blank.sqlformatter.SqlFormatter;\n\n");
        result.append("public class ").append(className).append("_Generated {\n\n");

        // 메소드를 찾기 위한 정규식 패턴
        Pattern methodPattern = Pattern.compile(
                "private\\s+SQLObject\\s+SQL([^{]*?)\\{([^}]*)}",
                Pattern.DOTALL);

        Matcher matcher = methodPattern.matcher(sourceCode);
        List<String> methodNames = new ArrayList<>();

        while (matcher.find()) {
            String methodSignature = matcher.group(1).trim();
            String methodBody = matcher.group(2);

            // 메소드 이름만 추출 (파라미터 제거)
            String methodName;
            if (methodSignature.contains("(")) {
                methodName = methodSignature.substring(0, methodSignature.indexOf("(")).trim();
            } else {
                methodName = methodSignature.trim();
            }

            String[] methodLines = methodBody.split("\n");

            int startLine = -1;
            int endLine = -1;

            // String query=""; 라인과 마지막 query += 라인 찾기
            for (int i = 0; i < methodLines.length; i++) {
                String line = methodLines[i].trim();
                if (line.contains("String query=\"\";") || line.contains("String query = \"\";")
                        || line.contains("String    query=\"\";")) {
                    startLine = i;
                }
                if (line.contains("query +=")) {
                    endLine = i;
                }
            }

            if (startLine != -1 && endLine != -1) {
                methodNames.add(methodName);

                result.append("    public static String SQL").append(methodName).append("() {\n");

                // startLine부터 endLine까지의 모든 코드 포함
                for (int i = startLine; i <= endLine; i++) {
                    result.append(convertParams(methodLines[i])).append("\n");
                }

                // query 출력문 추가
                result.append("        String xml =\"\";\n");
                result.append("        xml += \"<select id=\\\"SQL").append(methodName.trim()).append("\\\" parameterType=\\\"\\\" resultType=\\\"\\\">\\n\";\n");
                result.append("        xml += SqlFormatter.format(query, FormatConfig.builder().indent(\"\\t\").build());\n");
                result.append("        xml += \"\\n</select>\\n\" ;\n\n");
                result.append("        return xml;\n\n");
                result.append("    }\n\n");
            }
        }

        // main 메소드 추가
        result.append("    public static void main(String[] args) {\n");
        result.append("        String dir = \""+xmlLocation+"\";\n");
        result.append("        String top = \"<mapper namespace=\\\"kr.or.komca.center..mapper.Mapper\\\">\";\n");
        result.append("        String bottom = \"</mapper>\";\n\n");
        result.append("        String content = top + \"\\n\";    \n");
        for (String methodName : methodNames) {
            result.append("        content += SQL").append(methodName).append("() + \"\\n\";\n");
        }
        result.append("        content += bottom;\n\n");
        result.append("        try {\n");
        result.append("            String fileName = \"bra01_s01_1.xml\";\n");
        result.append("            FileWriter fw = new FileWriter(dir + \""+fileName+".xml\");\n");
        result.append("            fw.write(content);\n");
        result.append("            fw.close();\n");
        result.append("        } catch (IOException e) {\n");
        result.append("            e.printStackTrace();\n");
        result.append("        }\n");
        result.append("    }\n");

        // 클래스 종료
        result.append("}\n");

        return result.toString();
    }

    public static String convertParams(String input) {
        // 정규 표현식 패턴: ":"로 시작하고, "_"가 포함된 단어 찾기 
        Pattern pattern = Pattern.compile(":(\\w+)");
        Matcher matcher = pattern.matcher(input);
        
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String original = matcher.group(1); // CHG_NUM, CHG_DAY 등
            String converted = "#{" + toCamelCase(original) + "}";
            matcher.appendReplacement(result, converted);
        }
        matcher.appendTail(result);
        
        return result.toString();
    }
    
    private static String toCamelCase(String input) {
        String[] parts = input.split("_");
        StringBuilder camelCaseString = new StringBuilder(parts[0].toLowerCase());
        for (int i = 1; i < parts.length; i++) {
            camelCaseString.append(Character.toUpperCase(parts[i].charAt(0)))
                           .append(parts[i].substring(1).toLowerCase());
        }
        return camelCaseString.toString();
    }
}
