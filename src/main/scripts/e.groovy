#!/usr/bin/env groovyclient
/* e <filename>: Edits a file with the default editor */

if (args.size() != 1) {
    println "e <filename>"
    System.exit 1
}
final File file = new File(args[0])

final defaultProgram = "sub"
final def programs = [
        "sub": ["xml", "groovy", "gradle", "properties", "java", "txt"],
        "open -a Preview": ["jpg", "png", "psd", "jpeg", "gif"]
]



if (file.exists() && file.isFile() && file.canRead()) {
    final extension = file.name.substring(file.name.lastIndexOf(".") + 1).toLowerCase()
    List array = new ArrayList()
    programs.each { k, v ->
        v.each { String ext ->
            if ( ext == extension) {
                array.addAll k.split(" ")
                array << file

                array.execute(null, file.parentFile)
                System.exit(0)
            }
        }
    }
    array.addAll defaultProgram.split(" ")
    array << file
    array.execute(null, file.parentFile)

}
