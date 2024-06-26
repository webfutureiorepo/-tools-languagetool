/* LanguageTool, a natural language style checker
 * Copyright (C) 2019 Daniel Naber (http://www.danielnaber.de)
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
package org.languagetool.rules.sv;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.LanguageSpecificTest;
import org.languagetool.language.Swedish;

import java.io.IOException;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class SwedishTest extends LanguageSpecificTest {
  
  @Test
  public void testLanguage() throws IOException {
    runTests(new Swedish());
  }

  @Test
  public void testSpellingAndColon() throws IOException {
    JLanguageTool lt = new JLanguageTool(new Swedish());
    assertThat(lt.check("Arbeta med var:").size(), is(0));
  }
  
  @Test
  public void testWeekdayAndMonthNames() throws IOException {
    JLanguageTool lt = new JLanguageTool(new Swedish());
    assertThat(lt.check("På måndag är alla lediga.").size(), is(0));
    assertThat(lt.check("På Måndag är alla lediga.").size(), is(1));
    assertThat(lt.check("Onsdag är lillördag på många håll.").size(), is(0));
    assertThat(lt.check("I oktober kommer ofta den första snön.").size(), is(0));
    assertThat(lt.check("I Oktober kommer ofta den första snön.").size(), is(1));
    assertThat(lt.check("Septembers färger är sköna.").size(), is(0));
  }

}
