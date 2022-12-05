package me.ftsos.utils;

import java.util.Map;

public class PlaceholderUtils {
    public static String replacePlaceholders(Map<String, String> keysAndPlaceholdersMap, String toReplace) {
        String copyToReplaceString = toReplace;
        String replacedString = "";
        for(Map.Entry<String, String> entry : keysAndPlaceholdersMap.entrySet()) {
            if(!copyToReplaceString.contains(entry.getKey())) {
                replacedString = replacedString.equals("") ? copyToReplaceString : replacedString;
                continue;
            }
            if(replacedString.equals("")) {
                replacedString = copyToReplaceString.replace(entry.getKey(), entry.getValue());
                continue;
            }
            replacedString = replacedString.replace(entry.getKey(), entry.getValue());

        }
        return replacedString;
    }
}
