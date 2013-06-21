#!/usr/bin/env groovyclient
/* idea: Opens a IntelliJ project if there is any "ipr" file within the folder */

def files = new File(System.getProperty("user.dir")).listFiles(new FileFilter() {
    boolean accept(File file) {
        return !file.isDirectory() && file.canRead() && file.name.toLowerCase().endsWith("ipr")
    }
})

if ( files.size() == 1) {
    List array = new ArrayList()
    array << "open"
    array << files.first()
    array.execute(null, files.first().parentFile)
}
