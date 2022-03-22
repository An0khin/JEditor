package com.home;

import com.home.JEditorForm;
import java.awt.*;
import java.util.*;

public class JChecker {
	public static void find(JEditorForm jef) {
		jef.getHighlighter().removeAllHighlights();
		String[] keywordsAr = {"abstract", "continue", "for", "new", "switch", "assert", "default", "goto", "package", "synchronized",
		"boolean", "do", "if", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import",
		"public", "throws", "case", "enum", "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char",
		"final", "interface", "static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super",
		"while"};
		java.util.List<String> keywords = Arrays.asList(keywordsAr);
		ArrayList<Character> brackets = new ArrayList<>(Arrays.asList('(', ')', '{', '}', '[', ']', '<', '>'));
		int index;
		String word;
		String code = jef.getCode();
		for(String key : keywords) {
			//System.out.println("Key: " + key);
			index = -1;
			do {
				//System.out.println("Begin: " + index);
				index = code.indexOf(key, index+1);
				//System.out.println(index);

				// if(index != -1) {
				// 	word = code.substring(index == 0 ? index : index - 1, index + key.length() + 1).trim();
				// 	System.out.println("WORD: " + code.substring(index == 0 ? index : index - 1, index + key.length() + 1));
				// 	System.out.println("BEGIN: " + code.charAt(index == 0 ? index : index - 1));
				// 	System.out.println("END: " + code.charAt(index + key.length()));
				// 	System.out.println("WORDb: " + word.charAt(0));
				// 	System.out.println("WORDe: " + word.charAt(word.length()-1));
					
				// 	System.out.println(word);
				// }
				
				if(index != -1) {
					word = code.substring(index == 0 ? index : index - 1, index + key.length() + (index+key.length() >= code.length() ? 0 : 1)).trim();
					if(brackets.contains(word.charAt(0)))
						word = word.substring(1, word.length());
					if(brackets.contains(word.charAt(word.length() - 1)))
						word = word.substring(0, word.length() - 1);

					if(keywords.contains(word))
						//(keywords.contains(word.substring(0, key.length() - 2)) && brackets.contains(Character.toString(word.charAt(key.length()))))) {
						jef.addHighlight(index++, index + key.length() - 1, Color.RED);	
				}
					
			} while(index != -1);			
		}
	}
}