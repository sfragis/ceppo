Ceppo
=====

Ceppo is a Java library that helps you write clean logs.
Thanks to a simple domain specific grammar, log information is separated from its representation.

There're two main reasons that lead to Ceppo:

1. impose best-practices by getting rid of those awful log strings that pullate the source code when developers choose their own log formatting style rather than adhering to a predefined one
2. collect row log data using many backends at once
3. keep code clean

Ceppo is still a proof-of-concept and its grammar is yet very tight with a specific functional domain (telecommunication applications).

How to use it
-------------
Logging has never been so easy:
```java
final Ceppo log = Ceppo.get(this.getClass());

error().during("order fulfillment")
        .message("<root><msg>yyy</msg></root>")
        .to("system B")
        .failed("broken pipe")
        .log(log);
````
Above line of code creates an error log item that clearly indentifies when did the error happened (during the order fulfillment phase), why did it happened (because of a broken pipe) and what was the business context (an XML message being sent to system B).

What is important most here is that developers don't need to think about log formatting; they only have to care to what data is worth to be logged.

Of course the grammar has to be defined to be expressive enough for the application domain.