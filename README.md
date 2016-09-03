# docker-gauge-cd
[![Build Status](https://travis-ci.org/sitture/docker-gauge-cd.svg?branch=master)](https://travis-ci.org/sitture/docker-gauge-cd)

An example project to run gauge-java tests within a container when main web application is also a docker container.

The project uses this simple [docker-cd-seed](https://github.com/sitture/docker-cd-seed) application and runs `gauge-java` tests from a docker container.

## Prerequisite

* Install Docker from [here](https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-getting-started)

## Running

Run the following to bring up the both `web` and `sut` containers (in background).

```bash
docker-compose build
docker-compose up -d
```

The above will bring up both the `web` app at `http://localhost:8080` and `sut` will compile the maven based gauge project and exit.

## Gauge Container

To build/run the gauge container `sut` itself.

```bash
docker-compose build sut
```

Run the following to run gauge tests from maven. The entry point command for `sut` container is `mvn`.

```bash
docker-compose run sut test
```

The above is equivalent to running `maven test`

## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request
