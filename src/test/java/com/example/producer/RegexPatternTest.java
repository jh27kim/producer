package com.example.producer;

import com.vdurmont.emoji.EmojiParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatternTest {

    @Test
    public void regexTest() {
        String result = "";
        result = "@ottomul of the PKK, in Stockholm.\\nAsli Biden artik main royel1968 Ye https:\\/\\/t.co\\/zq6, 🤔 so they \"I have no 😩 intention of letting\" Biden finallity &amp; support.\\n\\nMelania right. #theMAGAKING https:\\/\\/t.co\\/hX8u0odRZF";
//        result = "Absolutely perfect. Biden finally did one thing right. #theMAGAKING https:\\/\\/t.co\\/hX8u0odRZF ".replaceAll("https:\\W+\\w+\\W+\\w+\\W+\\w+", " ");
//        result = "RT @JoJoFromJerz: Jill Biden visited with the First Lady of Ukraine bearing flowers in an act of solidarity &amp; support.\\n\\nMelania Trump visit… ".replaceAll("(\\W\\Wn)+", " ");
        Pattern pattern = Pattern.compile("&amp;|^RT @\\w+:|https:\\W+\\w+\\W+\\w+\\W+\\w+|@\\w+");
        Matcher matcher = pattern.matcher(result);
        while (matcher.find()) {
            result = matcher.replaceAll("");
        }
        result = EmojiParser.removeAllEmojis(result);
        result = result.replace("\\n","");
        System.out.println(result);
//        {"text":"RT @GamebredFighter: Cubans aren’t crazy we just lived it already. Baby formula shortage is manufactured by the Biden administration do the…"},
//        {"text":"Absolutely perfect. Biden finally did one thing right. #theMAGAKING https:\/\/t.co\/hX8u0odRZF",},
//        {"text":"@DenicePeoples Things that make you go 🤔 I have yet to hear one Biden voter say “Uhm yeah, voting for Biden was a mistake”. But to continue to find excuses for the way this man ruins not only America, but truly this world as a whole is mind blowing to me 😩",},
//        {"text":"@asliaydintasbas Swedish International Minister Ann Linde hosted the officers of the so-called autonomous administration of the YPG\/DSG, often known as the Syrian department of the PKK, in Stockholm.\nAsli Biden artik main problem is they support PKK who kill Turkish people.",}
//        {"text":"RT @KamVTV: A judge ruled today Biden can't end Title 42, the Trump immigration health order that requires asylum seekers to wait outside t…",},
//        {"text":"@NancyLeeGrahn Dem Party Chair Jaimie Harrison said \"I have no intention of letting victory turn into complacency,because we have seen what happens when we don't invest everywhere. I plan 2 get 2 work 2 lift up the Biden-Harris admin, build on progress &amp; strengthen our party,\" How’s that going?",},
//        {"text":"@ottomul @sunnyek @Troyel1968 Yet Biden regime ignores laws as needed, so they will",},
//        {"text":"RT @FoxNews: Bezos calls on Disinformation Board to fact-check Biden's own inflation tweet https:\/\/t.co\/zq6xn4UaZs",},
//        {"text":"RT @JoJoFromJerz: Jill Biden visited with the First Lady of Ukraine bearing flowers in an act of solidarity &amp; support.\n\nMelania Trump visit…",},
//        {"text":"You did this MR BIDEN &amp; we voters will not forget.November 2022 is in 6.5 months...You are ruining our country.. https:\/\/t.co\/n3bM8RGEKH"}]
    }
}
