/* LanguageTool, a natural language style checker 
 * Copyright (C) 2007 Daniel Naber (http://www.danielnaber.de)
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

package org.languagetool.tagging.disambiguation.rules.de;

import org.jetbrains.annotations.Nullable;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.tagging.disambiguation.AbstractDisambiguator;
import org.languagetool.tagging.disambiguation.Disambiguator;
import org.languagetool.tagging.disambiguation.MultiWordChunker;
import org.languagetool.tagging.disambiguation.rules.XmlRuleDisambiguator;

import java.io.IOException;

public class GermanRuleDisambiguator extends AbstractDisambiguator {
  
  private final Disambiguator disambiguator;

  private final MultiWordChunker multitokenSpeller = MultiWordChunker.getInstance(
    "/de/multitoken-ignore.txt", true, true, false, MultiWordChunker.tagForNotAddingTags);

  private final MultiWordChunker multitokenSpeller2 = MultiWordChunker.getInstance(
    "/de/multitoken-suggest.txt", true, true, false, MultiWordChunker.tagForNotAddingTags);

  private final MultiWordChunker multitokenSpeller3 = MultiWordChunker.getInstance(
    "/spelling_global.txt", false, true, false, MultiWordChunker.tagForNotAddingTags);

  public GermanRuleDisambiguator(Language lang) {
    disambiguator = new XmlRuleDisambiguator(lang, true);
    //TODO: merge in one disambiguator:
    multitokenSpeller.setIgnoreSpelling(true);
    multitokenSpeller2.setIgnoreSpelling(true);
    multitokenSpeller3.setIgnoreSpelling(true);
  }
  @Override
  public final AnalyzedSentence disambiguate(AnalyzedSentence input)
      throws IOException {
    return disambiguator.disambiguate(input);
  }

  @Override
  public AnalyzedSentence disambiguate(AnalyzedSentence input, @Nullable JLanguageTool.CheckCancelledCallback checkCanceled) throws IOException {
    return disambiguator.disambiguate(multitokenSpeller2.disambiguate(multitokenSpeller3.disambiguate(
      multitokenSpeller.disambiguate(input, checkCanceled), checkCanceled), checkCanceled), checkCanceled
    );
  }
}
