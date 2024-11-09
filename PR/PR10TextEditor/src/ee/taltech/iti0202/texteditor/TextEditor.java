package ee.taltech.iti0202.texteditor;

import ee.taltech.iti0202.texteditor.textformatter.*;

import java.util.*;

/**
 * Realises the text editor and edits the text according to the users wishes.
 * Entered text must be convertible both ways.
 * At every moment the text active must be accessible and must provide history of adding text.
 */
public class TextEditor {

    private List<String> textEditor;
    private TextFormatter textFormatter;
    private Stack<String> history;
    private Stack<String> undoHistory;
    private Stack<String> redoHistory;

    public TextEditor() {
        this.textEditor = new ArrayList<>();
        this.history = new Stack<>();
        this.redoHistory = new Stack<>();
        this.undoHistory = new Stack<>();
    }

    /**
     * Adds text with currently assigned strategy to the text editor.
     * If no strategy is chosen, adds text to editor without format.
     * @param text to be added to editor.
     */
    public void addText(String text) {
        if (text == null) {
            return;
        }
        if (textFormatter != null) {
            String formattedText = textFormatter.format(text);
            textEditor.add(formattedText);
            if (!redoHistory.isEmpty()) {
                history.pop();
                redoHistory.clear();
            }
            history.add(formattedText);
        } else {
            textEditor.add(text);
            if (!redoHistory.isEmpty()) {
                history.pop();
                redoHistory.clear();
            }
            history.add(text);
        }
    }

    /**
     * Adds text with chosen type to text editor.
     * Strategy assigned with type is still active.
     * @param text to add.
     * @param type of text.
     */
    public void addText(String text, TextType type) {
        if (text == null) {
            return;
        }
        if (type != TextType.PLAIN) {
            setStrategy(type);
            textEditor.add(textFormatter.format(text));
            if (!redoHistory.isEmpty()) {
                history.pop();
                redoHistory.clear();
            }
            history.add(textFormatter.format(text));
        } else {
            textEditor.add(text);
            if (!redoHistory.isEmpty()) {
                history.pop();
                redoHistory.clear();
            }
            history.add(text);
        }
    }

    /**
     * Method that returns whole text, that the text editor has.
     * Adds a space to returned entries put in the text editor, unless:
     *      text begins on a new line or last bit of text ends with a space.
     *
     * @return text
     */
    public String getCurrentText() {
        StringBuilder currentText = new StringBuilder();
        boolean isNewLine = true;
        for (String entry : textEditor) {
            if (isNewLine) {
                isNewLine = false;
            } else {
                if (!currentText.isEmpty() && !entry.startsWith("\n")
                        && !Character.isWhitespace(currentText.charAt(currentText.length() - 1))) {
                    currentText.append(" ");
                }
            }
            currentText.append(entry);
            isNewLine = entry.endsWith("\n");

        }
        return currentText.toString();
    }

    /**
     * Takes the last word in history and returns active text.
     * If after undo a new word is added with method addText(), then the previous history is deleted.
     *
     * @return whole text in text editor.
     */
    public String undo() {
        try {
            String toUndo = history.get(history.size() - 1);
            redoHistory.push(toUndo);
            textEditor.remove(toUndo);
            return getCurrentText();
        } catch (EmptyStackException e) {
            return "";
        }
    }

    /**
     * Recovers the last word that was undone and returns the active text.
     * @return whole text in text editor.
     */
    public String redo() {
        try {
            String toRedo = redoHistory.pop();
            undoHistory.push(toRedo);
            textEditor.add(toRedo);
            return getCurrentText();
        } catch (EmptyStackException e) {
            return "";
        }
    }

    /**
     * Clears the text editor.
     */
    public void clear() {
        textEditor.clear();
        history.clear();
        //redoHistory.clear();
        //undoHistory.clear();
    }

    /**
     * Assigns a strategy used in the text editor based on the type of text.
     * @param type of text.
     */
    public void setStrategy(TextType type) {
        if (type == TextType.TITLE) {
            this.textFormatter = new TitleCaseFormatter();
        }
        if (type == TextType.CAMELCASE) {
            this.textFormatter = new CamelCaseFormatter();
        }
        if (type == TextType.SCREAMING) {
            this.textFormatter = new UppercaseFormatter();
        }
        if (type == TextType.BINARY) {
            this.textFormatter = new BinaryFormatter();
        }
        if (type == TextType.PLAIN) {
            this.textFormatter = null;
        }
    }

    /**
     * Returns the history of words that have been added.
     * @return collection of added words.
     */
    public Collection<String> getHistory() {
        return Collections.unmodifiableList(history);
    }

    /**
     * @return a strategy that is in use.
     */
    public TextFormatter getStrategy() {
        return textFormatter;
    }
}
