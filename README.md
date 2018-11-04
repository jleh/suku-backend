# suku-backend

Backend server for genealogy project.

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running 

Create postgres db
    
    docker run --name suku-db -d -p 5432:5432 postgres:alpine

Create `.lein-env` file and add local db url and authorization for updates

    {:database-url "postgresql://localhost:5432/postgres?user=postgres&password=postgres"
    :authorization "dGVzdDp0ZXN0"}

Run migrations with `lein migrate`

To start a web server for the application, run:

    lein ring server

For edits basic authentication header should be sent. Above configuration sets credentials to test:test.

## License

MIT
Copyright © 2018 Juuso Lehtinen
