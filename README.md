# suku-backend

FIXME

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

Create postgres db
    
    docker run --name suku-db -d -p 5432:5432 postgres:alpine

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2018 FIXME
