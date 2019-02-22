# Virtual Me

Virtual Me is a playground application that implements a chat bot with some
intelligence. It is fully written in Clojure and ClojureScript.

## Running the application

Clone the project. Then run:

    $ lein run

It will start the application on https://localhost:8080

If you run:

    $ lein figwheel

It will reload server and client code whenever you make a change.

## Usage

If you want to build the app to run it, first run:

    $ lein uberjar

This will produce a standalone jar that can be run with:

    $ java -jar virtual-me-0.1.0-standalone.jar


## License

Copyright © 2017 Hugo Valk

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
