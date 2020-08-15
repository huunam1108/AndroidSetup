tasks.register("distributeDevelopRelease", Exec::class) {
    workingDir = rootDir
    commandLine = "./gradlew assembleDevelopRelease appDistributionUploadDevelopRelease".split(" ")
}
// Add similar task for production, staging... if have
