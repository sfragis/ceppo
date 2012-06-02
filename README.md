Ceppo
=====

Ceppo is a Java library that helps you write clean logs.
Thanks to a simple domain specific grammar, log information is separated from its representation.

There're two main reasons that lead to Ceppo:

1. the need of getting rid of those awful log strings that pullate the source code when developers decide to use their own log style rather than adhering to a predefined one
2. the need to collect log row data usign many backends at once

Ceppo is still a proof-of-concept and its grammar is yet very tight with a specific functional domain (telecommunication applications).

How to use it
-------------
Logging has never been so easy:
```java
final Ceppo log = Ceppo.get(this.getClass());

error().during("order fulfillment").message("<root><msg>yyy</msg></root>").to("system B").failed("broken pipe").log(log);
````
Above line will record an error log item about a message not being delivered to system B because of broken pipe error during the order fulfillment phase.
