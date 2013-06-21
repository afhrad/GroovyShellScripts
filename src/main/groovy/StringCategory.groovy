

/**
 * Useful String extensions.
 *
 * @author sommermannt
 * @vsa.tsafe sommermannt:11.10.12:NICHT SICHER
 * @vsa.hist sommermannt:11.10.12: Erzeugt
 * @since v1.0
 */
class StringCategory {

    /**
     * Cuts the leading character (only one !).
     *
     * @param self String
     * @param character character to delete
     * @return trimmed string
     */
    static String cutLeadingChar(String self, String character) {
        if ( self.startsWith(character)) {
            return self.substring(1)
        }
        return self
    }

    /**
     * Cuts the leading string (only one !).
     *
     * @param self String
     * @param s string to delete
     * @return trimmed string
     */
    static String cutLeadingString(String self, String s) {
        if ( self.startsWith(s)) {
            return self.substring(s.length()-1)
        }
        return self
    }
    /**
     * Cuts the trailing string.
     *
     * @param self String
     * @param s string to delete
     * @return trimmed string
     */
    static String cutTrailingString(String self, String s) {
        if ( self.endsWith(s)) {
            return self.substring(0, self.length() - s.length())
        }
        return self
    }

    /**
     * Cuts the trailing character (only one !).
     *
     * @param self String
     * @param string character to delete
     * @return trimmed string
     */
    static String cutTrailingChar(String self, String string) {
        if ( self.endsWith(string)) {
            return self.substring(0, self.length()-1)
        }
        return self
    }

    /**
     * Checks if the String already ends with a trailing String and adds a trailing string if not.
     *
     * @param self String
     * @param string string
     * @return String
     */
    static String appendTrailingChar(String self, String string) {
        if ( !self.endsWith(string)) {
            self += string
        }
        return self
    }
    /**
     * Checks if the String already starts with a leading String and adds a leading string if not.
     *
     * @param self String
     * @param string string
     * @return String
     */
    static String appendLeadingChar(String self, String string) {
        if ( !self.endsWith(string)) {
            self = string + self
        }
        return self
    }

    /**
     * Checks if the string ends with a trailing string. If so, delete the trailing string.
     *
     * @param self String
     * @param trailingString string
     * @return String
     */
    static String stripTrailingChar(String self, String trailingString) {
        if ( self.endsWith(trailingString)) {
            self = self.substring(0, self.lastIndexOf(trailingString))
        }
        return self
    }
}
