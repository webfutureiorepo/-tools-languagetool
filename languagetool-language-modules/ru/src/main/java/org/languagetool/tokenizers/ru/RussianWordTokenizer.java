/* LanguageTool, a natural language style checker
 * Copyright (C) 2023 Yakov Reztsov
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package org.languagetool.tokenizers.ru;

import org.languagetool.tokenizers.WordTokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @since 6.1
 */
public class RussianWordTokenizer extends WordTokenizer {

  @Override
  public String getTokenizingCharacters() {
    return super.getTokenizingCharacters() + "'.";
  }
  
  @Override
  public List<String> tokenize(String text) {
    List<String> l = new ArrayList<>();
    String auxText = text;
    auxText = auxText.replace("б/у", "\u0001\u0001SOCR_BU\u0001\u0001")
      .replace("б/н", "\u0001\u0001SOCR_BN\u0001\u0001")
      .replace(" .. ", "\u0001\u0001SP_DDOT_SP\u0001\u0001")
      .replace(" . ", "\u0001\u0001SP_DOT_SP\u0001\u0001")
      .replace(" .", " \u0001\u0001SP_DOT\u0001\u0001")
      .replace("\u0001\u0001SP_DDOT_SP\u0001\u0001", " .. ")
      .replace("\u0001\u0001SP_DOT_SP\u0001\u0001", " . ");

    StringTokenizer st = new StringTokenizer(auxText, getTokenizingCharacters() , true);
    while (st.hasMoreElements()) {
      String s = st.nextToken();
      s = s.replace("\u0001\u0001SOCR_BU\u0001\u0001", "б/у")
        .replace("\u0001\u0001SOCR_BN\u0001\u0001", "б/н")
        .replace("\u0001\u0001SP_DOT\u0001\u0001", ".");
      l.addAll(wordsToAdd(s));
    }
    return joinEMailsAndUrls(l);
  }

  private List<String> wordsToAdd(String s) {
    List<String> l = new ArrayList<>();
    l.add(s);
    return l;
  }

}
