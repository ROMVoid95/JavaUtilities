# utils
[![](https://jitpack.io/v/ROMVoid95/utils.svg)](https://jitpack.io/#ROMVoid95/utils)
## Introduction

- I created this utility library after running Java Application inside a Linux based VPS and noticed that in certain circumstances using Thread classes would skyrocket the JVM's CUP useage to 100%. 
- the package at this moment is used for Async threading operations to replace the need of extending a class to Thread, instead implementing a Async operation that can be handled to any particular needs.
- as of right now the Async tool is only tested in production to update Discord Bot Playing status every 3 minutes.

## Getting started
You can either download the jar from the realeases tab or use the following to use in your project

**Maven**
```
<repository>
	<id>jitpack.io</id>
	<url>https://jitpack.io</url>
</repository>
```
```
<dependency>
	<groupId>com.github.ROMVoid95</groupId>
	<artifactId>utils</artifactId>
	<version>VERSION</version>
</dependency>
```

**Gradle**
```
repositories {
	maven { url 'https://jitpack.io' }
}
```
```
dependencies {
	implementation 'com.github.ROMVoid95:utils:VERSION'
}
```
## License
This project is released under the revised MIT License