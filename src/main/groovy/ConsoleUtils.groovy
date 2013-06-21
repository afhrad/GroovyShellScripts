class ConsoleUtils {

    /** Escape Sequence */
    private static String escape = ((char) 27) as String

    /** Escape sequences for terminal and console: switch on GREEN */
    private static final String YELLOW_ON = escape + "[22;33m"

    /** Escape sequences for terminal and console: switch on RED */
    private static final String RED_ON = escape + "[22;31m"

    /** Escape sequences for terminal and console: switch on GREEN */
    private static final String GREEN_ON = escape + "[22;32m"

    /** Escape sequences for terminal and console: switch on BLUE */
    private static final String BLUE_ON = escape + "[22;34m"

    /** Escape sequences for terminal and console: switch on CYAN */
    private static final String CYAN_ON = escape + "[22;36m"

    /** Escape sequences for terminal and console: switch on GRAY */
    private static final String GRAY_ON = escape + "[22;37m"

    /** Escape sequences for terminal and console: switch off all */
    private static final String ESC_OFF = escape + "[0m"

    /** System line separator character(s) */
    static final String lineSep = System.getProperty("line.separator")

    /** Input prompt */
    static final String inputPrompt = "${lineSep}??>"

    /**
     * Renders a line in the default Gradle Yellow style.
     *
     * @param line Line
     * @return Line with Escape Sequences
     */
    static String highlight(String line) {
        return YELLOW_ON + line + ESC_OFF
    }

    /**
     * Renders a line in the default Gradle Green style.
     *
     * @param line Line
     * @return Line with Escape Sequences
     */
    static String green(String line) {
        return GREEN_ON + line + ESC_OFF
    }

    /**
     * Renders a line in the blue style.
     *
     * @param line Line
     * @return Line with Escape Sequences
     */
    static String blue(String line) {
        return BLUE_ON + line + ESC_OFF
    }

    /**
     * Renders a line in the gray style.
     *
     * @param line Line
     * @return Line with Escape Sequences
     */
    static String gray(String line) {
        return GRAY_ON + line + ESC_OFF
    }

    /**
     * Renders a line in the default Cyan style.
     *
     * @param line Line
     * @return Line with Escape Sequences
     */
    static String cyan(String line) {
        return CYAN_ON + line + ESC_OFF
    }

    /**
     * Renders a line in the default Red style.
     *
     * @param line Line
     * @return Line with Escape Sequences
     */
    static String error(String line) {
        return RED_ON + line + ESC_OFF
    }

    static String readLine(String format, Object... args) throws IOException {
        if (System.console() != null) {
            return System.console().readLine(format, args)
        }
        System.out.print(String.format(format, args))
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
        return reader.readLine()
    }
    /**
     * Returns a prompt with a message and reads a string
     *
     * @param message prompt message
     * @param explanation if not null the explanation for the input will be printed above the prompt
     * @param defaultValue if not null a default value will be printed in square brackets
     * @return String or null
     */
    static String prompt(String message, String explanation = null, String defaultValue = null) {
        printExplanation(explanation)
        if (defaultValue) {
            return readLine("${inputPrompt} ${message} [${defaultValue}] ") ?: defaultValue
        }
        return readLine("${inputPrompt} ${message} ") ?: defaultValue
    }

    /**
     * Returns a prompt with a message and reads a string
     *
     * @param message prompt message
     * @param explanation if not null the explanation for the input will be printed above the prompt
     * @param defaultValue if not null a default value will be printed in square brackets
     * @return String or null
     */
    static String promptPassword(String message, String explanation = null, String defaultValue = null) {
        printExplanation(explanation)
        char[] password = System.console().readPassword("${inputPrompt} ${message} ")
        if (password != null) {
            return new String(password)
        } else {
            return defaultValue
        }
    }

    /**
     * Returns an index value for the selection of a list.
     * @param message prompt message
     * @param explanation if not null the explanation for the input will be printed above the prompt
     * @param defaultValue if not null a default value will be printed in square brackets
     * @param options List of values
     * @return index value
     */
    static int promptOptions(String message, String explanation = null, int defaultValue, List options = []) {

        String consoleMessage = "${inputPrompt} ${message}"
        printExplanation(explanation)
        consoleMessage += "${lineSep}    Pick an option ${1..options.size()}"
        options.eachWithIndex { option, index ->
            consoleMessage += "${lineSep}     (${index + 1}): ${option}"
        }
        if (defaultValue) {
            consoleMessage += "${inputPrompt} [${defaultValue}] "
        } else {
            consoleMessage += "${inputPrompt} "
        }
        try {
            def range = 0..options.size() - 1
            int choice = Integer.parseInt(readLine(consoleMessage) ?: "${defaultValue}")
            if (choice == 0) {
                throw new Exception('No option provided')
            }
            choice--
            if (range.containsWithinBounds(choice)) {
                return choice
            } else {
                throw new IllegalArgumentException('Option is not valid.')
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException('Option is not valid.', e)
        }
    }

    /**
     * If not null, prints the explanation for the to be entered value above the prompt.
     *
     * @param explanation Explanation
     */
    static void printExplanation(String explanation) {
        if (explanation != null) {
            def lines = explanation.split("\n")
            lines.each {
                String line = cyan(it)
                System.out.print("\n    $line")
            }
        }
    }

    /**
     * Returns a boolean value for a prompt.
     *
     * @param message prompt message
     * @param explanation if not null the explanation for the input will be printed above the prompt
     * @param defaultValue if not null a default value will be printed in square brackets
     * @return selected value
     */
    static boolean promptYesOrNo(String message, boolean defaultValue = false, String explanation = null) {
        def defaultStr = defaultValue ? 'Y' : 'n'
        printExplanation(explanation)
        String consoleVal = readLine("${inputPrompt} ${message} (Y|n) [${defaultStr}] ")
        if (consoleVal) {
            return consoleVal.toLowerCase().startsWith('y')
        }
        return defaultValue
    }

    /**
     * Method to fill a string with spaces to make a list of string with an equal length.
     *
     * @param s String
     * @param len length
     * @return filled String
     */
    static private String rightSpace(String s, int len) {
        return s + (" " * (len - s.length()))
    }

    /**
     * Prints a confirmation screen and awaits a confirmation (y/n).
     *
     * @param options Map with "key/values".
     * @return true if confirmed
     */
    static boolean confirmation(def options = [:]) {
        int maxKeyLen = 0
        options.keySet().each { maxKeyLen = Math.max(maxKeyLen, it.length()) }
        maxKeyLen += 2
        System.out.print("\n-------------[ Confirmation ]-------------------------------------\n")
        options.keySet().each {
            System.out.print(highlight(rightSpace(it, maxKeyLen) + ": ") + cyan(options[it]) + "\n")
        }
        return promptYesOrNo("Continue", true)
    }
}
