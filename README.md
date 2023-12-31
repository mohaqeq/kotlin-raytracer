# Kotlin Raytracer
A simple raytracer in Kotlin based on the book series
[Raytracing in a weekend](https://raytracing.github.io/).

This is a Gradle project, and you can run it with `./gradlew run`.
The result will be saved in the file `image.ppm`.

Final code for the first book, saved with
[v1.0 tag](https://github.com/mohaqeq/kotlin-raytracer/releases/tag/v1.0).

![Result of first book](https://github.com/mohaqeq/kotlin-raytracer/blob/8445ffff596c9a8a97093838ba22b4fc34573e8b/FirstBookFinalScene.png)

I've used a `FixedThreadPool` executor to speed up the rendering process. The number of threads depends on the number of CPUs available.
