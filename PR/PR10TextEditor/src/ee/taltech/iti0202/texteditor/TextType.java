package ee.taltech.iti0202.texteditor;

/**
 * Text type values and their according strategies
 * Plain - uses no formatting
 * Screaming - UppercaseFormatter
 * Title - TitleCaseFormatter
 * CamelCase - CamelCaseFormatter
 * Binary - BinaryFormatter
 */
public enum TextType {

    PLAIN,
    SCREAMING,
    TITLE,
    CAMELCASE,
    BINARY
}
