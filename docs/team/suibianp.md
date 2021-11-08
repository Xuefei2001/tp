# Hu Jialun - Project Portfolio Page

## Product Overview - SchedUrMods <a id="scrollToHere"></a>

**SchedUrMods** is a highly organic blend of conventional calendar applications and the official NUS timetable service, NUSMods. It enables immense possibilities for command-line interface (CLI) users at NUS to manage the entire time planning at the convenience of their fingertips.

I am honoured to have my contributions in this project. Please find below some descriptions of my contributions, and also see [RepoSense](https://nus-cs2113-ay2122s1.github.io/tp-dashboard/?search=AY2122S1-CS2113T-W13-3&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-25&tabOpen=true&tabType=authorship&tabAuthor=SuibianP&tabRepo=AY2122S1-CS2113T-W13-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false) for the exact parts of my contributions.

## Major functionality
### NUSMods API interfacing

As a core flagship feature of our product, the ability to interface the NUSMods API, parse its response, be it from local cache or remote server, is crucial. Implemented the `NusModsParser` and `Semester` class forming the `nusmods` package  to efficiently handle various situations.

### Lesson type

Unlike most calendar applications, lessons with their unique characteristics tightly bound to NUS academic timetable are treated as primitive types in this project. Implemented the `Lesson` class to seamlessly integrate with the aforementioned interface.

Nonetheless, with customisation capabilities in mind, the lesson type was made modifiable via standard edit command for the user to have greater control over them.

### Attaching link to tasks

Zoom classes and web lecture recordings have been prevalent at NUS for a relatively long time, and it is recognised as an annoyance to find those links all over the place, Hence, this project also acts as a link hub by allowing users to add link to tasks. 

Two classes are constructed for this purpose, namely `command.BrowseCommand` and `utility.ExternalHelper`. This feature is implemented using OS capabilities so that protocol supports are as abundant as possible.

## Documentation
### User Guide

Added comprehensive description for `module` and `browse` commands, together with common precautions that users are advised to take.

### Developer Guide

Added documentation for the `nusmods` package to complement JavaDoc inside the relevant source files.

## Testing

### Linux

As a regular Linux user, executed unit tests as well as functional test on Manjaro Linux to ensure platform compatibility. Several OS-specific problems were discovered and subsequently rectified or documented, such as the user guide notice on external error messages and the EOF behaviour which is widely assumed by Linux users.

### Test cases

Wrote exceptional test cases simulating network outage and file manipulation failure to mimic extreme situations that may arise on users' clients.

### Code auditing

While JVM prevents common attacks, bugs can compromise the application and also degrade user experience. Reviewed others' code for earlier discovery of uncaught exceptions and other vulnerabilities.

## Team task

Set up the team GitHub organisation and tp repository. Created team PR to module repository.

Pulled down entire nusmods data for AY20/21 to conform to the minimal network requirement.
```shell
curl "https://api.nusmods.com/v2/2021-2022/moduleList.json" |
jq -r '"https://api.nusmods.com/v2/2021-2022/modules/" + .[].moduleCode + ".json"' |
wget -i - -nc  2>&1 >> log
```

Tested dead links for the team GitHub page.
```shell
LANG=C wget --spider -r -nv 'https://ay2122s1-cs2113t-w13-3.github.io/tp/' 2>&1 |
grep broken -A 2
```

## Unmerged enhancements
### Use annotations and reflection for commands
#### Benefits

Annotations allow further conformance to the open-close principle by keeping everything task-related inside its class and eliminate the need to modify an overall class each time a new type is added.

Moreover, it also allows further third-party plugin development by taking advantage of the dynamic linking nature.

As such, a functional commit was implemented based upon this concept. There was also an attempt to integrate annotation proprocessors and add that to Gradle to guarantee a certain level of compile-time check on the annotations.

##### Considerations

This proposal is declined for the consideration of stability and maintainability, since runtime reflection is essentially bypassing compile time checks, which entails too much work for the scope of CS2113T.
