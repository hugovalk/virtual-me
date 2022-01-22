# Virtual Me

Virtual Me is a playground application that implements a chat bot with some
intelligence. It is fully written in Clojure and ClojureScript.

## Running the application
This project uses Clojure CLI and Deps. 
Clone the project. Then run:

    $ clj -M:run

It will start the application on https://localhost:8080

If you run:

    $ clj -M:fig -b dev -r

It will reload server and client code whenever you make a change.

To run unit tests continuously:

    $ clj -M:autotest
    
To run all tests (including integration tests):

    $ clj -M:test

To compile CSS:

    $ clj -M:garden-compile

There are also scripts you can run, like downloading NLP models. Check `deps.edn`. 

## Creating a package

TODO. 

## License

Copyright © 2017 Hugo Valk

Distributed under the Eclipse Public License either version 1.0 or any later version.
