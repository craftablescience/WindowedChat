package io.github.craftablescience.windowedchat.helpers;

import java.util.HashMap;


public class ChatStyleBlock {

    public static final HashMap<Character,String> colorCodes;
    public static final HashMap<Character,String> formatCodes;

    private String string;
    private char color;
    private boolean k;
    private boolean l;
    private boolean m;
    private boolean n;
    private boolean o;


    public ChatStyleBlock(String s, char color, boolean k, boolean l, boolean m, boolean n, boolean o) {
        this.string = s;
        this.color = color;
        this.k = k;
        this.l = l;
        this.m = m;
        this.n = n;
        this.o = o;
    }

    public String getHTMLMarkup() {
        StringBuilder output = new StringBuilder();

        if (color != '\0')
            output.append("<font " + colorCodes.get(color) + ">");
        if (l)
            output.append("<b>");
        if (m)
            output.append("<strike>");
        if (n)
            output.append("<u>");
        if (o)
            output.append("<i>");
        if (k)
            for (char ignored : this.string.toCharArray())
                output.append(formatCodes.get('k'));
        else
            output.append(this.string);
        if (o)
            output.append("</i>");
        if (n)
            output.append("</u>");
        if (m)
            output.append("</strike>");
        if (l)
            output.append("</b>");
        if (color != '\0')
            output.append("</font>");

        return output.toString();
    }

    static {
        colorCodes = new HashMap<>();
        colorCodes.put('0', "color=#000000;");
        colorCodes.put('1', "color=#0000AA;");
        colorCodes.put('2', "color=#00AA00;");
        colorCodes.put('3', "color=#00AAAA;");
        colorCodes.put('4', "color=#AA0000;");
        colorCodes.put('5', "color=#AA00AA;");
        colorCodes.put('6', "color=#FFAAAA;");
        colorCodes.put('7', "color=#AAAAAA;");
        colorCodes.put('8', "color=#555555;");
        colorCodes.put('9', "color=#5555FF;");
        colorCodes.put('a', "color=#55FF55;");
        colorCodes.put('b', "color=#55FFFF;");
        colorCodes.put('c', "color=#FF5555;");
        colorCodes.put('d', "color=#FF55FF;");
        colorCodes.put('e', "color=#FFFF55;");
        colorCodes.put('f', "color=#FFFFFF;");

        formatCodes = new HashMap<>();
        formatCodes.put('k', "\u25AE");
    }
}
