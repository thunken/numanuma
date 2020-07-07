# 🛠 numanuma

`numanuma` provides utility methods for working with Java enumerated types, including enum sets and other bit vectors.


## Latest release

[![Release](https://jitpack.io/v/thunken/numanuma.svg?style=flat-square)](https://github.com/thunken/numanuma/releases)

To add a dependency on this project using Gradle, Maven, sbt, or Leiningen, we recommend using [JitPack](https://jitpack.io/#thunken/numanuma/1.1.2). The Maven group ID is `com.github.thunken`, and the artifact ID is `numanuma`.

For example, for Maven, first add the JitPack repository to your build file:
```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

And then add the dependency:
```xml
	<dependency>
	    <groupId>com.github.thunken</groupId>
	    <artifactId>numanuma</artifactId>
	    <version>1.1.2</version>
	</dependency>
```

## Documentation

* Javadoc: https://thunken.github.io/numanuma/
  * Note: the current Javadoc for this project is incomplete. We rely on [Lombok](https://projectlombok.org/) to generate boilerplate code, and Lombok does not plug into Javadoc. Generated methods and constructors are not included, and the Javadoc for other methods and constructors may be incomplete. See [delombok](https://projectlombok.org/features/delombok) and [numanuma#1](https://github.com/thunken/numanuma/issues/1) for more information.
