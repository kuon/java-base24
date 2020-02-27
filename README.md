# Base24 encoder/decoder for Java

This library is an encoder/decoder for the
[Base24 encoding](https://www.kuon.ch/post/2020-02-27-base24/) written in Kotlin.

## Usage


When using gradle (Kotlin DSL):

Add the repository:

```kotlin

repositories {
    maven {
        setUrl("https://dl.bintray.com/kuon/java/")
    }
}

```

Add the following dependencies:

```kotlin
dependencies {
    // ...
    implementation("ch.kuon.commons:base24:0.1.0")
    // ...
}
```

Then the API is very simple:

```kotlin

object ch.kuon.commons.Base24

/**
 * Encode bytes to a string using base24
 * @param data A byte array. Length must be multiple of 4.
 * @return The encoded string
 */
fun encode24(data: ByteArray): String

/**
 * Decode a string to byete arrary using base24
 * @param data A string encoded un base24. Length must be multiple of 7
 * @return The decoded bytes
 */
fun decode24(data: String): ByteArray
```


### Example

```kotlin
import ch.kuon.commons.Base24.encode24
import ch.kuon.commons.Base24.decode24

val bytes = byteArrayOf(1, 2, 3, 4)
val myString = encode24(bytes)

myString == "ZCCYBZB" // true

val decodedBytes = decode24("2Z42F6A")

decodedBytes contentEquals byteArrayOf(0x22, 0x55, 0x33, 0x11) // true
```

## License

Licensed under either of

 * Apache License, Version 2.0
   ([LICENSE-APACHE](LICENSE-APACHE) or http://www.apache.org/licenses/LICENSE-2.0)
 * MIT license
   ([LICENSE-MIT](LICENSE-MIT) or http://opensource.org/licenses/MIT)

at your option.

## Contribution

Unless you explicitly state otherwise, any contribution intentionally submitted
for inclusion in the work by you, as defined in the Apache-2.0 license, shall be
dual licensed as above, without any additional terms or conditions.

