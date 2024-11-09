import ee.taltech.iti0202.texteditor.TextEditor;
import ee.taltech.iti0202.texteditor.textformatter.BinaryFormatter;
import ee.taltech.iti0202.texteditor.textformatter.CamelCaseFormatter;

import static ee.taltech.iti0202.texteditor.TextType.*;

class Main {
    public static void main(String[] args) {
        String text = "Hello, world!\n";
        BinaryFormatter binary = new BinaryFormatter();
        String binaryRepresentation = binary.format(text);
        //System.out.println(binaryRepresentation); // 0100100001100101011011000110110001101111

        TextEditor textEditor = new TextEditor();
        /*textEditor.addText("Entry1", TITLE);
        textEditor.addText("Entry2", TITLE);
        System.out.println(textEditor.getHistory()); // [Entry1, Entry2]    OK
        textEditor.undo();
        System.out.println(textEditor.getCurrentText()); // Entry1          OK
        System.out.println(textEditor.getHistory()); // [Entry1, Entry2]    OK
        textEditor.addText("Entry3", TITLE);
        System.out.println(textEditor.getHistory()); // [Entry1, Entry3]*/


        textEditor.addText("diary of a programmer", TITLE);
        textEditor.addText("Day1: Finally started with my new project today.", PLAIN);
        textEditor.addText("I have \nbeen working on my camelcase.\n", CAMELCASE);
        System.out.println(textEditor.getCurrentText());
        // Diary of a Programmer
        // Day1: Finally started with my new project today. iHaveBeenWorkingOnMyCamelcase.

        //textEditor.addText(null, BINARY);

        //textEditor.addText("", TITLE);
        //textEditor.addText("a story of a tired programmer called Or!");

        //textEditor.addText("Once more\n", BINARY);
        textEditor.addText("don't night!", CAMELCASE);
        textEditor.addText("S1de!\n", CAMELCASE);
        textEditor.addText("a story of a tired programmer called Or!\n", TITLE);
        textEditor.addText("Day2: ", PLAIN);
        textEditor.addText("i'm losing my mind, this bug keeps avoiding meeeeeeeee.\n", SCREAMING);
        textEditor.addText("Day3: ", PLAIN);
        textEditor.addText("help me\n", BINARY);
        System.out.println(textEditor.getCurrentText());
        // Diary of a Programmer
        // Day1: Finally started with my new project today. iHaveBeenWorkingOnMyCamelcase.
        // Day2: I'M LOSING MY MIND, THIS BUG KEEPS AVOIDING MEEEEEEEEE. 01101000011001010110110001110000001000000110110101100101
        // Day3: 01101000011001010110110001110000001000000110110101100101
        System.out.println(textEditor.undo());
        System.out.println();
        // Diary of a Programmer
        // Day1: Finally started with my new project today. iHaveBeenWorkingOnMyCamelcase.
        // Day2: I'M LOSING MY MIND, THIS BUG KEEPS AVOIDING MEEEEEEEEE.
        // Day3:
        textEditor.addText("Fixed the bug, it was a typo.");
        System.out.println(textEditor.getCurrentText());
        System.out.println();
        // Diary of a Programmer
        // Day1: Finally started with my new project today. iHaveBeenWorkingOnMyCamelcase.
        // Day2: I'M LOSING MY MIND, THIS BUG KEEPS AVOIDING MEEEEEEEEE.
        // Day3: 0100011001101001011110000110010101100100001000000111010001101000011001010010000001100010011101010110011100101100001000000110100101110100001000000111011101100001011100110010000001100001001000000111010001111001011100000110111100101110

        textEditor.undo();
        textEditor.setStrategy(PLAIN);
        textEditor.addText("Fixed the bug, it was a typo.");
        System.out.println(textEditor.getCurrentText());
        System.out.println();
        // Diary of a Programmer
        // Day1: Finally started with my new project today. iHaveBeenWorkingOnMyCamelcase.
        // Day2: I'M LOSING MY MIND, THIS BUG KEEPS AVOIDING MEEEEEEEEE.
        // Day3: Fixed the bug, it was a typo.

        textEditor.clear();
        System.out.println(textEditor.redo().isEmpty()); // true
    }
}