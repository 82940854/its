package com.base.util;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.*;

public class HtmlToText extends HTMLEditorKit.ParserCallback {

    private static HtmlToText html2Text = new HtmlToText();

    StringBuffer s;

    public HtmlToText() {
    }

    public void parse(InputStream iin,String charse) throws IOException {

        Reader in = new InputStreamReader(iin,charse);
        s = new StringBuffer();
        ParserDelegator delegator = new ParserDelegator();
        // the third parameter is TRUE to ignore charset directive
        delegator.parse(in, this, Boolean.TRUE);
        in.close();
    }

    public void handleText(char[] text, int pos) {
        s.append(text);
    }

    public StringBuffer getText() {
        return s;
    }

    public static StringBuffer getContent(InputStream iin,String charse) {
        try {
            html2Text.parse(iin,charse);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return html2Text.getText();
    }
}