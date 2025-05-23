package org.languagetool.rules.ca;

import java.util.List;
import java.util.Map;

import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.Language;
import org.languagetool.language.Catalan;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.patterns.RuleFilter;
import org.languagetool.synthesis.ca.CatalanSynthesizer;

public class CatalanNumberSpellerFilter extends RuleFilter {
  
  private final Language language = Catalan.getInstance();
  private final CatalanSynthesizer synth = (CatalanSynthesizer) language.getSynthesizer();

  @Override
  public RuleMatch acceptRuleMatch(RuleMatch match, Map<String, String> arguments, int patternTokenPos,
                                   AnalyzedTokenReadings[] patternTokens, List<Integer> tokenPositions) {

    String strToSpell = getRequired("number_to_spell", arguments).replace(".", "");
    if (getRequired("gender", arguments).contentEquals("feminine")) {
      strToSpell = "feminine " + strToSpell;
    }
    String spelledNumber = synth.getSpelledNumber(strToSpell);
    if (!spelledNumber.isEmpty() && spelledNumber.replace("-i-", " ").replace("-", " ").split(" ").length < 4) {
      String message = match.getMessage();
      RuleMatch ruleMatch = new RuleMatch(match.getRule(), match.getSentence(), match.getFromPos(), match.getToPos(),
          message, match.getShortMessage());
      ruleMatch.setType(match.getType());
      ruleMatch.setSuggestedReplacement(spelledNumber);
      return ruleMatch;
    } else {
      return null;
    }

  }

}
