#!/usr/bin/env groovyclient
/* changecvsroot [<CVSROOT>]: changes all CVS Root Entries in all "Root" files to the new CVSROOT variable */
import groovy.io.FileType

String newroot = null

if (args.size() != 1) {
    println "changecvsroot <CVSROOT>"
    newroot = System.getenv("CVSROOT")
} else {
    newroot = args[0]
}


def list = []

//
// Collect all CVS folders.
//
new File(".").eachFileRecurse(FileType.DIRECTORIES) { File file ->
    if (file.name == "CVS") {
        list << file
        println file.absolutePath
    }
}

if (ConsoleUtils.promptYesOrNo("Change all entries to new CVSROOT=$newroot ?", false)) {
    //
    // Write new CVSHOME
    //
    list.each { File file ->
        File rootFile = new File(file, "Root")
        rootFile.delete()
        rootFile << newroot
        println("Changing $rootFile.absolutePath")
    }
}
