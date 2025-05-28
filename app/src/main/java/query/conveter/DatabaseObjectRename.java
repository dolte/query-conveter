package query.conveter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseObjectRename {
    static String filePath = "C:\\komca\\workspace\\komca-center-api\\src\\main\\resources";
    //static String filePath = "/Users/pp01713/cursor-workspace/komca-center-api/src/main/resources/mapper/";
    static String directory = "\\mapper\\upso\\command";
    static String fileName = "CenterStaffCommandMapper";
    static String tableNameListFile = filePath + "\\references\\db\\table-rename.txt";
    static String columnNameListFile = filePath + "\\references\\db\\column-rename.txt";
    static String paramNameListFile = filePath + "\\references\\db\\param-rename.txt";

    public static void main(String[] args) {
        try {
            // 원본 XML 파일 읽기
            String content = readFile(filePath + directory + File.separator + fileName + ".xml");
            
            // 테이블명 변경
            String tableModifiedContent = replaceTableName(content);
            
            // 컬럼명 변경
            String columnModifiedContent = replaceColumnName(tableModifiedContent);
            
            // 파라미터명 변경
            String paramModifiedContent = replaceParamName(columnModifiedContent);
            
            // 'GIBU.' 문자열 제거
            String schemaModifiedContent = replaceSchemaName(paramModifiedContent);

            // 'GIBU.' 문자열 제거
            String finalModifiedContent = replaceSpecialCharsWithCDATA(schemaModifiedContent);
            
            // 변경된 내용 저장
            writeFile(filePath + directory + File.separator + fileName + "-rename.xml", finalModifiedContent);
            System.out.println("File successfully processed and saved as " + fileName + "-rename.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        // UTF-8 인코딩으로 파일 읽기
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private static String replaceTableName(String content) throws IOException {
        // 테이블 이름 매핑 정보를 저장할 맵
        Map<String, String> tableNameMap = new HashMap<>();
        
        // tableNameListFile에서 테이블 이름 매핑 정보 읽기
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(tableNameListFile), "UTF-8"))) {
            String line;
            // 첫 줄은 헤더일 수 있으므로 건너뛸 수 있음
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                // 빈 줄 건너뛰기
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // 헤더 건너뛰기 (선택적)
                if (isFirstLine) {
                    isFirstLine = false;
                    if (line.contains("ASIS") || line.contains("TOBE")) {
                        continue; // 헤더 라인 건너뛰기
                    }
                }
                
                // 쉼표로 분리
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String oldTableName = parts[0].trim();
                    String newTableName = parts[1].trim();
                    tableNameMap.put(oldTableName, newTableName);
                }
            }
        }
        
        // 내용에서 테이블 이름 변경
        String modifiedContent = content;
        for (Map.Entry<String, String> entry : tableNameMap.entrySet()) {
            String oldTableName = entry.getKey();
            String newTableName = entry.getValue();
            // 테이블 이름 교체 (대소문자 구분)
            modifiedContent = modifiedContent.replace(oldTableName, newTableName);
        }
        
        return modifiedContent;
    }
    
    private static String replaceColumnName(String content) throws IOException {
        // 컬럼 이름 매핑 정보를 저장할 맵
        Map<String, String> columnNameMap = new HashMap<>();
        
        // columnNameListFile에서 컬럼 이름 매핑 정보 읽기
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(columnNameListFile), "UTF-8"))) {
            String line;
            // 첫 줄은 헤더일 수 있으므로 건너뛸 수 있음
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                // 빈 줄 건너뛰기
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // 헤더 건너뛰기 (선택적)
                if (isFirstLine) {
                    isFirstLine = false;
                    if (line.contains("AS-IS") || line.contains("TO-BE")) {
                        continue; // 헤더 라인 건너뛰기
                    }
                }
                
                // 쉼표로 분리
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String oldColumnName = parts[0].trim();
                    String newColumnName = parts[1].trim();
                    columnNameMap.put(oldColumnName, newColumnName);
                }
            }
        }
        
        // 내용에서 컬럼 이름 변경
        String modifiedContent = content;
        for (Map.Entry<String, String> entry : columnNameMap.entrySet()) {
            String oldColumnName = entry.getKey();
            String newColumnName = entry.getValue();
            
            // 컬럼 이름 교체 (대소문자 구분)
            // 0. 컬럼명이 단독으로 있는 경우 처리 (들여쓰기 후 컬럼명만 있는 경우)
            // 원래의 들여쓰기를 유지하기 위해 캡처 그룹을 사용
            modifiedContent = modifiedContent.replaceAll("(?m)(^\\s+)" + oldColumnName + "$", "$1" + newColumnName);
            
            // 1. 정확한 컬럼명만 변경하기 위한 패턴 지정 - 공백, 콤마, 괄호, 등 주변에 있는 경우만 교체
            modifiedContent = modifiedContent.replace(" " + oldColumnName + " ", " " + newColumnName + " ");
            modifiedContent = modifiedContent.replace(" " + oldColumnName + ",", " " + newColumnName + ",");
            modifiedContent = modifiedContent.replace(" " + oldColumnName + ")", " " + newColumnName + ")");
            modifiedContent = modifiedContent.replace(" " + oldColumnName + "\n", " " + newColumnName + "\n");
            modifiedContent = modifiedContent.replace("." + oldColumnName + ",", "." + newColumnName + ",");
            modifiedContent = modifiedContent.replace("." + oldColumnName + "\n", "." + newColumnName + "\n");
            modifiedContent = modifiedContent.replace("," + oldColumnName + " ", "," + newColumnName + " ");
            modifiedContent = modifiedContent.replace("," + oldColumnName + ",", "," + newColumnName + ",");
            modifiedContent = modifiedContent.replace("," + oldColumnName + ")", "," + newColumnName + ")");
            modifiedContent = modifiedContent.replace("(" + oldColumnName + " ", "(" + newColumnName + " ");
            modifiedContent = modifiedContent.replace("(" + oldColumnName + ",", "(" + newColumnName + ",");
            modifiedContent = modifiedContent.replace("(" + oldColumnName + ")", "(" + newColumnName + ")");
            
            // 2. 줄 시작 부분에 있는 경우
            modifiedContent = modifiedContent.replace("\n" + oldColumnName + "\n", "\n" + newColumnName + "\n");
            modifiedContent = modifiedContent.replace("\n" + oldColumnName + " ", "\n" + newColumnName + " ");
            modifiedContent = modifiedContent.replace("\n" + oldColumnName + ",", "\n" + newColumnName + ",");
            modifiedContent = modifiedContent.replace("\n" + oldColumnName + ")", "\n" + newColumnName + ")");
            
            // 3. 별칭으로 사용될 경우 (AS 뒤에 오는 경우)
            modifiedContent = modifiedContent.replace(" AS " + oldColumnName, " AS " + newColumnName);
            modifiedContent = modifiedContent.replace(" as " + oldColumnName, " as " + newColumnName);
            
            // 4. XML 요소로 사용될 경우 (특히 select, update, insert 문에서)
            modifiedContent = modifiedContent.replace("\t" + oldColumnName + ",", "\t" + newColumnName + ",");
            modifiedContent = modifiedContent.replace("\t" + oldColumnName + "\n", "\t" + newColumnName + "\n");
            modifiedContent = modifiedContent.replace("\t" + oldColumnName + " ", "\t" + newColumnName + " ");
            modifiedContent = modifiedContent.replace("\t\t" + oldColumnName + ",", "\t\t" + newColumnName + ",");
            modifiedContent = modifiedContent.replace("\t\t" + oldColumnName + "\n", "\t\t" + newColumnName + "\n");
            modifiedContent = modifiedContent.replace("\t\t" + oldColumnName + " ", "\t\t" + newColumnName + " ");
            modifiedContent = modifiedContent.replace("\t\t\t" + oldColumnName + ",", "\t\t\t" + newColumnName + ",");
            modifiedContent = modifiedContent.replace("\t\t\t" + oldColumnName + "\n", "\t\t\t" + newColumnName + "\n");
            modifiedContent = modifiedContent.replace("\t\t\t" + oldColumnName + " ", "\t\t\t" + newColumnName + " ");
            
            // 5. 들여쓰기가 있는 경우
            modifiedContent = modifiedContent.replace("    " + oldColumnName + ",", "    " + newColumnName + ",");
            modifiedContent = modifiedContent.replace("    " + oldColumnName + "\n", "    " + newColumnName + "\n");
            modifiedContent = modifiedContent.replace("    " + oldColumnName + " ", "    " + newColumnName + " ");
            modifiedContent = modifiedContent.replace("        " + oldColumnName + ",", "        " + newColumnName + ",");
            modifiedContent = modifiedContent.replace("        " + oldColumnName + "\n", "        " + newColumnName + "\n");
            modifiedContent = modifiedContent.replace("        " + oldColumnName + " ", "        " + newColumnName + " ");
            
            // 6. Oracle 외부 조인 구문(+)이 사용된 경우
            modifiedContent = modifiedContent.replace(" " + oldColumnName + "(+)", " " + newColumnName + "(+)");
            modifiedContent = modifiedContent.replace("," + oldColumnName + "(+)", "," + newColumnName + "(+)");
            modifiedContent = modifiedContent.replace("." + oldColumnName + "(+)", "." + newColumnName + "(+)");
            modifiedContent = modifiedContent.replace("\n" + oldColumnName + "(+)", "\n" + newColumnName + "(+)");
            
            // 7. 테이블 별칭이 있는 경우의 컬럼 처리 (XC.STAFF_NUM(+) 같은 패턴)
            // 모든 가능한 테이블 별칭(A-Z)에 대해 패턴 적용
            for (char c = 'A'; c <= 'Z'; c++) {
                modifiedContent = modifiedContent.replace(c + "." + oldColumnName + " ", c + "." + newColumnName + " ");
                modifiedContent = modifiedContent.replace(c + "." + oldColumnName + ",", c + "." + newColumnName + ",");
                modifiedContent = modifiedContent.replace(c + "." + oldColumnName + ")", c + "." + newColumnName + ")");
                modifiedContent = modifiedContent.replace(c + "." + oldColumnName + "\n", c + "." + newColumnName + "\n");
                
                // 2글자 별칭 처리 (XC, TB, etc.)
                for (char c2 = 'A'; c2 <= 'Z'; c2++) {
                    String alias = String.valueOf(c) + String.valueOf(c2);
                    modifiedContent = modifiedContent.replace(alias + "." + oldColumnName + " ", alias + "." + newColumnName + " ");
                    modifiedContent = modifiedContent.replace(alias + "." + oldColumnName + ",", alias + "." + newColumnName + ",");
                    modifiedContent = modifiedContent.replace(alias + "." + oldColumnName + ")", alias + "." + newColumnName + ")");
                    modifiedContent = modifiedContent.replace(alias + "." + oldColumnName + "\n", alias + "." + newColumnName + "\n");
                    
                    // 외부 조인(+)이 있는 경우
                    modifiedContent = modifiedContent.replace(alias + "." + oldColumnName + "(+)", alias + "." + newColumnName + "(+)");
                }
                
                // 외부 조인(+)이 있는 경우
                modifiedContent = modifiedContent.replace(c + "." + oldColumnName + "(+)", c + "." + newColumnName + "(+)");
            }
            
            // 8. 테이블 별칭이 소문자인 경우 (a.column_name)
            for (char c = 'a'; c <= 'z'; c++) {
                modifiedContent = modifiedContent.replace(c + "." + oldColumnName + " ", c + "." + newColumnName + " ");
                modifiedContent = modifiedContent.replace(c + "." + oldColumnName + ",", c + "." + newColumnName + ",");
                modifiedContent = modifiedContent.replace(c + "." + oldColumnName + ")", c + "." + newColumnName + ")");
                modifiedContent = modifiedContent.replace(c + "." + oldColumnName + "\n", c + "." + newColumnName + "\n");
                modifiedContent = modifiedContent.replace(c + "." + oldColumnName + "(+)", c + "." + newColumnName + "(+)");
            }
        }
        
        return modifiedContent;
    }
    
    private static String replaceParamName(String content) throws IOException {
        // 파라미터 이름 매핑 정보를 저장할 맵
        Map<String, String> paramNameMap = new HashMap<>();
        
        // paramNameListFile에서 파라미터 이름 매핑 정보 읽기
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(paramNameListFile), "UTF-8"))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                // 빈 줄 건너뛰기
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // 쉼표로 분리
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String oldParamName = parts[0].trim();
                    String newParamName = parts[1].trim();
                    paramNameMap.put(oldParamName, newParamName);
                }
            }
        }
        
        // 내용에서 파라미터 이름 변경 (단순 문자열 치환)
        String modifiedContent = content;
        for (Map.Entry<String, String> entry : paramNameMap.entrySet()) {
            String oldParamName = entry.getKey();
            String newParamName = entry.getValue();
            
            // 단순 문자열 치환 - 앞뒤 공백이나 패턴 고려하지 않음
            modifiedContent = modifiedContent.replace(oldParamName, newParamName);
        }
        
        return modifiedContent;
    }

    /**
     * XML 특수 문자를 CDATA 섹션으로 변환하는 메소드
     * 
     * @param content 처리할 내용
     * @return 특수 문자가 CDATA 섹션으로 변환된 내용
     */
    private static String replaceSpecialCharsWithCDATA(String content) {
        // 태그와 CDATA 섹션을 기준으로 콘텐츠를 분할하여 처리
        StringBuilder result = new StringBuilder();
        
        // 태그 또는 CDATA 내부 여부를 추적
        boolean insideTag = false;
        boolean insideCdata = false;
        StringBuilder currentToken = new StringBuilder();
        
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            currentToken.append(c);
            
            // CDATA 시작 감지
            if (currentToken.toString().endsWith("<![CDATA[")) {
                insideCdata = true;
                result.append(currentToken);
                currentToken = new StringBuilder();
                continue;
            }
            
            // CDATA 종료 감지
            if (insideCdata && currentToken.toString().endsWith("]]>")) {
                insideCdata = false;
                result.append(currentToken);
                currentToken = new StringBuilder();
                continue;
            }
            
            // 태그 시작 감지
            if (!insideCdata && c == '<' && !currentToken.toString().endsWith("&lt;")) {
                if (currentToken.length() > 1) {
                    // 태그 시작 전에 모인 내용 처리
                    String processed = processSpecialChars(currentToken.substring(0, currentToken.length() - 1));
                    result.append(processed);
                    currentToken = new StringBuilder();
                    currentToken.append(c);
                }
                insideTag = true;
                continue;
            }
            
            // 태그 종료 감지
            if (!insideCdata && insideTag && c == '>') {
                insideTag = false;
                result.append(currentToken);
                currentToken = new StringBuilder();
                continue;
            }
            
            // 태그나 CDATA 내부가 아닐 때, 문자 처리의 효율성을 위해 일정 길이에 도달하면 처리
            if (!insideTag && !insideCdata && currentToken.length() > 20) {
                String processed = processSpecialChars(currentToken.toString());
                result.append(processed);
                currentToken = new StringBuilder();
            }
        }
        
        // 남은 내용 처리
        if (currentToken.length() > 0) {
            if (!insideTag && !insideCdata) {
                String processed = processSpecialChars(currentToken.toString());
                result.append(processed);
            } else {
                result.append(currentToken);
            }
        }
        
        return result.toString();
    }

    /**
     * 특수 문자를 CDATA 섹션으로 변환하는 보조 메소드
     */
    private static String processSpecialChars(String text) {
        // 복합 특수 문자 처리
        String processed = text;
        processed = processed.replace("&lt;=", "<![CDATA[ <= ]]>");
        processed = processed.replace("&gt;=", "<![CDATA[ >= ]]>");
        processed = processed.replace("&lt;&gt;", "<![CDATA[ <> ]]>");
        
        // 단일 특수 문자 처리 (SQL 연산자로 사용되는 경우)
        processed = processed.replace(" &lt; ", " <![CDATA[ < ]]> ");
        processed = processed.replace(" &gt; ", " <![CDATA[ > ]]> ");
        processed = processed.replace("(&lt;", "(<![CDATA[ < ]]>");
        processed = processed.replace("&lt;)", "<![CDATA[ < ]]>)");
        processed = processed.replace(",&lt;", ",<![CDATA[ < ]]>");
        processed = processed.replace("&lt;,", "<![CDATA[ < ]]>,");
        
        // 숫자 주변의 특수 문자 처리
        for (int i = 0; i <= 9; i++) {
            processed = processed.replace(i + "&lt;", i + "<![CDATA[ < ]]>");
            processed = processed.replace("&lt;" + i, "<![CDATA[ < ]]>" + i);
            processed = processed.replace(i + "&gt;", i + "<![CDATA[ > ]]>");
            processed = processed.replace("&gt;" + i, "<![CDATA[ > ]]>" + i);
        }
        
        return processed;
    }
    
    /**
     * 'GIBU.' 문자열을 제거하는 메소드
     * 
     * @param content 처리할 내용
     * @return 'GIBU.' 문자열이 제거된 내용
     */
    private static String replaceSchemaName(String content) {
        // 'GIBU.' 문자열을 찾아 빈 문자열로 대체
        String modifiedContent = content.replace("GIBU.", "");
        
        // 대소문자 구분 없이 검색하려면 필요할 경우 아래와 같이 사용
        // modifiedContent = modifiedContent.replaceAll("(?i)GIBU\\.", "");
        
        return modifiedContent;
    }
    
    private static void writeFile(String filePath, String content) throws IOException {
        // UTF-8 인코딩으로 파일 쓰기
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"))) {
            writer.write(content);
        }
    }
}
