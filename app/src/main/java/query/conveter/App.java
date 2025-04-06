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
    static String filePath = "/Users/pp01713/cursor-workspace/kosmos-demo/src/main/resources/static/miplatform/java/bra/";
    static String fileName = "bra04_r04_1";
    
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

            // 생성된 코드를 파일로 저장 (UTF-8 인코딩 사용)
            try (PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(new FileOutputStream(new File(directory, outputFileName)), "UTF-8"))) {
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
        // EUC-KR 인코딩으로 파일 읽기
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), "EUC-KR"))) {
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

        // 메소드를 찾기 위한 정규식 패턴 - 수정된 패턴
        Pattern methodPattern = Pattern.compile(
                "private\\s+SQLObject\\s+(SQL\\w+)\\s*\\([^)]*\\)\\s*(?:throws\\s+[\\w,\\s]+)?\\s*\\{",
                Pattern.DOTALL);

        Matcher matcher = methodPattern.matcher(sourceCode);
        List<String> methodNames = new ArrayList<>();

        while (matcher.find()) {
            String methodName = matcher.group(1);
            
            // 메소드 본문의 시작과 끝 인덱스 찾기
            int[] bodyRange = findMethodBodyRange(sourceCode, matcher.end() - 1);
            int bodyStart = bodyRange[0];
            int bodyEnd = bodyRange[1];

            System.out.println("methodName: " + methodName + " bodyStart: " + bodyStart + " bodyEnd: " + bodyEnd);
            
            if (bodyStart != -1 && bodyEnd != -1) {
                String methodBody = sourceCode.substring(bodyStart, bodyEnd);
                String[] methodLines = methodBody.split("\n");

                int startLine = -1;
                int endLine = -1;
                String ddl = "";

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

                    result.append("    public static String ").append(methodName).append("() {\n");

                    // startLine부터 endLine까지의 모든 코드 포함
                    for (int i = startLine; i <= endLine; i++) {
                        if(startLine == (i-1) ) {
                            ddl = methodLines[i].trim().substring(10, 16);
                            ddl = ddl.toLowerCase();
                        }

                        result.append(convertParams(methodLines[i])).append("\n");
                    }

                    // query 출력문 추가
                    result.append("        String xml =\"\";\n");
                    if(ddl.equals("select")) {
                        result.append("            xml += \"<" +ddl+" id=\\\"").append(methodName.trim()).append("\\\" parameterType=\\\"\\\" resultType=\\\"\\\">\\n\";\n");
                    } else {
                        result.append("            xml += \"<" +ddl+" id=\\\"").append(methodName.trim()).append("\\\" parameterType=\\\"\\\">\\n\";\n");
                    }
                    result.append("        xml += SqlFormatter.format(query, FormatConfig.builder().indent(\"\\t\").build());\n");
                    result.append("            xml += \"\\n</" + ddl + ">\\n\" ;\n\n");
                    result.append("        return xml;\n\n");
                    result.append("    }\n\n");
                }
            }
        }

        // main 메소드 추가
        result.append("    public static void main(String[] args) {\n");
        result.append("        String dir = \""+xmlLocation+"\";\n");
        result.append("        String top = \"<mapper namespace=\\\"kr.or.komca.center..mapper.Mapper\\\">\";\n");
        result.append("        String bottom = \"</mapper>\";\n\n");
        result.append("        String content = top + \"\\n\";    \n");
        for (String methodName : methodNames) {
            result.append("        content += ").append(methodName).append("() + \"\\n\";\n");
        }
        result.append("        content += bottom;\n\n");
        result.append("        try {\n");
        result.append("            String fileName = \"bra01_s01_1.xml\";\n");
        result.append("            FileWriter fw = new FileWriter(dir + \""+fileName+".xml\");\n");
        result.append("            fw.write(new String(content.getBytes(), \"UTF-8\"));\n");
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

    // 메소드 본문의 시작과 끝 인덱스를 찾는 함수
    private static int[] findMethodBodyRange(String sourceCode, int startIndex) {
        //System.out.println("startIndex: "+startIndex);
        int openBraceCount = 0;
        boolean foundFirstBrace = false;
        int bodyStart = -1;
        int bodyEnd = -1;
        
        for (int i = startIndex; i < sourceCode.length(); i++) {
            char c = sourceCode.charAt(i);
            //System.out.println("c: "+c);
            if (c == '{') {
                openBraceCount++;
                if (!foundFirstBrace) {
                    foundFirstBrace = true;
                    bodyStart = i + 1; // 첫 번째 '{' 다음부터 본문 시작
                }
            } else if (c == '}') {
                openBraceCount--;
                if (foundFirstBrace && openBraceCount == 0) {
                    bodyEnd = i;
                    break;
                }
            }
        }
        
        return new int[] {bodyStart, bodyEnd};
    }
}
