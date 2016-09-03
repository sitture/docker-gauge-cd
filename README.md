# docker-gauge-cd
[![Build Status](https://travis-ci.org/sitture/docker-gauge-cd.svg?branch=master)](https://travis-ci.org/sitture/docker-gauge-cd)

An example project to run gauge-java tests within a container when main web application is also a docker container.

The project uses this simple [docker-cd-seed](https://github.com/sitture/docker-cd-seed) application and runs `gauge-java` tests from a docker container.

## Prerequisite

* Install Docker from [here](https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-getting-started)

## Running

Build the docker Image

```bash
docker-compose build
```

Bring up Container

```bash
docker-compose up
```

## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request
