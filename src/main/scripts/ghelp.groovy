#!/usr/bin/env groovyclient
/* ghelp: gives an overview of all the installed groovy scripts */

final File distDir = new File(System.properties["user.home"] + "/bin/groovy")
final files = distDir.listFiles(new FileFilter() {
    boolean accept(File file) {
        return !file.isDirectory()
    }
})

println ""
println "List of installed Groovy Shell Scripts"
println ""
files.each { File f ->
    String text = f.text
    def lines = text.split("\n")
    if ( lines.length > 2) {
        String commentLine = lines[1]
        if ( commentLine.trim().startsWith("/*") && commentLine.trim().endsWith("*/")) {
            use (StringCategory) {
                println commentLine.trim().cutLeadingString("/*").cutTrailingString("*/")
                println ""
            }
        }
    }
}
