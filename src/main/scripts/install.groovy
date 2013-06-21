#!/usr/bin/env groovyclient
/* install <version-number>: Downloads and installs the groovy version from codehaus */

if ( args.size() != 1){
	println "start with groovy install <version-number>"
	System.exit 1
}

final version = args[0]
final installDir = "/Users/sommermannt/Dev/"
final base = "http://dist.groovy.codehaus.org/distributions/"
final urls = [
	"groovy-binary-${version}.zip",
	"groovy-src-${version}.zip",
	"groovy-docs-${version}.zip",
]

final ant = new AntBuilder()

urls.each { file ->
	ant.get		src: base + file,		dest: installDir + file, verbose:true
	ant.unzip	src: installDir + file,	dest: installDir
	ant.delete	file: installDir + file
}

new File(installDir + "groovy-${version}/bin/").eachFile {
	if (! it.name.contains(".")) {
		ant.chmod file: it, perm: "775"
	}
}